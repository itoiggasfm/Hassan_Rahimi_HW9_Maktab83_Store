package service;

import model.Product;
import model.enumeration.ProductType;
import repository.ProductRepository;

import java.util.List;

public class ProductService {

    private ProductRepository productRepository;
    public ProductService(){
        this.productRepository = new ProductRepository();
    }
//    ------------------------------------------------------------------------

        public Product findByID(int ID){
            return productRepository.findByID(ID);
    }
//    ------------------------------------------------------------------------

    public List<Product> findAll(){
        List<Product> allProducts = productRepository.findAll();
        if(allProducts.isEmpty()){
            System.out.println("No product");
        }
        else{
            int No =0;
            System.out.printf("%nNo  ID     Name               Price       Product type     Exists/Not exist");
            System.out.printf("%n---------------------------------------------------------------");
            for(Product product: allProducts){
                if(product.getProductType().equals(ProductType.ELECTRONIC))
                    System.out.printf("%n%-4d%-7d%-19s%-12.0f%-17s%-16s", ++No,  product.getId(), product.getName(), product.getPrice(), product.getProductType(), (product.getQuantity()>0?"Exists":"Not exist"));
                else if(product.getProductType().equals(ProductType.SHOE))
                    System.out.printf("%n%-4d%-7d%-19s%-12.0f%-17s%-16s", ++No,  product.getId(), product.getName(), product.getPrice(), product.getProductType(), (product.getQuantity()>0?"Exists":"Not exist"));
                else
                    System.out.printf("%n%-4d%-7d%-19s%-12.0f%-17s%-16s", ++No,  product.getId(), product.getName(), product.getPrice(), product.getProductType(), (product.getQuantity()>0?"Exists":"Not exist"));
            }
            System.out.println("\n===============================================================");
        }
        return allProducts;
    }
//    ---------------------------------------------------------------------

    public void create(Product newProduct){
        if(productRepository.create(newProduct))
            System.out.println("Product added successfully");
        else
            System.out.println("Product was not added.");
    }
//    -------------------------------------------------------------------------

    public void delete(int ID){
        if(productRepository.delete(ID))
            System.out.println("\nThe product deleted successfully.");
        else
            System.out.println("The product wasn't deleted.");
    }

}




