package com.siyukatu.bans;

import com.google.common.io.ByteStreams;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Objects;

public class Util {

    public static void copyFile(Path path, InputStream in) {
        try {
            if (Objects.equals(Files.readAllLines(path), Collections.EMPTY_LIST)) {
                OutputStream out = Files.newOutputStream(path);
                ByteStreams.copy(in, out);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void makeFile(Path path) {
        if (Files.notExists(path)) {
            try {
                Files.createDirectories(path.getParent());
                Files.createFile(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

}
