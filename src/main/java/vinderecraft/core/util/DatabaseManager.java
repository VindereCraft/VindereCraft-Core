package vinderecraft.core.util;

// VindereCraft Imports
import vinderecraft.core.Core;
import vinderecraft.core.db.MySQL;

public class DatabaseManager {

    // Determines which method is used to store data and connects to database if applicable
    public void connect (String storageMethod, Core core) {
        // Runs if storageMethod is "MYSQL"
        if (storageMethod.equalsIgnoreCase("MYSQL")) {
            MySQL mySQL = new MySQL();
            mySQL.connect(core);
        }
    }
}
