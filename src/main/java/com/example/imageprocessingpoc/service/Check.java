package com.example.imageprocessingpoc.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;

import static com.example.imageprocessingpoc.controller.ImageProcessingController.folderPath;

public class Check {

    public static void execute(MultipartFile multipartFile) throws IOException {

        if(Files.notExists(Path.of(folderPath))) throw new NotDirectoryException("Diretorio não encontrado");
        if(multipartFile.isEmpty() || multipartFile.getSize() == 0)
            throw new FileNotFoundException("Arquivo nulo ou vazio recebido");
    }


}
