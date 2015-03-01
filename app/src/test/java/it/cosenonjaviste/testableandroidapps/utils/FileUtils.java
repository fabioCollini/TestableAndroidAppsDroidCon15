package it.cosenonjaviste.testableandroidapps.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileUtils {

    public static String readFile(String fileName) throws IOException {
        StringBuilder b = new StringBuilder();
        BufferedReader reader = null;
        try {
            File file = new File("app/src/test/resources/" + fileName);
            if (!file.exists()) {
                file = new File("src/test/resources/" + fileName);
            }
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            while ((line = reader.readLine()) != null) {
                b.append(line).append("\n");
            }
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
            }
        }
        return b.toString();
    }
}