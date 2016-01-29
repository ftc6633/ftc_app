/* FTC 6633 4 Wheel Tank Drive */

package com.ftc6633.opmodes;

import com.ftc6633.utils.ScaleInput;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * TeleOp Mode
 * <p>
 * Enables control of the robot via the gamepad
 */
public class TeleOperator extends OpMode {

    DcMotor frontRightWheel;
    DcMotor rearRightWheel;
    DcMotor frontLeftWheel;
    DcMotor rearLeftWheel;
    Servo rightSideServo;
    Servo leftSideServo;
    DcMotor tapeMeasureMotor;
    DcMotor tapeMeasurePositioner;
	/**
	 * Constructor
	 */
	public TeleOperator() {

	}

	/*
	 * Code to run when the op mode is first enabled goes here
	 * 
	 * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
	 */
	@Override
	public void init() {
		/*
		 * Use the hardwareMap to get the dc motors and servos by name. Note
		 * that the names of the devices must match the names used when you
		 * configured your robot and created the configuration file.
		 */
		
		/*
		 * For the demo Tetrix K9 bot we assume the following,
		 *   There are two motors "motor_1" and "motor_2"
		 *   "motor_1" is on the right side of the bot.
		 *   "motor_2" is on the left side of the bot.
		 *   
		 * We also assume that there are two servos "servo_1" and "servo_6"
		 *    "servo_1" controls the arm joint of the manipulator.
		 *    "servo_6" controls the claw joint of the manipulator.
		 */
        frontRightWheel = hardwareMap.dcMotor.get("m32");
        rearRightWheel = hardwareMap.dcMotor.get("m12");

        frontLeftWheel = hardwareMap.dcMotor.get("m31");
        rearLeftWheel = hardwareMap.dcMotor.get("m11");
//Robot Functions!
        //Servo Functions!
        rightSideServo = hardwareMap.servo.get("rss");
        leftSideServo = hardwareMap.servo.get("lss");
        tapeMeasurePositioner = hardwareMap.dcMotor.get("mp1");
        //Dc Motor Functions!
        tapeMeasureMotor = hardwareMap.dcMotor.get("tmm");

        leftSideServo.setPosition(0.0);
        rightSideServo.setPosition(1.0);

        frontLeftWheel.setDirection(DcMotor.Direction.REVERSE);
        rearLeftWheel.setDirection(DcMotor.Direction.REVERSE);

	}

	/*
	 * This method will be called repeatedly in a loop
	 * 
	 * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#run()
	 */
	@Override
	public void loop() {

		/*
		 * Gamepad 1
		 * 
		 * Gamepad 1 controls the motors via the left stick, and it controls the
		 * wrist/claw via the a,b, x, y buttons
		 */

        // tank drive
        // note that if y equal -1 then joystick is pushed all of the way forward.
        float left = gamepad1.left_stick_y;
        float right = gamepad1.right_stick_y;

        float servoLeft = gamepad2.left_trigger;
        float servoRight = gamepad2.right_trigger;

        boolean gamepadYup = gamepad2.y;
        boolean gamepadAdown = gamepad2.a;

        float gamepadXtend = gamepad2.left_stick_y;
        float gamepadBack = gamepad2.right_stick_y;
		// clip the right/left values so that the values never exceed +/- 1
		right = Range.clip(right, -1, 1);
		left = Range.clip(left, -1, 1);

        double motorPower = Range.clip( gamepad2.right_stick_y,-1, 1);
        motorPower = (float) ScaleInput.scaleInput(motorPower);
        tapeMeasureMotor.setPower(motorPower);

        motorPower = Range.clip(gamepad2.left_stick_y, -1, 1);
        motorPower = (float) ScaleInput.scaleInput(motorPower);
        tapeMeasurePositioner.setPower(motorPower);

        // triggers are zero to one
        double servoPosition = Range.clip(gamepad2.left_trigger, Servo.MIN_POSITION, Servo.MAX_POSITION);
        leftSideServo.setPosition(servoPosition);

        servoPosition = Range.clip(1.0-gamepad2.right_trigger, Servo.MIN_POSITION, Servo.MAX_POSITION);
        rightSideServo.setPosition(servoPosition);

		// scale the joystick value to make it easier to control
		// the robot more precisely at slower speeds.
		right = (float) ScaleInput.scaleInput(right);
		left =  (float)ScaleInput.scaleInput(left);

		// write the values to the motors
        frontRightWheel.setPower(right);
        rearRightWheel.setPower(right);

        frontLeftWheel.setPower(left);
        rearLeftWheel.setPower(left);


		/*
		 * Send telemetry data back to driver station. Note that if we are using
		 * a legacy NXT-compatible motor controller, then the getPower() method
		 * will return a null value. The legacy NXT-compatible motor controllers
		 * are currently write only.
		 */

		telemetry.addData("Text", "*** Robot Data***");
		telemetry.addData("left trig",  "left trig: " + String.format("%.2f", gamepad2.left_trigger));
		telemetry.addData("right trig", "right trig: " + String.format("%.2f", gamepad2.right_trigger));
	}

	/*
	 * Code to run when the op mode is first disabled goes here
	 * 
	 * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#stop()
	 */
	@Override
	public void stop() {

	}
	


}
