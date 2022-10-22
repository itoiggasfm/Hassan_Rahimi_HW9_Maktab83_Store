package connection;

import java.sql.*;

public class DataBaseConnection {
    private static Connection connection = null;

    private DataBaseConnection(){}

    public static Connection getInstance() throws SQLException{
        if(connection == null){
            connection = DriverManager.getConnection(DBConfig.url , DBConfig.user , DBConfig.password);
        }
        return connection;
    }
}


