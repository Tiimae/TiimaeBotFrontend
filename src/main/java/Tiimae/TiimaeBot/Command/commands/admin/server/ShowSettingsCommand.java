package Tiimae.TiimaeBot.Command.commands.admin.server;

import Tiimae.TiimaeBot.Command.CommandContext;
import Tiimae.TiimaeBot.Command.ICommand;
import Tiimae.TiimaeBot.controllers.commands.SettingController;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;

public class ShowSettingsCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) throws ParseException {
        SettingController settingController = new SettingController();
        TextChannel channel = (TextChannel) ctx.getEvent().getChannel();
        final List<String> args = ctx.getArgs();
        final EmbedBuilder embedBuilder = new EmbedBuilder();

        if (args.isEmpty()) {
            final JSONArray allGuildSettings = settingController.getAllGuildSettings(ctx.getGuild().getIdLong());
            embedBuilder.setTitle("All guild settings");
            embedBuilder.setDescription(String.format("These are the settings for server %s", ctx.getGuild().getName()));
            embedBuilder.setThumbnail(ctx.getGuild().getIconUrl());
            embedBuilder.setFooter(ctx.getEvent().getAuthor().getName(), ctx.getEvent().getAuthor().getAvatarUrl());

            for (int i = 0; i < allGuildSettings.length(); i++) {
                final JSONObject jsonObject = allGuildSettings.getJSONObject(i);
                if (jsonObject.getString("key").contains("ROLE")) {
                    final Role value = this.getRole(jsonObject.getString("value"), ctx.getGuild());
                    embedBuilder.addField(new MessageEmbed.Field(
                            String.format("This is the setting for %s: ", jsonObject.getString("key").toLowerCase(Locale.ROOT)),
                            String.format("The role is %s", value),
                            false
                    ));
                } else if (jsonObject.getString("key").contains("PREFIX")) {
                    embedBuilder.addField(new MessageEmbed.Field(
                            String.format("This is the setting for %s: ", jsonObject.getString("key").toLowerCase(Locale.ROOT)),
                            String.format("The prefix is %s", jsonObject.getString("value")),
                            false
                    ));
                } else {
                    final TextChannel value = this.getTextChannel(jsonObject.getString("value"), ctx.getGuild());
                    embedBuilder.addField(new MessageEmbed.Field(
                            String.format("This is the setting for %s", jsonObject.getString("key").toLowerCase(Locale.ROOT)),
                            String.format("The channel is %s", value),
                            false
                    ));
                }
            }
        }

        channel.sendMessage("").setEmbeds(embedBuilder.build()).queue();
    }

    @Override
    public String getName() {
        return "show-setting";
    }

    @Override
    public String getHelp() {
        return "shows all guild settings you have. \n" +
                "Usage: `<Your-prefix>show-setting`";
    }

    public TextChannel getTextChannel(String id, Guild guild) {
        for (TextChannel textChannel : guild.getTextChannels()) {
            if (textChannel.getId().equals(id)) {
                return textChannel;
            }
        }
        return null;
    }

    public Role getRole(String id, Guild guild) {
        for (Role role : guild.getRoles()) {
            if (role.getId().equals(id)) {
                return role;
            }
        }

        return null;
    }
}
