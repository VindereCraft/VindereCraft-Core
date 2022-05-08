package vinderecraft.core;

// VindereCraft Imports
import vinderecraft.core.events.PlayerJoin;
import vinderecraft.core.util.ConfigManager;

// Bukkit Imports
import org.bukkit.plugin.java.JavaPlugin;

public final class Core extends JavaPlugin {
    // Global instances
    public ConfigManager Config = new ConfigManager(this);
    // Global variables
    public Boolean debug;

    // Runs when the plugin is enabled
    @Override
    public void onEnable () {
        // Creates config files if they do not already exist and loads them if they do
        Config.initialize();
        // Updates debug boolean if 'verbose-output' is 'true' in 'config.yml'
        debug = Config.readBoolean("primary", "verbose-output");
        // Sends debug messages
        if (debug) {
            getServer().getConsoleSender().sendMessage("[VC CORE] Debug messages enabled.");
            getServer().getConsoleSender().sendMessage("[VC CORE] [DEBUG] Configuration successfully loaded.");
        }

        // TODO initialize database specified in 'config.yml' using 'util/DatabaseManager.java'

        // Registers event handlers.
        getServer().getPluginManager().registerEvents(new PlayerJoin(this), this);
        // Sends debug message
        if (debug) { getServer().getConsoleSender().sendMessage("[VC CORE] [DEBUG] Event handlers successfully registered."); }

        // Sends message to the console advising plugin is enabled
        getServer().getConsoleSender().sendMessage("[VC CORE] Plugin enabled.");
    }

    // Runs when the plugin is disabled
    @Override
    public void onDisable () {
        // Resets global variables to null on disable
        debug = null;
        // Sends message to the console advising plugin is disabled
        getServer().getConsoleSender().sendMessage("[VC CORE] Plugin disabled.");
    }
}
