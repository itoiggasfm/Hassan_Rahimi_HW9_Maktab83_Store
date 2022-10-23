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
            boolean electronicExist = false;
            boolean shoeExist = false;
            boolean printedMatterExist = false;
            for (Product product: allProducts)
                if(product.getProductType().equals(ProductType.ELECTRONIC)){
                    electronicExist = true;
                    break;
                }
                for (Product product: allProducts)
                if(product.getProductType().equals(ProductType.SHOE)){
                    shoeExist = true;
                    break;
                }
                for (Product product: allProducts)
                if(product.getProductType().equals(ProductType.PRINTED_MATTER)){
                    printedMatterExist = true;
                    break;
                }

            System.out.printf("%nNo     Name               Price       Product type     Exists/Not exist");
        if(electronicExist){
            System.out.printf("%n%n%s  Electronic goods     ------------------------------------------------", "\u21DB");
            for(Product product: allProducts)
                if(product.getProductType().equals(ProductType.ELECTRONIC))
                    System.out.printf("%n%-7d%-19s%-12.0f%-17s%-16s",  product.getId(), product.getName(), product.getPrice(), product.getProductType(), (product.getQuantity()>0?"Exists":"Not exist"));
        }
            if(shoeExist){
                System.out.printf("%n%n%s  SHOE     ------------------------------------------------------------", "\u21DB");
                for(Product product: allProducts)
                    if(product.getProductType().equals(ProductType.SHOE))
                        System.out.printf("%n%-7d%-19s%-12.0f%-17s%-16s",  product.getId(), product.getName(), product.getPrice(), product.getProductType(), (product.getQuantity()>0?"Exists":"Not exist"));
            }
            if(printedMatterExist){
                System.out.printf("%n%n%s  PRINTED MATTER     --------------------------------------------------", "\u21DB");
                for(Product product: allProducts)
                    if(product.getProductType().equals(ProductType.PRINTED_MATTER))
                        System.out.printf("%n%-7d%-19s%-12.0f%-17s%-16s",  product.getId(), product.getName(), product.getPrice(), product.getProductType(), (product.getQuantity()>0?"Exists":"Not exist"));
            }
            }
            System.out.println("\n\n========================================================================");
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




