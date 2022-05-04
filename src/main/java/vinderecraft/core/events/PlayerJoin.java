package vinderecraft.core.events;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import vinderecraft.core.Core;

public class PlayerJoin implements Listener {

    // Allows 'vinderecraft.core.Core' to designate this class as an event listener.
    private Core core;
    public PlayerJoin(Core instance){
        core = instance;
    }

    // Runs when a player joins.
    public void PlayerJoin(PlayerJoinEvent e) {
       e.setJoinMessage("Welcome to the server, " + e.getPlayer().getName() + "!");
    }
}
