package com.example.pumb_test_task.controller;

import com.example.pumb_test_task.model.Animal;
import com.example.pumb_test_task.model.AnimalSpecifications;
import com.example.pumb_test_task.repository.AnimalRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/animals")
@Validated
public class AnimalController {

    @Autowired
    private AnimalRepository animalRepository;

    @GetMapping
    @Operation(summary = "Retrieve animals based on filters", description = "Fetches animals optionally filtered by type, category, sex, and sorted by a specified field.")
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

        if (sortBy == null || sortBy.trim().isEmpty()) {
            sortBy = "id";
        }

        return animalRepository.findAll(spec, Sort.by(sortBy));
    }
}
