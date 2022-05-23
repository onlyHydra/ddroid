package domain;

public class Calculator {
    private static ShippingFees shippingFees;

    private float subtotal;
    private float total;
    private float discounts;
    private float VAT;
    private float shipping;
    private static final float VAT_value = 0.19F;

    private static Calculator calculator;

    public Calculator() {
        shippingFees = ShippingFees.getInstance();
        subtotal=total=discounts=VAT=shipping=0;
    }

    public static Calculator getInstance(){
        if( calculator == null)
            calculator = new Calculator();
        return calculator;
    }


    public void calculate_discounts(float ongoing_discounts){
        discounts = ongoing_discounts;
    }

    public void calculate_subtotal(float order_value){
        subtotal+=order_value;

    }
    public void calculate_total(){
        total= total+subtotal+shipping+VAT-discounts;

    }
    public void calculate_VAT(){
        VAT= subtotal* VAT_value;
    }

    public void calculate_shipping( CountryList country,float weight,int quantity){
      shipping+= shippingFees.find_shipping_fee_for_country(country)*weight*quantity*10;
    }

    public ShippingFees getShippingFees() {
        return shippingFees;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public float getTotal() {
        return total;
    }

    public float getDiscounts() {
        return discounts;
    }

    public float getVAT() {
        return VAT;
    }

    public float getShipping() {
        return shipping;
    }

    public static Calculator getCalculator() {
        return calculator;
    }

    public  void reset(){ total =0; subtotal =0 ; discounts = 0 ; shipping = 0 ; VAT = 0;
    }
}
