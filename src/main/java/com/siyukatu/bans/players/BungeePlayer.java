package com.siyukatu.bans.players;

import com.siyukatu.bans.BungeeBans;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

public class BungeePlayer implements BansPlayer {

    ProxiedPlayer player;
    public BungeePlayer(ProxiedPlayer player) {
        this.player = player;

    }

    @Override
    @Deprecated
    public void sendMessage(String msg) {
        player.sendMessage(msg);

    }

    @Override
    public boolean hasPermission(String permission) {
        return player.hasPermission(permission);
    }

    @Override
    public UUID getUniqueId() {
        return player.getUniqueId();
    }

    @Override
    public void kick(String msg) {
        throw new IllegalArgumentException();

    }

    public static BansPlayer getPlayer(UUID uuid) {
        ProxiedPlayer p = BungeeBans.getInstance().getProxy().getPlayer(uuid);
        return new BungeePlayer(p);

    }

}
