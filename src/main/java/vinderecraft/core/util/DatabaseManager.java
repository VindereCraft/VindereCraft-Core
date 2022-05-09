package vinderecraft.core.util;

import vinderecraft.core.Core;
import vinderecraft.core.db.MySQL;

public class DatabaseManager {

    public void connect (String storageMethod, Core core) {

        if (storageMethod.equalsIgnoreCase("MYSQL")) {
            MySQL mySQL = new MySQL();
            mySQL.connect(core);
        }
    }
}
