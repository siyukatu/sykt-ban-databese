package com.siyukatu.bans;

import com.google.common.io.ByteStreams;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Collections;
import java.util.Objects;
import java.util.UUID;

public class Util {

    private Util() { }

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

    public static UUID getUUID(String name) {
        try {
            HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).connectTimeout(Duration.ofSeconds(5)).build();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://api.mojang.com/users/profiles/minecraft/"+name)).GET().timeout(Duration.ofSeconds(5)).build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject UUIDObject = (JSONObject) JSONValue.parseWithException(response.body());
            String uuid = UUIDObject.get("id").toString();
            StringBuilder tmp = new StringBuilder();
            for(int i = 0; i <= 31; i++) {
                tmp.append(uuid.charAt(i));
                if(i == 7 || i == 11 || i == 15 || i == 19) {
                    tmp.append("-");
                }
            }
            return UUID.fromString(tmp.toString());

        } catch (IOException | ParseException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
