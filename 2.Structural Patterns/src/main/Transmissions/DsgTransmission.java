package main.Transmissions;

import main.Enums.TransmissionType;

public class DsgTransmission extends Transmission {
    public DsgTransmission(int gears){
        super(TransmissionType.DSG, gears, 8);
        construct();
    }

    @Override
    protected void construct(){
        System.out.println("Building dsg transmission...");
    }
}
