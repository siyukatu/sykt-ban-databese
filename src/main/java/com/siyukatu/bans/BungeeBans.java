package com.siyukatu.bans;

import com.siyukatu.bans.command.*;
import com.siyukatu.bans.configuration.DefaultConfig;
import com.siyukatu.bans.listener.LoginEventBungee;
import com.siyukatu.bans.players.BansPlayer;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.Collections;
import java.util.List;

public final class BungeeBans extends Plugin {
    private static BungeeBans instance;
    private CommandManager manager;

    private DefaultConfig config;

    public BungeeBans() { }

    @Override
    public void onEnable() {
        getLogger().info(ChatColor.RED + " * " + ChatColor.RESET + "起動中... (³ω³).｡o");

        // モードの設定
        Bans.mode = ServerMode.Bukkit;

        // インスタンス
        instance = this;

        // メインクラスの読み込み
        Bans bans = new Bans();

        // コンフィグの読み込み
        config = bans.getConfig();

        // イベントリスナーの追加
        getProxy().getPluginManager().registerListener(this, new LoginEventBungee(config.getListString("whitelist")));

        // コマンドクラスの保持
        manager = new CommandManager(new HelpCommand() ,
                new UserInfoCommand()
                , new ReloadConfigCommand()
                , new HelpCommand()
        );

        getProxy().getPluginManager().registerCommand(this, new Command("ban-database") {
            @Override
            public void execute(CommandSender sender, String[] args) {
                BansPlayer player;
                if (sender instanceof ProxiedPlayer) {
                    player = BansPlayer.getPlayer(((ProxiedPlayer) sender).getUniqueId());
                }else {
                   player = BansPlayer.getConsole();

                }

                if (args.length == 0) {
                    manager.execute(player, null,  Collections.emptyList());

                }else {
                    String command_name = args[0];
                    List<String> list = List.of(args).subList(1, args.length);
                    manager.execute(player, command_name, list);

                }

            }
        });

        getLogger().info(ChatColor.GREEN + " * " + ChatColor.RESET + "起動完了 ｷﾀ――――(ﾟ∀ﾟ)――――!!");

    }

    @Override
    public void onDisable() {
        manager.AllUnRegister();
        getLogger().info(ChatColor.RED + " * " + ChatColor.RESET + "終了しました ( ˘ω˘ )ｽﾔｧ… ");

    }

    public DefaultConfig getDefaultConfig() {
        return config;

    }

    public static BungeeBans getInstance() {
        return instance;
    }
}
