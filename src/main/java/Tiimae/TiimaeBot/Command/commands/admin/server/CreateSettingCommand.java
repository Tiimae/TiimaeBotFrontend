package Tiimae.TiimaeBot.Command.commands.admin.server;

import Tiimae.TiimaeBot.ButtonEnum;
import Tiimae.TiimaeBot.Command.CommandContext;
import Tiimae.TiimaeBot.Command.ICommand;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;

import java.text.ParseException;
import java.util.Collection;

public class CreateSettingCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) throws ParseException {

        final TextChannel channel = (TextChannel) ctx.getEvent().getChannel();

        final MessageCreateBuilder builder = new MessageCreateBuilder()
                .addActionRow(Button.primary(ButtonEnum.ADDXPBUTTON.name(), "Add Xp Channel"));

        channel.sendMessage(builder.build()).queue();
    }

    @Override
    public String getName() {
        return "create-setting";
    }

    @Override
    public String getHelp() {
        return null;
    }
}
