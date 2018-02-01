package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

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
    private DcMotor leftDrive, rightDrive, leftArm, rightArm;
    private Servo leftServo, rightServo;
    private int slowTrig;

    //runs ONCE when the driver hits INIT
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        //Configures the motors
        leftDrive = configMotor("leftDrive");
        rightDrive = configMotor("rightDrive");
        leftArm = configMotor("leftArm");
        rightArm = configMotor("rightArm");
        leftServo = hardwareMap.get(Servo.class, "leftServo");
        rightServo = hardwareMap.get(Servo.class, "rightServo");

        //Sets the direction of all motors and servos
        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        rightArm.setDirection(DcMotor.Direction.FORWARD);
        leftArm.setDirection(DcMotor.Direction.REVERSE);
        leftServo.setDirection(Servo.Direction.REVERSE);
        rightServo.setDirection(Servo.Direction.FORWARD);

        rightArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
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

        leftDrive.setPower(leftPower);
        rightDrive.setPower(rightPower);

        //Uses the joystick for gamepad2 for the arm
        armPower(((-gamepad2.left_stick_y) / 2) / slowDown(gamepad2));
        spinServo(); //runs the servo method

        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
        telemetry.addData("Arm Motor", " (%.2f)", ((-gamepad2.left_stick_y) / 2) / slowDown(gamepad2));
    }

    //runs ONCE after the driver hits STOP
    @Override
    public void stop() {

    }

    public DcMotor configMotor(String motor) {
        return hardwareMap.get(DcMotor.class, motor);
    }

    //slows down all applied motors if left bumper is held-NOT WORKING
    public int slowDown(Gamepad gamepad) {
        if (gamepad.left_bumper)
            slowTrig = 2;
        else if (!gamepad.left_bumper)
            slowTrig = 1;
        return slowTrig;
    }

    //Moves the arm up
    public void armPower(float power) {
        leftArm.setPower(power);
        rightArm.setPower(power);
    }

    /*
    public void armPower(float power)
    {
        if(-gamepad2.left_stick_y == 0)
        {
            leftArm.setPower(0);
            rightArm.setPower(0);
        }
        else
        {
            leftArm.setPower(power);
            rightArm.setPower(power);
        }
    }
    */

    //opens up the servos if the a button on gamepad2 is held
    public void spinServo() {
        if (gamepad2.a) {
            leftServo.setPosition(0.25);
            rightServo.setPosition(0.25);
        } else {
            leftServo.setPosition(0);
            rightServo.setPosition(0);
        }
    }
}