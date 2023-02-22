package com.siyukatu.bans.players;

import com.siyukatu.bans.Bans;
import com.siyukatu.bans.ServerMode;

public interface BansObject {

    void sendMessage(String msg);

    boolean hasPermission(String permission);

    static BansObject getConsole() {
        if (Bans.mode == ServerMode.Bungee) {
            return BungeeConsole.getConsole();
        }else if (Bans.mode == ServerMode.Bukkit) {
            return BukkitConsole.getConsole();

        }
        return null;

    }

}
