package repository;

import connection.DataBaseConnection;
import model.*;
import model.enumeration.OrderStatus;
import model.enumeration.ProductType;

//import org.apache.commons.lang3.ArrayUtils;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.lang.*;

public class OrderRepository {

private ProductRepository productRepository;
public OrderRepository(){
    this.productRepository = new ProductRepository();
}
    private static final String CREATE_QUERY = "INSERT INTO tbl_order (item_id, id, person_id, product_id, product_type, item_quantity, item_price, total_item_price, order_date, order_status) VALUES (?, ?, ?, ?, ?::product_type, ?, ?, ?, ?, ?::order_status)";

    public int create(List<Order> cart){
        try{
            Connection connection = DataBaseConnection.getInstance();
            int orderCount = 0;
            for(Order orderItem: cart){
                PreparedStatement preparedStatement = connection.prepareStatement(CREATE_QUERY);
                preparedStatement.setInt(1, orderItem.getItemID());
                preparedStatement.setString(2, orderItem.getId());
                preparedStatement.setString(3, orderItem.getPersonID());
                preparedStatement.setInt(4, orderItem.getProductID());
                preparedStatement.setString(5, orderItem.getProductType().toString());
                preparedStatement.setInt(6, orderItem.getItemQuantity());
                preparedStatement.setFloat(7, orderItem.getItemPrice());
                preparedStatement.setFloat(8, orderItem.getTotalItemPrice());
                preparedStatement.setString(9, orderItem.getOrderDate());;
                preparedStatement.setString(10, orderItem.getOrderStatus().toString());

                preparedStatement.executeUpdate();
                ++orderCount;
                productRepository.decreaseProductQuantity(orderItem.getProductID());
                preparedStatement.close();
            }



            return orderCount;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
//    ----------------------------------------------------------------------

    private static final String CUSTOMER_ALL_ORDERS_QUERY = "SELECT * FROM tbl_order WHERE person_id = ?";
    public List customerOrders(String caseCustomerID){
        try {
            Connection connection = DataBaseConnection.getInstance();
            PreparedStatement preparedStatement = connection.prepareStatement(CUSTOMER_ALL_ORDERS_QUERY);
            preparedStatement.setString(1, caseCustomerID);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Order> customerAllOrdersList = new ArrayList<>();

            while (resultSet.next()){
                Order order = new Order();
                order.setItemID(resultSet.getInt("item_id"));
                order.setId(resultSet.getString("id"));
                order.setPersonID(resultSet.getString("person_id"));
                order.setProductID(resultSet.getInt("product_id"));
                order.setProductType(ProductType.valueOf(resultSet.getString("product_type")));
                order.setItemQuantity(resultSet.getInt("item_quantity"));
                order.setItemPrice(resultSet.getFloat("item_price"));
                order.setTotalItemPrice(resultSet.getFloat("total_item_price"));
                order.setOrderDate(resultSet.getString("order_date"));
                order.setDeliverDate(resultSet.getString("deliver_date"));
                order.setOrderStatus(OrderStatus.valueOf(resultSet.getString("order_status")));
                order.setSalesman(resultSet.getString("salesman"));

                customerAllOrdersList.add(order);
            }
            preparedStatement.close();

            return customerAllOrdersList;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }//end of findAllMyPrescriptions
//    ----------------------------------------------------------------------


    private static final String SPECIFIC_ORDER_QUERY = "SELECT * FROM tbl_order WHERE id = ?";
    public Order specificOrder(String orderID){
        try {
            Connection connection = DataBaseConnection.getInstance();
            PreparedStatement preparedStatement = connection.prepareStatement(SPECIFIC_ORDER_QUERY);
            preparedStatement.setString(1, orderID);
            ResultSet resultSet = preparedStatement.executeQuery();

            Order specificOrder = new Order();

            while (resultSet.next()){

                specificOrder.setItemID(resultSet.getInt("item_id"));
                specificOrder.setId(resultSet.getString("id"));
                specificOrder.setPersonID(resultSet.getString("person_id"));
                specificOrder.setProductID(resultSet.getInt("product_id"));
                specificOrder.setProductType(ProductType.valueOf(resultSet.getString("product_type")));
                specificOrder.setItemQuantity(resultSet.getInt("item_quantity"));
                specificOrder.setItemPrice(resultSet.getFloat("item_price"));
                specificOrder.setTotalItemPrice(resultSet.getFloat("total_item_price"));
                specificOrder.setOrderDate(resultSet.getString("order_date"));
                specificOrder.setDeliverDate(resultSet.getString("deliver_date"));
                specificOrder.setOrderStatus(OrderStatus.valueOf(resultSet.getString("order_status")));
                specificOrder.setSalesman(resultSet.getString("salesman"));
            }
            preparedStatement.close();

            return specificOrder;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }//end of specificOrder

//    -------------------------------------------------------------


    private static final String ALL_ORDERS_QUERY = "SELECT * FROM tbl_order";
    public List<Order> findAll(){
        try {
            Connection connection = DataBaseConnection.getInstance();
            PreparedStatement preparedStatement = connection.prepareStatement(ALL_ORDERS_QUERY);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Order> allOrdersList = new ArrayList<>();

            while (resultSet.next()){
                Order order = new Order();
                order.setItemID(resultSet.getInt("item_id"));
                order.setId(resultSet.getString("id"));
                order.setPersonID(resultSet.getString("person_id"));
                order.setProductID(resultSet.getInt("product_id"));
                order.setProductType(ProductType.valueOf(resultSet.getString("product_type")));
                order.setItemQuantity(resultSet.getInt("item_quantity"));
                order.setItemPrice(resultSet.getFloat("item_price"));
                order.setTotalItemPrice(resultSet.getFloat("total_item_price"));
                order.setOrderDate(resultSet.getString("order_date"));
                order.setDeliverDate(resultSet.getString("deliver_date"));
                order.setOrderStatus(OrderStatus.valueOf(resultSet.getString("order_status")));
                order.setSalesman(resultSet.getString("salesman"));

                allOrdersList.add(order);
            }
            preparedStatement.close();

            return allOrdersList;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }//end of findAll
//-------------------------------------------------------------------

    private  static final String COMPLETE_ORDER_QUERY = "UPDATE tbl_order set salesman = ?, deliver_date = ? WHERE id = ?";
    public boolean completeOrder(Order completedOrder){
        try{
            Connection connection = DataBaseConnection.getInstance();
            PreparedStatement preparedStatement = connection.prepareStatement(COMPLETE_ORDER_QUERY);
            preparedStatement.setString(1, completedOrder.getSalesman());
            preparedStatement.setString(2, completedOrder.getDeliverDate());
            preparedStatement.setString(3, completedOrder.getId());

            if(preparedStatement.executeUpdate()>0)
                return true;

            preparedStatement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
//    -------------------------------------------------------------------------------


}//end of class PrescriptionRepository
