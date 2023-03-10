package com.siyukatu.bans;

import com.siyukatu.bans.players.BansPlayer;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class BanCheckRunner implements Runnable {
    String api_key;
    boolean debug_level;
    List<String> uuids;

    public BanCheckRunner(String api_key, boolean debug_level, List<String> uuids) {
        this.api_key = api_key;
        this.debug_level = debug_level;
        this.uuids = uuids;

    }

    public void run() {
        Results result = sendApi(api_key);
        if (debug_level) {
            System.out.println("データベースへの同期結果は" + result + "でした。");
        }

    }

    private Results sendApi(String api_key) {

        String[] api_res;

        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(5))
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://cdn.siyukatu.com/bansapi/"+api_key+".js"))
                .GET()
                .timeout(Duration.ofSeconds(5))
                .build();

        HttpResponse<String> response;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            return Results.Exception;
        }

        api_res = response.body().split("\n",-1);

        if (api_res[0].equals("OK")) {
            int i = 0;
            for (String ban:api_res) {
                if (i == 0) { i++; continue; }
                String[] bans = ban.split(",", 2);
                String uuid = bans[0];
                String content = bans[1];
                Bans.database.put(uuid, content);

                String[] ban_info = content.split(",", 2);
                if (!uuid.startsWith("0")) {
                    BansPlayer p = BansPlayer.getPlayer(UUID.fromString(uuid));
                    if (p != null) { continue; }
                    if (!uuids.contains(p.getUniqueId().toString())
                            && !p.hasPermission("com.siyukatu.bans.ban")) {
                        p.kick("[しゆかつBANデータベース]\nあなたはBANされています\n理由: " + ban_info[1] + "\n処罰情報: https://p.sykt.jp/" + ban_info[0]);
                    }
                }

            }

            return Results.SUCCESS;

        } else if (api_res[0].equals("ERROR")) {
            System.err.println("データベースへの同期に失敗しました。");
            return Results.ERROR;

        } else {
            System.err.println("データベースを正常に読み込めませんでした。");
            return Results.UNKNOWN;

        }

    }

}
