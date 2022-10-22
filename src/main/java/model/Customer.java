package model;
import service.*;

public class Customer extends Person{
    private static final CustomerService customerService = new CustomerService();

    public Customer singIn(String username, String password) {
        if(customerService.signIn(username, password) == null){
            return null;
        }
        else
            return customerService.signIn(username, password);
    }

    @Override
    public boolean chnagePasword(String ID, String newPassword) {
        if(customerService.chnagePasword(ID, newPassword))
            return true;
        else
            return false;
    }


    public boolean signUp(Customer user) {
        if(customerService.create(user))
            return true;
        else
            return  false;
    }

}
