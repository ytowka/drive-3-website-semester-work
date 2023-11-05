package org.danilkha.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class FileProvider {

    private final String basePath;
    private final CodeGenerator codeGenerator;
    public FileProvider(
            String basePath,
            CodeGenerator codeGenerator
    ){
        this.basePath = basePath;
        File f = new File(basePath);
        if(!f.exists()){
            f.mkdir();
        }
        this.codeGenerator = codeGenerator;
    }

    /**
     *
     * @param inputStream
     * @return saved file username
     * @throws IOException
     */
    public String saveFile(InputStream inputStream, String originalName) throws IOException {
        String[] fileNameParts = originalName.split("\\.");
        String extension = "."+fileNameParts[fileNameParts.length-1];
        String encodedName = Base64.getEncoder().encodeToString(originalName.getBytes(StandardCharsets.UTF_8));
        String name = codeGenerator.generateStringCode(10)+System.currentTimeMillis()+encodedName+extension;
        File f = new File(basePath+File.separator+name);
        if(!f.exists()) {
            f.createNewFile();
        }
        try(FileOutputStream fileOutputStream = new FileOutputStream(f)) {
            fileOutputStream.write(inputStream.readAllBytes());

        }
        return name;
    }

    public String getBasePath() {
        return basePath;
    }
}
