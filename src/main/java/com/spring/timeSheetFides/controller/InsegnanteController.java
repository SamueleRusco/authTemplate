package com.spring.timeSheetFides.controller;

import com.spring.timeSheetFides.model.Insegnante;
import com.spring.timeSheetFides.service.InsegnanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/insegnanti")
public class InsegnanteController {

    @Autowired
    private InsegnanteService insegnanteService;

    @GetMapping
    public List<Insegnante> getAllInsegnante() {
        return insegnanteService.getAllInsegnante();
    }

    @GetMapping("/{idInsegnante}")
    public Optional<Insegnante> getInsegnanteById(@PathVariable Long idInsegnante) {
        return insegnanteService.getInsegnanteById(idInsegnante);
    }

    @PostMapping
    public void saveInsegnante(@RequestBody Insegnante insegnante) {
        insegnanteService.saveInsegnante(insegnante);
    }

    @DeleteMapping("/{idInsegnante}")
    public void deleteTeacher(@PathVariable Long idInsegnante) {
        insegnanteService.deleteInsegnante(idInsegnante);
    }
}
