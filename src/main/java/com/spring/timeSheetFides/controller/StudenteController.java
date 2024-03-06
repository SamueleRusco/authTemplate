package com.spring.timeSheetFides.controller;

import com.spring.timeSheetFides.model.Studente;
import com.spring.timeSheetFides.service.StudenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/studenti")
public class StudenteController {

    @Autowired
    private StudenteService studenteService;

    @GetMapping
    public List<Studente> getAllStudenti() {
        return studenteService.getAllStudents();
    }

    @GetMapping("/{idStudente}")
    public Optional<Studente> getStudentById(@PathVariable Long idStudente) {
        return studenteService.getStudenteById(idStudente);
    }

    @PostMapping
    public void saveStudente(@RequestBody Studente studente) {
        studenteService.saveStudente(studente);
    }

    @DeleteMapping("/{idStudente}")
    public void deleteStudente(@PathVariable Long idStudente) {
        studenteService.deleteStudent(idStudente);
    }
}
