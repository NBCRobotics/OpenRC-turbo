package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.*;

/**
 * Created by 20Seethalla on 11/16/2017.
 */

@TeleOp(name = "TestTele", group = "Iterative Opmode")

public class TestTele extends OpMode {
    //Declares motors, servo, runtime counter and variables
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDrive, rightDrive, leftArm, rightArm;
    private Servo leftServo, rightServo;
    Util util = new Util(gamepad1, gamepad2, leftDrive, rightDrive, leftArm, rightArm, leftServo, rightServo);

    //runs ONCE when the driver hits INIT
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        //Configures the motors
        leftDrive = configure("leftDrive");
        rightDrive = configure("rightDrive");
        leftArm = configure("leftArm");
        rightArm = configure("rightArm");
        leftServo = configure("leftServo", "");
        rightServo = configure("rightServo", "");

        //Sets the direction of all motors and servos
        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        rightArm.setDirection(DcMotor.Direction.FORWARD);
        leftArm.setDirection(DcMotor.Direction.REVERSE);
        leftServo.setDirection(Servo.Direction.REVERSE);
        rightServo.setDirection(Servo.Direction.FORWARD);
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
        double rightPower = (-gamepad1.left_stick_y) / 1.25 ;

        leftDrive.setPower(leftPower);
        rightDrive.setPower(rightPower);

        //Uses the joystick for gamepad2 for the arm
        util.setArmPower((-gamepad2.left_stick_y) / 2);
        servoRun(); //runs the servo method

        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("OVERCLOCKING: ", util.overClock());
        telemetry.addData("OVERDRIVE: ", util.overDrive());
        telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);

    }

    //runs ONCE after the driver hits STOP
    @Override
    public void stop() {

    }

    public DcMotor configure(String motor) {
        return hardwareMap.get(DcMotor.class, motor);
    }

    /**OVERLOADED METHOD:
     *
     * @param servo: Servo to be filled in
     * @param s: used to overload
     * @return maps hardware
     */
    public Servo configure(String servo, String s)
    {
        return hardwareMap.get(Servo.class, servo);
    }



    //opens up the servos if the a button on gamepad2 is held
    public void servoRun() {
        util.spinServo(gamepad2.a ? 0.25 : util.overClock() ? 0.5 : util.overDrive() ? 0.5 : 0);

    }

    /*
    if (gamepad2.a) {
            util.spinServo(0.25);
        }

        else if(Util.overClock(gamepad2))
        {
            util.spinServo(0.5);
        }
        else if(Util.overDrive(gamepad2))
        {
            util.spinServo(0.5);
        }

        else
        {
            util.spinServo(0);
        }
     */


}