package com.epam.mjc.io;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UncheckedIOException;
import java.io.IOException;
import java.util.HashMap;


public class FileReader {

    public Profile getDataFromFile(File file) {

        HashMap<String, String> hashMap = stringToHashMap(readFile(file));

        Profile profile = new Profile();
        profile.setName(hashMap.get("name"));
        profile.setEmail(hashMap.get("email"));
        try{profile.setAge(Integer.parseInt(hashMap.get("age")));}
        catch (NumberFormatException e){profile.setAge(null);}

        try{profile.setPhone(Long.parseLong(hashMap.get("phone")));
        }catch (NumberFormatException e){profile.setPhone(null);}


        return profile;
    }

    private String readFile(File file){

        StringBuilder sb = new StringBuilder();
        try(FileInputStream fileInputStream = new FileInputStream(file)) {
            int i;

            while((i=fileInputStream.read())!= -1){
                sb.append((char) i);
            }
        } catch (FileNotFoundException e) {
            throw new NullPointerException();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        return sb.toString();
    }

    private HashMap<String, String> stringToHashMap(String line){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("name",null);
        hashMap.put("age",null);
        hashMap.put("email",null);
        hashMap.put("phone",null);
        String[] arr = line.split("\n");

        for (String str: arr) {
            String key=str.substring(0,str.indexOf(":")).toLowerCase();
            String value=str.substring(str.indexOf(" ")+1).replaceAll("[\\r,\\n]*", "");

            if(hashMap.containsKey(key)){
                hashMap.replace(key,value);
            }
        }

        return hashMap;
    }
}