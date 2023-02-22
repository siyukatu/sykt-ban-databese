package com.siyukatu.bans;

import com.siyukatu.bans.configuration.DefaultConfig;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Bans {
    public static ServerMode mode;
    private static DefaultConfig config;

    public static Map<String, String> database;

    public Bans() {

        // コンフィグの読み込み
        Path path = Paths.get("./plugins/ban-database/config.yml");
        config = new DefaultConfig(path);
        Util.copyFile(path, this.getClass().getClassLoader().getResourceAsStream("config.yml"));


        // スケジューラーの設定
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(
                new BanCheckRunner(config.getString("api_key")
                        , config.getString("debug_level").equals("debug")
                        , config.getListString("whitelist"))
                , 0
                , config.getInt("req_delay")
                , TimeUnit.SECONDS);

        // mapの初期化
        database = new HashMap<>();

    }

    public DefaultConfig getConfig() {
        return config;

    }

}
