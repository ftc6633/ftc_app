package com.ftc6633.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by rupello on 12/10/2015.
 */



public class ParkOnMountain extends LinearOpMode {

    DcMotor frontRightWheel;
    DcMotor rearRightWheel;
    DcMotor frontLeftWheel;
    DcMotor rearLeftWheel;
    DcMotor collector;

    public void runOpMode() throws InterruptedException {
        frontRightWheel = hardwareMap.dcMotor.get("m11");
        rearRightWheel = hardwareMap.dcMotor.get("m31");

        frontLeftWheel = hardwareMap.dcMotor.get("m12");
        rearLeftWheel = hardwareMap.dcMotor.get("m32");

        collector = hardwareMap.dcMotor.get("m21");



        frontLeftWheel.setDirection(DcMotor.Direction.REVERSE);
        rearLeftWheel.setDirection(DcMotor.Direction.REVERSE);



        SetAllMotors(0.3);
        sleep(2000);
        SetAllMotors(0.0);

        Turn(0.3,-0.3);
        sleep(750);
        SetAllMotors(0.0);
        sleep(500);
        SetAllMotors(0.5);
        sleep(750);
        SetAllMotors(0.0);




    }

    public void SetAllMotors(double power) {
        frontRightWheel.setPower(-power);
        frontLeftWheel.setPower(power);
        rearLeftWheel.setPower(-power);
        rearRightWheel.setPower(power);
    }

    public void Turn(double leftPower, double rightPower) {

        frontRightWheel.setPower(rightPower);
        frontLeftWheel.setPower(leftPower);
        rearLeftWheel.setPower(leftPower);
        rearRightWheel.setPower(rightPower);
    }
}
