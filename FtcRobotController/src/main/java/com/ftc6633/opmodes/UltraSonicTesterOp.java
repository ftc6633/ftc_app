package com.ftc6633.opmodes;

import com.ftc6633.utils.SoundPlayer;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;

/**
 * TeleOp Mode
 * <p>
 *Enables control of the robot via the gamepad
 */
public class UltraSonicTesterOp extends OpMode {

    // this declares the attribute sensor_ods, but does not create it
    private UltrasonicSensor sensor_usonic ;
    private SoundPlayer sound_effect = new SoundPlayer("/sdcard/sounds/coin.wav") ;

    @Override
    public void init() {
        // get the sensor using the hardware map created on the robot
        sensor_usonic = hardwareMap.ultrasonicSensor.get ("usonic");

    }

    /*
       * Code to run when the op mode is first enabled goes here
       * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
       */
    @Override
    public void init_loop() {
    }

    /*
     * This method will be called repeatedly in a loop
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#loop()
     */
    @Override
    public void loop() {
        // send a message to the driver phone
        double value = sensor_usonic.getUltrasonicLevel();
        telemetry.addData("1 Start", "distance: " + value);

        if(value<15.0) {
            sound_effect.play();
        }
    }
}