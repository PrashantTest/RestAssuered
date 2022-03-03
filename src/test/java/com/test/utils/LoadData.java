package com.test.utils;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;


public class LoadData {
    public String postData() throws IOException {
        String file = "src/test/resources/TestData/createrequest.json";
        return new String(Files.readAllBytes(Paths.get(file)));
    }
    public String updateData() throws IOException {
        String file = "src/test/resources/TestData/updaterequest.json";
        return new String(Files.readAllBytes(Paths.get(file)));
    }

}
