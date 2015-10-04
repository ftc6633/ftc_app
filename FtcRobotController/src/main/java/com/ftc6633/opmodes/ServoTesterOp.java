package com.ftc6633.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * TeleOp Mode
 * <p>
 *Enables control of the robot via the gamepad
 */
public class ServoTesterOp extends OpMode {

    // this declares the attribute testservo, but does not create it
    Servo testservo;

    @Override
    public void init() {
        // get the servo using the hardware map created on the robot
        testservo = hardwareMap.servo.get("servo_test");
    }

    /*
       * Code to run when the op mode is first enabled goes here
       * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
       */
    @Override
    public void init_loop() {
        testservo.setPosition(0.5);
    }

    /*
     * This method will be called repeatedly in a loop
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#loop()
     */
    @Override
    public void loop() {
        // read the stick position (-1 to 1) and scale to servo (0- to 1)
        double servoPosition = Range.clip((gamepad1.left_stick_y + 1.0) / 2.0, Servo.MIN_POSITION, Servo.MAX_POSITION);
        testservo.setPosition(servoPosition);

        // send a message to the driver phone
        telemetry.addData("1 Start", "servo " + servoPosition);
    }
}