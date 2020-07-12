package mcmi;

import java.awt.*;

public class Constant {

    public static final int LIGHT_THEME = 0;
    public static final int DARK_THEME = 1;
    public static int CURRENT_THEME = DARK_THEME;

    public static final Color mainColor = CURRENT_THEME == DARK_THEME ? new Color(36, 38, 59) : new Color(246, 249, 250);
    public static final Color secondaryColor = CURRENT_THEME == DARK_THEME ? new Color(58, 58, 79) : new Color(255, 255, 255);
    public static final Color btnBorderColor = CURRENT_THEME == DARK_THEME ? new Color(89, 114, 124) : new Color(219, 229, 233);
    public static final Color textColor = CURRENT_THEME == DARK_THEME ? new Color(208, 211, 226) : new Color(141, 146, 149);
    public static final Color linkColor =CURRENT_THEME == DARK_THEME ?  new Color(49, 149, 198) : new Color(14, 119, 187);
    public static final Font body = new Font("Microsoft GothicNeo", Font.PLAIN, 13);

}
