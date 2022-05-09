package vinderecraft.core.events;

// VindereCraft Imports
import vinderecraft.core.Core;

// Bukkit Imports
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {
    // Allows access to 'vinderecraft.core.Core.java'
    private final Core core;
    public PlayerJoin (Core core) {
        this.core = core;
    }

    // Runs when a player joins/loads
    @EventHandler
    public void onPlayerJoin (PlayerJoinEvent e) {
        // Sends debug message
        if (core.debug) { core.getServer().getConsoleSender().sendMessage("[VC CORE] [DEBUG] Player " + e.getPlayer().getName() + " joined the server."); }
    }
}
