package domain.Utils;

import repository.DTOcatalogView;

import java.util.AbstractMap;
import java.util.List;

public class DiscountSystem {

    private float discount_amount=0F;
    private static boolean valid_discount;

    public DiscountSystem() {
    }


    private boolean run_two_or_more_items_offer(List<AbstractMap.SimpleEntry<DTOcatalogView,Integer>> cart){
            if(cart.size()>=2) {discount_amount+=10F ;return true;}
            return false;
    }

    private  boolean run_keyboards_offer(List<AbstractMap.SimpleEntry<DTOcatalogView,Integer>> cart){
        for (AbstractMap.SimpleEntry<DTOcatalogView, Integer> el : cart)
                if(el.getKey().getName().equals("Keyboard")){
                    discount_amount=discount_amount+  el.getKey().getPrice()*el.getValue()*0.10F;
                    return true;
                }
        return false;
    }

    private boolean run_monitors_offer(List<AbstractMap.SimpleEntry<DTOcatalogView,Integer>> cart){
        valid_discount=false;
        for (AbstractMap.SimpleEntry<DTOcatalogView, Integer> el : cart)
            if(el.getKey().getName().equals("Monitor") && el.getValue() >= 2)
                valid_discount=true;

        for (AbstractMap.SimpleEntry<DTOcatalogView, Integer> el : cart)
            if(el.getKey().getName().equals("DeskLamp"))
            {
                discount_amount= discount_amount+ el.getKey().getPrice()*0.5F;
                return true;
            }

        return false;
    }

    private String composeMessage(boolean offer1, boolean offer2, boolean offer3){
        String message="";


        if(offer1)
            message+="$10 off shipping\n";
        if(offer2)
            message+="10% off keyboards\n";
        if(offer3)
            message+="50% off desk lamp\n";
        return message;
    }

    public String runAllDiscounts(List<AbstractMap.SimpleEntry<DTOcatalogView,Integer>> cart){
        boolean offer1,offer2,offer3;
        offer1= run_two_or_more_items_offer(cart);
        offer2= run_keyboards_offer(cart);
        offer3= run_monitors_offer(cart);
        return composeMessage(offer1,offer2,offer3);
    }

    public float getDiscount_amount() {
        return discount_amount;
    }
}
