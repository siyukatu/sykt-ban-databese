package com.siyukatu.bans.players;

import com.siyukatu.bans.Bans;
import com.siyukatu.bans.ServerMode;

import java.util.UUID;

public interface BansPlayer extends BansObject {

    UUID getUniqueId();

    void kick(String msg);

    static BansPlayer getPlayer(UUID uuid) {
        if (Bans.mode == ServerMode.Bungee) {
            return BungeePlayer.getPlayer(uuid);
        }else if (Bans.mode == ServerMode.Bukkit) {
            return BukkitPlayer.getPlayer(uuid);

        }
        return null;

    }

}
