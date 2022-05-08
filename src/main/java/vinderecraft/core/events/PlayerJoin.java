package vinderecraft.core.events;

import vinderecraft.core.Core;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

import net.kyori.adventure.text.format.TextColor;
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
        if (core.debug) { core.getServer().getConsoleSender().sendMessage("[VC CORE] [DEBUG] Player " + e.getPlayer().getName() + " joined the server."); }
        TextComponent joinMessage = Component.text("[", TextColor.color(87, 115, 153))
                .append(Component.text("+",
                        TextColor.color(189, 213, 234)))
                .append(Component.text("] ",
                        TextColor.color(87, 115, 153)))
                .append(Component.text(e.getPlayer().getName(),
                        TextColor.color(247, 247, 255)))
                .append(Component.text(" has joined.",
                        TextColor.color(189, 213, 234)));
        e.joinMessage(joinMessage);
    }
}
