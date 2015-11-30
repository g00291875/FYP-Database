package work3;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;


public class Main {

    public static void main(String [] args){
        try {
            byte[] array = Files.readAllBytes(new File("C:\\the set\\a.mp3").toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
