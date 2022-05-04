package vinderecraft.core;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import vinderecraft.core.events.PlayerJoin;

public final class Core extends JavaPlugin{

    // Runs when the plugin is enabled.
    @Override
    public void onEnable() {

        // Sends message to the console advising plugin is enabled.
        getServer().getConsoleSender().sendMessage("[VC CORE] Plugin enabled.");

        // Designates 'vinderecraft.core.events.PlayerJoin' as the class to handle player join events.
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(this), this);
    }

    // Runs when the plugin is disabled.
    @Override
    public void onDisable() {

        // Sends message to the console advising plugin is disabled.
        getServer().getConsoleSender().sendMessage("[VC CORE] Plugin disabled.");
    }
}
