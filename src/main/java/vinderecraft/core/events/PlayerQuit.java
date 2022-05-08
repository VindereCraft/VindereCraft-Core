package vinderecraft.core.events;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import vinderecraft.core.Core;

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
        TextComponent quitMessage = Component.text("[", TextColor.color(87, 115, 153))
                .append(Component.text("-",
                        TextColor.color(189, 213, 234)))
                .append(Component.text("] ",
                        TextColor.color(87, 115, 153)))
                .append(Component.text(e.getPlayer().getName(),
                        TextColor.color(247, 247, 255)))
                .append(Component.text(" has left.",
                        TextColor.color(189, 213, 234)));
        e.quitMessage(quitMessage);
    }
}
