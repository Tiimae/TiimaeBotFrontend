package Tiimae.TiimaeBot.Command.commands.admin.server;

import Tiimae.TiimaeBot.controllers.commands.SettingController;
import Tiimae.TiimaeBot.Command.CommandContext;
import Tiimae.TiimaeBot.Command.ICommand;
import Tiimae.TiimaeBot.service.SettingsService;
import kong.unirest.json.JSONArray;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class CreateSettingCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) throws ParseException {

        SettingController settingController = new SettingController();
        final SettingsService settingsService = new SettingsService();
        final TextChannel channel = (TextChannel) ctx.getEvent().getChannel();
        final List<String> args = ctx.getArgs();
        final JSONArray allGuildSettings = settingController.getAllGuildSettings(ctx.getGuild().getIdLong());
        settingsService.filterAllCreateButtons(allGuildSettings);

        if (args.isEmpty()) {
            channel.sendMessage("You must give a channel or role id").queue();
            return;
        }

        boolean founded = false;

        for (TextChannel textChannel : ctx.getGuild().getTextChannels()) {
            if (textChannel.getId().equals(args.get(0))) {
                founded = true;
                break;
            }
        }

        if (!founded) {
            for (Role role : ctx.getGuild().getRoles()) {
                if (role.getId().equals(args.get(0))) {
                    founded = true;
                    break;
                }
            }
        }

        if (founded && !settingsService.getCreateButtons().isEmpty()) {
            final MessageCreateBuilder message = settingsService.createMessage(settingsService.getCreateButtons(), args.get(0));

            channel.sendMessage(message.build()).queue();
        } else {
            channel.sendMessage("This id is not a role or a channel").queue();
        }
    }

    @Override
    public String getName() {
        return "create-setting";
    }

    @Override
    public String getHelp() {
        return "Create a new channel/role for a specific action \n" +
                "Usage: `<your-prefix>create-setting <channel/role id>`";
    }

}
