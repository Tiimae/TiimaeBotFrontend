package Tiimae.TiimaeBot.button;

import Tiimae.TiimaeBot.button.buttons.admin.XpChannelButton;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import javax.annotation.Nullable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Objects;

public class ButtonManager {

    private ArrayList<IButton> buttons = new ArrayList<IButton>();

    public ButtonManager() {

        addButton(new XpChannelButton());

    }

    public void addButton(IButton button) {
        boolean idExists = this.buttons.stream().anyMatch((it) -> it.getId().equalsIgnoreCase(button.getId()));

        if (idExists) {
            throw new IllegalArgumentException(String.format("A button with this %s is already in use!", button.getId()));
        }

        this.buttons.add(button);
    }

    @Nullable
    public IButton getButton(String search) {
        for (IButton btn : this.buttons) {
            if (btn.getId().equals(search)) {
                return btn;
            }
        }

        return null;
    }

    public void handle(ButtonInteractionEvent event) throws ParseException {
        IButton btn = this.getButton(Objects.requireNonNull(event.getButton().getId()));

        if (btn != null) {
            event.getChannel().sendTyping().queue();

            ButtonContext ctx = new ButtonContext(event);

            btn.handle(ctx);
        }
    }
}
