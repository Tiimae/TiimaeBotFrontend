package Tiimae.TiimaeBot.Command;

import java.text.ParseException;
import java.util.List;

public interface ICommand {
    void handle(CommandContext ctx) throws ParseException;

    String getName();

    String getHelp();

    default List<String> getAliases() {
        return List.of();
    }
}
