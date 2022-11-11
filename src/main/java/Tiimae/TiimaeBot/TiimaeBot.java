package Tiimae.TiimaeBot;

import Tiimae.TiimaeBot.listeners.OnMessageListerner;
import Tiimae.TiimaeBot.listeners.OnReadyListener;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;

public class TiimaeBot {

    private TiimaeBot() throws LoginException {
        EmbedUtils.setEmbedBuilder(
                () -> new EmbedBuilder()
                        .setColor(0x3883d9)
                        .setFooter("Tiimae bot")
        );

        JDABuilder.createDefault(
                        Config.get("discord_token"),
                        GatewayIntent.GUILD_MEMBERS,
                        GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.GUILD_VOICE_STATES,
                        GatewayIntent.GUILD_PRESENCES,
                        GatewayIntent.MESSAGE_CONTENT
                )
                .enableCache(CacheFlag.VOICE_STATE, CacheFlag.ACTIVITY)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .setChunkingFilter(ChunkingFilter.ALL)
                .addEventListeners(new OnReadyListener())
                .addEventListeners(new OnMessageListerner())
                .setActivity(Activity.watching("Yo Mama"))
                .build();
    }

    public static void main(String[] args) throws LoginException {
        new TiimaeBot();
    }
}
