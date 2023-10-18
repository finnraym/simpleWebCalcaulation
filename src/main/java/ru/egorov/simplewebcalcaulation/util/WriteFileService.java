package ru.egorov.simplewebcalcaulation.util;

import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class WriteFileService {

    public void writeFile(String filePath, int result) throws IOException {
        try(BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {
            writer.write(String.valueOf(result));
        }
    }
}
