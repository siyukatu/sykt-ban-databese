package com.siyukatu.bans;

import com.siyukatu.bans.command.CommandManager;
import com.siyukatu.bans.command.HelpCommand;
import com.siyukatu.bans.command.ReloadConfigCommand;
import com.siyukatu.bans.command.UserInfoCommand;
import com.siyukatu.bans.configuration.DefaultConfig;
import com.siyukatu.bans.listener.LoginEventBukkit;
import com.siyukatu.bans.players.BansObject;
import com.siyukatu.bans.players.BansPlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.List;

public final class BukkitBans extends JavaPlugin  {
    private static BukkitBans instance;
    private CommandManager manager;
    private DefaultConfig config;

    public BukkitBans() { }

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
        manager = new CommandManager(new HelpCommand(),
                new UserInfoCommand()
                , new ReloadConfigCommand()
                , new HelpCommand()
        );

        this.getCommand("ban-database").setExecutor((sender, command, label, args) -> {
            BansObject player;
            if (sender instanceof Player) {
                player = BansPlayer.getPlayer(((Player) sender).getUniqueId());
            } else {
                player = BansObject.getConsole();

            }

            if (args.length == 0) {
                return manager.execute(player, null, Collections.emptyList());

            } else {
                String command_name = args[0];
                List<String> list = List.of(args).subList(1, args.length);
                return manager.execute(player, command_name, list);

            }
        });

        // コンフィグの読み込み
        config = bans.getConfig();

        // イベントリスナーの追加
        getServer().getPluginManager().registerEvents(new LoginEventBukkit(config.getListString("whitelist")),this);

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

    public static BukkitBans getInstance() {
        return instance;
    }

}
