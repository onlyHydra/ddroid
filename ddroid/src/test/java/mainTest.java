import domain.Calculator;
import domain.CountryList;
import domain.Product;
import domain.ShippingFees;
import domain.Utils.DiscountSystem;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import repository.DTOcatalogView;
import repository.Product_Repository;
import repository.RawRepository;
import services.Features;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class mainTest {

    private static RawRepository database_of_products;
    private static List<AbstractMap.SimpleEntry<DTOcatalogView, Integer>> cart;
    private float total_price, subtotal_price, shipping, VAT;
    private final static ShippingFees shippingFeesCalculator = ShippingFees.getInstance();
    private final static DiscountSystem discounts = new DiscountSystem();
    private final static Calculator calculator = Calculator.getInstance();


    @Test
    @BeforeAll
    public static void initialize() {

        List<Product> initial_catalog_of_products = new LinkedList<>();
        initial_catalog_of_products.add(new Product("id_1", "Mouse", 10.99F, 0.2F, CountryList.RO));
        initial_catalog_of_products.add(new Product("id_2", "Keyboard", 40.99F, 0.7F, CountryList.UK));
        initial_catalog_of_products.add(new Product("id_3", "Monitor", 164.99F, 1.9F, CountryList.US));
        initial_catalog_of_products.add(new Product("id_4", "Webcam", 84.99F, 0.2F, CountryList.RO));
        initial_catalog_of_products.add(new Product("id_5", "Headphones", 59.99F, 0.6F, CountryList.US));
        initial_catalog_of_products.add(new Product("id_6", "DeskLamp", 89.99F, 1.3F, CountryList.UK));

        database_of_products = new Product_Repository(initial_catalog_of_products);
        cart = new LinkedList<>();
        cart.add(
                new AbstractMap.SimpleEntry<>(new DTOcatalogView("Mouse",10.99F,"id_1"),1));
        cart.add(
                new AbstractMap.SimpleEntry<>(new DTOcatalogView("Keyboard",40.99F,"id_2"),1));
        cart.add(
                new AbstractMap.SimpleEntry<>(new DTOcatalogView("Monitor",164.99F,"id_3"),1));
        cart.add(
                new AbstractMap.SimpleEntry<>(new DTOcatalogView("Webcam",84.99F,"id_4"),1));
        cart.add(
                new AbstractMap.SimpleEntry<>(new DTOcatalogView("Headhphones",59.99F,"id_5"),1));
        cart.add(
                new AbstractMap.SimpleEntry<>(new DTOcatalogView("DeskLamp",89.99F,"id_6"),1));
    }
@Test
@BeforeEach
    public  void resetValues(){
        calculator.reset();
    }

    @Test
    @Order(1)
    //the Value from our Pair represent quantity of the selected product in our cart
    public  void test_subtotal(){

        float good_value =0;
      for( AbstractMap.SimpleEntry<DTOcatalogView, Integer> product : cart){
            good_value = good_value + product.getKey().getPrice() * product.getValue();
      }

        for( AbstractMap.SimpleEntry<DTOcatalogView, Integer> product : cart)
                calculator.calculate_subtotal(product.getKey().getPrice()*product.getValue());

        assert(Float.compare(good_value,calculator.getSubtotal())==0);
    }

    @Test
    @Order(2)
    public   void test_shipping(){

        float weight = 11.11F;
        int quantity = 5;
        float ShippingFeePerGram = 1 * 10 ;// weight is in Kg , fees are in grams
        float formula = weight*quantity*ShippingFeePerGram;
        calculator.calculate_shipping(CountryList.RO,weight,quantity);
        assert(formula==calculator.getShipping());
        calculator.reset();

        ShippingFeePerGram = 2*10;
        formula = weight*quantity*ShippingFeePerGram;
        calculator.calculate_shipping(CountryList.UK,weight,quantity);
        assert(formula==calculator.getShipping());

        calculator.reset();
        ShippingFeePerGram = 3*10;
        formula = weight*quantity*ShippingFeePerGram;
        calculator.calculate_shipping(CountryList.US,weight,quantity);
        assert(formula==calculator.getShipping());

    }

    @Test
    @Order(3)
    public   void test_total(){

        Product actual_product;
        float weight = 0;
        int quantity = 0;
        float ShippingFeePerGram = 0 * 10 ;// weight is in Kg , fees are in grams
        float formula = weight*quantity*ShippingFeePerGram; // the shipping fee formula
        float good_value =0;
        for( AbstractMap.SimpleEntry<DTOcatalogView, Integer> product : cart){
            good_value = good_value + product.getKey().getPrice() * product.getValue();
            actual_product = database_of_products.findOne(product.getKey().getUnique_id());
            weight = actual_product.getProduct_weight();
            quantity = product.getValue();
            if(actual_product.getShipped_from().equals(CountryList.RO))
            ShippingFeePerGram = 1*10;
            if(actual_product.getShipped_from().equals(CountryList.UK))
                ShippingFeePerGram = 2*10;
            if(actual_product.getShipped_from().equals(CountryList.US))
                ShippingFeePerGram = 3*10;
            formula =formula + weight*quantity*ShippingFeePerGram;
        }



        for( AbstractMap.SimpleEntry<DTOcatalogView, Integer> product : cart) {
            calculator.calculate_subtotal(product.getKey().getPrice() * product.getValue());
            actual_product = database_of_products.findOne(product.getKey().getUnique_id());
            calculator.calculate_shipping(actual_product.getShipped_from(),actual_product.getProduct_weight(),product.getValue());
        }

        calculator.calculate_total();
        good_value = good_value + formula;
        assert(good_value  == calculator.getTotal());
    }

}