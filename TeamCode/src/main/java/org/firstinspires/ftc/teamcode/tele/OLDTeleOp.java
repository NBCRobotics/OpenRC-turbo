package org.firstinspires.ftc.teamcode.tele;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.hardware.bots.Shortround;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by 20Seethalla on 11/16/2017.
 */

/*
 ** main TeleOp used for competition
 */
//SlowDown function not working
@TeleOp(name = "OLDTeleOp", group = "Iterative Opmode")
public class OLDTeleOp extends OpMode {
    //Declares motors, servo, runtime counter and variables
    private ElapsedTime runtime = new ElapsedTime();
    private Shortround  bot     = new Shortround(hardwareMap);

    //runs ONCE when the driver hits INIT
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
    }

    //runs REPEATEDLY after the driver hits INIT, but before they hit PLAY
    @Override
    public void init_loop() {

    }

    //runs  ONCE when the driver hits PLAY
    @Override
    public void start() {
        runtime.reset();
    }

    //runs REPEATEDLY after the driver hits PLAY but before they hit STOP
    @Override
    public void loop() {
        //Sets up power for Tank Drive
        double leftPower = (-gamepad1.right_stick_y) / 1.25 / slowDown(gamepad1);
        double rightPower = (-gamepad1.left_stick_y) / 1.25 / slowDown(gamepad1);

        bot.driveTrain.setLeftPower(leftPower);
        bot.driveTrain.setRightPower(rightPower);

        //Uses the joystick for gamepad2 for the lift
        armPower(((-gamepad2.left_stick_y) / 2) / slowDown(gamepad2));
        spinServo(); //runs the servo method

        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
    }

    //runs ONCE after the driver hits STOP
    @Override
    public void stop() {
        telemetry.addData("Status", "Stopped");
        telemetry.update();
    }

    //slows down all applied motors if left bumper is held-NOT WORKING
    @NonNull
    public int slowDown(Gamepad gamepad) {
        int slowTrig = 1;

        if (gamepad.left_bumper) {
            slowTrig = 2;
        } else if (!gamepad.left_bumper) {
            slowTrig = 1;
        }

        return slowTrig;
    }

    //Moves the lift up
    public void armPower(float power) {
        bot.lift.setPower(power);
    }


    //opens up the servos if the a button on gamepad2 is held
    public void spinServo() {
        if (gamepad2.a) {
            bot.grabbers.openGrabbers();
        } else {
            bot.grabbers.closeGrabbers();
        }
    }


}
