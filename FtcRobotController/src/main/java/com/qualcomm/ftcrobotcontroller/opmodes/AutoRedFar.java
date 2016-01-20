package com.qualcomm.ftcrobotcontroller.opmodes;

import android.util.Log;

import com.ftc6633.utils.SoundPlayer;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.ftcrobotcontroller.FtcRobotControllerActivity;

import org.opencv.core.Rect;

/**
 * RJG on 2015-12-19
 * - 2016 January added code to store preset colors
 * - changed motor constants every time wheels, gears, etc. changed
 * - added dead reckoning to get in front of beacon
 * - set up to have several autonomous opmodes for different conditions
 *
 */

public class AutoRedFar extends LinearOpMode {
    DcMotor frontRightWheel;
    DcMotor rearRightWheel;
    DcMotor frontLeftWheel;
    DcMotor rearLeftWheel;
    DcMotor collector;
    long index;
    float localDirection;
    Rect localRect;

    Boolean deadReckoning;  // Switch for testing to focus on different tasks

    public void runOpMode() throws InterruptedException {
        /*
		 * Use the hardwareMap to get the dc motors and servos by name.
		 * Note that the names of the devices must match the names used
		 * when you configured your robot and created the configuration file.
		 */
        frontRightWheel = hardwareMap.dcMotor.get("m11");
        rearRightWheel = hardwareMap.dcMotor.get("m31");

        frontLeftWheel = hardwareMap.dcMotor.get("m12");
        rearLeftWheel = hardwareMap.dcMotor.get("m32");

        // RJG the moters are 180 degrees from each otgher
        frontLeftWheel.setDirection(DcMotor.Direction.REVERSE);
        rearLeftWheel.setDirection(DcMotor.Direction.REVERSE);
// RJG no more colector        collector = hardwareMap.dcMotor.get("m21");
        SoundPlayer powerup = new SoundPlayer("/sdcard/sounds/valk.wav") ;
        Log.i("RJG", "Rockandroll");

        waitForStart();

        powerup.play();
// RJG
        deadReckoning = Boolean.TRUE;

// RJG dead reckon to within CV range
        if( deadReckoning ) {
            driveXFeet(2.7);
            SetAllMotors(0);
            Turn(.8);          // RJG 45 degrees to left
            sleep(800);         // RJG 45 degrees to left
            SetAllMotors(0);
            driveXFeet(4.5);
            SetAllMotors(0);
            Turn(.8);          // RJG 45 degrees to left
            sleep(800);         // RJG 45 degrees to left
            SetAllMotors(0);
        }

        // RJG get direction from ColorBlobDetector
        // also maybe boundRect
        index = 3000000;
        Log.i("*", "**********");
        while(index-- >0){
            localDirection =((FtcRobotControllerActivity) hardwareMap.appContext).getDirection();
//            localRect = ((FtcRobotControllerActivity) hardwareMap.appContext).getDetector().getBoundRect();
//            Log.i("*","got direction*");
            if (localDirection > 0) {
//                Log.i("RJG", "boundRect.tl:" +  localRect.tl() );
 //               Log.i("RJG", "boundRect.br:" +  localRect.br() );
//                Log.i("RJG", "direction =" + localDirection);
//                Log.i("RJG", "rect =" + localRect);

                steer(localDirection);
            }else {
                Log.i("*","*cant' see*");

                index = 0;
            }
        }
        Log.i("*", "**********");
        SetAllMotors(0);

/* RJG lots of test code snippets. delete when all done
        driveXFeet(-2);         // RJG back away from beacon
        SetAllMotors(0);

        Turn(.8);          // RJG 45 degrees to right
        sleep(300);         // RJG 45 degrees to right

        SetAllMotors(0);

        driveXFeet(-2);         // RJG back away from beacon
        SetAllMotors(0);

        Turn(-.8);          // RJG 45 degrees to left
        sleep(600);         // RJG 45 degrees to left
        SetAllMotors(0);

        index = 10000000;
        Log.i("*", "**********");
        while(index-- >0){
            localDirection =((FtcRobotControllerActivity) hardwareMap.appContext).getDirection();
            Log.i("*","got direction*");
            if (localDirection > 0) {
                Log.i("RJG", "direction =" + localDirection);
                steer(localDirection);
            }else {
                Log.i("*","*cant' see*");

                //               index = 0;
            }
        }
        Log.i("*", "**********");

        SetAllMotors(0);


        index = 100000;
        while(index-- >0){
            localDirection =((FtcRobotControllerActivity) hardwareMap.appContext).getDirection();
            if (localDirection > 0) {
                Log.i("RJG", "direction =" + localDirection);
                steer(((float).5 -  localDirection) / 10);
            }else {
                index = 0;
            }
        }


        Turn(-.2);
        sleep(1250);
        SetAllMotors(0.1);
        sleep(3000);
        Turn(-0.1);
        sleep(1000);
        SetAllMotors(0.1);
        sleep(1000);
        Turn(0.1);
        sleep(1000);

        SetAllMotors(0.0);

        while(index-- >0){
            localDirection =((FtcRobotControllerActivity) hardwareMap.appContext).getDirection();
            if (localDirection > 0) {
                Log.i("RJG", "direction =" + localDirection);
                steer(((float).5 -  localDirection) / 10);
            }else {
                index = 0;
            }
        }

        index = 100000;
        while(index-- >0){
            localDirection =((FtcRobotControllerActivity) hardwareMap.appContext).getDirection();
            if (localDirection > 0) {
                Log.i("RJG", "direction =" + localDirection);
                steer(((float).5 -  localDirection) / 10);
            }else {
                index = 0;
            }
        }
     */

        /*
        index = 1000000;
        while(index-->0){
            Log.i("RJG", "direction ="+((FtcRobotControllerActivity) hardwareMap.appContext).getDirection());
        }
        Log.i("RJG", "All Done");
		 */


        //       Log.i("RJG", "direction ="+((FtcRobotControllerActivity) hardwareMap.appContext).getDirection());

        //       SetAllMotors(0.3);
        //       sleep(2000);
        //       SetAllMotors(0.0);

        //       Turn(0.12);
        //       sleep(3000);
        //       SetAllMotors(0.12);
        //      sleep(4000);
//        SetAllMotors(0.5);
//        sleep(750);
//        SetAllMotors(0.0);
/*// RJG moter identification code
//        frontRightWheel.setPower(.2);
//        frontLeftWheel.setPower(.2);
//        rearRightWheel.setPower(.2);
//        rearLeftWheel.setPower(.2);
//        rearRightWheel.setPower(.1 + adjust*scaleFactor);
//        frontLeftWheel.setPower(.1 - adjust*scaleFactor);
//        rearLeftWheel.setPower(.1 - adjust*scaleFactor);
//        sleep(30000);         // RJG 45 degrees to left

        // RJG calibrate turns
        Turn(-.8);
        sleep(1000);
        SetAllMotors(0);
        Turn(.8);
        sleep(1000);
        SetAllMotors(0);
        Turn(-.8);
        sleep(1000);
        SetAllMotors(0);
        Turn(.8);
        sleep(1000);
        SetAllMotors(0);
    */
    }


