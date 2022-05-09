package vinderecraft.core.db;

import vinderecraft.core.Core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {

    private String host;
    private String port;
    private String database;
    private String user;
    private String password;
    private String tablePlayerData;


    public void connect (Core core) {
        host = core.Config.readString("sql", "mysql.host");
        port = core.Config.readString("sql", "mysql.port");
        database = core.Config.readString("sql", "mysql.database");
        user = core.Config.readString("sql", "mysql.user");
        password = core.Config.readString("sql", "mysql.password");
        tablePlayerData = core.Config.readString("sql", "mysql.tables.player-data");
        Connection conn;
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
