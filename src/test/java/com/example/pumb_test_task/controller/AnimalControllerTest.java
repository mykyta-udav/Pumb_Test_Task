package com.example.pumb_test_task.controller;

import com.example.pumb_test_task.model.Animal;
import com.example.pumb_test_task.repository.AnimalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Buddy"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].type").value("Dog"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].sex").value("Male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].weight").value(20))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].cost").value(50))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].category").value(2));
    }
}