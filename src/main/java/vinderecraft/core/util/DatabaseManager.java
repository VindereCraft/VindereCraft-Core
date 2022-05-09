package vinderecraft.core.util;

// VindereCraft Imports
import org.bukkit.entity.Player;
import vinderecraft.core.Core;
import vinderecraft.core.db.MySQL;

public class DatabaseManager {
    public MySQL mySQL = new MySQL();

    // Determines which method is used to store data and connects to database if applicable
    public void connect (String storageMethod, Core core) {
        // Runs if storageMethod is "MYSQL"
        if (storageMethod.equalsIgnoreCase("MYSQL")) {
            mySQL.connect(core);
        }
    }
    public void terminate (String storageMethod, Core core) {
        // Runs if storageMethod is "MYSQL"
        if (storageMethod.equalsIgnoreCase("MYSQL")) {
            mySQL.terminate(core);
        }
    }
    public void buildDatabase (String storageMethod, Core core) {
        // Runs if storageMethod is "MYSQL"
        if (storageMethod.equalsIgnoreCase("MYSQL")) {
            mySQL.createTables(core);
        }
    }
    public void createPlayerRecord(String storageMethod, Core core, Player player) {
        // Runs if storageMethod is "MYSQL"
        if (storageMethod.equalsIgnoreCase("MYSQL")) {
            mySQL.createPlayerRecord(player, core);
        }
    }
}
