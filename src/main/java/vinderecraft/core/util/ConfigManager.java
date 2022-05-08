package vinderecraft.core.util;

import vinderecraft.core.Core;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    // Allows vinderecraft.core.Core to create an instance of this class.
    private final Core core;
    public ConfigManager (Core core) {
        this.core = core;
    }

    // Initiates private variables for creating and loading configuration files.
    private File primaryFile;
    private FileConfiguration primaryFileConfig;
    private File SQLFile;
    private FileConfiguration SQLFileConfig;

    // Method to see if configuration files have been created and also loads them.
    public void initialize () {
        File dir = core.getDataFolder();

        // Checks to see if the 'VindereCraft-Core' directory exists inside of the 'Plugins' folder
        if (!core.getDataFolder().exists()){
            boolean directoryCreated;
            directoryCreated = dir.mkdirs();
            if (directoryCreated) {
                core.getServer().getConsoleSender().sendMessage("[VC CORE] Created configuration directory.");
            } else {
                core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] Could not create configuration directory.");
            }
        } else {
            core.getServer().getConsoleSender().sendMessage("[VC CORE] Found configuration directory.");
        }

        // Instantiates Files
        primaryFile = new File(dir, "config.yml");
        SQLFile = new File(dir, "sql.yml");
        // Checks to see if config files already exist in the plugin directory. If not, it creates them.
        if (!primaryFile.exists()) {
            core.saveResource("config.yml", false);
            primaryFileConfig = YamlConfiguration.loadConfiguration(primaryFile);
            core.getServer().getConsoleSender().sendMessage("[VC CORE] config.yml created using default values.");
        } else {
            primaryFileConfig = YamlConfiguration.loadConfiguration(primaryFile);
            core.getServer().getConsoleSender().sendMessage("[VC CORE] config.yml loaded.");
        }

        if (!SQLFile.exists()) {
            core.saveResource("sql.yml", false);
            SQLFileConfig = YamlConfiguration.loadConfiguration(SQLFile);
            core.getServer().getConsoleSender().sendMessage("[VC CORE] sql.yml created using default values.");
        } else {
            SQLFileConfig = YamlConfiguration.loadConfiguration(SQLFile);
            core.getServer().getConsoleSender().sendMessage("[VC CORE] sql.yml loaded.");
        }
    }

    // Used to get a string from the specified path of a config file
    public String readString (String config, String datapath) {
        if (config.equalsIgnoreCase("primary")) {
            return (String) primaryFileConfig.get(datapath);
        } else if (config.equalsIgnoreCase("sql")) {
            return (String) SQLFileConfig.get(datapath);
        } else return null;
    }

    // Used to get a boolean from the specified path of a config file
    public Boolean readBoolean (String config, String datapath) {
        if (config.equalsIgnoreCase("primary")) {
            return  primaryFileConfig.getBoolean(datapath);
        } else if (config.equalsIgnoreCase("sql")) {
            return SQLFileConfig.getBoolean(datapath);
        } else return null;
    }

    // Used to get an integer from the specified path of a config file
    public int readInt (String config, String datapath) {
        if (config.equalsIgnoreCase("primary")) {
            return (int) primaryFileConfig.getInt(datapath);
        } else if (config.equalsIgnoreCase("sql")) {
            return (int) SQLFileConfig.getInt(datapath);
        } else return 0;
    }

    // Used to get an instance of a FileConfiguration instance created earlier on
    public FileConfiguration getConfig (String config) {
        // Checks to see which FileConfiguration needs to be retrieved
        if (config.equalsIgnoreCase("primary")) {
            return primaryFileConfig;
        } else if (config.equalsIgnoreCase("sql")) {
            return SQLFileConfig;
        }
        return null;
    }

    // Used to save any changes to a configuration file
    public void saveConfig (String config) {
        // Checks to see which config needs to be saved
        if (config.equalsIgnoreCase("primary")) {
            try {
                primaryFileConfig.save(primaryFile);
                // Sends debug message
                if (core.debug) { core.getServer().getConsoleSender().sendMessage("[VC CORE] [DEBUG] config.yml saved."); }
            } catch (IOException e) {
                core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] config.yml not saved.");
            }
        } else if (config.equalsIgnoreCase("sql")) {
            try {
                SQLFileConfig.save(SQLFile);
                // Sends debug message
                if (core.debug) { core.getServer().getConsoleSender().sendMessage("[VC CORE] [DEBUG] sql.yml saved."); }
            } catch (IOException e) {
                core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] sql.yml was not saved.");
            }
        }
    }
}
