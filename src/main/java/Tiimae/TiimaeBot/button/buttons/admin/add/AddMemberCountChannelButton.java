package Tiimae.TiimaeBot.button.buttons.admin.add;

import Tiimae.TiimaeBot.button.ButtonContext;
import Tiimae.TiimaeBot.button.IButton;
import Tiimae.TiimaeBot.controllers.buttons.SettingButtonController;
import Tiimae.TiimaeBot.enums.ButtonEnum;
import Tiimae.TiimaeBot.enums.SettingKeyEnum;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.text.ParseException;

public class AddMemberCountChannelButton implements IButton {
    @Override
    public void handle(ButtonContext event) throws ParseException {
        SettingButtonController settingButtonController = new SettingButtonController();
        final TextChannel channel = (TextChannel) event.getEvent().getChannel();

        final String[] s = event.getEvent().getMessage().getContentRaw().split(" ");
        settingButtonController.createGuildSettings(event.getEvent().getGuild().getId(), SettingKeyEnum.MEMBERCOUNTCHANNEL.name(), s[s.length - 1]);
        final long channelId = Long.parseLong(s[s.length - 1]);

        event.getEvent().getMessage().delete().queue();

        channel.sendMessage(String.format(
                        "Log channel has been created on channel %s",
                        event.getEvent().getGuild().getTextChannelById(channelId)))
                .queue();

        return;
    }

    @Override
    public String getId() {
        return ButtonEnum.ADDMEMBERCOUNTCHANNELBUTTON.name();
    }
}
