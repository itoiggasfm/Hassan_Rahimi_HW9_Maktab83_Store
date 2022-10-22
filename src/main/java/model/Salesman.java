package model;

import service.SalesmanService;

public class Salesman extends Person {

    private static final SalesmanService salesmanService = new SalesmanService();
    @Override
    public boolean chnagePasword(String ID, String newPassword) {
        if(salesmanService.chnagePasword(ID, newPassword))
            return true;
        else
            return false;
    }


}
