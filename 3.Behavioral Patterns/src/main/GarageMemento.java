package main;

import main.Cars.ICar;

import java.util.List;

public class GarageMemento {
    private List<ICar> cars;

    public GarageMemento(List<ICar> cars){
        this.cars = cars;
    }

    public List<ICar> getCars(){
        return this.cars;
    }
}
