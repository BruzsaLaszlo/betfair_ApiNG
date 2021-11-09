package com.betfair.aping;

/**
 * This is a demonstration class to show a quick demo of the new Betfair API-NG.
 * When you execute the class will: <li>find a market (next horse race in the
 * UK)</li> <li>get prices and runners on this market</li> <li>place a bet on 1
 * runner</li> <li>handle the error</li>
 */
public class ApiNGDemo {

    public static void main(String[] args) {

        System.out.println("Welcome to the Betfair API NG!");

        ApiNGJRescriptDemo rescriptDemo = new ApiNGJRescriptDemo();
        rescriptDemo.start();

    }

}
