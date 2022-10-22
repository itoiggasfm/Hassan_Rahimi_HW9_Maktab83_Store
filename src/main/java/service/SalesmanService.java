package service;

import repository.*;

public class SalesmanService {

    private SalesmanRepository salesmanRepository;
    public SalesmanService(){
        this.salesmanRepository = new SalesmanRepository();
    }

    public boolean chnagePasword(String ID, String newPassword) {
        if(salesmanRepository.chnagePasword(ID, newPassword)){
            System.out.println("Password changed successfully.");
            return true;
        }
        else{
            System.out.println("Password wasn't changed.");
            return false;
        }

    }
}
