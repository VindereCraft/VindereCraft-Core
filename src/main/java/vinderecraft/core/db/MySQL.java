package vinderecraft.core.db;

import vinderecraft.core.Core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {
    public Connection getConnection(Core core) {
        if (core.conn == null) {
            String username = core.Config.readString("sql", "mysql.user");
            String password = core.Config.readString("sql", "mysql.password");
            String connString = "jdbc:mysql//" + core.Config.readString("sql", "mysql.host") + ":" + core.Config.readString("sql", "mysql.port") + "/" + core.Config.readString("sql", "mysql.database");
            try {
                return DriverManager.getConnection(connString, username, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        } else return core.conn;
    }
}