    public void steer(double direction) {
        double adjust;
        double scaleFactor;
        double basePower =.2;

        adjust = 0.5 - direction;
        scaleFactor = .85;
//        Log.i("RJG", "adjust ="+adjust);

        frontRightWheel.setPower(basePower - adjust*scaleFactor);
        rearRightWheel.setPower(basePower - adjust*scaleFactor);
        frontLeftWheel.setPower(basePower + adjust*scaleFactor);
        rearLeftWheel.setPower(basePower + adjust*scaleFactor);
    }

    public void SetAllMotors(double power) {
        frontRightWheel.setPower(power);
        frontLeftWheel.setPower(power);
        rearLeftWheel.setPower(power);
        rearRightWheel.setPower(power);
    }

    // RJG turn all wheels are the same size
    public void Turn(double speed) {
        frontRightWheel.setPower(-speed);
        frontLeftWheel.setPower(speed);
        rearLeftWheel.setPower(speed);
        rearRightWheel.setPower(-speed);
    }

    public void driveXFeet(double distance) throws InterruptedException {
        SetAllMotors(0.8);
        sleep(Math.abs((long) (940 * distance)));
    }

    public void turnXDegrees(float distance) throws InterruptedException {
        SetAllMotors(0.2);
        sleep((long)(610*distance));
    }

}

