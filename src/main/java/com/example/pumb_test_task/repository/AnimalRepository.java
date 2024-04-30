package com.example.pumb_test_task.repository;

import com.example.pumb_test_task.model.Animal;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
    List<Animal> findAll(Specification<Animal> spec, Sort by);
    // Here you can define custom query methods, for example:
    // List<Animal> findByType(String type);
}
