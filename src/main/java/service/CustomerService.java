package service;

import model.Customer;
import model.Person;
import repository.CostomerRepository;

import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;

public class CustomerService {

    private CostomerRepository customerRepository;

    public CustomerService(){
        this.customerRepository = new CostomerRepository();
    }


        public Customer signIn(String username, String password){
            if(customerRepository.signIn(username, password) == null){
                System.out.println("Username or password is incorrect\n");
                return null;
            }
            else
                return customerRepository.signIn(username, password);
        }
//        ----------------------------------------------------------------------

    //national code validator
    public boolean checkNationalCOde(String nationalCOde) {
        if(Pattern.compile("\\d{10}").matcher(nationalCOde).matches()){
            int sum = 0;
            for (int i=10, j=0; i>1; --i)
                sum += Integer.parseInt(String.valueOf(nationalCOde.charAt(j++)))*i;
            if((sum%11<3 && Integer.parseInt(String.valueOf(nationalCOde.charAt(9))) == sum%11) ||
                    (sum%11>=3 && Integer.parseInt(String.valueOf(nationalCOde.charAt(9))) == 11-sum%11) )
                return true;
            else{
                System.out.println("Invalid national code.");
                return false;
            }
        }
        else
            System.out.println("Invalid national code. National code is a 10-digit number.");
        return false;
    }
//    ------------------------------------------------------------------

        //Does national code exist
    public boolean findByID(String nationalCode) {
        if (customerRepository.findByID(nationalCode)){
            System.out.println("National code already exist.");
            return true;
        }
        else {
            return false;
        }
    }
///   ---------------------------------------------------------------------


    public Boolean create(Customer user){
        if(customerRepository.create(user)){
            System.out.println("Created successfuly.\n");
            return true;
        }
        else {
            System.out.println("Did not successfully.\n");
            return false;
        }
    }
//    -----------------------------------------------------------------
    public boolean chnagePasword(String ID, String newPassword) {
        if(customerRepository.chnagePasword(ID, newPassword)){
            System.out.println("Password changed successfully.");
            return true;
        }
        else{
            System.out.println("Password wasn't changed.");
            return false;
        }
    }
//    ---------------------------------------------------------

    public List<Person> findAll() {
        return customerRepository.findAll();

        }
//   ---------------------------------------------------------


}//end of class CustomerService
