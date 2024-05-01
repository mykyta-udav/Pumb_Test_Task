package com.example.pumb_test_task.service.impl;

import com.example.pumb_test_task.model.Animal;
import com.example.pumb_test_task.repository.AnimalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Test
    public void testProcessUnsupportedFileFormat() {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Some content".getBytes());

        assertThrows(IllegalArgumentException.class, () -> fileService.uploadFile(file),
                "Unsupported file format exception was expected.");
    }

    @Test
    public void testProcessEmptyCSVFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "empty.csv", "text/csv", "".getBytes());

        fileService.uploadFile(file);

        verify(animalRepository, never()).saveAll(any());
    }

    @Test
    public void testDatabaseErrorDuringSave(){
        when(animalRepository.saveAll(any())).thenThrow(new RuntimeException("Database error"));

        String csvData = "name,type,sex,weight,cost\nBuddy,dog,male,50,300";
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", csvData.getBytes());

        assertThrows(RuntimeException.class, () -> fileService.uploadFile(file),
                "Expected a RuntimeException due to a database error.");
    }
}