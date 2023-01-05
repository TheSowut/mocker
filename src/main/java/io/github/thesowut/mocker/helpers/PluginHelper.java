package io.github.thesowut.mocker.helpers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PluginHelper {
    public final String title = ChatColor.DARK_GRAY
            + "[" + ChatColor.DARK_PURPLE
            + "MOCKER"
            + ChatColor.DARK_GRAY + "] "
            + ChatColor.WHITE;

    /**
     * Send a chat message to the player & console after a player issues a plugin reload.
     *
     * @param player - Player who issued the reload
     */
    public void sendReloadMessage(Player player) {
        player.sendMessage(this.title + ChatColor.GREEN + "Plugin has been reloaded.");
        Bukkit.getServer().getConsoleSender().sendMessage(this.title + ChatColor.YELLOW +
                player.getName() + " issued a reload.");
    }
}
