package Tiimae.TiimaeBot.Command.commands.help;

import Tiimae.TiimaeBot.Command.CommandContext;
import Tiimae.TiimaeBot.Command.CommandManager;
import Tiimae.TiimaeBot.Command.ICommand;
import Tiimae.TiimaeBot.controllers.listeners.OnMessageController;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.text.ParseException;
import java.util.List;

public class HelpCommand implements ICommand {

    private final CommandManager manager;

    public HelpCommand(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(CommandContext ctx) throws ParseException {
        final long guildId = ctx.getGuild().getIdLong();
        List<String> args = ctx.getArgs();
        TextChannel channel = (TextChannel) ctx.getEvent().getChannel();
        final OnMessageController onMessageController = new OnMessageController();
        final EmbedBuilder embedBuilder = new EmbedBuilder();

        if (args.isEmpty()) {
            StringBuilder builder = new StringBuilder();

            builder.append("List of commands\n");
            embedBuilder.setTitle("List of commands");
            embedBuilder.setDescription(":spy:   **BotCommands for " + ctx.getGuild().getName() + ":**");

            for(ICommand iCommand : manager.getCommands()) {
                embedBuilder.addField(new MessageEmbed.Field(
                        String.format("%s%s", onMessageController.getPrefix(guildId), iCommand.getName()),
                        iCommand.getHelp(),
                        false
                ));
            }

            channel.sendMessage("").setEmbeds(embedBuilder.build()).queue();

            return;
        }

        String search = args.get(0);
        ICommand command = manager.getCommand(search);

        if (command == null) {
            channel.sendMessage("Nothing found for " + search).queue();
            return;
        }

        embedBuilder.setTitle(command.getName());
        embedBuilder.setDescription(command.getHelp());

        channel.sendMessage("").setEmbeds(embedBuilder.build()).queue();
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getHelp() {
        return "Shows a list of commands";
    }

    @Override
    public List<String> getAliases() {
        return List.of("commands", "cmds", "commandlist");
    }
}
