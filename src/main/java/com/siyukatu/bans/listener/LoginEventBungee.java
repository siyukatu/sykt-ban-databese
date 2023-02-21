package com.siyukatu.bans.listener;

import com.siyukatu.bans.Bans;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.List;

public class LoginEventBungee implements Listener {
    List<String> uuids;
    public LoginEventBungee(List<String> uuids) {
        this.uuids = uuids;

    }

    @EventHandler
    public void ban(PreLoginEvent e) {
        PendingConnection con = e.getConnection();

        if (!uuids.contains(con.toString())) {

            String content = Bans.database.get(con.getUniqueId().toString());

            String[] ban_info = content.split(",", 2);
            con.disconnect("[しゆかつBANデータベース]\nあなたはBANされています\n理由: " + ban_info[1] + "\n処罰情報: https://p.sykt.jp/" + ban_info[0]);


        }

    }
}
