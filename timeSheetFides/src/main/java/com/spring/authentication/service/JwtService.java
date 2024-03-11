package com.spring.authentication.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    /*
    Questa classe si occupa della gestione del token nella nostra applicazione, richiameremo questa classe ed i metodi contenuti in essa tutte le volte che dovremo
    generare, controllare, utilizzare o manipolare il JWT,

    */


    private final String SECRET_KEY; //la variabile è dichiarata globalmente nell'application.properties, sarà obbligatorio inserirlo nel gitIgnore(aggiungere un application.properties.template da poter inserire nel progetto con le istruzioni per la sua compilazione)

    private JwtService(@Value("${jwt.secret-key}") String secretKey) {
        this.SECRET_KEY = secretKey;
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);//operatore di riferimento ad un metodo, letteralmente prendi getSubject della classe claims ed usalo
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claim = extractAllClaims(token);
        return claimsResolver.apply(claim);
    }

    public String generateToken(UserDetails userDetails) {//utilizzo questo metodo quando genero un token senza informazioni aggiuntive
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(//overload del metodo precedente modificandone la firma
                                Map<String, Object> extraClaims,
                                UserDetails userDetails
    ) {
        return Jwts.builder()//crea un oggetto di tipo builder per costruire il token
                .setClaims(extraClaims)//aggiungiamo le informazioni aggiuntive al token
                .setSubject(userDetails.getUsername())//inseriamo le informazioni dell'utente nel token
                .setIssuedAt(new Date(System.currentTimeMillis()))//inseriamo il timestamp (momento esatto di generazione del token)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))//inseriamo la data di scadenza del token (1000ms * 60 = 1h * 24 = 24h)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)//firmiamo il token utilizzando la chiave segreta con l'algoritmo di firma hmac sha265
                .compact();//trasformiamo il token in una stringa (vedi come sotto)

    }


    /*per parsing intendiamo tutto il processo di lettura e di "traduzione" del token, quindi parseare un token vuol dire trasformarlo da questo:

    eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9. // header
    eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ. //payload
    SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c firma
    a questo:

{ //Header
  "alg": "HS256",
  "typ": "JWT"
}

{ //Payload
  "sub": "1234567890",
  "name": "John Doe",
  "iat": 1516239022
}

SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c //questa firma viene generata tramite una chiave segreta e viene aggiunta
in coda al token per garantirne l'autenticità
    */

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !IsTokenExpired(token);
    }

    private boolean IsTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String Token) {

        return Jwts.parserBuilder().//crea un nuovo oggetto per il parsing del token
                setSigningKey(getSignInKey()).//imposta la chiave di firma utilizzata per verificare l'autenticità del token/get restituisce chiave di firma
                build().//costruisce il parserJwt con la chiave di firma sopraspecificata
                parseClaimsJws(Token).// parsea il token, in questa fase se il token è compromesso viene restituito un errore
                getBody();//ritorna il token "tradotto" trasformato in un oggetto di tipo claims (contiene i dati dell'utente, ruolo e scadenza del token)
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);//decodifica la chiave segreta da base64 in seguenza di byte
        return Keys.hmacShaKeyFor(keyBytes); //crea una chiave segreta HMAC codificata con l'algoritmo SHA
    }
}
