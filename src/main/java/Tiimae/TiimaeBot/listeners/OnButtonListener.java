package Tiimae.TiimaeBot.listeners;

import Tiimae.TiimaeBot.Command.CommandManager;
import Tiimae.TiimaeBot.button.ButtonManager;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class OnButtonListener extends ListenerAdapter {
    private final ButtonManager manager = new ButtonManager();

    @SneakyThrows
    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        manager.handle(event);
    }

}
