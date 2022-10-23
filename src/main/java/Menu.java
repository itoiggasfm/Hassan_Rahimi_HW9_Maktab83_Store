import model.*;
import model.enumeration.*;
import service.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.apache.commons.lang3.math.NumberUtils;

public class Menu {
    private static final Customer customer = new Customer();
    private static final Salesman salesman = new Salesman();
    private static final Order order = new Order();
    private static final CustomerService customerService = new CustomerService();
    private static final ProductService productService = new ProductService();
    private static final OrderService orderService = new OrderService();
    private static final Scanner input = new Scanner(System.in);
    private static Customer signedInPerson = null;
    private static List<Order> cart = new ArrayList<>();

    private static List<Order> allOrdersList = new ArrayList<>();
    private static List<Person> allPeopleList = new ArrayList<>();
    private static List<Product> productsList = new ArrayList<>();


    public static void home(){


        boolean valid = true;
        while (valid == true){
            System.out.println("----- Home page -----");
            System.out.println("1. Sign in");
            System.out.println("2. Sign up");
            System.out.println("3. Cart");
            System.out.println("4. Add to cart");
            System.out.println("5. Exit");
            System.out.printf("%n=======================%n");
            productsList = products();

            String choice = input.next();
            switch (choice){
                case "1":{
                    signIn();
                    if(signedInPerson != null){
                        if(signedInPerson.getPersonRole().equals(PersonRole.CUSTOMER))
                            customerAffairs(signedInPerson);
                        if(signedInPerson.getPersonRole().equals(PersonRole.SALESMAN))
                            salesmanAffairs(signedInPerson);
;
                    }
                }//case 1
                break;

                case "2": {
                    signUp();
                    if (signedInPerson != null) {
                        if (signedInPerson.getPersonRole().equals(PersonRole.CUSTOMER))
                            customerAffairs(signedInPerson);
                    }
                }
                break;

                case "3":{
                    cart();
                }//case 3
                break;

                case "4":{
                    addToCart();
                }
                break;

                case "5":{
                    valid = false;
                }
                break;

                default:
                    System.out.println("Invalid choice");
                    break;
            }//end of switch (choice)
        }//end of while (valid = true)
    }//end of home
//    -------------------------------------------------------------------

    public static void signIn(){

        System.out.println("Username: (National code) ");
        String username = input.next().toLowerCase();
        System.out.println("Password: (National code) ");
        String password = input.next().toLowerCase();
//        return signedInPerson = customer.singIn(username, password);
        signedInPerson = customer.singIn(username, password);
    }
//    -------------------------------------------------------------------

    public static void signUp(){
        System.out.println("Name: ");
        customer.setName(input.next().toLowerCase());

        System.out.println("National Code: ");
        String nationalCode = input.next();
        while (!customerService.checkNationalCOde(nationalCode) || customerService.findByID(nationalCode)){
            System.out.println("National Code: ");
            nationalCode = input.next();
            if (customerService.checkNationalCOde(nationalCode) && !customerService.findByID(nationalCode))
                break;
        }
        customer.setId(nationalCode);
        customer.setUserName(nationalCode);
        customer.setPassword(nationalCode);
        customer.setPersonRole(PersonRole.CUSTOMER);

        customer.signUp(customer);
        signedInPerson = customer;
    }//end of signUp
    // -------------------------------------------------------------------

