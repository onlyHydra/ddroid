package repository;

public class DTOcatalogView {
    private String name;
    private float price;
    private String unique_id;

    public DTOcatalogView(String name, float price, String unique_id) {
        this.name = name;
        this.price = price;
        this.unique_id = unique_id;
    }


    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public String getUnique_id() {
        return unique_id;
    }

    @Override
    public String toString() {
        return ""+name+" - $"+price+"\n";
    }
}
