package services;

import domain.Calculator;
import domain.CountryList;
import domain.Product;
import domain.ShippingFees;
import domain.Utils.DiscountSystem;
import repository.DTOcatalogView;
import repository.Product_Repository;
import repository.RawRepository;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Features {

    private RawRepository database_of_products;
    private List<DTOcatalogView> catalog;
    private List<AbstractMap.SimpleEntry<DTOcatalogView,Integer>> cart; // A list of Pairs, i didnt want to import javafx so I had to use base Java Pair structure
    private float total_price,subtotal_price,shipping,VAT;
    private final static ShippingFees shippingFeesCalculator = ShippingFees.getInstance();
    private final static DiscountSystem discounts = new DiscountSystem();
    private final static Calculator calculator= Calculator.getInstance();


    public Features() {

        List<Product> initial_catalog_of_products = new LinkedList<>();
        initial_catalog_of_products.add(new Product("id_1","Mouse",10.99F,0.2F, CountryList.RO));
        initial_catalog_of_products.add(new Product("id_2","Keyboard",40.99F,0.7F, CountryList.UK));
        initial_catalog_of_products.add(new Product("id_3","Monitor",164.99F,1.9F, CountryList.US));
        initial_catalog_of_products.add(new Product("id_4","Webcam",84.99F,0.2F, CountryList.RO));
        initial_catalog_of_products.add(new Product("id_5","Headphones",59.99F,0.6F, CountryList.US));
        initial_catalog_of_products.add(new Product("id_6","DeskLamp",89.99F,1.3F, CountryList.UK));

        database_of_products = new Product_Repository(initial_catalog_of_products);
        catalog  = Arrays.asList(database_of_products.catalog());
        cart = new LinkedList<>();

    }
    //Catalog of Products, I use Data transport Objects to represent all the needed information for the end-user
    public void display_catalog(){

        for(DTOcatalogView el: catalog){
            System.out.println( el.toString());
        }
    }

    public List<DTOcatalogView> getCatalog() {
        return catalog;
    }


    //Function to add a product in our shopping cart
    public void select_product(DTOcatalogView product, int quantity){

       float order_value =  database_of_products.findOne(product.getUnique_id()).getProduct_price()*quantity;
       boolean found=false;
       //We look for the item in our shopping cart, if it's there we raise the quantity if not we add it in
       for(AbstractMap.SimpleEntry<DTOcatalogView,Integer> el : cart){
          if( el.getKey().equals(product.getName())){
              Product selected_product = database_of_products.findOne(el.getKey().getUnique_id());
              calculator.calculate_shipping(selected_product.getShipped_from(),selected_product.getProduct_weight(),quantity);
              cart.add(cart.indexOf(el), new AbstractMap.SimpleEntry<>(product, el.getValue() + quantity));
              found =true;
          }
       }
       if(found == false){
           cart.add(new AbstractMap.SimpleEntry<DTOcatalogView,Integer>( product,quantity));
           Product selected_product = database_of_products.findOne(product.getUnique_id());
           calculator.calculate_shipping(selected_product.getShipped_from(),selected_product.getProduct_weight(),quantity);
       }
        calculator.calculate_subtotal(order_value);

    }

    //We do the calculations add the checkout besides shipping fees and subtotal which i do when an item is added in the shopping cart
    public void purchase(){

        String message =  discounts.runAllDiscounts(cart);
        calculator.calculate_discounts(discounts.getDiscount_amount());
        calculator.calculate_VAT();
        calculator.calculate_total();

        shipping =calculator.getShipping();
        subtotal_price = calculator.getSubtotal();
        VAT = calculator.getVAT();
        total_price = calculator.getTotal();

        for(AbstractMap.SimpleEntry<DTOcatalogView,Integer> el: cart){
            System.out.println( el.getKey().getName()+" x " + el.getValue()+ " price: "+el.getValue()*el.getKey().getPrice()+"\n");
        }
        System.out.println("Subtotal:"+ subtotal_price + "\nShipping: "+ shipping+"\nVAT: "+ VAT + "\n");
        System.out.println(message +"Discounts :"+ calculator.getDiscounts() +"\n");
        System.out.println("Total:"+total_price+"\n");
    }

}
