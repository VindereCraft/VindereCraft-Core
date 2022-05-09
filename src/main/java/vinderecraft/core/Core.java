package vinderecraft.core;

// VindereCraft Imports
import vinderecraft.core.db.MySQL;
import vinderecraft.core.events.PlayerJoin;
import vinderecraft.core.events.PlayerQuit;
import vinderecraft.core.util.ConfigManager;

// Bukkit Imports
import org.bukkit.plugin.java.JavaPlugin;
import vinderecraft.core.util.DatabaseManager;

public final class Core extends JavaPlugin {
    // Global instances
    public ConfigManager Config = new ConfigManager(this);
    public DatabaseManager DB = new DatabaseManager();
    // Global variables
    public Boolean debug;
    public String playerDataStorageMethod;

    // Runs when the plugin is enabled
    @Override
    public void onEnable () {
        // Creates config files if they do not already exist and loads them if they do
        Config.initialize();
        // Updates global variables
        debug = Config.readBoolean("primary", "verbose-output");
        playerDataStorageMethod = Config.readString("primary", "player-data-storage-method");
        if (playerDataStorageMethod.equalsIgnoreCase("MYSQL")) {  }
        // Sends debug messages
        if (debug) {
            getServer().getConsoleSender().sendMessage("[VC CORE] Debug messages enabled.");
            getServer().getConsoleSender().sendMessage("[VC CORE] [DEBUG] Configuration successfully loaded.");
        }

        // TODO initialize database specified in 'config.yml' using 'util/DatabaseManager.java'

        // Registers event handlers.
        getServer().getPluginManager().registerEvents(new PlayerJoin(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuit(this), this);
        // Sends debug message
        if (debug) { getServer().getConsoleSender().sendMessage("[VC CORE] [DEBUG] Event handlers successfully registered."); }
        if (debug) { getServer().getConsoleSender().sendMessage("[VC CORE] [DEBUG] Player Data Storage Method: " + Config.readString("primary", "player-data-storage-method")); }

        DB.connect(playerDataStorageMethod, this);

        // Sends message to the console advising plugin is enabled
        getServer().getConsoleSender().sendMessage("[VC CORE] Plugin enabled.");
    }

    // Runs when the plugin is disabled
    @Override
    public void onDisable () {
        // Resets global variables to null on disable (maybe it helps?)
        debug = null;
        // Sends message to the console advising plugin is disabled
        getServer().getConsoleSender().sendMessage("[VC CORE] Plugin disabled.");
    }
}
