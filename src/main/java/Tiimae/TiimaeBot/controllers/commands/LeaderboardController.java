package Tiimae.TiimaeBot.controllers.commands;

import Tiimae.TiimaeBot.service.ApiCallServices;
import kong.unirest.GetRequest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import net.dv8tion.jda.api.entities.Guild;

public class LeaderboardController {

    public JSONArray getLeaderboard(Guild guild) {
        final GetRequest getRequest = ApiCallServices.getInstance().get("guild/leaderboard/" + guild.getIdLong());

        return getRequest.asJson().getBody().getObject().getJSONArray("payload");
    }

}
