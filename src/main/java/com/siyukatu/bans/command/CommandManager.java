package com.siyukatu.bans.command;

import com.siyukatu.bans.players.BansPlayer;

import java.util.ArrayList;
import java.util.List;

public final class CommandManager {
    private static final List<ICommand> commands = new ArrayList<>();
    private final ICommand helpCommand;

    public CommandManager(ICommand helpCommand, ICommand ... commands) {
        this.helpCommand = helpCommand;
        if (commands != null) {
            register(commands);

        }

    }

    public void register(ICommand ... commands) {
        CommandManager.commands.addAll(List.of(commands));

    }

    public void unRegister(ICommand ... commands) {
        CommandManager.commands.removeAll(List.of(commands));

    }

    public void AllUnRegister() {
        for (ICommand cmd:commands) {
            unRegister(cmd);

        }

    }

    public boolean execute(BansPlayer player, String name, List<String> args) {
        if (name == null) {
            helpCommand.execute(player, args);
            return true;

        }
        for (ICommand cmd:commands) {
            if (cmd.getName().equalsIgnoreCase(name)) {
                return cmd.execute(player, args);

            }

        }

        return true;

    }

    public static List<String> getDescriptions() {
        List<String> descriptions = new ArrayList<>();
        for (ICommand cmd:commands) {
            descriptions.add(cmd.getDescription());

        }
        return descriptions;

    }

}
