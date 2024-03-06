package com.spring.timeSheetFides.service;

import com.spring.timeSheetFides.model.Insegnante;
import com.spring.timeSheetFides.repository.InsegnanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InsegnanteService {


    @Autowired
    private InsegnanteRepository insegnanteRepo;

    public List<Insegnante> getAllInsegnante() {
        return insegnanteRepo.findAll();
    }

    public Optional<Insegnante> getInsegnanteById(Long idInsegnante) {
        return insegnanteRepo.findById(idInsegnante);
    }

    public void saveInsegnante(Insegnante insegnante) {
        insegnanteRepo.save(insegnante);
    }

    public void deleteInsegnante(Long teacherId) {
        insegnanteRepo.deleteById(teacherId);
    }
}
