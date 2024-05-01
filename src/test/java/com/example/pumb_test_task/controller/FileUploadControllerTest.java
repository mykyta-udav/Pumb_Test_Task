package com.example.pumb_test_task.controller;

import com.example.pumb_test_task.service.FileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FileUploadController.class)
public class FileUploadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileService fileService;

    @Test
    public void testFileUpload() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "some xml".getBytes());

        mockMvc.perform(multipart("/files/upload").file(file))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUploadFileSuccess() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "data.csv", "text/csv", "name,type\nAlice,Human".getBytes());

        mockMvc.perform(multipart("/files/upload").file(file))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));

        verify(fileService).uploadFile(file);
    }

    @Test
    public void testUploadEmptyFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "empty.csv", "text/csv", "".getBytes());

        mockMvc.perform(multipart("/files/upload").file(file))
                .andExpect(status().isBadRequest());  // Expect a bad request or a custom status indicating the issue

        verify(fileService, never()).uploadFile(any());  // File service should not be called
    }

    @Test
    public void testUploadMissingFileParameter() throws Exception {
        mockMvc.perform(multipart("/files/upload"))
                .andExpect(status().isBadRequest());  // Expect a bad request due to missing file parameter

        verify(fileService, never()).uploadFile(any());  // Service method should not be called
    }
}