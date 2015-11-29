/*
Run_using_encoders vs run_to_position
Can someone explain the difference between RUN_USING_ENCODERS and RUN_TO_POSITION. I do not see
 anything in the javadocs and as far I can tell, each channel mode requires you to:

1) reset the encoder
2) wait one or more hardware cycles for the motor position to register
3) set your own desired power
4) check if the motor position value is close to the desired target within each hardware cycle
5) issue your own motor stop command.

All of the above seems obvious to me when the channel mode is set to RUN_USING_ENCODERS.
However RUN_TO_POSITION requires you to set the target position. This to me implies that the onus
would not be on the programmer to handle #4 and #5 above.
Is this in fact the case or am I missing some other difference in behavior?

...
...

The run to position command slows the motor as you approach the set target position and holds the
motor at that position similar to a servo until you issue a new command.
The motor will turn CW or CCW as needed to get to the set target value.
You just set a positive setpower level.

Run using encoders is more of a set speed command. The max setpower results in about an 80% of max
speed so PID can adjust the power level to maintain the speed in case of increased drag or slope.

http://www.hitechnic.com/blog/wp-content/uploads/HiTechnic-Motor-Controller-Specification.pdf

http://ftcforum.usfirst.org/showthread.php?4986-Run_using_encoders-vs-run_to_position
http://ftcforum.usfirst.org/showthread.php?5535-Motor-Encoders

 */

package com.ftc6633.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * A simple example of a linear op mode that will approach an IR beacon
 */
public class EncoderTestOp extends LinearOpMode {

  final static double MOTOR_POWER = 0.75; // Higher values will cause the robot to move faster

  DcMotor motorRight;
  DcMotor motorLeft;


  @Override
  public void runOpMode() throws InterruptedException {

    // set up the hardware devices we are going to use
    motorLeft = hardwareMap.dcMotor.get("left_drive");
    motorRight = hardwareMap.dcMotor.get("right_drive");

    motorLeft.setDirection(DcMotor.Direction.REVERSE);

    motorLeft.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
    motorRight.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);

    // wait for the start button to be pressed
    waitForStart();

    int target = 1514;

    motorLeft.setTargetPosition(target);
    motorRight.setTargetPosition(target);

    motorLeft.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
    motorRight.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);

    // now approach the beacon
    motorRight.setPower(MOTOR_POWER);
    motorLeft.setPower(MOTOR_POWER);

    waitForNextHardwareCycle();

      double leftpos =  motorLeft.getCurrentPosition();
      double rightpos =  motorRight.getCurrentPosition();

    while(rightpos<target) {
        leftpos =  motorLeft.getCurrentPosition();
        rightpos =  motorRight.getCurrentPosition();
        telemetry.addData("Left pos ",leftpos );
        telemetry.addData("Right pos ", rightpos);
        sleep(100);
    }

    // stop the motors
    motorRight.setPower(0);
    motorLeft.setPower(0);
  }
}
