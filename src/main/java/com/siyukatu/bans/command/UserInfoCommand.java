package com.siyukatu.bans.command;

import com.siyukatu.bans.Bans;
import com.siyukatu.bans.players.BansPlayer;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

public class UserInfoCommand implements ICommand {

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public boolean execute(BansPlayer player, List<String> args) {

        String uuid;
        try {
            HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).connectTimeout(Duration.ofSeconds(5)).build();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://api.mojang.com/users/profiles/minecraft/"+args.get(1))).GET().timeout(Duration.ofSeconds(5)).build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject UUIDObject = (JSONObject) JSONValue.parseWithException(response.body());
            uuid = UUIDObject.get("id").toString();
        } catch (IOException | ParseException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        String content = Bans.database.get(uuid);
        String banned = "このプレイヤーはBanされていま" + (content == null ? "せん" : "す") + "。";
        String[] ban_info = content.split(",",2);
        player.sendMessage("[しゆかつBANデータベース]\n" + banned + "\n" + ban_info[1] + "\n処罰情報: https://p.sykt.jp/" + ban_info[0]);
        return true;

    }
}
