package io.github.thesowut.mocker.listeners;

import io.github.thesowut.mocker.Mocker;
import io.github.thesowut.mocker.helpers.FileHelper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;

public class MockerListener implements Listener {
    private final Mocker _main;
    private final FileHelper _fileHelper;
    private final ArrayList<String> _mockedUsers;

    public MockerListener(Mocker main, FileHelper fileHelper, ArrayList<String> mockedUsers) {
        this._main = main;
        this._fileHelper = fileHelper;
        this._mockedUsers = mockedUsers;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        // Check if all non op users should be mocked and player is non op.
        if (_fileHelper.shouldMockAllNonOpPlayers() && !player.isOp()) {
            this.sendMessage(event);
            return;
        }
        // If the user isn't in the list of mocked users, do not mock him.
        if (!this._mockedUsers.contains(player.getName().toLowerCase())) return;
        this.sendMessage(event);
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

    /**
     * Send the player's chat message in a mocking manner.
     *
     * @param event - The chat event.
     */
    private void sendMessage(AsyncPlayerChatEvent event) {
        // Cancel the sending of chat message by player.
        event.setCancelled(true);
        // Mock the message sent by the player.
        String playerMessage = mockMessage(event.getMessage());
        // Send the mocked message as if the player said it.
        this._main.getServer().broadcastMessage(
                String.format("<%s> %s",
                        event.getPlayer().getDisplayName(),
                        playerMessage)
        );
    }
}