package Tiimae.TiimaeBot.service;

import Tiimae.TiimaeBot.enums.ButtonEnum;
import Tiimae.TiimaeBot.enums.SettingKeyEnum;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;

import java.util.ArrayList;
import java.util.Objects;

public class SettingsService {

    public ArrayList<Button> filterAllButtons(JSONArray allGuildSettings) {

        final ArrayList<Button> allButtons = this.createAllButtons();

        for (int i = 0; i < allGuildSettings.length(); i++) {
            final JSONObject jsonObject = allGuildSettings.getJSONObject(i);
            final String key = jsonObject.getString("key");

            for (SettingKeyEnum setting : SettingKeyEnum.values()) {
                if (setting.name().equals(key)) {
                    for (Button button : allButtons) {
                        if (Objects.requireNonNull(button.getId()).contains(setting.name())) {
                            allButtons.remove(button);
                            break;
                        }
                    }
                    break;
                }
            }
        }

        return allButtons;
    }

    public ArrayList<Button> createAllButtons() {
        ArrayList<Button> allButtons = new ArrayList<>();

        final Button xpChannelButton = Button.primary(ButtonEnum.ADDXPCHANNELBUTTON.name(), "Add Xp Channel");
        final Button logChannelButton = Button.primary(ButtonEnum.ADDLOGCHANNELBUTTON.name(), "Add Log Channel");
        final Button welcomeChannelButton = Button.primary(ButtonEnum.ADDWELCOMECHANNELBUTTON.name(), "Add Welcome Channel");
        final Button prefixButton = Button.primary(ButtonEnum.ADDPREFIXBUTTON.name(), "Add Prefix");
        final Button welcomeRoleButton = Button.primary(ButtonEnum.ADDWELCOMEROLEBUTTON.name(), "Add Welcome Role");
        final Button leaveChannelButton = Button.primary(ButtonEnum.ADDLEAVECHANNELBUTTON.name(), "Add Leave Channel");
        final Button memberCountChannel = Button.primary(ButtonEnum.ADDMEMBERCOUNTCHANNELBUTTON.name(), "Add Member Count Channel");
        final Button botCountChannel = Button.primary(ButtonEnum.ADDBOTCOUNTCHANNELBUTTON.name(), "Add Bot Count Channel");
        final Button muteRoleButton = Button.primary(ButtonEnum.ADDMUTEROLEBUTTON.name(), "Add Mute Role");

        allButtons.add(xpChannelButton);
        allButtons.add(logChannelButton);
        allButtons.add(welcomeChannelButton);
        allButtons.add(prefixButton);
        allButtons.add(welcomeRoleButton);
        allButtons.add(leaveChannelButton);
        allButtons.add(memberCountChannel);
        allButtons.add(botCountChannel);
        allButtons.add(muteRoleButton);

        return allButtons;
    }

    public MessageCreateBuilder createMessage(ArrayList<Button> buttons, String id) {
        final MessageCreateBuilder builder = new MessageCreateBuilder()
                .addContent(String.format("For what kinda Channel/Role is this ID: %s", id));

        if (buttons.size() > 5) {
            builder.addActionRow(buttons.get(0), buttons.get(1), buttons.get(2), buttons.get(3), buttons.get(4));
            if (buttons.size() >= 10) {
                builder.addActionRow(buttons.get(5), buttons.get(6), buttons.get(7), buttons.get(8), buttons.get(9));
            } else {
                if (buttons.size() == 6) {
                    builder.addActionRow(buttons.get(5));
                } else if (buttons.size() == 7) {
                    builder.addActionRow(buttons.get(5), buttons.get(6));
                } else if (buttons.size() == 8) {
                    builder.addActionRow(buttons.get(5), buttons.get(6), buttons.get(7));
                } else if (buttons.size() == 9) {
                    builder.addActionRow(buttons.get(5), buttons.get(6), buttons.get(7), buttons.get(8));
                }
            }
        }

        return builder;
    }

}
