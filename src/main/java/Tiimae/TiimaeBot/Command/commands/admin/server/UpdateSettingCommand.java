package Tiimae.TiimaeBot.Command.commands.admin.server;

import Tiimae.TiimaeBot.Command.CommandContext;
import Tiimae.TiimaeBot.Command.ICommand;
import Tiimae.TiimaeBot.service.SettingsService;

import java.text.ParseException;

public class UpdateSettingCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) throws ParseException {
        final SettingsService settingsService = new SettingsService();
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
