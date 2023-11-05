package org.danilkha.utils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class PropertyReader {


    private final File propFile;

    private Map<String, String> propsMap = null;

    private static final String PROP_SEPARATOR = "=";

    public PropertyReader(
            String path
    ){
        this.propFile = new File(path);
        if(!propFile.exists()){
            throw new RuntimeException("properties file not found in "+path);
        }
    }

    public String getProp(String name){
        initProperties();
        var value = propsMap.get(name);
        if(value == null){
            throw new RuntimeException("property \""+name+"\" not declared");
        }
        return value;
    }


    public Map<String, String> getPropertyMap(){
        initProperties();
        return Map.copyOf(propsMap);
    }
    private void initProperties(){
        if(propsMap == null){
            propsMap = new HashMap<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(propFile))) {
                reader.lines()
                        .forEach(s ->{
                            if(!s.isEmpty()){
                                PropEntry propEntry = decodeProp(s);
                                if(propEntry == null){
                                    throw new RuntimeException("Invalid property: \""+s+"\" in file "+propFile.getPath());
                                }
                                propsMap.put(propEntry.key, propEntry.value);
                            }
                        });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static PropEntry decodeProp(String s){
        var separator = s.indexOf(PROP_SEPARATOR);
        if(separator == -1){
            return null;
        }
        var key = s.substring(0, separator);
        var value = s.substring(separator+1);
        return new PropEntry(key, value);
    }


    private record PropEntry(String key, String value){}
}
