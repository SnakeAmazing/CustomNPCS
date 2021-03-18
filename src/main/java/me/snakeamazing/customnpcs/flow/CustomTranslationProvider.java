package me.snakeamazing.customnpcs.flow;

import me.fixeddev.commandflow.Namespace;
import me.fixeddev.commandflow.translator.TranslationProvider;
import me.snakeamazing.customnpcs.files.FileCreator;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;

public class CustomTranslationProvider implements TranslationProvider {

    @Inject @Named("messages") private FileCreator messages;

    protected final Map<String, String> translations;

    public CustomTranslationProvider() {
        this.translations = new HashMap<>();
        translations.put("argument.no-more", "No more arguments were found, size: %s position: %s");
    }

    @Override
    public String getTranslation(Namespace namespace, String key) {

        switch (key) {
            case "sender.only-player":
                return messages.getString("global.only-player-sender")
                        .replace("%prefix%", messages.getString("global.prefix"));
            case "command.no-permission":
                return messages.getString("global.no-permission")
                        .replace("%prefix%", messages.getString("global.prefix"));
            case "command.subcommand.invalid":
                return messages.getString("global.invalid-argument")
                        .replace("%prefix%", messages.getString("global.prefix"));
        }
        return translations.get(key);
    }
}
