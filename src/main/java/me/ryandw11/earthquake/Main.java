package me.ryandw11.earthquake;

import org.kakara.engine.GameEngine;

public class Main {
    public static void main(String[] args) {
        EarthquakeDemo earthquakeDemo = new EarthquakeDemo();
        GameEngine gameEngine = new GameEngine("Earthquake Demo", 1080, 720, true, earthquakeDemo);
        gameEngine.run();
    }
}
