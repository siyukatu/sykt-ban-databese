package com.siyukatu.bans.command;

import com.siyukatu.bans.players.BansPlayer;

import java.util.List;

public interface ICommand {

    String getName();

    String getDescription();

    boolean execute(BansPlayer player, List<String> args);

}
