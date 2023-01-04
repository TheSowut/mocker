package io.github.thesowut.mocker.commands;

import io.github.thesowut.mocker.helpers.FileHelper;
import io.github.thesowut.mocker.helpers.PluginHelper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MockerCommands implements CommandExecutor {
    private final FileHelper _fileHelper;
    private final PluginHelper _pluginHelper;
    private final ArrayList<String> _mockedUsers;

    public MockerCommands(
            FileHelper fileHelper,
            PluginHelper pluginHelper,
            ArrayList<String> mockedUsers
    ) {
        this._fileHelper = fileHelper;
        this._pluginHelper = pluginHelper;
        this._mockedUsers = mockedUsers;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        final String playerName = args[0].toLowerCase();
        if (_mockedUsers.contains(playerName)) {
            sender.sendMessage(_pluginHelper.title + args[0] + " will no longer be mocked.");
            _mockedUsers.remove(playerName);
        } else {
            sender.sendMessage(_pluginHelper.title + args[0] + " will be mocked!");
            _mockedUsers.add(playerName);
        }

        _fileHelper.getMockedUsers().set("mocked", _mockedUsers);
        _fileHelper.saveMockedUsers();
        return true;
    }
}
