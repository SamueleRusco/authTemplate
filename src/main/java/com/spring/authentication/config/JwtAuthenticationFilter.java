package com.spring.authentication.config;

import com.spring.authentication.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor // jwtService sotto è senza costruttore, con questa annotation di lombok viene generato automaticamente
class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailService;
    private final JwtService jwtService;
// il costruttore andrebbe dopo questa dichiarazione


    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String email;

        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }

        //mettiamo 7 su authHeader.substring() perché avremo sicuramente un token tipo: Bearer 123sonounbearertoken456
        //quindi "Bearer " (piu lo spazio) conterà 7 (partendo da 0) ed a noi interessa utilizzare solo il raw token
        jwt = authHeader.substring(7);
        email = jwtService.extractUsername(jwt);
        //se esiste la mail ma l'autenticazione è nulla (token scaduto ad esempio)
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            //recupero userDetail fetchando per la mail inserita
            UserDetails userDetails = this.userDetailService.loadUserByUsername(email);//recupero oggetto utente tramite mail (password, email, permessi ecceecc..)
            if (jwtService.isTokenValid(jwt, userDetails)) {//verifico che il token fornito è valido per i dettagli dell'utente

                //creo oggetto UsernamePasswordAuthenticationToken con:
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,//dettagli utente (mail, password)
                        null,//nessuna credenziale d'acceso
                        userDetails.getAuthorities()//le autorizzazioni dell'utente
                );
                authToken.setDetails(//configuro l'oggetto sopra creato con i dettagli ottenuti dalla richiesta http
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken); //viene autorizzato l'oggetto utente nel contesto di sicurezza
            }
        }
        filterChain.doFilter(request, response);


    }
}
