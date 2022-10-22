package service;

import java.util.List;

import model.*;
import repository.*;
import service.*;


public class OrderService {

private OrderRepository orderRepository;
private ProductService productService;
private CustomerService customerService;
public OrderService(){
    this.orderRepository = new OrderRepository();
    this.productService = new ProductService();
    this.customerService = new CustomerService();
}

    public void create(List<Order> cart){
       if(orderRepository.create(cart) == cart.size())
           System.out.println("All orders registered successfully.");
       else
           System.out.println("Order wasn't registered.");
    }
//    --------------------------------------------------------------

    public List<Order> findAll(){

        List<Order> allOrdersList = orderRepository.findAll();
        

        if(allOrdersList.isEmpty()){
            System.out.println("No orders registered yet.\n");
        }
        else{
            List<Product> allProductsList = productService.findAll();
            List<Person> allPeopleList = customerService.findAll();
            String personName = "";
            String productName = "";
            System.out.printf("%nItem ID     Order ID    Customer ID     Customer name     Product ID     Product name     Product type     Item quantity     Item price      Total item price     Order date     Deliver date     Order status    salesman");
            System.out.printf("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            for(Order order: allOrdersList){
                for(Person person: allPeopleList)
                    if(person.getId().equals(order.getPersonID()))
                        personName = person.getName();
                for(Product product: allProductsList)
                    if(product.getId() == order.getProductID())
                        productName = product.getName();
                System.out.printf("%-12d%-13s%-16s%-18s%-15d%-17s%-17s%-18d%-19f%-21f%-15s%-17s%-17s%-10s", order.getItemID(), order.getId(), order.getPersonID(), personName, order.getProductID(), productName, order.getProductType(), order.getItemQuantity(), order.getItemPrice(), order.getTotalItemPrice(), order.getOrderDate(), order.getDeliverDate()==null?"":order.getDeliverDate(), order.getOrderStatus(), order.getSalesman()==null?"":order.getSalesman());
            }
            System.out.println("\n=======================================================================================================================================================================================================================\n");
        }
        return allOrdersList;
    }
//    ----------------------------------------------------------------

    public List customerOrders(String caseCustomerID, String customerName){
        List<Order> customerAllOrdersList = orderRepository.customerOrders(caseCustomerID);
        List<Product> allProductsList = productService.findAll();
        if(customerAllOrdersList.isEmpty()){
            System.out.println("No orders registered yet.");
            return  null;
        }
        else{
            String productName = "";
            System.out.printf("%nItem ID     Order ID    Customer ID     Customer name     Product ID     Product name     Product type     Item quantity     Item price      Total item price     Order date     Deliver date     Order status    salesman");
            System.out.printf("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            for(Order order: customerAllOrdersList){
                for(Product product: allProductsList)
                    if(product.getId() == order.getProductID())
                        productName = product.getName();
                System.out.printf("%-12d%-13s%-16s%-18s%-15d%-17s%-17s%-18d%-19f%-21f%-15s%-17s%-17s%-10s", order.getItemID(), order.getId(), order.getPersonID(), customerName, order.getProductID(), productName, order.getProductType(), order.getItemQuantity(), order.getItemPrice(), order.getTotalItemPrice(), order.getOrderDate(), order.getDeliverDate()==null?"":order.getDeliverDate(), order.getOrderStatus(), order.getSalesman()==null?"":order.getSalesman());
            }
            System.out.println("\n=======================================================================================================================================================================================================================\n");
        }
        return customerAllOrdersList;
    }//end of customerOrders
//    ------------------------------------------------------------
public void specificOrder(String orderID, String customerName){
    Order specificOrder = orderRepository.specificOrder(orderID);
    List<Product> allProductsList = productService.findAll();
    if(specificOrder == null){
        System.out.println("No orders registered yet.");
    }
    else{
        String productName = "";
        System.out.printf("%nItem ID     Order ID    Customer ID     Customer name     Product ID     Product name     Product type     Item quantity     Item price      Total item price     Order date     Deliver date     Order status    salesman");
        System.out.printf("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            for(Product product: allProductsList)
                if(product.getId() == specificOrder.getProductID())
                    productName = product.getName();
            System.out.printf("%-12d%-13s%-16s%-18s%-15d%-17s%-17s%-18d%-19f%-21f%-15s%-17s%-17s%-10s", specificOrder.getItemID(), specificOrder.getId(), specificOrder.getPersonID(), customerName, specificOrder.getProductID(), productName, specificOrder.getProductType(), specificOrder.getItemQuantity(), specificOrder.getItemPrice(), specificOrder.getTotalItemPrice(), specificOrder.getOrderDate(), specificOrder.getDeliverDate()==null?"":specificOrder.getDeliverDate(), specificOrder.getOrderStatus(), specificOrder.getSalesman()==null?"":specificOrder.getSalesman());

        System.out.println("\n=======================================================================================================================================================================================================================\n");
    }

}//end of specificOrder
//   -----------------------------------------------------------------------

    public void completeOrder(Order completedOrder){
        if(orderRepository.completeOrder(completedOrder))
            System.out.println("Order completed.");
        else
            System.out.println("Order is still open.");
    }
//    --------------------------------------------------------------------------
//


}



