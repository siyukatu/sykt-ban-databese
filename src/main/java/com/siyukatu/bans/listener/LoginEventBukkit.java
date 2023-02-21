package com.siyukatu.bans.listener;

import com.siyukatu.bans.Bans;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.List;

public class LoginEventBukkit implements Listener {
    List<String> uuids;
    public LoginEventBukkit(List<String> uuids) {
        this.uuids = uuids;

    }

    @EventHandler
    public void ban(AsyncPlayerPreLoginEvent e) {

        if (!uuids.contains(e.getUniqueId().toString())) {
            String content = Bans.database.get(e.getUniqueId().toString()) != null
                    ? Bans.database.get(e.getUniqueId().toString()) : "no content, no content";

            String[] ban_info = content.split(",", 2);
            e.setKickMessage("[しゆかつBANデータベース]\nあなたはBANされています\n理由: " + ban_info[1] + "\n処罰情報: https://p.sykt.jp/" + ban_info[0]);

        }
    }

}
