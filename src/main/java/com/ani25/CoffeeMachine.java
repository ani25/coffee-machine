package com.ani25;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * CoffeeMachine class, for keeping the CoffeeMachine properties and functions.
 */
public class CoffeeMachine {
    private int noOfOutlets;
    private List<Beverage> menu;
    public static Map<String, Integer> items;

    /**
     * Constructor with the json file as input, which is required for defining the machine's properties.
     * @param fileName
     */
    public CoffeeMachine(String fileName) {
        try {
            JSONParser jsonParser = new JSONParser();
            FileReader reader = new FileReader(fileName);

            JSONObject machineJsonObj = (JSONObject) jsonParser.parse(reader);
            JSONObject machine = (JSONObject) machineJsonObj.get("machine");

            parseNoOfOutlets(machine);

            parseCoffeeMachineItems(machine);
            parseBeverageMenu(machine);

        } catch ( IOException | ParseException e ) {
            e.printStackTrace();
        }
    }

    /**
     * This method parallely executes the requests as per file in machine.
     */
    public void serveTheRequestsInFullCapacity() {
        try {
            ExecutorService executorService = Executors.newFixedThreadPool(noOfOutlets);

            menu.parallelStream()
                .forEach(beverage -> {
                    Runnable worker = new DefaultBrew(beverage);
                    executorService.execute(worker);
                });

            // Finish all existing threads in the queue
            executorService.shutdown();
            // Wait until all threads are finish
            executorService.awaitTermination(1000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseNoOfOutlets(JSONObject machine) {
        JSONObject outlets = (JSONObject) machine.get("outlets");
        noOfOutlets = ((Long)outlets.get("count_n")).intValue();
    }

    private void parseCoffeeMachineItems(JSONObject machine) {
        items = new HashMap<>();
        JSONObject total_items_quantity = (JSONObject) machine.get("total_items_quantity");
        for (Object sub : total_items_quantity.keySet()) {
            items.put(sub.toString(), ((Long)total_items_quantity.get(sub)).intValue());
        }
    }

    private void parseBeverageMenu(JSONObject machine) {
        menu = new ArrayList<>();
        JSONObject beverages = (JSONObject) machine.get("beverages");
        for (Object bev : beverages.keySet()) {
            Beverage beverage = new Beverage(bev.toString());
            JSONObject beverage1 = (JSONObject) beverages.get(bev.toString());
            for (Object sub : beverage1.keySet()) {
                beverage.components.put(sub.toString(), ((Long)beverage1.get(sub)).intValue());
            }
            menu.add(beverage);
        }
    }

    //Main method for running the coffee machine on local.
    /*
    public static void main(String[] args){
        CoffeeMachine coffeeMachine = new CoffeeMachine("./src/main/resources/machine.json");
        coffeeMachine.serveTheRequestsInFullCapacity();
    }
    */
}
