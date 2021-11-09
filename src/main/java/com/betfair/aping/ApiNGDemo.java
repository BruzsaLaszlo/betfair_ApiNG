package com.betfair.aping;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This is a demonstration class to show a quick demo of the new Betfair API-NG.
 * When you execute the class will: <li>find a market (next horse race in the
 * UK)</li> <li>get prices and runners on this market</li> <li>place a bet on 1
 * runner</li> <li>handle the error</li>
 */
public class ApiNGDemo {

    private static final Properties prop = new Properties();

    public static boolean isDebug() {
        return debug;
    }

    private static boolean debug;


    static {
        try (InputStream in = ApiNGDemo.class.getResourceAsStream("/apingdemo.properties")) {
            prop.load(in);
            debug = Boolean.parseBoolean(prop.getProperty("DEBUG"));
        } catch (IOException e) {
            System.out.println("Error loading the properties file: " + e);
        }
    }

    public static void main(String[] args) {

        System.out.println("Welcome to the Betfair API NG!");

        ApiNGJRescriptDemo rescriptDemo = new ApiNGJRescriptDemo();
        rescriptDemo.start();

    }

    public static Properties getProp() {
        return prop;
    }

    public static void debug(String what, String debugString) {
        if (debug)
            System.out.println("\n" + what + ": " + debugString);
    }
}
