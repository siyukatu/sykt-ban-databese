package com.siyukatu.bans.command;

import com.siyukatu.bans.Bans;
import com.siyukatu.bans.Util;
import com.siyukatu.bans.players.BansObject;

import java.util.List;

public class UserInfoCommand implements ICommand {

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "/ban-database info <user_name> - データベースに保存されているものからユーザー名に指定されたユーザーがBanされている確認します。";
    }

    @Override
    public boolean execute(BansObject player, List<String> args) {
        String content = Bans.database.get(Util.getUUID(args.get(0)).toString());
        String banned = "このプレイヤーはBanされていま" + (content == null ? "せん" : "す") + "。";
        String[] ban_info;
        if (content != null) {
            ban_info = content.split(",", 2);
        }else {
            ban_info = new String[]{"No content", "No content"};
        }
        player.sendMessage("[しゆかつBANデータベース] " + banned + "\n" + ban_info[1] + "\n処罰情報: https://p.sykt.jp/" + ban_info[0]);
        return true;

    }
}
