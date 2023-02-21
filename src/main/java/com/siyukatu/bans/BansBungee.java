package com.siyukatu.bans;

import com.siyukatu.bans.command.ICommand;
import com.siyukatu.bans.command.ReloadConfigCommand;
import com.siyukatu.bans.command.UserInfoCommand;
import com.siyukatu.bans.listener.LoginEventBungee;
import com.siyukatu.bans.configuration.DefaultConfig;
import com.siyukatu.bans.players.BansPlayer;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public final class BansBungee extends Plugin {
    private static BansBungee instance;
    List<ICommand> commands;

    @Override
    public void onEnable() {
        getLogger().info(ChatColor.RED + " * " + ChatColor.RESET + "起動中... (³ω³).｡o");

        // モードの設定
        Bans.mode = ServerMode.Bukkit;

        // インスタンス
        instance = this;

        // メインクラスの読み込み
        new Bans();

        // コンフィグの読み込み
        DefaultConfig config = Bans.getConfig();

        // イベントリスナーの追加
        getProxy().getPluginManager().registerListener(this, new LoginEventBungee(config.getListString("whitelist")));

        // コマンドクラスの保持
        commands = new ArrayList<>();
        commands.add(new ReloadConfigCommand());
        commands.add(new UserInfoCommand());

        getProxy().getPluginManager().registerCommand(this, new Command("ban-database") {
            @Override
            public void execute(CommandSender sender, String[] args) {
                for (ICommand cmd:commands) {
                    if (sender instanceof ProxiedPlayer) {
                        cmd.execute(BansPlayer.getPlayer(((ProxiedPlayer) sender).getUniqueId()), List.of(args));
                    }
                }
            }
        });

        getLogger().info(ChatColor.GREEN + " * " + ChatColor.RESET + "起動完了 ｷﾀ――――(ﾟ∀ﾟ)――――!!");

    }

    @Override
    public void onDisable() {
        getLogger().info(ChatColor.RED + " * " + ChatColor.RESET + "終了しました ( ˘ω˘ )ｽﾔｧ… ");

    }

    public static BansBungee getInstance() {
        return instance;
    }
}
