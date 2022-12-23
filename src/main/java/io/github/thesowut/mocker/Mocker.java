package io.github.thesowut.mocker;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Mocker extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Mocker] Plugin loaded.");
        getServer().getPluginManager().registerEvents(new MockerListener(), this);
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Mocker] Plugin disconnected.");
    }

    public class MockerListener implements Listener {

        @EventHandler
        public void onPlayerChat(AsyncPlayerChatEvent event) {
            // If the player is an OP, don't mock him.
            if (event.getPlayer().isOp()) return;

            // Cancel the sending of chat message by player.
            event.setCancelled(true);
            String playerName = event.getPlayer().getDisplayName();
            // Mock the message sent by the player.
            String playerMessage = mockMessage(event.getMessage());
            // Send the mocked message as if the player said it.
            getServer().broadcastMessage(String.format("<%s> %s", playerName, playerMessage));
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
}
