package com.ftc6633.opmodes;

import com.ftc6633.utils.SoundPlayer;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * TeleOp Mode
 * <p>
 *Enables control of the robot via the gamepad
 */
public class SoundTesterOp extends OpMode {

    SoundPlayer powerup = new SoundPlayer("/sdcard/sounds/powerup.wav") ;
    SoundPlayer coin = new SoundPlayer("/sdcard/sounds/coin.wav") ;
    SoundPlayer jump = new SoundPlayer("/sdcard/sounds/jump.wav") ;
    SoundPlayer laser = new SoundPlayer("/sdcard/sounds/laser.wav") ;

    @Override
    public void init() {
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
        if(gamepad1.a){
            powerup.play();
        }
        if(gamepad1.b){
            coin.play();
        }
        if(gamepad1.x){
            jump.play();
        }
        if(gamepad1.y){
            laser.play();
        }
    }
}