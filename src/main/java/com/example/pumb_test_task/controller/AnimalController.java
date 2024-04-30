package com.example.pumb_test_task.controller;

import com.example.pumb_test_task.model.Animal;
import com.example.pumb_test_task.model.AnimalSpecifications;
import com.example.pumb_test_task.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/animals")
public class AnimalController {

    @Autowired
    private AnimalRepository animalRepository;

    @GetMapping
    public List<Animal> getAnimals(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Integer category,
            @RequestParam(required = false) String sex,
            @RequestParam(required = false) String sortBy
    ) {
        Specification<Animal> spec = Specification
                .where(AnimalSpecifications.hasType(type))
                .and(AnimalSpecifications.hasCategory(category))
                .and(AnimalSpecifications.hasSex(sex));

        return animalRepository.findAll(spec, Sort.by(sortBy));
    }
}
