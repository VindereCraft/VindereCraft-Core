package vinderecraft.core;

// VindereCraft Imports
import vinderecraft.core.cmd.CoreCmd;
import vinderecraft.core.events.PlayerJoin;
import vinderecraft.core.events.PlayerQuit;
import vinderecraft.core.util.DatabaseManager;
import vinderecraft.core.util.ConfigManager;

// Bukkit Imports
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;

public final class Core extends JavaPlugin {
    // Global instances
    public ConfigManager Config = new ConfigManager(this);
    public DatabaseManager DB = new DatabaseManager();
    // Global variables
    public Boolean debug;
    public String playerDataStorageMethod;
    public Connection conn;

    // Runs when the plugin is enabled
    @Override
    public void onEnable () {
        // Creates config files if they do not already exist and loads them if they do
        Config.initialize();
        // Updates global variables
        debug = Config.readBoolean("primary", "verbose-output");

        // Retrieves player data storage method
        playerDataStorageMethod = Config.readString("primary", "player-data-storage-method");
        // Sends debug message.
        if (debug) { getServer().getConsoleSender().sendMessage("[VC CORE] [DEBUG] Player Data Storage Method: " + Config.readString("primary", "player-data-storage-method")); }
        // Connects to database service specified in 'config.yml'

        if (Config.readString("primary", "player-data-storage-method").equalsIgnoreCase("mysql")) {
            conn = DB.connectMYSQL(this);
            if (debug) { getServer().getConsoleSender().sendMessage("[VC CORE] [DEBUG] MySQL database connected."); }
        }

        // Sends debug messages
        if (debug) {
            getServer().getConsoleSender().sendMessage("[VC CORE] Debug messages enabled.");
            getServer().getConsoleSender().sendMessage("[VC CORE] [DEBUG] Configuration successfully loaded.");
        }

        // Registers event handlers
        getServer().getPluginManager().registerEvents(new PlayerJoin(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuit(this), this);
        // Sends debug messages
        if (debug) { getServer().getConsoleSender().sendMessage("[VC CORE] [DEBUG] Event handlers successfully registered."); }

        // Sets command executors
        this.getCommand("core").setExecutor(new CoreCmd(this));
        // Sends debug messages
        if (debug) { getServer().getConsoleSender().sendMessage("[VC CORE] [DEBUG] Command executors successfully registered."); }

        // Sends message to the console advising plugin is enabled
        getServer().getConsoleSender().sendMessage("[VC CORE] Plugin enabled.");
    }

    // Runs when the plugin is disabled
    @Override
    public void onDisable () {
        if (Config.readString("primary", "player-data-storage-method").equalsIgnoreCase("mysql")) {
            DB.terminateSQL(this, this.conn);
            if (debug) { getServer().getConsoleSender().sendMessage("[VC CORE] [DEBUG] MySQL database disconnected."); }
        }
        // Resets global variables to null on disable (maybe it helps?)
        debug = null;
        // Sends message to the console advising plugin is disabled
        getServer().getConsoleSender().sendMessage("[VC CORE] Plugin disabled.");
    }
}
