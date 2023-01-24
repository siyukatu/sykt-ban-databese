package com.siyukatu.bans.com.siyukatu.bans.Com.siyukatu;

import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.*;

public final class bans extends JavaPlugin implements Listener {
    private Map<String, String> ban_map = new HashMap<String, String>() {};
    private BukkitTask bans_getter;
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this,this);
        getLogger().info("起動しました。");
        Path apikey_dir = Path.of("plugins/siyukatu_bans/");
        Path apikey_file = Path.of("plugins/siyukatu_bans/apikey.txt");
        bans_getter = Bukkit.getScheduler().runTaskTimer(this, () -> {
            String apikey = "";
            try {
                 apikey = Files.readString(apikey_file);
            } catch (IOException ignored) {
                try{
                    Files.createDirectory(apikey_dir);
                }catch(Exception e){}
                try{
                    Files.createFile(apikey_file);
                }catch(Exception e){}
            }
            if (Objects.equals(apikey, "")){
                getLogger().warning("plugins/siyukatu_bans/apikey.txt にapikeyを記入してください。");
                return;
            }
            HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).connectTimeout(Duration.ofSeconds(5)).build();;
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://siyukatu.com/api/banlist.php?apikey="+apikey)).GET().timeout(Duration.ofSeconds(5)).build();
            HttpResponse<String> response = null;
            try {
                response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            } catch (Exception e) {
                return;
            }
            String[] bans = response.body().split("\n",-1);
            if (Objects.equals(bans[0], "OK")) {
                ban_map = new HashMap<String, String>() {
                    {
                        Boolean first = true;
                        for (int i = 0; i < bans.length; i++) {
                            if (first){first=false;continue;}
                            String[] ban = bans[i].split(",", 2);
                            String uuid = ban[0];
                            String content = ban[1];
                            put(uuid, content);
                            try{
                                String[] ban_info = content.split(",",2);
                                Player p = getServer().getPlayer(UUID.fromString(uuid));
                                if (!p.isWhitelisted()) {
                                    p.kickPlayer("[しゆかつBANデータベース]\nあなたはBANされています\n理由: " + ban_info[1] + "\n処罰情報: https://p.sykt.jp/" + ban_info[0]);
                                }
                            }catch (Exception e){}
                        }
                    }
                };
            }
            if (Objects.equals(bans[0], "ERROR")) {
                getLogger().warning("BAN者の同期に失敗しました。\n"+bans[1]);
            }
        }, 0L, 600L);
        HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).connectTimeout(Duration.ofSeconds(5)).build();;
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://siyukatu.com/api/banlist.php?apikey=test")).GET().timeout(Duration.ofSeconds(5)).build();
        HttpResponse<String> response = null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            return;
        }
        String[] bans = response.body().split("\n",-1);
        if (Objects.equals(bans[0], "OK")) {
            ban_map = new HashMap<String, String>() {
                {
                    Boolean first = true;
                    for (int i = 0; i < bans.length; i++) {
                        if (first){first=false;continue;}
                        String[] ban = bans[i].split(",", 2);
                        String uuid = ban[0];
                        String content = ban[1];
                        put(uuid, content);
                        try{
                            String[] ban_info = content.split(",",2);
                            Player p = getServer().getPlayer(UUID.fromString(uuid));
                            if (!p.isWhitelisted()) {
                                p.kickPlayer("[しゆかつBANデータベース]\nあなたはBANされています\n理由: " + ban_info[1] + "\n処罰情報: https://p.sykt.jp/" + ban_info[0]);
                            }
                        }catch (Exception e){}
                    }
                }
            };
        }
        if (Objects.equals(bans[0], "ERROR")) {
            getLogger().warning("BAN者の同期に失敗しました。\n"+bans[1]);
        }
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        getLogger().info("終了しました。");
        bans_getter.cancel();
        // Plugin shutdown logic
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void ban(PlayerLoginEvent event) {
        Player p = event.getPlayer();
        if(!p.isWhitelisted() && ban_map.containsKey(p.getUniqueId().toString())) {
            String[] ban_info = ban_map.get(p.getUniqueId().toString()).split(",", 2);
            event.disallow(PlayerLoginEvent.Result.KICK_BANNED, "[しゆかつBANデータベース]\nあなたはBANされています\n理由: " + ban_info[1] + "\n処罰情報: https://p.sykt.jp/" + ban_info[0]);
        }
    }
}
