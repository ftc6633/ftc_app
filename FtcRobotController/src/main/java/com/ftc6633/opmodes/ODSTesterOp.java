package com.ftc6633.opmodes;

import android.util.Log;

import com.ftc6633.utils.SoundPlayer;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * TeleOp Mode
 * <p>
 *Enables control of the robot via the gamepad
 */
public class ODSTesterOp extends OpMode {

    // this declares the attribute sensor_ods, but does not create it
    private OpticalDistanceSensor sensor_ods;
    private SoundPlayer sound_effect = new SoundPlayer("/sdcard/sounds/coin.wav") ;

    @Override
    public void init() {
        // get the sensor using the hardware map created on the robot
        sensor_ods = hardwareMap.opticalDistanceSensor.get ("sensor_ods");
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
        double value = sensor_ods.getLightDetected ();
        telemetry.addData("1 Start", "light: " + value);

        if(value>0.9) {
            sound_effect.play();
        }
    }
}