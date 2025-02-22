package org.example.volunteers.service;


import org.example.volunteers.model.PersonProperties;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Parser {
    public List<Map<PersonProperties, String>> parseCsv(String filePath, String separator) throws IOException {

        List<Map<PersonProperties, String>> maps = new ArrayList<>();
        File csv = new File(filePath);

        if((!separator.equals(";") || !csv.exists())) {
            return maps;
        }

        List<String[]> lines = Files.readAllLines(Paths.get(filePath))
                .stream().map(string -> string.split(separator))
                .collect(Collectors.toList());

        lines.forEach(strings -> {
            Map<PersonProperties, String> personPropertiesStringMap = new HashMap<>();
            personPropertiesStringMap.put(PersonProperties.email, strings[3]);
            personPropertiesStringMap.put(PersonProperties.userName, strings[2]);
            personPropertiesStringMap.put(PersonProperties.firstName, strings[0]);
            personPropertiesStringMap.put(PersonProperties.lastName, strings[1]);
            if(strings.length == PersonProperties.values().length) {
                personPropertiesStringMap.put(PersonProperties.phoneNumber, strings[4]);
            } else {
                personPropertiesStringMap.put(PersonProperties.phoneNumber, "");
            }

            if(!maps.contains(personPropertiesStringMap)) {
                maps.add(personPropertiesStringMap);
            }
        });
        return maps;
    }
}
