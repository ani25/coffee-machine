package com.ani25;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Data class for representing a Beverage.
 */
@Data
public class Beverage {
    String beverage;
    Map<String, Integer> components;

    /**
     * Constructor for initializing Beverage data.
     * @param beverage
     */
    public Beverage(String beverage) {
        this.beverage = beverage;
        components = new HashMap<>();
    }
}
