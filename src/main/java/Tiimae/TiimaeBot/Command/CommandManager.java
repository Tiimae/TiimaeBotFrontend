package Tiimae.TiimaeBot.Command;

import Tiimae.TiimaeBot.Command.commands.admin.server.CreateSettingCommand;
import Tiimae.TiimaeBot.Command.commands.admin.server.ShowSettingsCommand;
import Tiimae.TiimaeBot.Command.commands.help.HelpCommand;
import Tiimae.TiimaeBot.Command.commands.xp.LeaderboarcCommand;
import Tiimae.TiimaeBot.Command.commands.xp.LevelCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.annotation.Nullable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class CommandManager {
    private final List<ICommand> commands = new ArrayList<>();

    public CommandManager() {
        // help commands
        addCommand(new HelpCommand(this));

        //admin commands
        addCommand(new CreateSettingCommand());
        addCommand(new ShowSettingsCommand());

        // xp commands
        addCommand(new LevelCommand());
        addCommand(new LeaderboarcCommand());
    }

    private void addCommand(ICommand cmd) {
        boolean nameFound = this.commands.stream().anyMatch((it) -> it.getName().equalsIgnoreCase(cmd.getName()));

        if (nameFound) {
            throw new IllegalArgumentException("A command with this name is already in use!");
        }

        commands.add(cmd);
    }

    @Nullable
    public ICommand getCommand(String search) {
        String searchLower = search;

        for (ICommand cmd : this.commands) {
            if (cmd.getName().equals(searchLower) || cmd.getAliases().contains(searchLower)) {
                return cmd;
            }
        }

        return null;
    }

    public List<ICommand> getCommands() {
        return commands;
    }

    public void handle(MessageReceivedEvent event, String prefix) throws ParseException {
        String[] split = event.getMessage().getContentRaw()
                .replaceFirst("(?i)" + Pattern.quote(prefix), "")
                .split("\\s+");

        String invoke = split[0].toLowerCase();
        ICommand cmd = this.getCommand(invoke);

        if (cmd != null) {
            event.getChannel().sendTyping().queue();
            List<String> args = Arrays.asList(split).subList(1, split.length);

            CommandContext ctx = new CommandContext(event, args);

            cmd.handle(ctx);
        }
    }
}
