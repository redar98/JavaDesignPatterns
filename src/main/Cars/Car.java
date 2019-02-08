package main.Cars;

import main.Enums.CarType;
import main.Enums.Manufacturer;

public abstract class Car {

    private Manufacturer manufacturer;
    private String model;
    private int year;
    private CarType type;

    // TODO: add a class that will have information about engine
    //  (model, horsepower, cylinders, petrol/diesel/hybrid/electric)
    //  Consider using premade engines?
    //private Engine engine;

    public Car(){
        manufacturer = null;
        model = null;
        year = 0;
        type = null;
    }

    public Car(Manufacturer manufacturer, String model, int year, CarType type){
        this.manufacturer = manufacturer;
        this.model = model;
        this.year = year;
        this.type = type;
    }

    public Manufacturer getManufacturer(){
        return this.manufacturer;
    }

    public String getModel(){
        return this.model;
    }

    public int getYear(){
        return this.year;
    }

    public CarType getType(){
        return this.type;
    }

    public void setManufacturer(Manufacturer manufacturer){
        this.manufacturer = manufacturer;
    }

    public void setModel(String model){
        this.model = model;
    }

    public void setYear(int year){
        this.year = year;
    }

    public void setType(CarType type){
        this.type = type;
    }

    protected abstract void construct();

    public abstract Car clone();

    @Override
    public String toString(){
        return this.manufacturer + " " + this.model + "(" + this.type + ") manufactured in " + this.year;
    }
}

