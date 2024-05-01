package com.example.pumb_test_task.service.impl;

import com.example.pumb_test_task.model.Animal;
import com.example.pumb_test_task.model.Animals;
import com.example.pumb_test_task.repository.AnimalRepository;
import com.example.pumb_test_task.service.FileService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private AnimalRepository animalRepository;

    @Override
    public void uploadFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        if (fileName.endsWith(".csv")) {
            processCSV(file);
        } else if (fileName.endsWith(".xml")) {
            processXML(file);
        } else {
            throw new IllegalArgumentException("Unsupported file format");
        }
    }

    private void processCSV(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return;
        }
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CsvToBean<Animal> csvToBean = new CsvToBeanBuilder<Animal>(fileReader)
                    .withType(Animal.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<Animal> animals = csvToBean.stream().collect(Collectors.toList());
            saveValidAnimals(animals);
        }
    }

    private void processXML(MultipartFile file) throws IOException {
        try {
            JAXBContext context = JAXBContext.newInstance(Animals.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Animals animals = (Animals) unmarshaller.unmarshal(file.getInputStream());
            List<Animal> animalList = animals.getAnimals();
            if (animalList != null) {
                animalList.stream().filter(this::isValidAnimal).peek(Animal::determineCategory).forEach(animalRepository::save);
            }
        } catch (JAXBException e) {
            throw new RuntimeException("Error processing XML file", e);
        }
    }


    public void saveValidAnimals(List<Animal> animals) {
        List<Animal> validAnimals = animals.stream()
                .filter(this::isValidAnimal)
                .peek(Animal::determineCategory)
                .collect(Collectors.toList());
        animalRepository.saveAll(validAnimals);
    }

    private boolean isValidAnimal(Animal animal) {
        return animal.getName() != null && !animal.getName().trim().isEmpty() &&
                animal.getType() != null && !animal.getType().trim().isEmpty() &&
                animal.getSex() != null && !animal.getSex().trim().isEmpty() &&
                animal.getWeight() != null && animal.getWeight() > 0 &&
                animal.getCost() != null && animal.getCost() >= 0;
    }
}
