package com.siyukatu.bans;

import com.siyukatu.bans.command.ICommand;
import com.siyukatu.bans.command.ReloadConfigCommand;
import com.siyukatu.bans.command.UserInfoCommand;
import com.siyukatu.bans.configuration.DefaultConfig;
import com.siyukatu.bans.listener.LoginEventBukkit;
import com.siyukatu.bans.players.BansPlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class BansBukkit extends JavaPlugin  {
    private static BansBukkit instance;
    List<ICommand> commands;

    @Override
    public void onEnable() {
        getLogger().info(ChatColor.RED + " * " + ChatColor.RESET + "起動中... (³ω³).｡o");

        // モードの設定
        Bans.mode = ServerMode.Bukkit;

        // インスタンス
        instance = this;

        // メインクラスの読み込み
       Bans bans = new Bans();

        // コマンドクラスの保持
        commands = new ArrayList<>();
        commands.add(new ReloadConfigCommand());
        commands.add(new UserInfoCommand());
        this.getCommand("ban-database").setExecutor((sender, command, label, args) -> {
            for (ICommand cmd:commands) {
                if (sender instanceof Player) {
                    return cmd.execute(BansPlayer.getPlayer(((Player) sender).getUniqueId()), List.of(args));
                }

            }
            return true;
        });

        // コンフィグの読み込み
        DefaultConfig config = Bans.getConfig();

        // イベントリスナーの追加
        getServer().getPluginManager().registerEvents(new LoginEventBukkit(config.getListString("whitelist")),this);

        getLogger().info(ChatColor.GREEN + " * " + ChatColor.RESET + "起動完了 ｷﾀ――――(ﾟ∀ﾟ)――――!!");

    }

    @Override
    public void onDisable() {
        getLogger().info(ChatColor.RED + " * " + ChatColor.RESET + "終了しました ( ˘ω˘ )ｽﾔｧ… ");

    }

    public static BansBukkit getInstance() {
        return instance;
    }

}
