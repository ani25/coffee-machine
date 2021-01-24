package com.ani25;

/**
 * Default implementation of interface Brew.
 */
public class DefaultBrew implements Brew{
    private final Beverage beverage;

    /**
     * Constructor for DefaultBrew, initializes beverage.
     * @param beverage
     */
    public DefaultBrew(Beverage beverage) {
        this.beverage = beverage;
    }

    /**
     * Method is responsible for actual execution of identifying and brewing.
     */
    @Override
    public synchronized void run() {
        int componentAvailability = 0;
        String notAvailableComponent = null;
        String notSufficientComponent = null;
        for(String comp : beverage.components.keySet()){
            if(CoffeeMachine.items.containsKey(comp)
                    && CoffeeMachine.items.get(comp) >= beverage.components.get(comp)){
                componentAvailability++;
            } else if(CoffeeMachine.items.containsKey(comp)) {
                notSufficientComponent = comp;
            } else {
                notAvailableComponent = comp;
            }
        }
        if (beverage.components.size() == componentAvailability){
            for(String comp : beverage.components.keySet()){
                if(CoffeeMachine.items.containsKey(comp)
                        && CoffeeMachine.items.get(comp) >= beverage.components.get(comp)){
                    CoffeeMachine.items.put(comp, CoffeeMachine.items.get(comp) - beverage.components.get(comp));
                }
            }
            System.out.println(beverage.beverage + " is prepared");
        } else {
            if(notAvailableComponent != null)
                System.out.println(beverage.beverage + " cannot be prepared because " + notAvailableComponent
                        + " is not available");
            else System.out.println(beverage.beverage + " cannot be prepared because item " + notSufficientComponent
                    + " is not sufficient");
        }

    }
}
