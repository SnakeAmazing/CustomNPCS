package me.snakeamazing.customnpcs.service;

import me.snakeamazing.customnpcs.loader.CommandsLoader;

import javax.inject.Inject;

public class CustomNPCSService implements Service {

    @Inject private CommandsLoader commandsLoader;

    @Override
    public void start() {
        commandsLoader.load();
    }

    @Override
    public void stop() {

    }
}

