package domain;

import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ShippingFees {


    private static ShippingFees shippingfees;

    private List< AbstractMap.SimpleEntry<CountryList,Integer>> list_of_shipping_fees;


    private void add_new_shipping_service_fee(CountryList country, Integer fee){
        AbstractMap.SimpleEntry<CountryList,Integer> new_entry;
        new_entry = new AbstractMap.SimpleEntry<>(country,fee);
        list_of_shipping_fees.add(new_entry);
    }





    public ShippingFees() {

        list_of_shipping_fees = new LinkedList<>();
        add_new_shipping_service_fee(CountryList.RO,1);
        add_new_shipping_service_fee(CountryList.UK,2);
        add_new_shipping_service_fee(CountryList.US,3);
    }

    public static ShippingFees getInstance(){
        if( shippingfees == null)
            shippingfees = new ShippingFees();
        return shippingfees;
    }


    public int find_shipping_fee_for_country(CountryList country){
        for(AbstractMap.SimpleEntry<CountryList,Integer> el : list_of_shipping_fees){
                if( el.getKey() == country)
                    return el.getValue();

        }
        return -1;
    }
}
