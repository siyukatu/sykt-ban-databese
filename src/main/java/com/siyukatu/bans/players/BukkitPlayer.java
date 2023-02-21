package com.siyukatu.bans.players;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BukkitPlayer implements BansPlayer {
    Player player;
    public BukkitPlayer(Player player) {
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
        player.kickPlayer(msg);
    }

    public static BansPlayer getPlayer(UUID uuid) {
        Player p = Bukkit.getPlayer(uuid);
        return new BukkitPlayer(p);

    }
}
