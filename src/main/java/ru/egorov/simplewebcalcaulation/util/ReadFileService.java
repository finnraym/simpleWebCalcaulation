package ru.egorov.simplewebcalcaulation.util;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class ReadFileService {

    public List<Integer> readFile(String filePath) throws IOException {
        List<Integer> data = new ArrayList<>();

        try(BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
            String[] split = reader.readLine().split(" ");
            for (String str : split) {
                int num = Integer.parseInt(str);
                data.add(num);
            }
        }

        return data;
    }
}
