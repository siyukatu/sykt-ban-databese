package com.siyukatu.bans.command;

import com.siyukatu.bans.players.BansPlayer;

import java.util.List;

public class HelpCommand implements ICommand {
    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "/ban-database help - get helps.";
    }

    @Override
    public boolean execute(BansPlayer player, List<String> args) {
        for (String description:CommandManager.getDescriptions()) {
            player.sendMessage(description);

        }
        return true;
    }
}
