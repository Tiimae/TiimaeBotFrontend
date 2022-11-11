package Tiimae.TiimaeBot.button;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;


public class ButtonContext {

    private final ButtonInteractionEvent event;

    public ButtonContext(ButtonInteractionEvent event) {
        this.event = event;
    }

    public ButtonInteractionEvent getEvent() {
        return event;
    }
}
