package Tiimae.TiimaeBot.Command.commands.xp;

import Tiimae.TiimaeBot.Command.CommandContext;
import Tiimae.TiimaeBot.Command.ICommand;
import Tiimae.TiimaeBot.controllers.commands.LevelController;
import Tiimae.TiimaeBot.model.Xp;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.text.ParseException;
import java.util.List;

public class LevelCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) throws ParseException {
        final TextChannel channel = (TextChannel) ctx.getEvent().getChannel();
        List<Member> mentionedMembers = ctx.getEvent().getMessage().getMentions().getMembers();
        LevelController levelController = new LevelController();
        EmbedBuilder embed = new EmbedBuilder();

        if (!mentionedMembers.isEmpty()) {
            for (Member member : mentionedMembers) {
                final Xp userXp = levelController.getUserXp(member.getUser(), ctx.getGuild());
                String title = String.format("The level of %s", member.getUser().getName());
                String description = String.format("%s is at level %s with %s XP", member.getUser().getAsTag(), userXp.getLevel(), userXp.getXp());

                embed.setTitle(title);
                embed.setDescription(description);

                final MessageEmbed build = embed.build();

                channel.sendMessage("").setEmbeds(build).queue();
            }
        } else {
            final Xp userXp = levelController.getUserXp(ctx.getEvent().getMessage().getAuthor(), ctx.getGuild());
            String title = String.format("The level of %s", ctx.getEvent().getMessage().getAuthor().getName());
            String description = String.format("%s is at level %s with %s XP", ctx.getEvent().getMessage().getAuthor().getName(), userXp.getLevel(), userXp.getXp());

            embed.setTitle(title);
            embed.setDescription(description);

            channel.sendMessage("").setEmbeds(embed.build()).queue();
        }
    }

    @Override
    public String getName() {
        return "level";
    }

    @Override
    public String getHelp() {
        return "Shows the level of you or an other member. \n" +
                "Usage: <your-prefix>level <mentioned-member>";
    }

    @Override
    public List<String> getAliases() {
        return List.of("lvl");
    }
}
