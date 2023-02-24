package com.siyukatu.bans.configuration;

import java.util.List;

public interface MapConfig extends YamlConfig {

    Object get(String path);

    String getString(String path);

    Integer getInt(String path);

    List<Object> getList(String path);

    List<String> getListString(String path);

    void set(String path, Object obj);

    void setString(String path, String str);

    void setInt(String path, Integer integer);

    void setList(String path, List<Object> objects);

    void setListString(String path, List<String> stringList);

}
