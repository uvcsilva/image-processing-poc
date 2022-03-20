package com.example.imageprocessingpoc.model;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class Metadata {

    String originalFileName;
    String name;
    String contentType;
    long size;
    int height;
    int width;
}
