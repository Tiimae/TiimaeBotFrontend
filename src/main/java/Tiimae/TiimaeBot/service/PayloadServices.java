package Tiimae.TiimaeBot.service;

import Tiimae.TiimaeBot.model.Xp;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

import java.util.HashMap;
import java.util.Map;

public class PayloadServices {

    public Map createGuildPayload(Guild guild) {
        Map payload = new HashMap();
        payload.put("name", guild.getName());
        payload.put("guildId", guild.getId());
        payload.put("userGuildIds", new String[0]);
        payload.put("settingsId", null);

        return payload;
    }

    public Map createMemberPayload(Member member, String guildId) {
        Map payload = new HashMap();
        payload.put("name", member.getUser().getName());
        payload.put("userId", member.getUser().getId());
        payload.put("discriminator", member.getUser().getDiscriminator());
        payload.put("tag", member.getUser().getAsTag());
        payload.put("userGuildIds", new String[0]);
        payload.put("guildId", guildId);

        return payload;
    }

    public Map createSettingsPayload(String guildId, String key, String value) {
        Map payload = new HashMap();
        payload.put("guildId", guildId);
        payload.put("key", key);
        payload.put("value", value);

        return payload;
    }

    public Map createXpPayload(Xp xp) {
        Map payload = new HashMap();
        payload.put("userGuildId", xp.getUserGuildID());
        payload.put("exp", xp.getXp());
        payload.put("level", xp.getLevel());
        payload.put("xplock", xp.getXpLock());

        return payload;
    }
}
