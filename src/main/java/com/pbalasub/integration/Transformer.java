package com.pbalasub.integration;

import org.springframework.stereotype.Component;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class Transformer {
    public String transform(String filePath) throws IOException {
        return "Transformed:" + new String(Files.readAllBytes(Paths.get(filePath)));
    }
}
