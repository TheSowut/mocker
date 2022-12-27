package io.github.thesowut.mocker.listeners;

import io.github.thesowut.mocker.Mocker;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;

public class MockerListener implements Listener {
    private final ArrayList<String> _mockedUsers;
    private final Mocker _main;

    public MockerListener(Mocker main, ArrayList<String> mockedUsers) {
        this._main = main;
        this._mockedUsers = mockedUsers;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        // If the user isn't in the list of mocked users, do not mock him.
        if (!this._mockedUsers.contains(player.getName().toLowerCase())) return;

        // Cancel the sending of chat message by player.
        event.setCancelled(true);
        // Mock the message sent by the player.
        String playerMessage = mockMessage(event.getMessage());
        // Send the mocked message as if the player said it.
        this._main.getServer().broadcastMessage(String.format("<%s> %s", player.getDisplayName(), playerMessage));
    }

    /**
     * Modify a message sent by the player in a mocking manner.
     *
     * @param message - User chat message
     * @return - Mocked chat message
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