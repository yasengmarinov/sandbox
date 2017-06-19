package server.config;

/**
 * Created by B06514A on 6/18/2017.
 */
public class PumpConfig {
    private int id;
    private int pinGroud;
    private int pinIn;

    public PumpConfig(int id, int pinGroud, int pinIn) {
        this.id = id;
        this.pinGroud = pinGroud;
        this.pinIn = pinIn;
    }

    public int getId() {
        return id;
    }

    public int getPinGroud() {
        return pinGroud;
    }

    public int getPinIn() {
        return pinIn;
    }
}
