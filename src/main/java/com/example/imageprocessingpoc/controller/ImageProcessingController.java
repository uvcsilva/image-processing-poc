package com.example.imageprocessingpoc.controller;

import com.example.imageprocessingpoc.model.Metadata;
import com.example.imageprocessingpoc.service.Check;
import com.example.imageprocessingpoc.service.ResizeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/processing/v1")
public class ImageProcessingController {

    public final static String folderPath = "../image-processing-poc/img";

    private final ResizeService resizeService;

    @Autowired
    public ImageProcessingController(ResizeService resizeService){
        this.resizeService = resizeService;
    }

    @PostMapping("/sync")
    public ResponseEntity<List<Metadata>> sync(@RequestParam("file") MultipartFile multipartFile) throws IOException {

        Check.execute(multipartFile);
        log.info("Setup iniciado com sucesso");

        return ResponseEntity.ok(resizeService.execute(multipartFile));
    }

    @PostMapping("/async")
    public ResponseEntity<Metadata> async(@RequestParam("file") MultipartFile multipartFile){

        return ResponseEntity.ok(null);
    }


}
