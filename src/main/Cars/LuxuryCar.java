package main.Cars;

import main.Enums.CarType;
import main.Enums.Manufacturer;

public class LuxuryCar extends Car {
    public LuxuryCar(Manufacturer manufacturer, String model, int year){
        super(manufacturer, model, year, CarType.LUXURY);
        construct();
    }

    public LuxuryCar(LuxuryCar other){
        super(other.getManufacturer(), other.getModel(), other.getYear(), other.getType());
    }

    @Override
    protected void construct() {
        System.out.println("Building Luxury car...");
    }

    @Override
    public Car clone() {
        return new LuxuryCar(this);
    }
}
