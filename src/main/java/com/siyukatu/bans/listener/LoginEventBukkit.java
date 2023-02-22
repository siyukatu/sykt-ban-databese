package com.siyukatu.bans.listener;

import com.siyukatu.bans.Bans;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.List;

public class LoginEventBukkit implements Listener {
    List<String> uuids;
    public LoginEventBukkit(List<String> uuids) {
        this.uuids = uuids;

    }

    @EventHandler
    public void ban(PlayerLoginEvent e) {
        Player p = e.getPlayer();

        if (!uuids.contains(p.getUniqueId().toString())
                && !p.hasPermission("com.siyukatu.bans.ban")) {
            String content = Bans.database.get(p.getUniqueId().toString()) != null
                    ? Bans.database.get(p.getUniqueId().toString()) : "no content, no content";

            String[] ban_info = content.split(",", 2);
            e.setKickMessage("[しゆかつBANデータベース]\nあなたはBANされています\n理由: " + ban_info[1] + "\n処罰情報: https://p.sykt.jp/" + ban_info[0]);

        }
    }

}
