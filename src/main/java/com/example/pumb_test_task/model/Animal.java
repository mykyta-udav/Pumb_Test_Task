package com.example.pumb_test_task.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "animal")
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Name must not be empty")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "Type must not be empty")
    @Column(name = "type")
    private String type;

    @NotBlank(message = "Sex must not be empty")
    @Column(name = "sex")
    private String sex;

    @NotNull(message = "Weight must not be null")
    @Min(value = 1, message = "Weight must be at least 1")
    @Column(name = "weight")
    private Integer weight;

    @NotNull(message = "Cost must not be null")
    @Min(value = 0, message = "Cost must be non-negative")
    @Column(name = "cost")
    private Integer cost;

    @Column(name = "category")
    private Integer category;

    public void determineCategory() {
        if (this.cost <= 20) {
            this.category = 1;
        } else if (this.cost <= 40) {
            this.category = 2;
        } else if (this.cost <= 60) {
            this.category = 3;
        } else {
            this.category = 4;
        }
    }
}




