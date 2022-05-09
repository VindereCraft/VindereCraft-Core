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
    public void removePlayerRecord(String storageMethod, Core core, Player player) {
        // Runs if storageMethod is "MYSQL"
        if (storageMethod.equalsIgnoreCase("MYSQL")) {
            mySQL.removePlayerRecord(player, core);
        }
    }
    public void updatePlayerRecordString(String storageMethod, Core core, Player player, String field, String data) {
        // Runs if storageMethod is "MYSQL"
        if (storageMethod.equalsIgnoreCase("MYSQL")) {
            mySQL.updatePlayerRecordString(player, core, field, data);
        }
    }
    public void updatePlayerRecordInt(String storageMethod, Core core, Player player, String field, int data) {
        // Runs if storageMethod is "MYSQL"
        if (storageMethod.equalsIgnoreCase("MYSQL")) {
            mySQL.updatePlayerRecordInt(player, core, field, data);
        }
    }
    public String getPlayerRecordString(String storageMethod, Core core, Player player, String field) {
        // Runs if storageMethod is "MYSQL"
        if (storageMethod.equalsIgnoreCase("MYSQL")) {
            return mySQL.getPlayerRecordString(player, core, field);
        } else return null;
    }
    public int getPlayerRecordInt(String storageMethod, Core core, Player player, String field) {
        // Runs if storageMethod is "MYSQL"
        if (storageMethod.equalsIgnoreCase("MYSQL")) {
            return mySQL.getPlayerRecordInt(player, core, field);
        } else return 0;
    }
}
