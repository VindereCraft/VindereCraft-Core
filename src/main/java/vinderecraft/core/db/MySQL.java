package vinderecraft.core.db;

// VindereCraft Imports
import org.bukkit.entity.Player;
import vinderecraft.core.Core;

// SQL Imports
import java.sql.*;
import java.util.UUID;

public class MySQL {

    private Connection conn;
    private String host;
    private String port;
    private String database;
    private String user;
    private String password;
    private String tablePlayerData;

    public boolean isConnected () {
        return (conn == null ? false : true);
    }

    public void connect (Core core) {
        if (!isConnected()) {
            host = core.Config.readString("sql", "mysql.host");
            port = core.Config.readString("sql", "mysql.port");
            database = core.Config.readString("sql", "mysql.database");
            user = core.Config.readString("sql", "mysql.user");
            password = core.Config.readString("sql", "mysql.password");
            tablePlayerData = core.Config.readString("sql", "mysql.tables.player-data");

            try {
                conn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?user=" + user + "&password=" + password);
                if (core.debug) { core.getServer().getConsoleSender().sendMessage("[VC CORE] [DEBUG] Connected to MySQL Database. (" + user + "@" + host + ":" + port + ")"); }

            } catch (SQLException e) {
                core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] Unable to connect to MySQL database.");
                core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] SQLException: " + e.getMessage());
                core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] SQLState: " + e.getSQLState());
                core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] VendorError: " + e.getErrorCode());
            }
        }
    }

    public void terminate(Core core) {
        if (isConnected()) {
            try {
                conn.close();
                if (core.debug) { core.getServer().getConsoleSender().sendMessage("[VC CORE] [DEBUG] Terminated connection to MySQL Database."); }
            } catch (SQLException e) {
                core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] Unable to disconnect from MySQL database.");
                core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] SQLException: " + e.getMessage());
                core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] SQLState: " + e.getSQLState());
                core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] VendorError: " + e.getErrorCode());
            }
        }
    }

    public Connection getConn () {
        return conn;
    }

    public void createTables(Core core) {
        PreparedStatement ps;
        try {
            ps = getConn().prepareStatement("CREATE TABLE IF NOT EXISTS player_data "
                    + "(UUID VARCHAR(100),NAME VARCHAR(100),DISPLAY_NAME VARCHAR(100),IP_ADDR VARCHAR(50), PRIMARY KEY (UUID))");
            ps.executeUpdate();
            // Sends debug message
            if (core.debug) { core.getServer().getConsoleSender().sendMessage("[VC CORE] [DEBUG] Created MySQL tables."); }
        } catch (SQLException e) {
            core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] Unable to create 'player_data' table.");
            core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] SQLException: " + e.getMessage());
            core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] SQLState: " + e.getSQLState());
            core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] VendorError: " + e.getErrorCode());
        }
    }

    public void createPlayerRecord(Player player, Core core) {
        PreparedStatement ps;
        ResultSet rs;
        UUID uuid = player.getUniqueId();
        if (!playerExists(player, core)) {
            try {
                ps = getConn().prepareStatement("INSERT INTO player_data"
                        + " (UUID,NAME,DISPLAY_NAME,IP_ADDR) VALUES (?,?,?,?)");
                ps.setString(1, uuid.toString());
                ps.setString(2, player.getName());
                ps.setString(3, player.getName());
                ps.setString(4, player.getAddress().getHostString());
                ps.executeUpdate();
                // Sends debug message
                if (core.debug) { core.getServer().getConsoleSender().sendMessage("[VC CORE] [DEBUG] Created player record for " + player.getName() + "."); }
            } catch (SQLException e) {
                core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] Unable to create player record.");
                core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] SQLException: " + e.getMessage());
                core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] SQLState: " + e.getSQLState());
                core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] VendorError: " + e.getErrorCode());
            }
        }
    }

    public boolean playerExists(Player player, Core core) {
        PreparedStatement ps;
        ResultSet rs;
        UUID uuid = player.getUniqueId();
        try {
            ps = getConn().prepareStatement("SELECT * FROM player_data WHERE UUID=?");
            ps.setString(1, uuid.toString());
            rs = ps.executeQuery();
            if (rs.next()) {
                if (core.debug) { core.getServer().getConsoleSender().sendMessage("[VC CORE] [DEBUG] " + player.getName() + " has joined before."); }
                return true;
            } else {
                if (core.debug) { core.getServer().getConsoleSender().sendMessage("[VC CORE] [DEBUG] " + player.getName() + " has not joined before."); }
                return false;
            }
        } catch (SQLException e) {
            core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] Unable to check if player exists.");
            core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] SQLException: " + e.getMessage());
            core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] SQLState: " + e.getSQLState());
            core.getServer().getConsoleSender().sendMessage("[VC CORE] [ERROR] VendorError: " + e.getErrorCode());
        }
        return false;
    }
}
