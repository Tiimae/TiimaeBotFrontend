package Tiimae.TiimaeBot.controllers.listeners;

import Tiimae.TiimaeBot.model.Xp;
import Tiimae.TiimaeBot.service.ApiCallServices;
import Tiimae.TiimaeBot.service.PayloadServices;
import kong.unirest.GetRequest;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.json.JSONObject;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class OnMessageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OnMessageController.class);
    private final PayloadServices payloadServices = new PayloadServices();

    public String getPrefix(long guildId) {
        final GetRequest getRequest = ApiCallServices.getInstance().get("setting/key/" + guildId + "/prefix");

        return getRequest.asJson().getBody().getObject().getJSONObject("payload").getString("value");
    }

    public Xp getUserXp(User user, Guild guild) throws ParseException {
        final GetRequest getRequest = ApiCallServices.getInstance().get("xp/" + user.getIdLong() + "/" + guild.getIdLong());

        if (getRequest.asJson().getBody().getObject().getJSONObject("payload") != null) {
            final JSONObject payload = getRequest.asJson().getBody().getObject().getJSONObject("payload");
            final JSONObject xp = (JSONObject) payload.get("xp");
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSSSS").format(payload.get("xplock"));

            return new Xp(
                    xp.getString("id"),
                    payload.getString("userGuildId"),
                    xp.getLong("exp"),
                    xp.getLong("level"),
                    Timestamp.valueOf(timestamp)
            );
        }

        return null;
    }

    public void updateXPTable(Xp xp) {
        final HttpResponse<JsonNode> jsonNodeHttpResponse = ApiCallServices
                .getInstance()
                .put("xp/" + xp.getId())
                .body(this.payloadServices.createXpPayload(xp))
                .asJson();

        LOGGER.info("Xp Table has been updated");
    }

}
