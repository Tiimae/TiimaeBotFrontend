package Tiimae.TiimaeBot.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Xp {
    private String id;
    private String userGuildID;
    private long xp;
    private long level;
    private Timestamp xpLock;

    public Xp(String id, String userGuildID, long xp, long level, Timestamp xpLock) {
        this.id = id;
        this.userGuildID = userGuildID;
        this.xp = xp;
        this.level = level;
        this.xpLock = xpLock;
    }
}
