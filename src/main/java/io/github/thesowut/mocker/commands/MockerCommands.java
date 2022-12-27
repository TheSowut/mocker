package io.github.thesowut.mocker.commands;

import io.github.thesowut.mocker.Mocker;
import io.github.thesowut.mocker.helpers.PluginHelper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;

public class MockerCommands implements CommandExecutor {
    private final Mocker _main;
    private final FileConfiguration _config;
    private final PluginHelper _pluginHelper;
    private final ArrayList<String> _mockedUsers;

    public MockerCommands(
            Mocker main,
            FileConfiguration config,
            PluginHelper pluginHelper,
            ArrayList<String> mockedUsers
    ) {
        this._main = main;
        this._config = config;
        this._pluginHelper = pluginHelper;
        this._mockedUsers = mockedUsers;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        final String playerName = args[0].toLowerCase();
        if (_mockedUsers.contains(playerName)) {
            sender.sendMessage(_pluginHelper.title + args[0] + " will no longer be mocked.");
            _mockedUsers.remove(playerName);
        } else {
            sender.sendMessage(_pluginHelper.title + args[0] + " will be mocked!");
            _mockedUsers.add(playerName);
        }

        _config.set("mocked", _mockedUsers);
        _main.saveConfig();
        return true;
    }
}
