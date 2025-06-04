package me.elpomoika.funarea.command;

import me.elpomoika.funarea.FunArea;
import me.elpomoika.funarea.command.subcommand.MenuOpenCommand;
import me.elpomoika.funarea.command.subcommand.model.SubCommand;
import me.elpomoika.funarea.menu.MainMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class AreaCommand implements CommandExecutor {

    private final Map<String, SubCommand> subCommands = new HashMap<>();
    private final FunArea plugin;
    private final MainMenu mainMenu;

    public void registerSubCommands() {
        subCommands.put("menu", new MenuOpenCommand(mainMenu));
    }

    public AreaCommand(FunArea plugin, MainMenu mainMenu) {
        this.plugin = plugin;
        this.mainMenu = mainMenu;
        registerSubCommands();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 0) {
            if (sender.hasPermission("funarea.admin")) {
                sender.sendMessage("§cДоступные команды: menu, ");
            }
            return true;
        }

        SubCommand subCommand = subCommands.get(args[0].toLowerCase());
        if (subCommand == null) {
            sender.sendMessage("§cНеизвестная подкоманда!");
            return true;
        }

        subCommand.execute(sender, args);
        return true;
    }
}
