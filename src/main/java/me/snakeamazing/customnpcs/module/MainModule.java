package me.snakeamazing.customnpcs.module;

import me.snakeamazing.customnpcs.CustomNPCS;
import me.snakeamazing.customnpcs.files.FileCreator;
import me.snakeamazing.customnpcs.files.FileMatcher;
import me.yushust.inject.AbstractModule;
import org.bukkit.plugin.Plugin;

public class MainModule extends AbstractModule {

    private final CustomNPCS customNPCS;

    public MainModule(CustomNPCS customNPCS) {
        this.customNPCS = customNPCS;
    }

    @Override
    public void configure() {

        FileMatcher fileMatcher = new FileMatcher()
                .bind("messages", new FileCreator(customNPCS, "messages"));

        install(fileMatcher.build());
        install(new ServiceModule());

        bind(CustomNPCS.class).toInstance(customNPCS);
        bind(Plugin.class).to(CustomNPCS.class);
    }
}
