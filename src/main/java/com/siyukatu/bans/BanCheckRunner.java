package com.siyukatu.bans;

import com.siyukatu.bans.players.BansPlayer;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.UUID;

public class BanCheckRunner implements Runnable {
    String api_key;

    public BanCheckRunner(String api_key) {
        this.api_key = api_key;

    }

    public void run() {
        Results results = sendApi(api_key);
        System.out.println(results);

    }

    private Results sendApi(String api_key) {
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(5))
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://siyukatu.com/api/banlist.php?apikey=" + api_key))
                .GET()
                .timeout(Duration.ofSeconds(5))
                .build();
        HttpResponse<String> response;

        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            return Results.Exception;
        }

        String[] api_res = response.body().split("\n", -1);
        if (api_res[0].equals("OK")) {
            Bans.database = new HashMap<>() {
                {
                    boolean first = true;
                    for (String line : api_res) {
                        if (first) {
                            first = false;
                            continue;
                        }
                        String[] ban = line.split(",", 2);
                        String uuid = ban[0];
                        String content = ban[1];
                        put(uuid, content);

                        String[] ban_info = content.split(",", 2);
                        BansPlayer p = BansPlayer.getPlayer(UUID.fromString(uuid));
                        if (!p.hasPermission("com.siyukatu.bans.ban")) {
                            p.kickPlayer("[しゆかつBANデータベース]\nあなたはBANされています\n理由: " + ban_info[1] + "\n処罰情報: https://p.sykt.jp/" + ban_info[0]);
                        }

                    }
                }
            };
            return Results.SUCCESS;
        } else if (api_res[0].equals("ERROR")) {
            System.err.println("同期に失敗しました。");
            return Results.ERROR;

        }else {
            System.err.println("正常に読み込めませんでした。\n" +
                    response.body());
            return Results.UNKNOWN;

        }

    }

}
