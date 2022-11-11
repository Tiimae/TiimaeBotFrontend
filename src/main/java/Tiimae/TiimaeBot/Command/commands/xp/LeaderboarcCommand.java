package Tiimae.TiimaeBot.Command.commands.xp;

import Tiimae.TiimaeBot.Command.CommandContext;
import Tiimae.TiimaeBot.Command.ICommand;
import Tiimae.TiimaeBot.controllers.commands.LeaderboardController;
import kong.unirest.json.JSONArray;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.text.ParseException;

public class LeaderboarcCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) throws ParseException {
        LeaderboardController leaderboardController = new LeaderboardController();
        final EmbedBuilder embedBuilder = new EmbedBuilder();
        final TextChannel channel = (TextChannel) ctx.getEvent().getChannel();

        String title = String.format("This is the exp leaderbord for %s", ctx.getGuild().getName());
        embedBuilder.setTitle(title);
        embedBuilder.setThumbnail(ctx.getGuild().getIconUrl());
        embedBuilder.setFooter(ctx.getEvent().getAuthor().getName(), ctx.getEvent().getAuthor().getAvatarUrl());

        final JSONArray leaderboard = leaderboardController.getLeaderboard(ctx.getGuild());

        for (int i = 0; i < leaderboard.length(); i++) {
            final JSONArray o = leaderboard.getJSONArray(i);

            embedBuilder.addField(new MessageEmbed.Field(
                    String.format("%s. %s", i + 1, o.get(2)),
                    String.format("exp: %s | lvl: %s", o.get(0), o.get(1)),
                    false
                    ));
        }

        channel.sendMessage("").setEmbeds(embedBuilder.build()).queue();
    }

    @Override
    public String getName() {
        return "leaderboard";
    }

    @Override
    public String getHelp() {
        return "Shows the xp leaderboard. \n" +
                "Usage: `<your-prefix>leaderboard`";
    }
}
