package domain;

public class Product {


    private String unique_id;
    private String product_name;
    private float product_price;
    private float product_weight;
    private CountryList shipped_from;




    public Product(String unique_id, String product_name, float product_price, float product_weight, CountryList shipped_from) {
        this.unique_id = unique_id;
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_weight = product_weight;
        this.shipped_from = shipped_from;
    }


    public CountryList getShipped_from() {
        return shipped_from;
    }

    public void setShipped_from(CountryList shipped_from) {
        this.shipped_from = shipped_from;
    }

    public String getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }

    public Product() {
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public float getProduct_price() {
        return product_price;
    }

    public void setProduct_price(float product_price) {
        this.product_price = product_price;
    }

    public float getProduct_weight() {
        return product_weight;
    }

    public void setProduct_weight(float product_weight) {
        this.product_weight = product_weight;
    }


    @Override
    public String toString() {
        return "Product{" +
                "unique_id='" + unique_id + '\'' +
                ", product_name='" + product_name + '\'' +
                ", product_price=" + product_price +
                ", product_weight=" + product_weight +
                '}';
    }
}
