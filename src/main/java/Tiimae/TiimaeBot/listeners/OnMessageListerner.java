package Tiimae.TiimaeBot.listeners;

import Tiimae.TiimaeBot.Command.CommandManager;
import Tiimae.TiimaeBot.Settings;
import Tiimae.TiimaeBot.controllers.commands.SettingController;
import Tiimae.TiimaeBot.controllers.listeners.OnMessageController;
import Tiimae.TiimaeBot.enums.SettingKeyEnum;
import Tiimae.TiimaeBot.model.Xp;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Random;

public class OnMessageListerner extends ListenerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(OnMessageListerner.class);
    private OnMessageController onMessageController = new OnMessageController();
    private final CommandManager manager = new CommandManager();

    @SneakyThrows
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        final User author = event.getAuthor();
        final Guild guild = event.getGuild();
        String rawMessage = event.getMessage().getContentRaw();

        if (author.isBot() || event.isWebhookMessage()) {
            return;
        }

        final String prefix = Settings.PREFIXES.computeIfAbsent(guild.getIdLong(), onMessageController::getPrefix);

        handleUserXp(author, guild);

        if (rawMessage.startsWith(prefix)) {
            manager.handle(event, prefix);
        }
    }

    public void handleUserXp(User author, Guild guild) throws ParseException {
        final Calendar cal = Calendar.getInstance();
        final Xp userXp = this.onMessageController.getUserXp(author, guild);
        final Timestamp now = new Timestamp(System.currentTimeMillis());

        if (now.compareTo(userXp.getXpLock()) > 0) {
            final Random random = new Random();
            int new_xp = random.nextInt(20);
            new_xp += userXp.getXp();

            int new_level = (int) Math.floor(Math.pow((new_xp) / 42, 0.50));

            cal.setTimeInMillis(now.getTime());
            cal.add(Calendar.SECOND, 60);
            final Timestamp newXPLock = new Timestamp(cal.getTime().getTime());

            if (new_level > userXp.getLevel()) {
                final String key = this.onMessageController.getKey(guild.getIdLong(), SettingKeyEnum.XPCHANNEL.name());
                if (!key.equals("Key not found")) {
                    final TextChannel textChannelById = guild.getTextChannelById(key);
                    assert textChannelById != null;
                    textChannelById.sendMessage(String.format("Congratz %s! You are promoted to level %s", author.getAsMention(), new_level)).queue();
                }
            }

            userXp.setLevel(new_level);
            userXp.setXp(new_xp);
            userXp.setXpLock(newXPLock);

            onMessageController.updateXPTable(userXp);
        }
    }
}
