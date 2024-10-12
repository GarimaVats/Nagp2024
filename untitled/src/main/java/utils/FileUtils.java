package utils;

import logger.Log;

import java.io.*;
import java.nio.file.Files;
import java.util.Base64;

public class FileUtils {

    public static byte[] convertFileToByteArray(File fileData) {
        byte[] files = new byte[0];
        try {
            files = Files.readAllBytes(fileData.toPath());
        } catch (Exception e) {
            Log.warn(e.getMessage());
        }
        return files;
    }

}
