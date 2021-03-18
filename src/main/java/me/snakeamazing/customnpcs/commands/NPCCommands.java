package me.snakeamazing.customnpcs.commands;

import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import me.snakeamazing.customnpcs.files.FileCreator;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Equipment;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Objects;

@Command(names = "customnpc", desc = "Main command", permission = "customnpcs.admin")
public class NPCCommands implements CommandClass {

    @Inject @Named("messages") private FileCreator messages;

    @Command(names = "")
    public boolean onCustomNPCCommand(@Sender Player player) {
        for (String line : messages.getStringList("npc.help")) {
            if (line.equals("")) {
                continue;
            }
            player.sendMessage(line);
        }

        return true;
    }

    @Command(names = "additem")
    public boolean onNPCAddItemCommand(@Sender Player player, int id, String where, String item, int data) {
        NPC npc = getNPC(id, player);

        if (npc == null) {
            if (messages.getString("npc.non-existent").equals("")) {
                return true;
            }
            player.sendMessage(messages.getString("npc.non-existent")
                    .replace("%prefix%", messages.getString("global.prefix")));
            return true;
        }

        if (item == null || item.equalsIgnoreCase("air") || item.equals("")) {
            if (messages.getString("npc.material-cannot-be-null").equals("")) {
                return true;
            }
            player.sendMessage(messages.getString("npc.material-cannot-be-null")
                    .replace("%prefix%", messages.getString("global.prefix")));
            return true;
        }

        String primal_material = "minecraft:" + item;
        Material material = Material.matchMaterial(primal_material);
        if (material == null) {
            if (messages.getString("npc.material-cannot-be-null").equals("")) {
                return true;
            }
            player.sendMessage(messages.getString("npc.material-cannot-be-null")
                    .replace("%prefix%", messages.getString("global.prefix")));
            return true;
        }

        ItemStack itemStack = new ItemStack(Objects.requireNonNull(material));
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setCustomModelData(data);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS);
        itemStack.setItemMeta(itemMeta);

        if (updateNPC(itemStack, where, npc, player)) {
            if (messages.getString("npc.added-item-successfully").equals("")) {
                return true;
            }
            player.sendMessage(messages.getString("npc.added-item-successfully")
                    .replace("%slot%", where)
                    .replace("%prefix%", messages.getString("global.prefix")));
        }

        return true;
    }

    @Command(names = "delitem", desc = "Remove the item")
    public boolean onDelItemCommand(@Sender Player player, int id, String where) {
        NPC npc = getNPC(id, player);

        if (npc == null) {
            if (messages.getString("npc.non-existent").equals("")) {
                return true;
            }
            player.sendMessage(messages.getString("npc.non-existent")
                    .replace("%prefix%", messages.getString("global.prefix")));
            return true;
        }

        ItemStack itemStack = new ItemStack(Material.AIR);
        if (updateNPC(itemStack, where, npc, player)) {
            if (messages.getString("npc.removed-item-successfully").equals("")) {
                return true;
            }
            player.sendMessage(messages.getString("npc.removed-item-successfully")
                    .replace("%slot%", where)
                    .replace("%prefix%", messages.getString("global.prefix")));
        }

        return true;
    }

    @Command(names = "reload")
    public boolean onReloadCommand(@Sender Player player) {
        player.sendMessage(ChatColor.YELLOW + "Trying to reload plugin files...");
        messages.save();
        messages.reload();
        player.sendMessage(ChatColor.GREEN + "Reloaded plugins files successfully.");

        return true;
    }

    private NPC getNPC(int id, Player player) {
        NPC npc = CitizensAPI.getNPCRegistry().getById(id);

        if (npc != null && npc.isSpawned()) {
            return npc;
        }
        return null;
    }

    private boolean updateNPC(ItemStack itemStack, String where, NPC npc, Player player) {

        switch (where.toUpperCase()) {
            case "BOOTS":
                npc.getOrAddTrait(Equipment.class).set(Equipment.EquipmentSlot.BOOTS, itemStack);
                break;

            case "LEGGINGS":
                npc.getOrAddTrait(Equipment.class).set(Equipment.EquipmentSlot.LEGGINGS, itemStack);
                break;

            case "CHESTPLATE":
                npc.getOrAddTrait(Equipment.class).set(Equipment.EquipmentSlot.CHESTPLATE, itemStack);
                break;

            case "HELMET":
                npc.getOrAddTrait(Equipment.class).set(Equipment.EquipmentSlot.HELMET, itemStack);
                break;

            case "HAND":
                npc.getOrAddTrait(Equipment.class).set(Equipment.EquipmentSlot.HAND, itemStack);
                break;

            case "OFFHAND":
                npc.getOrAddTrait(Equipment.class).set(Equipment.EquipmentSlot.OFF_HAND, itemStack);
                break;

            default:
                if (messages.getString("npc.invalid-slot").equals("")) {
                    return true;
                }
                player.sendMessage(messages.getString("npc.invalid-slot")
                        .replace("%slot%", where)
                        .replace("%prefix%", messages.getString("global.prefix")));
                return false;
        }
        return true;
    }
}
