package com.siyukatu.bans.command;

import com.siyukatu.bans.*;
import com.siyukatu.bans.configuration.DefaultConfig;
import com.siyukatu.bans.players.BansObject;

import java.util.List;

public class ReloadConfigCommand implements ICommand {
    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "/ban-database reload - リロードをします。";
    }

    @Override
    public boolean execute(BansObject player, List<String> args) {
        if (player.hasPermission("com.siyukatu.bans.reload")) {
            DefaultConfig config = null;
            if (Bans.mode == ServerMode.Bukkit) {
                config = BukkitBans.getInstance().getDefaultConfig();
            }else if (Bans.mode == ServerMode.Bungee) {
                config = BungeeBans.getInstance().getDefaultConfig();

            }
            assert config != null;
            config.load();
            new BanCheckRunner(config.getString("api_key"), config.getString("debug_level").equals("debug"), config.getListString("whitelist"));
            player.sendMessage("[しゆかつBANデータベース] ロードが完了しました。");
            return true;
        }

        return true;

    }
}
