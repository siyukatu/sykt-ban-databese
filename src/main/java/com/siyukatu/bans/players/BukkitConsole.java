package com.siyukatu.bans.players;

import com.siyukatu.bans.BukkitBans;
import org.bukkit.command.ConsoleCommandSender;

public class BukkitConsole implements BansPlayer {
    ConsoleCommandSender sender;

    private BukkitConsole() {
        sender = BukkitBans.getInstance().getServer().getConsoleSender();

    }

    @Override
    public void sendMessage(String msg) {
        sender.sendMessage(msg);
    }

    @Override
    public boolean hasPermission(String permission) {
        return sender.hasPermission(permission);
    }

    @Override
    public void kickPlayer(String msg) {
        throw new IllegalArgumentException();
    }

    static BansPlayer getConsole() {
        return new BukkitConsole();

    }

}
