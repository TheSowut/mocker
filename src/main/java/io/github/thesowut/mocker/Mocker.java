package io.github.thesowut.mocker;

import io.github.thesowut.mocker.commands.MockerCommands;
import io.github.thesowut.mocker.helpers.FileHelper;
import io.github.thesowut.mocker.helpers.PluginHelper;
import io.github.thesowut.mocker.listeners.MockerListener;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class Mocker extends JavaPlugin {
    private final FileConfiguration _config = this.getConfig();
    private final PluginHelper _pluginHelper = new PluginHelper();
    private final FileHelper _fileHelper = new FileHelper(this, _pluginHelper);
    private ArrayList<String> _mockedUsers = (new ArrayList<>());
    private final MockerCommands _commands = new MockerCommands(_fileHelper, _pluginHelper, _mockedUsers);
    private final MockerListener _listener = new MockerListener(this, _mockedUsers);

    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage(this._pluginHelper.title + ChatColor.GREEN + "Plugin enabled.");
        getServer().getPluginManager().registerEvents(this._listener, this);
        this.loadMockedUsers();
        this.setDefaultData();

        this._mockedUsers = (ArrayList<String>) _fileHelper.getMockedUsers().getStringList("mocked");
        this.getCommand("mock").setExecutor(_commands);
    }

    /**
     * Create & load list of mocked users.
     */
    private void loadMockedUsers() {
        _fileHelper.setup();
        _fileHelper.getMockedUsers().options().copyDefaults(true);
        _fileHelper.saveMockedUsers();
        saveConfig();
    }

    /**
     * Set default values for mocked users.
     */
    private void setDefaultData() {
        _fileHelper.getMockedUsers().addDefault("mocked", new ArrayList<String>());
        _fileHelper.getMockedUsers().options().copyDefaults(true);
        _fileHelper.saveMockedUsers();
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(_pluginHelper.title + ChatColor.RED + "Plugin disabled.");
    }
}
