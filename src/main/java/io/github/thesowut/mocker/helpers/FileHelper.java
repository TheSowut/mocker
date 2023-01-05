package io.github.thesowut.mocker.helpers;

import io.github.thesowut.mocker.Mocker;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileHelper {
    public enum ConfigKeys {
        mock_non_op_users
    }
    private final Mocker _main;
    private final PluginHelper _pluginHelper;
    private File _mockedUsers;
    private FileConfiguration _mockedUsersConfig;

    public FileHelper(Mocker main, PluginHelper pluginHelper) {
        this._main = main;
        this._pluginHelper = pluginHelper;
    }

    /**
     * Set up the data file storing list of mocked users.
     */
    public void setup() {
        _mockedUsers = new File(_main.getDataFolder(), "mocked_users.yml");
        this.createFile(_mockedUsers);

        _mockedUsersConfig = YamlConfiguration.loadConfiguration(_mockedUsers);
    }

    /**
     * Get data from mocked_users.yml.
     *
     * @return - List of mocked users.
     */
    public FileConfiguration getMockedUsers() {
        return _mockedUsersConfig;
    }

    /**
     * Save mocked_users.yml.
     */
    public void saveMockedUsers() {
        try {
            _mockedUsersConfig.save(_mockedUsers);
        } catch (IOException e) {
            _main.getServer().getLogger().severe(_pluginHelper.title + ChatColor.RED
                    + "Error saving " + _mockedUsers.getName());
        }
    }

    /**
     * Get mock_non_op_users flag value from config.yml
     *
     * @return Whether all non op players should be mocked.
     */
    public boolean shouldMockAllNonOpPlayers() {
        return _main.getConfig().get(String.valueOf(ConfigKeys.mock_non_op_users)).equals(true);
    }

    /**
     * Reload the plugin data files.
     */
    public void reload() {
        _main.reloadConfig();
        _mockedUsersConfig = YamlConfiguration.loadConfiguration(_mockedUsers);
    }

    /**
     * Try creating a file.
     *
     * @param file - File to be created.
     */
    private void createFile(File file) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                _main.getServer().getLogger().severe(_pluginHelper.title + ChatColor.RED + "Error creating " + file.getName());
            }
        }
    }
}
