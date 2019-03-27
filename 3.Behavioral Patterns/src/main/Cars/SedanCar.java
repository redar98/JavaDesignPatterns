package main.Cars;

import main.Enums.CarType;
import main.Enums.Manufacturer;

import java.util.Random;

public class SedanCar extends Car {
    public SedanCar(Manufacturer manufacturer, String model, int year){
        super(manufacturer, model, year, CarType.SEDAN);
        construct();
    }

    public SedanCar(SedanCar other){
        super(other.getManufacturer(), other.getModel(), other.getYear(), other.getType());
    }

    @Override
    protected void construct() {
        System.out.println("Building Sedan car...");
    }

    @Override
    public Car clone() {
        return new SedanCar(this);
    }
}
