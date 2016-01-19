package com.qualcomm.ftcrobotcontroller.opmodes;
import android.util.Log;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.ftcrobotcontroller.FtcRobotControllerActivity;

/**
 * RJG on 2015-12-19
 * JG
 * GitHub Test 4
 */

public class AutoTest extends LinearOpMode {

    DcMotor frontRightWheel;
    DcMotor rearRightWheel;
    DcMotor frontLeftWheel;
    DcMotor rearLeftWheel;
    DcMotor collector;
    long index;
    float localDirection;

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

        waitForStart();
        Log.i("RJG", "Rockandroll");


        index = 100000;
        while(index >0){
            localDirection =((FtcRobotControllerActivity) hardwareMap.appContext).getDirection();
            if (localDirection > 0) {
                Log.i("RJG", "direction =" + localDirection);
                steer(((float).5 -  localDirection) / 10);
            }else {
                index = 0;
            }
        }
        SetAllMotors(0.0);

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

    }


    public void steer(float direction) {
        frontRightWheel.setPower(.1+direction);
        rearRightWheel.setPower(.1+direction);
        frontLeftWheel.setPower(.1-direction);
        rearLeftWheel.setPower(.1-direction);
    }
    public void SetAllMotors(double power) {
        frontRightWheel.setPower(power);
        frontLeftWheel.setPower(power);
        rearLeftWheel.setPower(power);
        rearRightWheel.setPower(power);
    }

    // RJG turn all wheels are the same size
    public void Turn(double speed) {
        frontRightWheel.setPower(speed);
        frontLeftWheel.setPower(-speed);
        rearLeftWheel.setPower(-speed);
        rearRightWheel.setPower(speed);
    }
}
