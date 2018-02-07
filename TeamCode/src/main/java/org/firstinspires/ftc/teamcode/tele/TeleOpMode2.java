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
@TeleOp(name = "TeleOpMode2", group = "Iterative Opmode")
public class TeleOpMode2 extends OpMode {
    //Declares motors, servo, runtime counter and variables
    private ElapsedTime runtime = new ElapsedTime();
    private Shortround bot = new Shortround(hardwareMap);
    int lowPos = 10, highPos = 25, tarPos = 0;
    int startPos = 0;
    private String message = "No Height Set";

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
        bot.lift.setMotorModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bot.lift.setMotorModes(DcMotor.RunMode.RUN_TO_POSITION);
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
            //Uses the joystick for gamepad2 for the arm
            runArm();
        } catch (Exception e) {
            telemetry.addData("ERROR in runArm!!! Exception: ", e.getMessage());
            telemetry.addData("Stack Trace: ", e.getStackTrace());
        }
        try {
            spinServo(); //runs the servo method
        } catch (Exception e) {
            telemetry.addData("ERROR in spinServo!!! Exception: ", e.getMessage());
            telemetry.addData("Stack Trace: ", e.getStackTrace());
        }

        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
        telemetry.addData("Set Position: ", message);
        telemetry.addData("Current Position", bot.lift.armMotors[1].getCurrentPosition());
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

    public void stopArm() {
        try {
            setEncoderValues();
        } catch (Exception e) {
            telemetry.addData("ERROR!!! Exception in setEncoderValues: ", e.getMessage());
            telemetry.addData("Stack Trace: ", e.getStackTrace());
        }
        (bot.lift.armMotors[1].setTargetPosition(tarPos);
        telemetry.addData("Motors: ", "Arm should move to position and freeze");
    }

    public void runArm() {
        //int currentPos = (leftArm.getCurrentPosition() + rightArm.getCurrentPosition()) / 2;
        if (-gamepad2.left_stick_y == 0 || bot.lift.armMotors[1].getCurrentPosition() == startPos) { //|| (startPos == currentPos)) {
            stopArm();
        } else
            armPower(((-gamepad2.left_stick_y) / 4));
    }

    //opens up the servos if the a button on gamepad2 is held
    public void spinServo() {
        if (gamepad2.a) {
            bot.grabbers.openGrabbers();
        } else if(gamepad2.b) {
            bot.grabbers.clampGrabbers(0.5);
        } else {
            bot.grabbers.closeGrabbers();
        }
    }

    public void setStartPos() {
        try {
            startPos = bot.lift.armMotors[1].getCurrentPosition();
        } catch (NullPointerException n) {
            telemetry.addData("NULL POINTER EXCEPTION in setStartPos: ", n.getMessage());
            telemetry.addData("Stack Trace: ", n.getStackTrace());
        } catch (Exception e) {
            telemetry.addData("ERROR!!! Exception in setStartPos: ", e.getMessage());
            telemetry.addData("Stack Trace: ", e.getStackTrace());
        }
    }

    private void setEncoderValues() {
        if (gamepad2.x) {
            tarPos = lowPos;
            message = "Low Position";
        } else if (gamepad2.y) {
            tarPos = highPos;
            message = "High Position";
        } else if (gamepad2.right_bumper) {
            tarPos = startPos;
            message = "Starting Position";
        }
    }
}