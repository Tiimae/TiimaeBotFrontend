package Tiimae.TiimaeBot.button;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import java.text.ParseException;
import java.util.List;

public interface IButton {

    void handle(ButtonContext event) throws ParseException;

    String getId();

}
