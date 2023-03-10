package com.siyukatu.bans.listener;

import com.siyukatu.bans.Bans;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.List;

public class LoginEventBungee implements Listener {
    List<String> uuids;
    public LoginEventBungee(List<String> uuids) {
        this.uuids = uuids;

    }

    @EventHandler
    public void ban(PostLoginEvent e) {
        ProxiedPlayer p = e.getPlayer();

        if (!uuids.contains(p.getUniqueId().toString())
                && !p.hasPermission("com.siyukatu.bans.ban")) {

            String content = Bans.database.get(p.getUniqueId().toString());

            String[] ban_info = content.split(",", 2);
            p.disconnect("[しゆかつBANデータベース]\nあなたはBANされています\n理由: " + ban_info[1] + "\n処罰情報: https://p.sykt.jp/" + ban_info[0]);


        }

    }
}
