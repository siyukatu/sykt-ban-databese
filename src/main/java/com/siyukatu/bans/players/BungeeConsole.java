package com.siyukatu.bans.players;

import com.siyukatu.bans.BungeeBans;
import net.md_5.bungee.api.CommandSender;

public class BungeeConsole implements BansObject {
    CommandSender sender;

    private BungeeConsole() {
        sender = BungeeBans.getInstance().getProxy().getConsole();

    }

    @Override
    @Deprecated
    public void sendMessage(String msg) {
        sender.sendMessage(msg);
    }

    @Override
    public boolean hasPermission(String permission) {
        return sender.hasPermission(permission);
    }

    static BansObject getConsole() {
        return new BungeeConsole();

    }

}
