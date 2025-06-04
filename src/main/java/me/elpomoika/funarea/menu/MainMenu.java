package me.elpomoika.funarea.menu;

import me.elpomoika.funarea.FunArea;
import me.elpomoika.funarea.util.ConfigUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.CommandItem;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MainMenu {

    private final FunArea plugin;
    private final ConfigUtil config;

    public MainMenu(FunArea plugin, ConfigUtil config) {
        this.plugin = plugin;
        this.config = config;
    }

    public void openMenu(Player player) {
        Window window = Window.single()
                .setGui(createMenu(player))
                .setViewer(player)
                .build();

        window.open();
    }

    private Gui createMenu(Player player) {
        Gui gui = Gui.normal()
                .setStructure(
                        "# # x x x x x # #",
                        "# x x . b . x x #",
                        "# x s . . . p x #",
                        "# x x . c . x x #",
                        "# # x x x x x # #")
                .addIngredient('#', new SimpleItem(getFirstGlass()))
                .addIngredient('x', new SimpleItem(getSecondGlass()))
                .addIngredient('s', getSwordItem(player))
                .addIngredient('b', getBookItem())
                .build();

        return gui;
    }

    private CommandItem getSwordItem(Player player) {
        ItemBuilder swordItem = new ItemBuilder(Material.NETHERITE_SWORD)
                .addEnchantment(Enchantment.MENDING, 1, true)
                .setItemFlags(Collections.singletonList(ItemFlag.HIDE_ENCHANTS))
                .setDisplayName(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("menu.sword.displayname")))
                .setLegacyLore(getColoredLore("sword"));


        if (!isWearingFullNetherite(player)) {
            player.sendMessage(config.getNonFullNetheriteMessage());
            return new CommandItem(swordItem, "");
        } else {
            return new CommandItem(swordItem, config.getCommand());
        }
    }

    private SimpleItem getBookItem() {
        ItemBuilder bookItem = new ItemBuilder(Material.BOOK)
                .addEnchantment(Enchantment.MENDING, 1, true)
                .setItemFlags(Collections.singletonList(ItemFlag.HIDE_ENCHANTS))
                .setDisplayName(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("menu.book.displayname")))
                .setLegacyLore(getColoredLore("book"));

        return new SimpleItem(bookItem);
    }

    private boolean isWearingFullNetherite(Player player) {
        ItemStack[] armor = Objects.requireNonNull(player.getEquipment()).getArmorContents();
        if (armor == null || armor.length != 4) return false;

        int netheritePieces = 0;
        for (ItemStack item : armor) {
            if (item != null && item.getType().name().contains("NETHERITE")) {
                netheritePieces++;
            }
        }
        return netheritePieces == 4;
    }

    private ItemBuilder getFirstGlass() {
        return new ItemBuilder(Material.getMaterial(plugin.getConfig().getString("menu.glasses.first.material", "PAPER")))
                .setDisplayName(plugin.getConfig().getString("menu.glasses.first.displayname"));
    }

    private ItemBuilder getSecondGlass() {
        return new ItemBuilder(Material.getMaterial(plugin.getConfig().getString("menu.glasses.second.material", "PAPER")))
                .setDisplayName(plugin.getConfig().getString("menu.glasses.second.displayname"));
    }

    private List<String> getColoredLore(String item) {
        List<String> lore = plugin.getConfig().getStringList("menu." + item + ".lore");

        lore.replaceAll(s -> ChatColor.translateAlternateColorCodes('&', s));

        return lore;
    }
}
