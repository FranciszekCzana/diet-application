package com.springboot.dietapplication.controller;

import com.springboot.dietapplication.model.patient.Patient;
import com.springboot.dietapplication.repository.PatientRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {
    private PatientRepository patientRepository;

    public PatientController(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @GetMapping
    public List<Patient> getAll() {
        return this.patientRepository.findAll();
    }

    @GetMapping(path = "/{patientId}")
    public Optional<Patient> getFilteredProducts(@PathVariable("patientId") String patientId) {
        return this.patientRepository.findById(patientId);
    }
}
