package Tiimae.TiimaeBot.controllers.commands;

import Tiimae.TiimaeBot.service.ApiCallServices;
import kong.unirest.GetRequest;
import kong.unirest.json.JSONArray;

public class SettingController {

    public JSONArray getAllGuildSettings(long guildId) {
        final GetRequest getRequestGuild = ApiCallServices.getInstance().get("guild/" + guildId);
        final String id = getRequestGuild.asJson().getBody().getObject().getJSONObject("payload").getString("id");

        final GetRequest getRequestSettings = ApiCallServices.getInstance().get("setting/all/" + id);

        if (getRequestSettings.asJson().getBody().getObject().has("payload")) {
            final JSONArray payload = getRequestSettings.asJson().getBody().getObject().getJSONArray("payload");

            return payload;

        }

        return null;
    }

    public JSONArray getOneGuildSetting(long guildId, String setting) {
        return null;
    }

}
