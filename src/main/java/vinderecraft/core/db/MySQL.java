package vinderecraft.core.db;

// VindereCraft Imports
import vinderecraft.core.Core;

// Bukkit Imports
import org.bukkit.entity.Player;

// Java Imports
import java.sql.*;
import java.util.UUID;

public class MySQL {
    // Local variables
    private Connection conn;
    private String tablePlayerData;

    // Checks to see if a connection to the MySQL database exists
    public boolean isConnected () {
        return (conn == null ? false : true);
    }

    // Connects to the MySQL database
    public void connect (Core core) {
        // Runs only if a connection does not already exist
        if (!isConnected()) {
            // Local variable assignment from values in config.
            String host = core.Config.readString("sql", "mysql.host");
            String port = core.Config.readString("sql", "mysql.port");
            String database = core.Config.readString("sql", "mysql.database");
            String user = core.Config.readString("sql", "mysql.user");
            String password = core.Config.readString("sql", "mysql.password");
            tablePlayerData = core.Config.readString("sql", "mysql.tables.player-data");
            // Try to connect and throws an error if an exception is found
            try {
                conn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?user=" + user + "&password=" + password);
                // Sends debug message
                if (core.debug) { core.getServer().getConsoleSender().sendMessage("[VC CORE] [DEBUG] Connected to MySQL Database. (" + user + "@" + host + ":" + port + ")"); }
            // Print error stack on exception
            } catch (SQLException e) {
                core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] Unable to connect to MySQL database.");
                core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] SQLException: " + e.getMessage());
                core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] SQLState: " + e.getSQLState());
                core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] VendorError: " + e.getErrorCode());
            }
        }
    }

    // Ends connection to the MySQL database
    public void terminate(Core core) {
        // Runs only if a connection exists
        if (isConnected()) {
            // Try to close connection and throws an error if an exception is found
            try {
                conn.close();
                // Sends debug message
                if (core.debug) { core.getServer().getConsoleSender().sendMessage("[VC CORE] [DEBUG] Terminated connection to MySQL Database."); }
            // Print error stack on exception
            } catch (SQLException e) {
                core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] Unable to disconnect from MySQL database.");
                core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] SQLException: " + e.getMessage());
                core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] SQLState: " + e.getSQLState());
                core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] VendorError: " + e.getErrorCode());
            }
        }
    }

    // Returns type Connection
    public Connection getConn () {
        return conn;
    }

    // Create MySQL tables within database specified in 'sql.yml'
    public void createTables(Core core) {
        // Initiate and assign local variables
        PreparedStatement ps;
        tablePlayerData = core.Config.readString("sql", "mysql.tables.player-data");
        // Try to create player data table and throws an error if an exception is found
        try {
            ps = getConn().prepareStatement("CREATE TABLE IF NOT EXISTS ? "
                    + "(UUID VARCHAR(100),NAME VARCHAR(100),DISPLAY_NAME VARCHAR(100),IP_ADDR VARCHAR(50), PRIMARY KEY (UUID))");
            ps.setString(1, tablePlayerData);
            ps.executeUpdate();
            // Sends debug message
            if (core.debug) { core.getServer().getConsoleSender().sendMessage("[VC CORE] [DEBUG] Created MySQL tables."); }
        // Print error stack on exception
        } catch (SQLException e) {
            core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] Unable to create 'player_data' table.");
            core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] SQLException: " + e.getMessage());
            core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] SQLState: " + e.getSQLState());
            core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] VendorError: " + e.getErrorCode());
        }
    }

    // Creates record in MySQL database for player information
    public void createPlayerRecord(Player player, Core core) {
        // Initiate and assign local variables
        PreparedStatement ps;
        ResultSet rs;
        UUID uuid = player.getUniqueId();
        tablePlayerData = core.Config.readString("sql", "mysql.tables.player-data");
        // Run only if the player record does not already exist
        if (!playerExists(player, core)) {
            // Try to create new entry and throws an exception if an error is found
            try {
                ps = getConn().prepareStatement("INSERT INTO ?"
                        + " (UUID,NAME,DISPLAY_NAME,IP_ADDR) VALUES (?,?,?,?)");
                ps.setString(1, tablePlayerData);
                ps.setString(2, uuid.toString());
                ps.setString(3, player.getName());
                ps.setString(4, player.getName());
                ps.setString(5, player.getAddress().getHostString());
                ps.executeUpdate();
                // Sends debug message
                if (core.debug) { core.getServer().getConsoleSender().sendMessage("[VC CORE] [DEBUG] Created player record for " + player.getName() + "."); }
            // Print error stack on exception
            } catch (SQLException e) {
                core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] Unable to create player record.");
                core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] SQLException: " + e.getMessage());
                core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] SQLState: " + e.getSQLState());
                core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] VendorError: " + e.getErrorCode());
            }
        }
    }

    // Returns String value from player data table within the MySQL database
    public String getPlayerRecordString(Player player, Core core, String field) {
        // Initiate and assign local variables
        PreparedStatement ps;
        ResultSet rs;
        UUID uuid = player.getUniqueId();
        tablePlayerData = core.Config.readString("sql", "mysql.tables.player-data");
        // Runs only if the player record exists
        if (playerExists(player, core)) {
            // Try to get the string and throws an exception if an error is found
            try {
                ps = getConn().prepareStatement("SELECT ? FROM ? WHERE UUID=?");
                ps.setString(1, field);
                ps.setString(2, tablePlayerData);
                ps.setString(3, uuid.toString());
                rs = ps.executeQuery();
                if (rs.next()) {
                    // Sends debug message and returns field if exists
                    if (core.debug) { core.getServer().getConsoleSender().sendMessage("[VC CORE] [DEBUG] Retrieved player record string for " + player.getName() + "."); }
                    return rs.getString(field);
                }
            // Print error stack on exception
            } catch (SQLException e) {
                core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] Unable to retrieve player record.");
                core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] SQLException: " + e.getMessage());
                core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] SQLState: " + e.getSQLState());
                core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] VendorError: " + e.getErrorCode());
            }
        }
        // Catch-all return
        return null;
    }

    // Returns int value from player data table within the MySQL database
    public int getPlayerRecordInt(Player player, Core core, String field) {
        // Initiate and assign local variables
        PreparedStatement ps;
        ResultSet rs;
        UUID uuid = player.getUniqueId();
        tablePlayerData = core.Config.readString("sql", "mysql.tables.player-data");
        // Runs only if the player record exists
        if (playerExists(player, core)) {
            // Try to get the integer and throws an exception if an error is found
            try {
                ps = getConn().prepareStatement("SELECT ? FROM ? WHERE UUID=?");
                ps.setString(1, field);
                ps.setString(2, tablePlayerData);
                ps.setString(3, uuid.toString());
                rs = ps.executeQuery();
                if (rs.next()) {
                    // Sends debug message and returns field if exists
                    if (core.debug) { core.getServer().getConsoleSender().sendMessage("[VC CORE] [DEBUG] Retrieved player record int for " + player.getName() + "."); }
                    return rs.getInt(field);
                }
            // Print error stack on exception
            } catch (SQLException e) {
                core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] Unable to retrieve player record.");
                core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] SQLException: " + e.getMessage());
                core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] SQLState: " + e.getSQLState());
                core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] VendorError: " + e.getErrorCode());
            }
        }
        // Catch-all return
        return 0;
    }


    public void removePlayerRecord(Player player, Core core) {
        // Initiate and assign local variables
        PreparedStatement ps;
        ResultSet rs;
        UUID uuid = player.getUniqueId();
        tablePlayerData = core.Config.readString("sql", "mysql.tables.player-data");
        // Runs only if the player record exists
        if (playerExists(player, core)) {
            // Try to remove the player record and throws an exception if an error is found
            try {
                ps = getConn().prepareStatement("DELETE FROM ? WHERE UUID=?");
                ps.setString(1, tablePlayerData);
                ps.setString(2, uuid.toString());
                ps.executeUpdate();
                // Sends debug message
                if (core.debug) { core.getServer().getConsoleSender().sendMessage("[VC CORE] [DEBUG] Removed player record for " + player.getName() + "."); }
            // Print error stack on exception
            } catch (SQLException e) {
                core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] Unable to remove player record.");
                core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] SQLException: " + e.getMessage());
                core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] SQLState: " + e.getSQLState());
                core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] VendorError: " + e.getErrorCode());
            }
        }
    }

    // Updates String value from player data table within the MySQL database
    public void updatePlayerRecordString(Player player, Core core, String field, String data) {
        // Initiate and assign local variables
        PreparedStatement ps;
        ResultSet rs;
        UUID uuid = player.getUniqueId();
        tablePlayerData = core.Config.readString("sql", "mysql.tables.player-data");
        // Runs only if the player record exists
        if (playerExists(player, core)) {
            // Try to update the string and throws an exception if an error is found
            try {
                ps = getConn().prepareStatement("UPDATE ? SET ?=? WHERE UUID=?");
                ps.setString(1, tablePlayerData);
                ps.setString(2, field);
                ps.setString(3, data);
                ps.setString(4, uuid.toString());
                ps.executeUpdate();
            // Print error stack on exception
            } catch (SQLException e) {
                core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] Unable to update player record.");
                core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] SQLException: " + e.getMessage());
                core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] SQLState: " + e.getSQLState());
                core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] VendorError: " + e.getErrorCode());
            }
        }
    }

    // Updates int value from player data table within the MySQL database
    public void updatePlayerRecordInt(Player player, Core core, String field, int data) {
        // Initiate and assign local variables
        PreparedStatement ps;
        ResultSet rs;
        UUID uuid = player.getUniqueId();
        tablePlayerData = core.Config.readString("sql", "mysql.tables.player-data");
        // Runs only if the player record exists
        if (playerExists(player, core)) {
            // Try to update the integer and throws an exception if an error is found
            try {
                ps = getConn().prepareStatement("UPDATE ? SET ?=? WHERE UUID=?");
                ps.setString(1, tablePlayerData);
                ps.setString(2, field);
                ps.setInt(3, data);
                ps.setString(4, uuid.toString());
                ps.executeUpdate();
            // Print error stack on exception
            } catch (SQLException e) {
                core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] Unable to update player record.");
                core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] SQLException: " + e.getMessage());
                core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] SQLState: " + e.getSQLState());
                core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] VendorError: " + e.getErrorCode());
            }
        }
    }

    // Checks to see if player exists on the MySQL database.
    public boolean playerExists(Player player, Core core) {
        // Initiate and assign local variables
        PreparedStatement ps;
        ResultSet rs;
        UUID uuid = player.getUniqueId();
        tablePlayerData = core.Config.readString("sql", "mysql.tables.player-data");
        // Try to see if player exists and throws an error if an exception is found
        try {
            ps = getConn().prepareStatement("SELECT * FROM ? WHERE UUID=?");
            ps.setString(1, tablePlayerData);
            ps.setString(2, uuid.toString());
            rs = ps.executeQuery();
            // If results exist, player exists
            if (rs.next()) {
                if (core.debug) { core.getServer().getConsoleSender().sendMessage("[VC CORE] [DEBUG] " + player.getName() + " has joined before."); }
                return true;
            } else {
                if (core.debug) { core.getServer().getConsoleSender().sendMessage("[VC CORE] [DEBUG] " + player.getName() + " has not joined before."); }
                return false;
            }
        // Print error stack on exception
        } catch (SQLException e) {
            core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] Unable to check if player exists.");
            core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] SQLException: " + e.getMessage());
            core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] SQLState: " + e.getSQLState());
            core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] VendorError: " + e.getErrorCode());
        }
        // Catch-all return
        return false;
    }
}
