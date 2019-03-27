package main.Factories;

import main.Enums.TransmissionType;
import main.Transmissions.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class TransmissionFactory {
    public static Transmission buildTransmission(TransmissionType type, int gears){
        Transmission transmission;

        switch (type){
            case MANUAL:
                transmission = new ManualTransmission(gears);
                break;
            case AUTOMATIC:
                transmission = new AutomaticTransmission(gears);
                break;
            case TIPTRONIC:
                transmission = new TiptronicTransmission(gears);
                break;
            case DSG:
                transmission = new DsgTransmission(gears);
                break;
            default:
                throw new NotImplementedException();
        }

        return transmission;
    }
}
