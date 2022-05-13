package vinderecraft.core.cmd;

import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import vinderecraft.core.Core;

public class CoreCmd implements CommandExecutor {
    Core core;
    public CoreCmd (Core core) {
        this.core = core;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("core")) {
            getPlugins(sender);
            return true;
        }
        return true;
    }

    private void getPlugins(CommandSender sender) {
        TextComponent aboutMsg, coreVersion, chatVersion, nullify;

        aboutMsg =
                net.kyori.adventure.text.Component.text(" Core",
                                TextColor.color(
                                core.Config.readInt("chat", "color.highlight-text-color.r"),
                                core.Config.readInt("chat", "color.highlight-text-color.g"),
                                core.Config.readInt("chat", "color.highlight-text-color.b")
                                ))
                        .append(net.kyori.adventure.text.Component.text(" > ",
                                TextColor.color(
                                        core.Config.readInt("chat", "color.dark-text-color.r"),
                                        core.Config.readInt("chat", "color.dark-text-color.g"),
                                        core.Config.readInt("chat", "color.dark-text-color.b")
                                )))
                        .append(net.kyori.adventure.text.Component.text("VindereCraft Plugin Versions",
                                TextColor.color(
                                        core.Config.readInt("chat", "color.text-color.r"),
                                        core.Config.readInt("chat", "color.text-color.g"),
                                        core.Config.readInt("chat", "color.text-color.b")
                                )));

        coreVersion =
                net.kyori.adventure.text.Component.text(" - ",
                                TextColor.color(
                                        core.Config.readInt("chat", "color.dark-text-color.r"),
                                        core.Config.readInt("chat", "color.dark-text-color.g"),
                                        core.Config.readInt("chat", "color.dark-text-color.b")
                                ))
                        .append(net.kyori.adventure.text.Component.text("Core (",
                                TextColor.color(
                                        core.Config.readInt("chat", "color.text-color.r"),
                                        core.Config.readInt("chat", "color.text-color.g"),
                                        core.Config.readInt("chat", "color.text-color.b")
                                )))
                        .append(net.kyori.adventure.text.Component.text("v" + core.getDescription().getVersion(),
                                TextColor.color(
                                        core.Config.readInt("chat", "color.highlight-text-color.r"),
                                        core.Config.readInt("chat", "color.highlight-text-color.g"),
                                        core.Config.readInt("chat", "color.highlight-text-color.b")
                                )))
                        .append(net.kyori.adventure.text.Component.text(") enabled.",
                                TextColor.color(
                                        core.Config.readInt("chat", "color.text-color.r"),
                                        core.Config.readInt("chat", "color.text-color.g"),
                                        core.Config.readInt("chat", "color.text-color.b")
                                )));

        if (core.getServer().getPluginManager().isPluginEnabled("VindereCraft-Chat")) {
            chatVersion =
                    net.kyori.adventure.text.Component.text(" - ",
                                    TextColor.color(
                                            core.Config.readInt("chat", "color.dark-text-color.r"),
                                            core.Config.readInt("chat", "color.dark-text-color.g"),
                                            core.Config.readInt("chat", "color.dark-text-color.b")
                                    ))
                            .append(net.kyori.adventure.text.Component.text("Chat (",
                                    TextColor.color(
                                            core.Config.readInt("chat", "color.text-color.r"),
                                            core.Config.readInt("chat", "color.text-color.g"),
                                            core.Config.readInt("chat", "color.text-color.b")
                                    )))
                            .append(net.kyori.adventure.text.Component.text("v" + core.getServer().getPluginManager().getPlugin("VindereCraft-Chat").getDescription().getVersion(),
                                    TextColor.color(
                                            core.Config.readInt("chat", "color.highlight-text-color.r"),
                                            core.Config.readInt("chat", "color.highlight-text-color.g"),
                                            core.Config.readInt("chat", "color.highlight-text-color.b")
                                    )))
                            .append(net.kyori.adventure.text.Component.text(") enabled.",
                                    TextColor.color(
                                            core.Config.readInt("chat", "color.text-color.r"),
                                            core.Config.readInt("chat", "color.text-color.g"),
                                            core.Config.readInt("chat", "color.text-color.b")
                                    )));
        } else {
            chatVersion =
                    net.kyori.adventure.text.Component.text(" - ",
                                    TextColor.color(
                                            core.Config.readInt("chat", "color.dark-text-color.r"),
                                            core.Config.readInt("chat", "color.dark-text-color.g"),
                                            core.Config.readInt("chat", "color.dark-text-color.b")
                                    ))
                            .append(net.kyori.adventure.text.Component.text("Chat not enabled.",
                                    TextColor.color(
                                            core.Config.readInt("chat", "color.text-color.r"),
                                            core.Config.readInt("chat", "color.text-color.g"),
                                            core.Config.readInt("chat", "color.text-color.b")
                                    )));
        }

        nullify = net.kyori.adventure.text.Component.text("\n");

        sender.sendMessage(nullify);
        sender.sendMessage(aboutMsg);
        sender.sendMessage(coreVersion);
        sender.sendMessage(chatVersion);
        sender.sendMessage(nullify);
    }
}