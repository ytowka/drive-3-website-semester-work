package org.danilkha.utils;

import java.io.File;

public class FileProvider {

    private final String basePath;
    public FileProvider(String basePath){
        this.basePath = basePath;
    }

    public boolean saveFile(File file){
        return true;
    }

    public File getFile(){
        return null;
    }

    public String getBasePath() {
        return basePath;
    }
}
