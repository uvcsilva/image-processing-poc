package com.example.imageprocessingpoc.service;

import com.example.imageprocessingpoc.model.Metadata;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.imgscalr.AsyncScalr;
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
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class ResizeServiceAsync {

    private static final Path root = Paths.get("img");

    public List<Metadata> executeAsync(MultipartFile multipartFile) throws IOException, ExecutionException,
            InterruptedException {

        saveOriginalImage(multipartFile);
        log.info("Imagem " + multipartFile.getOriginalFilename() + " salva com sucesso.");

        List<Metadata> metadataList = new ArrayList<>();
        Metadata originalMetadata = getMetadata(multipartFile);
        metadataList.add(originalMetadata);

        Metadata finalMetadata = resizeImageAsync(multipartFile, root.toString());
        metadataList.add(finalMetadata);
        log.info("Conversão concluida com sucesso");

        return metadataList;
    }

    private Metadata resizeImageAsync(MultipartFile multipartFile, String outputPathWithName) throws IOException,
            ExecutionException, InterruptedException {

        BufferedImage srcImg = ImageIO.read(multipartFile.getInputStream());
        BufferedImage destImg = AsyncScalr.resize(srcImg, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.AUTOMATIC, 600,
                400, Scalr.OP_ANTIALIAS).get();
        File resizedImageFile = new File(outputPathWithName.concat("/resizedAsync.jpg"));
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
