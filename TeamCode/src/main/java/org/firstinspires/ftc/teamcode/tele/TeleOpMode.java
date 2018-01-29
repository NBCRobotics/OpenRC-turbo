package org.firstinspires.ftc.teamcode.tele;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.bots.Shortround;

/**
 * Created by 20Seethalla on 11/16/2017.
 */

/*
** Used to test SlowDown and freeze Lift methods
*/
//SlowDown function not working
@TeleOp(name = "TeleOpMode", group = "Iterative Opmode")
public class TeleOpMode extends OpMode {
    //Declares motors, servo, runtime counter and variables
    private ElapsedTime runtime = new ElapsedTime();
    private Shortround bot = new Shortround(hardwareMap);


    @Override
    public void init() {
        bot.grabbers.closeGrabbers();
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

        double drivePower = 1;

        if (gamepad1.right_trigger == 1) {
            drivePower = 0.5;
        } else {
            drivePower = 1;
        }

        //Sets up power for Tank Drive
        double leftPower = (-gamepad1.left_stick_y / 1.25) * drivePower;
        double rightPower = (-gamepad1.right_stick_y / 1.25) * drivePower;
        double servoPower = -gamepad2.right_stick_y; //Side servo control


        bot.driveTrain.setLeftPower(leftPower);
        bot.driveTrain.setRightPower(rightPower);
        bot.jewelArm.servo.setPosition(servoPower);

        try {
            //Uses the joystick for gamepad2 for the lift
            runArm();
        } catch (Exception e) {
            telemetry.addData("ERROR!!! Exception: ", e.getMessage());
            telemetry.addData("Stack Trace: ", e.getStackTrace());
        } finally {
            spinServo(); //runs the servo method
        }

        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
        //telemetry.addData("Color Number", cSensor.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER));
    }

    //runs ONCE after the driver hits STOP
    @Override
    public void stop() {
    }
    
    /*//slows down all applied motors if left bumper is held-NOT WORKING
    public int slowDown(Gamepad gamepad) {
        if (gamepad.left_bumper)
            slowTrig = 2;
        else if (!gamepad.left_bumper)
            slowTrig = 1;
        return slowTrig;
    }*/

    //Moves the lift up
    public void armPower(float power) {
        bot.lift.setPower(power);
    }

    public void brakeArm() {
        armPower(0);
        telemetry.addData("Motors", "Lift should freeze");
    }

    public void runArm() {
        //int currentPos = (leftArm.getCurrentPosition() + rightArm.getCurrentPosition()) / 2;
        if (-gamepad2.left_stick_y == 0) { //|| (startPos == currentPos)) {
            brakeArm();
        } else
            armPower(((-gamepad2.left_stick_y) / 4));
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