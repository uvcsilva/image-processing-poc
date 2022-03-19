package com.example.imageprocessingpoc.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;

public class Check {

    @Value("${folder.path}")
    private static String folderPath;

    public static void execute(MultipartFile multipartFile) throws IOException {

        if(Files.notExists(Path.of(folderPath))) throw new NotDirectoryException("Diretorio não encontrado");
        if(multipartFile.isEmpty()) throw new FileNotFoundException("Arquivo nulo recebido");

    }


}