    public static void cart(){
        if(!cart.isEmpty()){
            int No = 0;

            System.out.printf("%nNo  ID     Product name       Price     Quantity     Item's Total price     Order date    ");
            System.out.printf("%n-------------------------------------------------------------------------------------------");
            for(Order cartItem: cart)
                System.out.printf("%n%-4d%-7d%-17s%-12.0f%-13d%-23.0f%-15s", ++No, cartItem.getProductID(), productService.findByID(cartItem.getProductID()).getName(), cartItem.getItemPrice(), cartItem.getItemQuantity(), cartItem.getTotalItemPrice(), cartItem.getOrderDate());
                System.out.printf("%n%n");

                System.out.println("1. Back");
                System.out.println("2. Modify cart items. You can only modify the quantity of the items.");
                System.out.println("3. Delete cart items");
                System.out.println("4. Confirm cart items");
                String choice = input.next();
                switch (choice){
                    case "1":
                        break;

                    case "2":{
//                        Boolean valid = true;

                            for (int i=0; i< cart.size(); ++i){
                                System.out.println("No: ");
                                String cartItemNo = input.next();
                                    while(!NumberUtils.isParsable(cartItemNo)){
                                        System.out.println("Enter a number please.");
                                        System.out.println("No: ");
                                        cartItemNo = input.next();
                                    }
                                System.out.println("New quantity: ");
                                String newQuantity = input.next();
                                while(!NumberUtils.isParsable(newQuantity)){
                                    System.out.println("Enter a number please.");
                                    System.out.println("New quantity: ");
                                    newQuantity = input.next();
                                }
                                if(Integer.parseInt(newQuantity)>0)
                                    cart.get(Integer.parseInt(cartItemNo)-1).setItemQuantity(Integer.parseInt(newQuantity));
                                else if(Integer.parseInt(newQuantity) == 0){
                                    System.out.printf("%n0 as new quantity means you're gpoing to delete this item.%nAre you sure?(Y/N) ");
                                    String deletionConfirmation = input.next().toLowerCase();
                                    while(!(deletionConfirmation.equals("y") || deletionConfirmation.equals("n"))){
                                        System.out.printf("%nYou entered 0 as new quantity. It means you're gpoing to delete this item.%nAre you sure?(Y/N) ");
                                        deletionConfirmation = input.next().toLowerCase();
                                    }
                                    if(deletionConfirmation.equals("y"))
                                        cart.remove(Integer.parseInt(cartItemNo)-1);
                                    System.out.println();
                                }

                                System.out.println("Another Item?(Y/N) ");
                                String anotherItemConfirmation = input.next().toLowerCase();
                                while (!(anotherItemConfirmation.equals("y") || anotherItemConfirmation.equals("n"))){
                                    System.out.println("Another Item?(Y/N) ");
                                    anotherItemConfirmation = input.next().toLowerCase();
                                }
                                if(anotherItemConfirmation.equals("n"))
                                    break;
                            }
                    }
                    break;
        
                    case "3":{
                        System.out.println("No: ");
                        String cartItemNo = input.next();
                        while (!NumberUtils.isParsable(cartItemNo)) {
                            System.out.println("Enter a number please.");
                            System.out.println("No: ");
                            cartItemNo = input.next();
                        }
                        cart.remove(Integer.parseInt(cartItemNo)-1);
                    }
                    break;
        
                    case "4":{
                        if(signedInPerson == null){
                            System.out.println("\nYou're not signed in.\nPlease sign in or sign up if you haven't registered so far.\n");
                            break;
                        }
        
                        else{
                            for(Order order: cart){
                                order.setPersonID(signedInPerson.getId());
                                order.setId(signedInPerson.getId()+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmm")));
                            }
                            orderService.create(cart);
                        }
                    }
                    break;
        
                    default:
                        System.out.println("Invalid choice");
                }
            System.out.printf("%n%n");
        }
        else
            System.out.println("Nothing in the cart\n");
    }
//    ------------------------------------------------------------------
    public static List<Product> products(){
        return productService.findAll();
    }
//    ------------------------------------------------------------------

    public static void addToCart(){
        if(!productsList.isEmpty()){
            String addToCart = "y";
            int cartItemsNo = 0;
            while(addToCart.equals("y") && cartItemsNo<5){

                System.out.println("No: ");
                String No = input.next();
                while(!NumberUtils.isParsable(No)){

                    System.out.println("No: ");
                    No = input.next();
                }

                while(Integer.parseInt(No)>productsList.size()){
                    System.out.println("Wrong choice");
                    System.out.println("No: ");
                    No = input.next();
                }
                System.out.println("Quantity: ");
                String itemQuantity = input.next();
                while(!NumberUtils.isParsable(itemQuantity)){
                    System.out.println("Enter a number please.");
                    System.out.println("Quantity: ");
                    itemQuantity = input.next();
                }

                if(productsList.get(Integer.parseInt(No)-1).getQuantity() == 0)
                    System.out.println("This product does not exist.");
                else{
                    boolean itemExists = false;
                    if(!cart.isEmpty()){
                        for(Order item: cart)
                            if(item.getProductID() == productsList.get(Integer.parseInt(No)-1).getId()){
                                itemExists = true;
                                break;
                            }           
                    }
                    if(itemExists){
                        for(Order item: cart)
                            if(item.getProductID() == productsList.get(Integer.parseInt(No)-1).getId())
                                item.setItemQuantity(item.getItemQuantity()+Integer.parseInt(itemQuantity));
                    }  
                    else{
                        Order order = new Order();
                        order.setProductID(productsList.get(Integer.parseInt(No)-1).getId());
                        order.setProductType(productsList.get(Integer.parseInt(No)-1).getProductType());
                        order.setItemQuantity(Integer.parseInt(itemQuantity));
                        order.setItemPrice(productsList.get(Integer.parseInt(No)-1).getPrice());
                        order.setTotalItemPrice(order.getItemQuantity()*order.getItemPrice());
                        order.setOrderDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy.MM.dd HH:mm")));
                        order.setOrderStatus(OrderStatus.OPEN);
                        cart.add(order); 
                    }
                    
                }
                System.out.println("Add another product to cart? (Y/N)");
                String addToCartConfirm = input.next().toLowerCase();
                while (!(addToCartConfirm.equals("y") || addToCartConfirm.equals("n"))){
                    System.out.println("Add another product to cart? (Y/N)");
                    addToCartConfirm = input.next().toLowerCase();
                }
                if(addToCartConfirm.equals("n"))
                    addToCart = "n";
                ++cartItemsNo;
            }

        }else
        System.out.println("No product available\n");


    }
//    ------------------------------------------------------------------

    public static void customerAffairs(Customer signedInUser){

        boolean valid = true;
        while (valid == true){
            System.out.printf("%n--------------------%nWelcome dear %s%n--------------------%n",signedInUser.getName());
            System.out.println("1. Sign out");
            System.out.println("2. Change password");
            System.out.println("3. Add to cart");
            System.out.println("4. Cart");
            System.out.printf("%n--------------------%n");
            List<Product> productsList = products();
            String choice = input.next();

            switch (choice){
                case "1":{
                    valid =false;
                }//end of case 1
                break;

                case "2":{
                   String newPassword = changePassword();

                    if(customer.chnagePasword(signedInPerson.getId(), newPassword))
                        signedInPerson.setPassword(newPassword);
                }//case 2
                break;

                case "3":{
                    addToCart();
                }
                break;

                case "4":{
                    cart();
                }
                break;

                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }
//    ----------------------------------------------------------------------

    public static String changePassword(){
        System.out.println("Enter old password: ");
        String oldPassword = input.next();
        while(!signedInPerson.getPassword().equals(oldPassword)){
            System.out.println("Incorrect password");
            System.out.println("Enter old password: ");
            oldPassword = input.next();
        }

        System.out.println("New Password: ");
        String newPassword = input.next();
        System.out.println("Enter new password again: ");
        String confirmNewpassword = input.next();
        while (!newPassword.equals(confirmNewpassword)){
            System.out.println("Passwords don't match\n");
            System.out.println("New Password: ");
            newPassword = input.next();
            System.out.println("Enter new password again: ");
            confirmNewpassword = input.next();
        }
        return newPassword;
    }//end of changePassword
//    ----------------------------------------------------------------------


    public static void salesmanAffairs(Customer signedInUser){

        boolean valid = true;
        while (valid == true){
            System.out.printf("%n--------------------%nWelcome  dear %s%n--------------------%n",signedInUser.getName());
            System.out.println("1. Complete order");
            System.out.println("2. Dispaly all orders");
            System.out.println("3. Dispaly orders of a customer");
            System.out.println("4. Display a specific order");
            System.out.println("5. Change password");
            System.out.println("6. Display all products");
            System.out.println("7. Add product");
            System.out.println("8. Delete product");
            System.out.println("9. Sign out");
            System.out.printf("%n--------------------%n");
            String choice = input.next();

            switch (choice){
                case "1":{
                    if(!allOrdersList.isEmpty()){
                    Order completedOrder = null;
                    int caseOrderIDCounter = 0;
                    System.out.println("ID: ");
                    String completedOrderID = input.next();
                    for (Order order: allOrdersList){
                        if(order.getId().equals(completedOrderID)){
                            completedOrder =order;
                            completedOrder.setSalesman(signedInUser.getName());
                            completedOrder.setDeliverDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHdd")));
                            ++caseOrderIDCounter;
                        }

                    }
                    if(caseOrderIDCounter>0)
                        System.out.println("ID not found");
                    else
                        orderService.completeOrder(completedOrder);

                    }else
                    System.out.println("No order registered yet\n");

                }
                break;

                case "2":{
                    allOrdersList = orderService.findAll();
                }
                break;

                case "3":{
                    if(!allOrdersList.isEmpty()){
                    allPeopleList = customerService.findAll();
                    String customerID = "";
                    String customerName = "";
                    int caseCustomerCounter = 0;
                    System.out.println("ID: ");
                    String caseCustomerID = input.next();
                    for (Person person: allPeopleList)
                        if(person.getId().equals(customerID)){
                            customerID = person.getId();
                            customerName = person.getName();
                            ++caseCustomerCounter;
                        }
                    if(caseCustomerCounter>0)
                        System.out.println("ID not found.");
                    else
                        orderService.customerOrders(caseCustomerID, customerName);

                    }
                    else
                    System.out.println("No order registered yet");


                }
                break;

                case "4":{
                    if(!allOrdersList.isEmpty()){
                    String orderID = "";
                    String customerName = "";
                    int caseCustomerCounter = 0;
                    System.out.println("ID: ");
                    String inputID = input.next();
                    for (Order order: allOrdersList)
                        if(order.getId().equals(inputID)){
                            orderID = order.getId();

                            ++caseCustomerCounter;
                        }
                    for(Person person: allPeopleList){
                        if(person.getId().equals(orderID))
                            customerName = person.getName();
                    }
                    if(caseCustomerCounter>0)
                        System.out.println("ID not found.");
                    else
                        orderService.specificOrder(orderID, customerName);

                    }
                    else
                    System.out.println("No order registered yet");

                }
                break;

                case "5":{

                    String newPassword = changePassword();

                    if(salesman.chnagePasword(signedInPerson.getId(), newPassword))
                        signedInPerson.setPassword(newPassword);
                }
                break;

                case "6":{
                    // List<Product> productsList= products();
                    System.out.printf("%nID     Name               Price     Quantity     Product type");
                    System.out.printf("%n-------------------------------------------------------------");
                    for (Product product: productsList)
                        System.out.printf("%n%-7d%-19s%-10.0f%-13d%-15s",product.getId(), product.getName(), product.getPrice(), product.getQuantity(), product.getProductType().toString());
                }
                break;

                case "7":{
                    Product newProduct = new Product();
                    System.out.println("Name: ");
                    newProduct.setName(input.next());
                    System.out.println("Price: ");
                    newProduct.setPrice(input.nextFloat());
                    System.out.println("Quantity: ");
                    newProduct.setQuantity(input.nextInt());
                    System.out.println("Product type: E(Electronic), S(Shoe), P(printed matter)");
                    String productType = input.next().toLowerCase();
                    while (!(productType.equals("e") || productType.equals("s") || productType.equals("p"))){
                        System.out.println("Product type: E(Electronic), S(Shoe), P(printed matter)");
                        productType = input.next().toLowerCase();
                    }
                    if(productType.equals("e"))
                        newProduct.setProductType(ProductType.ELECTRONIC);
                    else if(productType.equals("s"))
                        newProduct.setProductType(ProductType.SHOE);
                    else
                        newProduct.setProductType(ProductType.PRINTED_MATTER);

                    productService.create(newProduct);
                    }
                    break;
                case "8":{
                    int underDeletionID = 0;
                    System.out.println("ID: ");
                    int inputID = input.nextInt();
                    for(Product product: productsList){
                        if(product.getId() == inputID)
                            underDeletionID = inputID;
                    }
                    if(underDeletionID == 0)
                        System.out.println("ID not found.");
                    else
                        productService.delete(underDeletionID);
                }
                break;
                case "9":{
                    valid = false;
                }
                break;

                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }


}//end of class Menu
