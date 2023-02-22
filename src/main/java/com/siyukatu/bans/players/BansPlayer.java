package com.siyukatu.bans.players;

import com.siyukatu.bans.Bans;
import com.siyukatu.bans.ServerMode;

import java.util.UUID;

public interface BansPlayer {

    void sendMessage(String msg);

    boolean hasPermission(String permission);

    void kickPlayer(String msg);

    static BansPlayer getPlayer(UUID uuid) {
        if (Bans.mode == ServerMode.Bungee) {
            return BungeePlayer.getPlayer(uuid);
        }else if (Bans.mode == ServerMode.Bukkit) {
            return BukkitPlayer.getPlayer(uuid);

        }
        return null;

    }

    static BansPlayer getConsole() {
        if (Bans.mode == ServerMode.Bungee) {
            return BungeeConsole.getConsole();
        }else if (Bans.mode == ServerMode.Bukkit) {
            return BukkitConsole.getConsole();

        }
        return null;

    }

}
