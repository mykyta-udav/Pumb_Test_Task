package com.example.pumb_test_task.service.iml;

import com.example.pumb_test_task.model.Animal;
import com.example.pumb_test_task.repository.AnimalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class FileServiceImplTest {

    @Mock
    private AnimalRepository animalRepository;

    @InjectMocks
    private FileServiceImpl fileService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUploadCSVFile() throws IOException {
        String csvContent = "name,type,sex,weight,cost\nBuddy,Dog,Male,50,100";
        MockMultipartFile file = new MockMultipartFile("file", "animals.csv", "text/csv", csvContent.getBytes());

        fileService.uploadFile(file);

        verify(animalRepository, times(1)).saveAll(any());
    }

    @Test
    void testUploadXMLFile() throws IOException {
        String xmlContent = "<animals><animal><name>Buddy</name><type>Dog</type><sex>Male</sex><weight>50</weight><cost>100</cost></animal></animals>";
        MockMultipartFile file = new MockMultipartFile("file", "animals.xml", "application/xml", xmlContent.getBytes());

        fileService.uploadFile(file);

        verify(animalRepository, times(1)).save(any(Animal.class));
    }
}