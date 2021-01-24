package com.ani25;

import org.junit.Test;

public class CoffeeMachineTest {

    CoffeeMachine coffeeMachine;

    @Test
    public void testCoffeeMachineWhereOnlyTwoCanBeBrewed() {
        coffeeMachine = new CoffeeMachine("./src/test/resources/machine.json");
        coffeeMachine.serveTheRequestsInFullCapacity();
        System.out.println("----------------------------");
    }

    @Test
    public void testCoffeeMachineWhereAllCanBeBrewedExceptGreenTea() {
        coffeeMachine = new CoffeeMachine("./src/test/resources/machine2.json");
        coffeeMachine.serveTheRequestsInFullCapacity();
        System.out.println("----------------------------");
    }

    @Test
    public void testCoffeeMachineWhereAllCanBeBrewed() {
        coffeeMachine = new CoffeeMachine("./src/test/resources/machine3.json");
        coffeeMachine.serveTheRequestsInFullCapacity();
        System.out.println("----------------------------");
    }
}