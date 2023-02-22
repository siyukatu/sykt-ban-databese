package com.siyukatu.bans.command;

import com.siyukatu.bans.players.BansObject;

import java.util.List;

public interface ICommand {

    String getName();

    String getDescription();

    boolean execute(BansObject player, List<String> args);

}
