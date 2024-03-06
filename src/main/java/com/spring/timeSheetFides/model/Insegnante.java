package com.spring.timeSheetFides.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity(name = "insegnante")
public class Insegnante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInsegnante;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String  cognome;
    @Column(nullable = false)
    private String email;
    @OneToMany(mappedBy = "insegnante", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Studente> studenti;
    @Column(nullable = false)
    private Boolean flgDelete;
    @Column(nullable = false)
    private Date dataCreazione;

    @Column(nullable = false)
    private Date ultimaModifica;


    //GETTER AND SETTER POWERED BY LOMBOK
}
