package me.elpomoika.funarea.listener;

import me.elpomoika.funarea.FunArea;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class AreaBoundListener implements Listener {
    private final FunArea plugin;
    private Location minPoint;
    private Location maxPoint;
    private String worldName;

    public AreaBoundListener(FunArea plugin) {
        this.plugin = plugin;
        loadArenaZone();
    }

    public void loadArenaZone() {
        FileConfiguration config = plugin.getConfig();

        double minX = config.getDouble("arena.min.x");
        double minY = config.getDouble("arena.min.y");
        double minZ = config.getDouble("arena.min.z");

        double maxX = config.getDouble("arena.max.x");
        double maxY = config.getDouble("arena.max.y");
        double maxZ = config.getDouble("arena.max.z");

        worldName = config.getString("arena.world");

        minPoint = new Location(Bukkit.getWorld(worldName), minX, minY, minZ);
        maxPoint = new Location(Bukkit.getWorld(worldName), maxX, maxY, maxZ);
    }

    private boolean isInArenaZone(Location location) {
        if (!location.getWorld().getName().equals(worldName)) {
            return false;
        }

        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();

        return x >= minPoint.getX() && x <= maxPoint.getX() &&
                y >= minPoint.getY() && y <= maxPoint.getY() &&
                z >= minPoint.getZ() && z <= maxPoint.getZ();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.isOnline()) {
                    cancel();
                    return;
                }

                boolean inArena = isInArenaZone(player.getLocation());
                if (inArena) {
                    player.sendActionBar("§aВы в арене!");
                }
            }
        }.runTaskTimer(plugin, 0, 20);
    }
}
