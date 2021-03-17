package me.snakeamazing.customnpcs.loader;

import me.fixeddev.commandflow.CommandManager;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilder;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilderImpl;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.part.PartInjector;
import me.fixeddev.commandflow.annotated.part.SimplePartInjector;
import me.fixeddev.commandflow.annotated.part.defaults.DefaultsModule;
import me.fixeddev.commandflow.bukkit.BukkitCommandManager;
import me.fixeddev.commandflow.bukkit.factory.BukkitModule;
import me.fixeddev.commandflow.translator.DefaultTranslator;
import me.snakeamazing.customnpcs.CustomNPCS;
import me.snakeamazing.customnpcs.commands.ActionBarCommand;
import me.snakeamazing.customnpcs.commands.NPCCommands;
import me.snakeamazing.customnpcs.flow.CustomTranslationProvider;
import me.snakeamazing.customnpcs.flow.CustomUsageBuilder;

import javax.inject.Inject;

public class CommandsLoader implements Loader {

    @Inject private CustomNPCS customNPCS;
    @Inject private NPCCommands npcCommands;
    @Inject private ActionBarCommand actionBarCommand;

    @Inject CustomUsageBuilder customUsageBuilder;
    @Inject CustomTranslationProvider customTranslationProvider;

    @Override
    public void load() {
        PartInjector partInjector = new SimplePartInjector();
        partInjector.install(new DefaultsModule());
        partInjector.install(new BukkitModule());

        AnnotatedCommandTreeBuilder annotatedCommandTreeBuilder = new AnnotatedCommandTreeBuilderImpl(partInjector);
        CommandManager commandManager = new BukkitCommandManager(customNPCS.getName());
        commandManager.setUsageBuilder(customUsageBuilder);
        commandManager.setTranslator(new DefaultTranslator(customTranslationProvider));

        registerCommands(annotatedCommandTreeBuilder, commandManager,
                npcCommands,
                actionBarCommand);
    }

    private void registerCommands(AnnotatedCommandTreeBuilder commandBuilder, CommandManager commandManager, CommandClass... commandClasses) {
        for(CommandClass commandClass : commandClasses) {
            commandManager.registerCommands(commandBuilder.fromClass(commandClass));
        }
    }
}
