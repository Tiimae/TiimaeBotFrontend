package Tiimae.TiimaeBot.button;

import Tiimae.TiimaeBot.button.buttons.admin.add.*;
import Tiimae.TiimaeBot.button.buttons.admin.update.*;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import javax.annotation.Nullable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Objects;

public class ButtonManager {

    private ArrayList<IButton> buttons = new ArrayList<IButton>();

    public ButtonManager() {

        //Add Buttons
        addButton(new AddBotCountChannelButton());
        addButton(new AddLeaveChannelButton());
        addButton(new AddLogChannelButton());
        addButton(new AddMemberCountChannelButton());
        addButton(new AddMuteRoleButton());
        addButton(new AddPrefixButton());
        addButton(new AddWelcomeChannelButton());
        addButton(new AddWelcomeRoleButton());
        addButton(new AddXpChannelButton());

        //Update buttons
        addButton(new UpdateBotCountChannelButton());
        addButton(new UpdateLeaveChannelButton());
        addButton(new UpdateLogChannelButton());
        addButton(new UpdateMemberCountChannelButton());
        addButton(new UpdateMuteRoleButton());
        addButton(new UpdatePrefixButton());
        addButton(new UpdateWelcomeRoleButton());
        addButton(new UpdateWelcomeChannelButton());

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
