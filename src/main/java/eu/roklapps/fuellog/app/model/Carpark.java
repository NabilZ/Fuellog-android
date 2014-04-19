package eu.roklapps.fuellog.app.model;

public class Carpark {
    private String vendor;
    private String name;
    private int fuelCycles;
    private String latestFuelCycle;

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFuelCycles() {
        return fuelCycles;
    }

    public void setFuelCycles(int fuelCycles) {
        this.fuelCycles = fuelCycles;
    }

    public String getLatestFuelCycle() {
        return latestFuelCycle;
    }

    public void setLatestFuelCycle(String latestFuelCycle) {
        this.latestFuelCycle = latestFuelCycle;
    }
}
