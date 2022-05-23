package repository;

import domain.Product;

import java.util.LinkedList;
import java.util.List;

public class Product_Repository implements  RawRepository {

    private List<Product> catalog_of_products;

    public Product_Repository(List<Product> catalog_of_products) {
        this.catalog_of_products = catalog_of_products;
    }

    public Product_Repository() {
            catalog_of_products = new LinkedList<>();

    }

    @Override
    public Product findOne(String id) {
        try{
            for(Product product : catalog_of_products){
                 if(product.getUnique_id().equals(id)){
                     return product;
                 }
            }
        }
        catch(Error e){
            System.out.println(e);
        }
        return null;
    }

    @Override
    public Product[] returnAll() {
       Product[] temporary_list =new Product[catalog_of_products.size()] ;
       int index=0;
       for(Product product : catalog_of_products)
            temporary_list[index++] = product;

        return temporary_list;
    }

    @Override
    public Product add(Product new_product) {
        try{
            catalog_of_products.add(new_product);

        }catch (Error e)
        {
            System.out.println(e);
        }
        return new_product;
    }

    @Override
    public Product delete(Product product_to_delete) {
        return null;
    }

    @Override
    public DTOcatalogView[] catalog() {
        DTOcatalogView[] temporary_list = new DTOcatalogView[catalog_of_products.size()];
        int index=0;
        for(Product product : catalog_of_products) {
            temporary_list[index++] = new DTOcatalogView(product.getProduct_name(),product.getProduct_price(), product.getUnique_id());
        }
        return temporary_list;
    }
}
