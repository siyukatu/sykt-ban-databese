package com.siyukatu.bans.players;

import com.siyukatu.bans.BansBungee;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

public class BungeePlayer implements BansPlayer {

    ProxiedPlayer player;
    public BungeePlayer(ProxiedPlayer player) {
        this.player = player;

    }

    @Override
    public void sendMessage(String msg) {
        player.sendMessage(msg);

    }

    @Override
    public boolean hasPermission(String permission) {
        return player.hasPermission(permission);
    }

    @Override
    public void kickPlayer(String msg) {

    }

    public static BansPlayer getPlayer(UUID uuid) {
        ProxiedPlayer p = BansBungee.getInstance().getProxy().getPlayer(uuid);
        return new BungeePlayer(p);

    }

}
