package Tiimae.TiimaeBot.Command.commands.admin.server;

import Tiimae.TiimaeBot.Command.CommandContext;
import Tiimae.TiimaeBot.Command.ICommand;
import Tiimae.TiimaeBot.controllers.commands.SettingController;
import Tiimae.TiimaeBot.service.SettingsService;
import kong.unirest.json.JSONArray;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class UpdateSettingCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) throws ParseException {
        SettingController settingController = new SettingController();
        final SettingsService settingsService = new SettingsService();
        final TextChannel channel = (TextChannel) ctx.getEvent().getChannel();
        final List<String> args = ctx.getArgs();
        final JSONArray allGuildSettings = settingController.getAllGuildSettings(ctx.getGuild().getIdLong());
        settingsService.filterAllUpdateButtons(allGuildSettings);

        if (args.isEmpty()) {
            channel.sendMessage("You must give a channel or role id").queue();
            return;
        }


        final MessageCreateBuilder message = settingsService.createMessage(settingsService.getUpdateButtons(), args.get(0));

        channel.sendMessage(message.build()).queue();
    }

    @Override
    public String getName() {
        return "update-setting";
    }

    @Override
    public String getHelp() {
        return null;
    }
}
