package me.snakeamazing.customnpcs.commands;

import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.Text;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@Command(names = "actionbar", desc = "Send action bar message", permission = "actionbar.admin")
public class ActionBarCommand implements CommandClass {

    @Command(names = "")
    public boolean onActionBarCommand(@Text String message) {

        for (Player player1 : Bukkit.getOnlinePlayers()) {
            player1.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                    TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', message)));
        }

        return true;
    }
}
