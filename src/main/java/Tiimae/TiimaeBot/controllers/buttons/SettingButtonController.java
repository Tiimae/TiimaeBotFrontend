package Tiimae.TiimaeBot.controllers.buttons;

import Tiimae.TiimaeBot.button.ButtonContext;
import Tiimae.TiimaeBot.enums.SettingKeyEnum;
import Tiimae.TiimaeBot.service.ApiCallServices;
import Tiimae.TiimaeBot.service.PayloadServices;
import kong.unirest.GetRequest;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.json.JSONObject;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SettingButtonController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SettingButtonController.class);
    private PayloadServices payloadServices = new PayloadServices();

    public void addSetting(String key, ButtonContext event) {
        final TextChannel channel = (TextChannel) event.getEvent().getChannel();

        final String[] s = event.getEvent().getMessage().getContentRaw().split(" ");
        this.createGuildSettings(event.getEvent().getGuild().getId(), key, s[s.length - 1]);
        final long channelId = Long.parseLong(s[s.length - 1]);

        event.getEvent().getMessage().delete().queue();

        channel.sendMessage(String.format(
                        "Log channel has been created on channel %s",
                        event.getEvent().getGuild().getTextChannelById(channelId)))
                .queue();

        return;
    }

    public void createGuildSettings(String guildId, String key, String value) {
        final GetRequest getRequest = ApiCallServices.getInstance().get("guild/" + guildId);
        final String id = getRequest.asJson().getBody().getObject().getJSONObject("payload").getString("id");


        final HttpResponse<JsonNode> returnedSetting = ApiCallServices
                .getInstance()
                .post("setting")
                .body(this.payloadServices.createSettingsPayload(id , key, value))
                .asJson();

        if (returnedSetting.getBody().getObject().has("payload")) {
            final JSONObject returnedSettingPayload = (JSONObject) returnedSetting.getBody().getObject().get("payload");
            LOGGER.info("Setting with key {} and value {} has been added to the database", returnedSettingPayload.get("key"), returnedSettingPayload.get("value"));
        }
    }

}
