package repository;
import connection.DataBaseConnection;
import model.*;
import model.enumeration.PersonRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CostomerRepository {
    
    private static final String SIGNIN_QUERY = "SELECT * FROM tbl_person WHERE user_name = ?";
    public Customer signIn(String username, String password){
        try {
            Connection connection = DataBaseConnection.getInstance();
            PreparedStatement preparedStatement = connection.prepareStatement(SIGNIN_QUERY);
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

           while (resultSet.next()){
               if(resultSet.getString("password").equals(password)){
                   Customer signedInUser = new Customer();
                   signedInUser.setId(resultSet.getString("id"));
                   signedInUser.setName(resultSet.getString("name"));
                   signedInUser.setUserName(resultSet.getString("user_name"));
                   signedInUser.setPassword(resultSet.getString("password"));
                   signedInUser.setPersonRole(PersonRole.valueOf(resultSet.getString("person_role")));
                   return signedInUser;
               }
               else
                   return null;
           }

            preparedStatement.close();

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }
//    -------------------------------------------------------------------

       //does national code(ID) exist
    private static final String FIND_BY_id_QUERY = "SELECT id FROM tbl_person WHERE id = ?";
    public boolean findByID(String ID){
        try {
            Connection connection = DataBaseConnection.getInstance();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_id_QUERY);
            preparedStatement.setString(1, ID);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.isBeforeFirst())
                return true;


            preparedStatement.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }
//    ----------------------------------------------------------
    
    //create user
    private static final String CREATE_QUERY = "INSERT INTO tbl_person (id, name, user_name, password, person_role) VALUES (?, ?, ?, ?, ?::person_role)";

    public boolean create(Customer user){
        try {
            Connection connection = DataBaseConnection.getInstance();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_QUERY);
            preparedStatement.setString(1, user.getId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getUserName());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getPersonRole().toString());

            if(preparedStatement.executeUpdate()>0)
                return true;

            preparedStatement.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }
//------------------------------------------------------------------
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
//    ------------------------------------------------------------------

    private static final String FIBD_ALL_QUERY = "SELECT * FROM tbl_person";
    public List<Person> findAll() {
        List<Person> allPeopleList = new ArrayList<>();
        try{
            Connection connection = DataBaseConnection.getInstance();
            PreparedStatement preparedStatement = connection.prepareStatement(FIBD_ALL_QUERY);
            ResultSet resultset = preparedStatement.executeQuery();

            while (resultset.next()){
                Person person = new Customer();
                person.setId(resultset.getString("id"));
                person.setName(resultset.getString("name"));
                person.setUserName(resultset.getString("user_name"));
                person.setPassword(resultset.getString("password"));
                person.setPersonRole(PersonRole.valueOf(resultset.getString("person_role")));
                allPeopleList.add(person);
            }
            preparedStatement.close();
            return allPeopleList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
//    ------------------------------------------------------------------


}//end of class UserRepository
