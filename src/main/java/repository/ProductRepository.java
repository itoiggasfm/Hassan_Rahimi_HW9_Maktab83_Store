package repository;

import connection.DataBaseConnection;
import model.Product;
import model.enumeration.ProductType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

//    ---------------------------------------------------------------

        private static final String FIND_BY_ID_QUERY = "SELECT id, name, price, quantity, product_type FROM tbl_product WHERE id = ?";

    public Product findByID(int ID){
        try {
            Connection connection = DataBaseConnection.getInstance();
                    PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_QUERY);
                    preparedStatement.setInt(1, ID);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    Product product = new Product();
                        while (resultSet.next()){
                            product.setId(resultSet.getInt("id"));
                            product.setName(resultSet.getString("name"));
                            product.setPrice(resultSet.getFloat("price"));
                            product.setQuantity(resultSet.getInt("quantity"));
                            product.setProductType(ProductType.valueOf(resultSet.getString("product_type")));
                        }
                        preparedStatement.close();
                        return product;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }//end of findByName
//    ---------------------------------------------------------------

    private static final String DECREASE_PRODUCT_NUMBER_QUERY = "UPDATE tbl_product SET quantity = quantity-1 WHERE id = ?";
    public void decreaseProductQuantity(int ID){
        try {

            Connection connection = DataBaseConnection.getInstance();
            PreparedStatement preparedStatement = connection.prepareStatement(DECREASE_PRODUCT_NUMBER_QUERY);
                preparedStatement.setInt(1, ID);
                preparedStatement.executeUpdate();

                preparedStatement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }//end of decreaseMedicineNumber
//    -------------------------------------------------------------------

    private static final String FIND_ALL_PRODUCTS_QUERY = "SELECT * FROM tbl_product";
    public List findAll(){
        try {
            List<Product> productsList = new ArrayList<>();
            Connection connection = DataBaseConnection.getInstance();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_PRODUCTS_QUERY);
            ResultSet resultset = preparedStatement.executeQuery();

            while (resultset.next()){
                Product product = new Product();
                product.setId(resultset.getInt("id"));
                product.setName(resultset.getString("name"));
                product.setPrice(resultset.getFloat("price"));
                product.setQuantity(resultset.getInt("quantity"));
                product.setProductType(ProductType.valueOf(resultset.getString("product_type")));
                productsList.add(product);
            }
            preparedStatement.close();
            return productsList;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }//end of findAll
//   ------------------------------------------------------------------
private static final String CREATE_QUERY = "INSERT INTO tbl_product (name, price, quantity, product_type) VALUES (?, ?, ?, ?::product_type)";
public boolean create(Product newProduct){
    try {

        Connection connection = DataBaseConnection.getInstance();
        PreparedStatement preparedStatement = connection.prepareStatement(CREATE_QUERY);
        preparedStatement.setString(1, newProduct.getName());
        preparedStatement.setFloat(2, newProduct.getPrice());
        preparedStatement.setInt(3, newProduct.getQuantity());
        preparedStatement.setString(4, newProduct.getProductType().toString());
        if(preparedStatement.executeUpdate()>0)
            return true;

        preparedStatement.close();



    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    return false;
}//end of create
//    ------------------------------------------------------------------

    private static final String DELETE_QUERY = "DELETE FROM tbl_product WHERE id = ?";
    public boolean delete(int ID){
        try {
            Connection connection = DataBaseConnection.getInstance();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY);
            preparedStatement.setInt(1, ID);
            if(preparedStatement.executeUpdate()>0)
                return true;

            preparedStatement.close();



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }//end of delete
}//end of MedicineRepository
