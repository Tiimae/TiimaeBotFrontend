package Tiimae.TiimaeBot.button.buttons.admin;

import Tiimae.TiimaeBot.ButtonEnum;
import Tiimae.TiimaeBot.button.ButtonContext;
import Tiimae.TiimaeBot.button.IButton;

import java.text.ParseException;

public class XpChannelButton implements IButton {
    @Override
    public void handle(ButtonContext event) throws ParseException {
        System.out.println(ButtonEnum.ADDXPBUTTON.name());
    }

    @Override
    public String getId() {
        return ButtonEnum.ADDXPBUTTON.name();
    }
}
