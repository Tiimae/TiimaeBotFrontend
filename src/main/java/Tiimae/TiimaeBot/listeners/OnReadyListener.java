package Tiimae.TiimaeBot.listeners;

import Tiimae.TiimaeBot.controllers.listeners.OnReadyController;
import Tiimae.TiimaeBot.enums.SettingKeyEnum;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;

public class OnReadyListener extends ListenerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(OnReadyListener.class);

    private OnReadyController onReadyController = new OnReadyController();

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        for (Guild guild : event.getJDA().getGuilds()) {
            this.onReadyController.createGuild(guild);
            this.onReadyController.createGuildSettings(SettingKeyEnum.PREFIX.name(), "!");
        }

        LOGGER.info("Database up to date!");
        LOGGER.info("{} is ready", event.getJDA().getSelfUser().getAsTag());
    }

}
