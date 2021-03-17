package me.snakeamazing.customnpcs.flow;

import me.fixeddev.commandflow.CommandContext;
import me.fixeddev.commandflow.command.Command;
import me.fixeddev.commandflow.usage.UsageBuilder;
import me.snakeamazing.customnpcs.files.FileCreator;
import net.kyori.text.Component;
import net.kyori.text.TextComponent;
import net.kyori.text.format.TextColor;

import javax.inject.Inject;
import javax.inject.Named;

public class CustomUsageBuilder implements UsageBuilder {

    @Inject @Named("messages") private FileCreator messages;

    @Override
    public Component getUsage(CommandContext commandContext) {

        Command toExecute = commandContext.getCommand();

        String label = String.join(" ", commandContext.getLabels());

        Component prefixComponent = TextComponent.of(messages.getString("global.usage"));
        Component labelComponent = TextComponent.of(label).color(TextColor.AQUA);
        Component partComponents = TextComponent.of(messages.getString("global.args"));

        if(partComponents != null) {
            partComponents.color(TextColor.AQUA);
            labelComponent = prefixComponent.append(labelComponent.append(TextComponent.of(" ")).append(partComponents));
        }

        return labelComponent;
    }
}
