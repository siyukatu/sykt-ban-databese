package com.siyukatu.bans.configuration;

import com.siyukatu.bans.Util;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class DefaultConfig implements MapConfig {
    private final Path path;
    private final Yaml yaml;

    private Map map;

    public DefaultConfig(Path path) {
        this.path = path;
        Util.makeFile(path);

        this.yaml = new Yaml();

        try {
            map = yaml.loadAs(Files.newBufferedReader(path), Map.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void load() {
        try {
            map = yaml.loadAs(Files.newBufferedReader(path), Map.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void write() {
        yaml.dump(map);

    }


    @Override
    public Object get(String path) {
        return map.get(path);
    }

    @Override
    public String getString(String path) {
        return (String) map.get(path);
    }

    @Override
    public Integer getInt(String path) {
        return (Integer) map.get(path);
    }


    @Override
    public List<Object> getList(String path) {
        return (List<Object>) map.get(path);
    }

    @Override
    public List<String> getListString(String path) {
        return (List<String>) map.get(path);
    }

    @Override
    public void set(String path, Object obj) {
        map.put(path, obj);
    }

    @Override
    public void setString(String path, String str) {
        map.put(path, str);
    }

    @Override
    public void setInt(String path, Integer integer) {
        map.put(path, integer);
    }

    @Override
    public void setList(String path, List<Object> objects) {
        map.put(path, objects);
    }

    @Override
    public void setListString(String path, List<String> stringList) {
        map.put(path, stringList);
    }
}
