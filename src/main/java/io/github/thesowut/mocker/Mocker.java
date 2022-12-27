package io.github.thesowut.mocker;

import io.github.thesowut.mocker.commands.MockerCommands;
import io.github.thesowut.mocker.helpers.PluginHelper;
import io.github.thesowut.mocker.listeners.MockerListener;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class Mocker extends JavaPlugin {
    private final FileConfiguration _config = this.getConfig();
    private final ArrayList<String> mockedUsers = (ArrayList<String>) _config.getStringList("mocked");
    private final PluginHelper _pluginHelper = new PluginHelper();
    private final MockerCommands _commands = new MockerCommands(this, _config, _pluginHelper, mockedUsers);
    private final MockerListener _listener = new MockerListener(this, mockedUsers);

    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage(this._pluginHelper.title + ChatColor.GREEN + "Plugin enabled.");
        getServer().getPluginManager().registerEvents(this._listener, this);

        this.getCommand("mock").setExecutor(_commands);

        _config.addDefault("mocked", new ArrayList<String>());
        _config.options().copyDefaults(true);
        saveConfig();
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(_pluginHelper.title + ChatColor.RED + "Plugin disabled.");
    }
}
