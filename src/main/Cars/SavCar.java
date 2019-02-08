package main.Cars;

import main.Enums.CarType;
import main.Enums.Manufacturer;

public class SavCar extends Car {
    public SavCar(Manufacturer manufacturer, String model, int year){
        super(manufacturer, model, year, CarType.SAV);
        construct();
    }

    public SavCar(SavCar other){
        super(other.getManufacturer(), other.getModel(), other.getYear(), other.getType());
    }

    @Override
    protected void construct() {
        System.out.println("Building Sav car...");
    }

    @Override
    public Car clone() {
        return new SavCar(this);
    }
}
