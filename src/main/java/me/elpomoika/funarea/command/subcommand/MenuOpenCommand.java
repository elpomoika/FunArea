package me.elpomoika.funarea.command.subcommand;

import me.elpomoika.funarea.command.subcommand.model.SubCommand;
import me.elpomoika.funarea.menu.MainMenu;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MenuOpenCommand implements SubCommand {

    private final MainMenu mainMenu;

    public MenuOpenCommand(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    @Override
    public String getUsage() {
        return "/area menu";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        mainMenu.openMenu(player);
    }
}
