package main.Transmissions;

import main.Enums.TransmissionType;

public class ManualTransmission extends Transmission {
    public ManualTransmission(int gears){
        super(TransmissionType.MANUAL, gears, 600);
        construct();
    }

    @Override
    protected void construct(){
        System.out.println("Building manual transmission...");
    }
}
