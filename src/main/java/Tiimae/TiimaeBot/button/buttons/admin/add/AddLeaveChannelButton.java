package Tiimae.TiimaeBot.button.buttons.admin.add;

import Tiimae.TiimaeBot.Command.ICommand;
import Tiimae.TiimaeBot.button.ButtonContext;
import Tiimae.TiimaeBot.button.IButton;
import Tiimae.TiimaeBot.controllers.buttons.SettingButtonController;
import Tiimae.TiimaeBot.enums.ButtonEnum;
import Tiimae.TiimaeBot.enums.SettingKeyEnum;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.text.ParseException;

public class AddLeaveChannelButton implements IButton {
    @Override
    public void handle(ButtonContext event) throws ParseException {
        final SettingButtonController settingButtonController = new SettingButtonController();
        settingButtonController.addSetting(SettingKeyEnum.LEAVECHANNEL.name(), event);

        return;
    }

    @Override
    public String getId() {
        return ButtonEnum.ADDLEAVECHANNELBUTTON.name();
    }
}
