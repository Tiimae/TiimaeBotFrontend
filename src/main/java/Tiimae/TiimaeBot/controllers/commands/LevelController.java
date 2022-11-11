package Tiimae.TiimaeBot.controllers.commands;

import Tiimae.TiimaeBot.controllers.listeners.OnMessageController;
import Tiimae.TiimaeBot.model.Xp;
import Tiimae.TiimaeBot.service.ApiCallServices;
import kong.unirest.GetRequest;
import kong.unirest.json.JSONObject;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class LevelController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OnMessageController.class);

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
}
