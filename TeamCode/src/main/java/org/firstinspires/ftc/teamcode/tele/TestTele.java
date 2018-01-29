package org.firstinspires.ftc.teamcode.tele;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.bots.Shortround;

/**
 * Created by 20Seethalla on 11/16/2017.
 */

@TeleOp(name = "TestTele", group = "Iterative Opmode")

public class TestTele extends OpMode {
    //Declares motors, servo, runtime counter and variables
    private ElapsedTime runtime = new ElapsedTime();
    private Shortround bot = new Shortround(hardwareMap);

    //runs ONCE when the driver hits INIT
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
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
        double leftPower = (-gamepad1.right_stick_y) / 1.25;
        double rightPower = (-gamepad1.left_stick_y) / 1.25;

        bot.driveTrain.setLeftPower(leftPower);
        bot.driveTrain.setRightPower(rightPower);

        //Uses the joystick for gamepad2 for the lift
        armPower((-gamepad2.left_stick_y) / 2);
        servoRun(); //runs the servo method

        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);

    }

    //runs ONCE after the driver hits STOP
    @Override
    public void stop() {
    }


    //Moves the lift up
    public void armPower(double power) {
        bot.lift.setPower(power);
    }


    //opens up the servos if the a button on gamepad2 is held
    public void servoRun() {

        if (gamepad2.a) {
            spinServo(0.25);

        } else if (gamepad2.b) {
            spinServo(0);
        }

    }

    public void spinServo(double pos) {
        bot.grabbers.servos[0].setPosition(pos);
        bot.grabbers.servos[1].setPosition(pos);
    }

}