package vinderecraft.core.events;

// VindereCraft Imports
import vinderecraft.core.Core;

// Bukkit Imports
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    // Allows access to 'vinderecraft.core.Core.java'
    private final Core core;
    public PlayerQuit (Core core) {
        this.core = core;
    }

    // Runs when a player quits/leaves
    @EventHandler
    public void onPlayerQuit (PlayerQuitEvent e) {
        if (core.debug) { core.getServer().getConsoleSender().sendMessage("[VC CORE] [DEBUG] Player " + e.getPlayer().getName() + " left the server."); }
    }
}
