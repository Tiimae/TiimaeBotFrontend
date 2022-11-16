package Tiimae.TiimaeBot.button.buttons.admin.update;

import Tiimae.TiimaeBot.button.ButtonContext;
import Tiimae.TiimaeBot.button.IButton;
import Tiimae.TiimaeBot.controllers.buttons.SettingButtonController;
import Tiimae.TiimaeBot.enums.ButtonEnum;
import Tiimae.TiimaeBot.enums.SettingKeyEnum;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.text.ParseException;

public class UpdateXpChannelButton implements IButton {
    @Override
    public void handle(ButtonContext event) throws ParseException {

    }

    @Override
    public String getId() {
        return ButtonEnum.UPDATEXPCHANNELBUTTON.name();
    }
}
