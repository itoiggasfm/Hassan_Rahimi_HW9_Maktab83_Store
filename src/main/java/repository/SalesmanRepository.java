package repository;

import connection.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SalesmanRepository {

    private static final String CHANGE_PASSWORD_QUERY = "UPDATE tbl_person set password = ? WHERE id = ?";
    public boolean chnagePasword(String ID, String newPassword) {
        try{
            Connection connection = DataBaseConnection.getInstance();
            PreparedStatement preparedStatement = connection.prepareStatement(CHANGE_PASSWORD_QUERY);
            preparedStatement.setString(1,newPassword);
            preparedStatement.setString(2, ID);

            if(preparedStatement.executeUpdate()>0)
                return true;
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
