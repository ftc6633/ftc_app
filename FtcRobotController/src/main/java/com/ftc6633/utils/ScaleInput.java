package com.ftc6633.utils;

import com.qualcomm.robotcore.util.Range;
import com.ftc6633.utils.PowerLevels;
/**
 * Created by rupello on 1/8/2016.
 */

public class ScaleInput {

    /*
	 * This method scales the joystick input so for low joystick values, the
	 * scaled value is less than linear.  This is to make it easier to drive
	 * the robot more precisely at slower speeds.
	 */
    public static double scaleInput(double dVal)  {
        double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.72, 0.72, 0.72 };

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);

        // index should be positive.
        if (index < 0) {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        if (index > 16) {
            index = 16;
        }

        // get value from the array.
        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;
    }

    public static PowerLevels arcadeScale(double stick_x, double stick_y) {
        stick_x = Range.clip(stick_x, -1, 1);
        stick_y = Range.clip(stick_y, -1, 1);
        double right_power = stick_y;
        double left_power = stick_y;
        // adjust power of left/right motor according to steering
        double scale = 1.0;
        if(stick_x > 0) {
            scale = -(stick_x * 2 - 1.);
            left_power = left_power * scale;
        }
        else if(stick_x < 0) {
            scale = (stick_x * 2 + 1.);
            right_power = right_power * scale;
        }
        return new PowerLevels(right_power,left_power);
    }
}
