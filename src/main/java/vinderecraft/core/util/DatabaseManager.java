package vinderecraft.core.util;

import vinderecraft.core.Core;

public class DatabaseManager {
    private final Core core;
    public DatabaseManager (Core core) {
        this.core = core;
    }

    public String getString () {

        if (core.Config.readString("primary", "player-data-storage-method").equalsIgnoreCase("MYSQL")) {
            return null;
        }
        return null;
    }
}
