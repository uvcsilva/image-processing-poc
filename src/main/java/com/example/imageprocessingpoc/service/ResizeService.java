package com.example.imageprocessingpoc.service;

import com.example.imageprocessingpoc.model.Metadata;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ResizeService {

    private static final Path root = Paths.get("img");

    public List<Metadata> execute(MultipartFile multipartFile) throws IOException {

        saveOriginalImage(multipartFile);
        log.info("Imagem " + multipartFile.getOriginalFilename() + " salva com sucesso.");

        List<Metadata> metadataList = new ArrayList<>();
        Metadata originalMetadata = getMetadata(multipartFile);
        metadataList.add(originalMetadata);

        Metadata finalMetadata = resizeImage(multipartFile, root.toString());
        metadataList.add(finalMetadata);
        log.info("Conversão concluida com sucesso");

        return metadataList;
    }

    private Metadata resizeImage(MultipartFile multipartFile, String outputPathWithName) throws IOException {

        //Scalr.resize(); Scalr.crop(); Scalr.pad(); Scalr.rotate();

        //Scalr.Method.AUTOMATIC; Scalr.Method.BALANCED; Scalr.Method.QUALITY; Scalr.Method.SPEED;
        //Scalr.Method.ULTRA_QUALITY;

        //Scalr.Mode.AUTOMATIC; Scalr.Mode.FIT_EXACT; Scalr.Mode.FIT_TO_HEIGHT; Scalr.Mode.FIT_TO_WIDTH;

        //Scalr.OP_ANTIALIAS; Scalr.OP_BRIGHTER; Scalr.OP_DARKER; Scalr.OP_GRAYSCALE;
        
        BufferedImage srcImg = ImageIO.read(multipartFile.getInputStream());
        BufferedImage destImg = Scalr.resize(srcImg, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.AUTOMATIC, 600,
                400, Scalr.OP_ANTIALIAS);
        File resizedImageFile = new File(outputPathWithName.concat("/resized.jpg"));
        ImageIO.write(destImg, "jpg", resizedImageFile);
        Metadata metadata = getMetadata(multipartFile, destImg);

        destImg.flush();
        srcImg.flush();
        //resizedImageFile.deleteOnExit();

        return metadata;
    }

    private Metadata getMetadata(MultipartFile multipartFile) throws IOException {

        BufferedImage bufferedImage = ImageIO.read(multipartFile.getInputStream());

        return new Metadata(multipartFile.getOriginalFilename(), multipartFile.getName(),
                multipartFile.getContentType(), multipartFile.getSize(), bufferedImage.getHeight(),
                bufferedImage.getWidth());
    }

    private Metadata getMetadata(MultipartFile multipartFile, BufferedImage bufferedImage) throws IOException {

        return new Metadata(multipartFile.getOriginalFilename(), multipartFile.getName(),
                multipartFile.getContentType(), multipartFile.getSize(), bufferedImage.getHeight(),
                bufferedImage.getWidth());
    }

    /*private String changeFileOriginalNameForTest(){
        StringBuffer newOriginalFileName = new StringBuffer(RandomStringUtils.random(23,true,true))
                .append(".jpg");
        return newOriginalFileName.toString();
    }*/

    private void saveOriginalImage(MultipartFile multipartFile) throws IOException {

        if(multipartFile.getOriginalFilename().isEmpty())
            Files.copy(multipartFile.getInputStream(), root.resolve(RandomStringUtils
                            .random(23,true,true)), StandardCopyOption.REPLACE_EXISTING);

        Files.copy(multipartFile.getInputStream(), root.resolve(multipartFile.getOriginalFilename()),
                StandardCopyOption.REPLACE_EXISTING);

    }
}
