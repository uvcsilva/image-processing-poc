package com.example.imageprocessingpoc.model;

import lombok.Value;

@Value
public class Metadata {

    String name;
    String mime;
    Long size;
    byte[] bytes;
}
