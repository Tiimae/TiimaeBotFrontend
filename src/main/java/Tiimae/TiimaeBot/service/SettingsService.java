package Tiimae.TiimaeBot.service;

import Tiimae.TiimaeBot.enums.ButtonEnum;
import Tiimae.TiimaeBot.enums.SettingKeyEnum;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;

import java.util.ArrayList;
import java.util.Objects;

public class SettingsService {

    private ArrayList<Button> createButtons = new ArrayList<>();
    private ArrayList<Button> updateButtons = new ArrayList<>();

    public void filterAllCreateButtons(JSONArray allGuildSettings) {
        this.createAllButtons();

        for (int i = 0; i < allGuildSettings.length(); i++) {
            final JSONObject jsonObject = allGuildSettings.getJSONObject(i);
            final String key = jsonObject.getString("key");

            for (SettingKeyEnum setting : SettingKeyEnum.values()) {
                if (setting.name().equals(key)) {
                    this.createButtons.removeIf(button -> Objects.requireNonNull(button.getId()).contains(setting.name()));
                }
            }
        }
    }

    public void filterAllUpdateButtons(JSONArray allGuildSettings) {

        this.createAllButtons();

        for (int i = 0; i < allGuildSettings.length(); i++) {
            final JSONObject jsonObject = allGuildSettings.getJSONObject(i);
            final String key = jsonObject.getString("key");

            for (SettingKeyEnum setting : SettingKeyEnum.values()) {
                if (!setting.name().equals(key)) {
                    this.updateButtons.removeIf(button -> Objects.requireNonNull(button.getId()).contains(setting.name()));
                }
            }
        }

    }


    public void createAllButtons() {

        this.createButtons.clear();
        this.updateButtons.clear();

        final Button addxpChannelButton = Button.primary(ButtonEnum.ADDXPCHANNELBUTTON.name(), "Add Xp Channel");
        final Button addlogChannelButton = Button.primary(ButtonEnum.ADDLOGCHANNELBUTTON.name(), "Add Log Channel");
        final Button addwelcomeChannelButton = Button.primary(ButtonEnum.ADDWELCOMECHANNELBUTTON.name(), "Add Welcome Channel");
        final Button addprefixButton = Button.primary(ButtonEnum.ADDPREFIXBUTTON.name(), "Add Prefix");
        final Button addwelcomeRoleButton = Button.primary(ButtonEnum.ADDWELCOMEROLEBUTTON.name(), "Add Welcome Role");
        final Button addleaveChannelButton = Button.primary(ButtonEnum.ADDLEAVECHANNELBUTTON.name(), "Add Leave Channel");
        final Button addmemberCountChannel = Button.primary(ButtonEnum.ADDMEMBERCOUNTCHANNELBUTTON.name(), "Add Member Count Channel");
        final Button addbotCountChannel = Button.primary(ButtonEnum.ADDBOTCOUNTCHANNELBUTTON.name(), "Add Bot Count Channel");
        final Button addmuteRoleButton = Button.primary(ButtonEnum.ADDMUTEROLEBUTTON.name(), "Add Mute Role");

        this.createButtons.add(addxpChannelButton);
        this.createButtons.add(addlogChannelButton);
        this.createButtons.add(addwelcomeChannelButton);
        this.createButtons.add(addprefixButton);
        this.createButtons.add(addwelcomeRoleButton);
        this.createButtons.add(addleaveChannelButton);
        this.createButtons.add(addmemberCountChannel);
        this.createButtons.add(addbotCountChannel);
        this.createButtons.add(addmuteRoleButton);

        final Button updatexpChannelButton = Button.primary(ButtonEnum.UPDATEXPCHANNELBUTTON.name(), "Update Xp Channel");
        final Button updatelogChannelButton = Button.primary(ButtonEnum.UPDATELOGCHANNELBUTTON.name(), "Update Log Channel");
        final Button updatewelcomeChannelButton = Button.primary(ButtonEnum.UPDATEWELCOMECHANNELBUTTON.name(), "Update Welcome Channel");
        final Button updateprefixButton = Button.primary(ButtonEnum.UPDATEPREFIXBUTTON.name(), "Update Prefix");
        final Button updatewelcomeRoleButton = Button.primary(ButtonEnum.UPDATEWELCOMEROLEBUTTON.name(), "Update Welcome Role");
        final Button updateleaveChannelButton = Button.primary(ButtonEnum.UPDATELEAVECHANNELBUTTON.name(), "Update Leave Channel");
        final Button updatememberCountChannel = Button.primary(ButtonEnum.UPDATEMEMBERCOUNTCHANNELBUTTON.name(), "Update Member Count Channel");
        final Button updatebotCountChannel = Button.primary(ButtonEnum.UPDATEBOTCOUNTCHANNELBUTTON.name(), "Update Bot Count Channel");
        final Button updatemuteRoleButton = Button.primary(ButtonEnum.UPDATEMUTEROLEBUTTON.name(), "Update Mute Role");

        this.updateButtons.add(updatexpChannelButton);
        this.updateButtons.add(updatelogChannelButton);
        this.updateButtons.add(updatewelcomeChannelButton);
        this.updateButtons.add(updateprefixButton);
        this.updateButtons.add(updatewelcomeRoleButton);
        this.updateButtons.add(updateleaveChannelButton);
        this.updateButtons.add(updatememberCountChannel);
        this.updateButtons.add(updatebotCountChannel);
        this.updateButtons.add(updatemuteRoleButton);

    }

    public MessageCreateBuilder createMessage(ArrayList<Button> buttons, String id) {
        final MessageCreateBuilder builder = new MessageCreateBuilder()
                .addContent(String.format("For what kinda Channel/Role is this ID: %s", id));

        while (buttons.size() > 0) {
            ArrayList<Button> buttonsToAdd = new ArrayList<>();

            if (buttons.size() > 5) {
                for (int i = 0; i < 5; i++) {
                    buttonsToAdd.add(buttons.get(i));
                }
            } else {
                buttonsToAdd.addAll(buttons);
            }

            builder.addActionRow(buttonsToAdd);
            buttons.removeAll(buttonsToAdd);
        }

        return builder;
    }

    public ArrayList<Button> getCreateButtons() {
        return createButtons;
    }

    public ArrayList<Button> getUpdateButtons() {
        return updateButtons;
    }
}
