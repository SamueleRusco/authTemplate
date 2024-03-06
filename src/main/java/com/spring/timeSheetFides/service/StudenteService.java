package com.spring.timeSheetFides.service;

import com.spring.timeSheetFides.model.Studente;
import com.spring.timeSheetFides.repository.StudenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudenteService {
    @Autowired
    private StudenteRepository studenteRepo;

    public List<Studente> getAllStudents() {
        return studenteRepo.findAll();
    }

    public Optional<Studente> getStudenteById(Long idStudente) {
        return studenteRepo.findById(idStudente);
    }

    public void saveStudente(Studente studente) {
        studenteRepo.save(studente);
    }

    public void deleteStudent(Long idStudente) {
        studenteRepo.deleteById(idStudente);
    }

}
