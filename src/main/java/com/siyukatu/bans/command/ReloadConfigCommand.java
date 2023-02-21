package com.siyukatu.bans.command;

import com.siyukatu.bans.Bans;
import com.siyukatu.bans.configuration.DefaultConfig;
import com.siyukatu.bans.players.BansPlayer;

import java.util.List;

public class ReloadConfigCommand implements ICommand {
    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public boolean execute(BansPlayer player, List<String> args) {
        if (player.hasPermission("com.siyukatu.bans.reload")) {
            // コンフィグの読み込み
            DefaultConfig config = Bans.getConfig();
            config.load();
            player.sendMessage("[しゆかつBANデータベース]\nロードが完了しました。");
            return true;
        }

        return true;

    }
}
