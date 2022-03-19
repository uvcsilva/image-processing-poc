package com.example.imageprocessingpoc.controller;

import com.example.imageprocessingpoc.model.Metadata;
import com.example.imageprocessingpoc.service.Check;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/processing/v1")
public class ImageProcessingController {

    @PostMapping("/sync")
    public ResponseEntity<Metadata> sync(@RequestParam("file") MultipartFile multipartFile) throws IOException {

        Check.execute(multipartFile);

        return ResponseEntity.ok(null);
    }

    @PostMapping("/async")
    public ResponseEntity<Metadata> async(@RequestParam("file") MultipartFile multipartFile){

        return ResponseEntity.ok(null);
    }


}
