package me.elpomoika.funarea;

import me.elpomoika.funarea.command.AreaCommand;
import me.elpomoika.funarea.listener.AreaBoundListener;
import me.elpomoika.funarea.menu.MainMenu;
import me.elpomoika.funarea.util.ConfigUtil;
import org.bukkit.plugin.java.JavaPlugin;

public final class FunArea extends JavaPlugin {
    private MainMenu mainMenu;
    private ConfigUtil configUtil;

    @Override
    public void onEnable() {
        configUtil = new ConfigUtil(this);
        mainMenu = new MainMenu(this, configUtil);
        getCommand("area").setExecutor(new AreaCommand(this, mainMenu));

        getServer().getPluginManager().registerEvents(new AreaBoundListener(this), this);
    }
}
