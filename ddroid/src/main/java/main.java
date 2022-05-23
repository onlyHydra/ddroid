import repository.DTOcatalogView;
import services.Features;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

public class main {

    private static Features mainService;



    public static void main(String[] args) {
        mainService = new Features();
        mainService.display_catalog();

        programLoop();
    }


    private static void commands(){
        String message ="Type the following commands:\n" +
                "\n" +
                "\n" +
                "1: \"Select\"  , it will expect you to select a product \n" +
                "syntax : Product_name quantity  \n" +
                "ex : \n" +
                "\tuser_input>>Monitor 5\n" +
                "checkout cart will be : Monitor x5 \n" +
                "\t\t\n" +
                "2: \"Checkout\" proceed to the cart checkout\n" +
                "\n" +
                "3: \"Stop\" it will stop the console app\n" +
                "\n" +
                "Type 0 if you're done selecting products the command midway;\n" ;
        System.out.println(message);
    }


    private static void parseInput(String[] input){
           List<DTOcatalogView> catalog =  mainService.getCatalog();
        for(DTOcatalogView el : catalog) {
            if (el.getName().equals(input[0])){
                mainService.select_product(el, Integer.parseInt(input[1]));
                return;
            }
        }
    }

    private static void programLoop(){
        String input ="";
        Scanner reader = new Scanner(new InputStreamReader((System.in)));
        commands();
        while(!input.equals("stop")){
            input= reader.nextLine();
            if(input.equals("Select")){
                while(!input.equals("0")) {
                    mainService.display_catalog();
                    System.out.println("waiting for syntax for product selection \n" +
                            "Syntax : Product_name quantity\n");
                    input = reader.nextLine();
                    parseInput(input.split(" "));
                    if(input.equals("Command"))
                        commands();
                }

            }
            if(input.equals("Checkout")){
                mainService.purchase();

            }
            if(input.equals("Remove")){
                System.out.println("A small prank I forgot to remove this command\n");


            }

        }
    }
}
