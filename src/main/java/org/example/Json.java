package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Json {
    static ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    static void listSouvenirsToJsonFile(File file, List<Souvenir> list) throws IOException {
        objectMapper.writeValue(file, list);
        System.out.println("Список сувениров записан в файл");
    }

    static void listManufacturersToJsonFile(File file, List<Manufacturer> list) throws IOException {
        objectMapper.writeValue(file, list);
        System.out.println("Список производителей записан в файл");
    }

    static List<Souvenir> fileToListOfSouvenirs(File file) throws IOException {
        return objectMapper.readValue(file, new TypeReference<>() {});
    }

    static List<Manufacturer> fileToListOfManufacturers(File file) throws IOException {
        return objectMapper.readValue(file, new TypeReference<>() {});
    }
}