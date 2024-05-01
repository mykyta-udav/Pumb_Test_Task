package com.example.pumb_test_task.controller;

import com.example.pumb_test_task.model.Animal;
import com.example.pumb_test_task.repository.AnimalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Collections;
import static org.hamcrest.Matchers.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AnimalController.class)
public class AnimalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnimalRepository animalRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAnimals() throws Exception {
        Animal animal = new Animal(1L, "Buddy", "Dog", "Male", 20, 50, 2);
        when(animalRepository.findAll(any(Specification.class), any()))
                .thenReturn(List.of(animal));

        mockMvc.perform(get("/animals")
                        .param("type", "Dog")
                        .param("category", "2")
                        .param("sex", "Male")
                        .param("sortBy", "name"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Buddy"))
                .andExpect(jsonPath("$[0].type").value("Dog"))
                .andExpect(jsonPath("$[0].sex").value("Male"))
                .andExpect(jsonPath("$[0].weight").value(20))
                .andExpect(jsonPath("$[0].cost").value(50))
                .andExpect(jsonPath("$[0].category").value(2));
    }

    @Test
    public void testGetAnimalsWithNoParameters() throws Exception {
        Animal animal1 = new Animal(1L, "Buddy", "Dog", "Male", 10, 20, 1);
        Animal animal2 = new Animal(2L, "Lucy", "Cat", "Female", 15, 30, 2);
        when(animalRepository.findAll(any(Specification.class), any(Sort.class)))
                .thenReturn(Arrays.asList(animal1, animal2));

        mockMvc.perform(get("/animals")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Buddy")))
                .andExpect(jsonPath("$[1].name", is("Lucy")));
    }

    @Test
    public void testGetAnimalsWithNoMatchingResults() throws Exception {
        when(animalRepository.findAll(any(Specification.class), any(Sort.class)))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/animals")
                        .param("type", "Bird")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void testGetAnimalsWithSorting() throws Exception {
        List<Animal> animals = Arrays.asList(new Animal(1L, "Buddy", "Dog", "Male", 20, 50, 2));
        when(animalRepository.findAll(any(Specification.class), any(Sort.class))).thenReturn(animals);

        mockMvc.perform(get("/animals")
                        .param("sortBy", "name")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Buddy")));
    }

    @Test
    public void testGetAnimalsWithoutSortBy() throws Exception {
        when(animalRepository.findAll(any(Specification.class), any(Sort.class)))
                .thenReturn(Arrays.asList(new Animal(1L, "Buddy", "Dog", "Male", 10, 20, 1)));

        mockMvc.perform(get("/animals")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1))); // Ensure sorting by default "id" if sortBy not provided
    }
}