package com.spring.timeSheetFides.model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Data
@Entity(name = "studente")
public class Studente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idStudente;

    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)

    private String cognome;
    @Column(nullable = false)
    private String email;

    @ManyToOne
    @JoinColumn(name = "id_insegnante")
    private Insegnante insegnante;

    @Column(nullable = false)
    private Boolean flgDelete;
    @Column(nullable = false)
    private Date dataCreazione;

    @Column(nullable = false)
    private Date ultimaModifica;

    //GETTER AND SETTER POWERED BY LOMBOK


}
