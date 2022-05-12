package vinderecraft.core.util;

import vinderecraft.core.Core;
import vinderecraft.core.db.MySQL;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseManager {

    public MySQL mySQL = new MySQL();

    public Connection connectMYSQL (Core core) {
        return mySQL.getConnection(core);
    }

    public void terminateSQL (Core core, Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
