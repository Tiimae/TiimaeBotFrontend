package Tiimae.TiimaeBot.controllers.listeners;

import Tiimae.TiimaeBot.listeners.OnReadyListener;
import Tiimae.TiimaeBot.service.ApiCallServices;
import Tiimae.TiimaeBot.service.PayloadServices;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.json.JSONObject;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class OnReadyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OnReadyListener.class);
    private final PayloadServices payloadServices = new PayloadServices();
    private String guildId;

    public void createGuild(Guild guild) {
        final HttpResponse<JsonNode> returnedGuild = ApiCallServices
                .getInstance()
                .post("guild")
                .body(this.payloadServices.createGuildPayload(guild))
                .asJson();

        final JSONObject returnedPayload = (JSONObject) returnedGuild.getBody().getObject().get("payload");
        this.guildId = (String) returnedPayload.get("id");
        LOGGER.info("Guild {} has been added to the database", returnedPayload.get("name"));

        for (Member member : guild.getMembers()) {
            if (!member.getUser().isBot()) {
                final HttpResponse<JsonNode> returnedUser = ApiCallServices
                        .getInstance()
                        .post("user")
                        .body(this.payloadServices.createMemberPayload(member, (String) returnedPayload.get("id")))
                        .asJson();

                final JSONObject returnedUserPayload = (JSONObject) returnedUser.getBody().getObject().get("payload");
                LOGGER.info("User {} has been added to the database", returnedUserPayload.get("name"));
            }
        }
    }

    public void createGuildSettings(String key, String value) {
        final HttpResponse<JsonNode> returnedSetting = ApiCallServices
                .getInstance()
                .post("setting")
                .body(this.payloadServices.createSettingsPayload(this.guildId, key, value))
                .asJson();

        if (returnedSetting.getBody().getObject().get("payload") instanceof JSONObject) {
            final JSONObject returnedSettingPayload = (JSONObject) returnedSetting.getBody().getObject().get("payload");
            LOGGER.info("Setting with key {} and value {} has been added to the database", returnedSettingPayload.get("key"), returnedSettingPayload.get("value"));
        }
    }

}
