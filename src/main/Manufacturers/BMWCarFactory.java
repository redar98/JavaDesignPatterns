package main.Manufacturers;

import main.*;
import main.CarTypes.LuxuryCar;
import main.CarTypes.SavCar;
import main.CarTypes.SedanCar;
import main.Enums.CarType;
import main.Enums.Manufacturer;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import java.util.Calendar;
import java.util.Random;

public class BMWCarFactory {
    public static Car buildCar(CarType type){
        Car car;
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int modelIndex = new Random().nextInt(99) * 100;

        switch (type){
            case SEDAN:
                car = new SedanCar(Manufacturer.BMW, "S" + modelIndex, year);
                break;
            case SAV:
                car = new SavCar(Manufacturer.BMW, "X" + modelIndex, year);
                break;
            case LUXURY:
                car = new LuxuryCar(Manufacturer.BMW, "L" + modelIndex, year);
                break;
            default:
                throw new NotImplementedException();
        }
        return car;
    }
}
