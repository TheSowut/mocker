package io.github.thesowut.mocker;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class Mocker extends JavaPlugin {
    private FileConfiguration _config = this.getConfig();
    private ArrayList<String> mockedUsers;
    private final String mockerTitle = ChatColor.DARK_GRAY
            + "[" + ChatColor.DARK_PURPLE
            + "MOCKER"
            + ChatColor.DARK_GRAY + "] "
            + ChatColor.WHITE;

    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage(mockerTitle + ChatColor.GREEN + "Plugin enabled.");
        getServer().getPluginManager().registerEvents(new MockerListener(), this);
        mockedUsers = (ArrayList<String>) _config.getStringList("mocked");

        this.getCommand("mock").setExecutor(new onMock());

        _config.addDefault("mocked", new ArrayList<String>());
        _config.options().copyDefaults(true);
        saveConfig();
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(mockerTitle + ChatColor.RED + "Plugin disabled.");
    }

    public class MockerListener implements Listener {

        @EventHandler
        public void onPlayerChat(AsyncPlayerChatEvent event) {
            Player player = event.getPlayer();
            // If the user isn't in the list of mocked users, do not mock him.
            if (!mockedUsers.contains(player.getName().toLowerCase())) return;

            // Cancel the sending of chat message by player.
            event.setCancelled(true);
            // Mock the message sent by the player.
            String playerMessage = mockMessage(event.getMessage());
            // Send the mocked message as if the player said it.
            getServer().broadcastMessage(String.format("<%s> %s", player.getDisplayName(), playerMessage));
        }

        /**
         * Modify a message sent by the player in a mocking manner.
         *
         * @param message
         * @return
         */
        private String mockMessage(String message) {
            char[] messageAsArr = message.toCharArray();
            for (int i = 0; i < messageAsArr.length; i++) {
                // If the character is odd, invert its case.
                if (i % 2 != 0) {
                    messageAsArr[i] = Character.isLowerCase(messageAsArr[i])
                            ? Character.toUpperCase(messageAsArr[i])
                            : Character.toLowerCase(messageAsArr[i]);
                }
            }
            return new String(messageAsArr);
        }
    }

    /**
     * Invoked when a user calls the /mock method.
     * Toggles mocking of a chosen user.
     */
    private class onMock implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            final String playerName = args[0].toLowerCase();
            if (mockedUsers.contains(playerName)) {
                sender.sendMessage(mockerTitle + args[0] + " will no longer be mocked.");
                mockedUsers.remove(playerName);
            } else {
                sender.sendMessage(mockerTitle + args[0] + " will be mocked!");
                mockedUsers.add(playerName);
            }

            _config.set("mocked", mockedUsers);
            saveConfig();
            return true;
        }
    }
}
