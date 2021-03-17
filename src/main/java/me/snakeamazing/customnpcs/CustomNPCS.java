package me.snakeamazing.customnpcs;

import me.snakeamazing.customnpcs.module.MainModule;
import me.snakeamazing.customnpcs.service.CustomNPCSService;
import me.yushust.inject.Injector;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;
import java.util.logging.Level;

public class CustomNPCS extends JavaPlugin {

    @Inject private CustomNPCSService customNPCSService;

    public void onLoad() {
        Injector injector = Injector.create(new MainModule(this));
        injector.getInstance(getClass());
    }

    public void onEnable() {
        if (getServer().getPluginManager().getPlugin("Citizens") == null || !getServer().getPluginManager().getPlugin("Citizens").isEnabled()) {
            getLogger().log(Level.SEVERE, "Citizens 2.0 not found or not enabled");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        customNPCSService.start();
    }

    public void onDisable() {
        customNPCSService.stop();
    }
}
