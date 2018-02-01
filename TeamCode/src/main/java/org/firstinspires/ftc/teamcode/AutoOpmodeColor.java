package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by d3mon on 11/18/2017.
 */


//Test Autonomous Opmode to place glyph in the Crytobox
@Autonomous(name = "AutoOpmodeColor", group = "Linear Opmode")
//@Disabled
public class AutoOpmodeColor extends LinearOpMode
{
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDrive, rightDrive, leftArm, rightArm;
    private Servo leftServo, rightServo, sideServo;
    private ColorSensor revSensorColor = null; // floor sensor is rev CDS

    private ColorSensor sideSensor = null; //side sensor is MR color

    // side color sensor raw
    private float sideHsvValues[] = {0F, 0F, 0F};

    // rev color sensor raw
    private float revHsvValues[] = {0F, 0f, 0F};

    Util util = new Util(gamepad1, gamepad2, leftDrive, rightDrive, leftArm, rightArm, leftServo, rightServo, sideServo);

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        //Configures the motors/servos and sets the ditance
        leftDrive = configMotor("leftDrive");
        rightDrive = configMotor("rightDrive");
        leftArm = configMotor("leftArm");
        rightArm = configMotor("rightArm");

        leftServo = hardwareMap.get(Servo.class, "leftServo");
        rightServo = hardwareMap.get(Servo.class, "rightServo");
        sideServo = hardwareMap.get(Servo.class, "siderServo");
        revSensorColor = hardwareMap.get(ColorSensor.class, "floorSensor");
        sideSensor = hardwareMap.get(ColorSensor.class, "sideSensor");

        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        rightArm.setDirection(DcMotor.Direction.FORWARD);
        leftArm.setDirection(DcMotor.Direction.REVERSE);
        leftServo.setDirection(Servo.Direction.REVERSE);
        rightServo.setDirection(Servo.Direction.FORWARD);

        waitForStart();
        runtime.reset();

        /**
         * Start Autonomous
         */
        revSensorColor.enableLed(true);
        sideSensor.enableLed(true);

        util.moveSideArm(1);
        getColor();
        knockOffJewel();
        if (sideHsvValues == revHsvValues) {
            driveForwardForTime(1, 2000, 0);
        }
        util.moveSideArm(0);
        util.clampServo();
        sleep(100);
        liftArm(250);
        sleep(250);
        util.driveForward(0.25);
        sleep(1300);

        requestOpModeStop();

    }
    public DcMotor configMotor(String motor) {
        return hardwareMap.get(DcMotor.class, motor);
    }

    public void liftArm(long time)
    {
        leftArm.setPower(0.25);
        rightArm.setPower(0.25);
        sleep(time);
        util.brakeArm();
    }


    public void getColor() {
        Color.RGBToHSV(sideSensor.red() * 8, sideSensor.green() * 8, sideSensor.blue() * 8, sideHsvValues);
        Color.RGBToHSV(revSensorColor.red() * 8, revSensorColor.green() * 8, revSensorColor.blue() * 8, revHsvValues);
    }

    public void knockOffJewel() {
        if (sideHsvValues == revHsvValues) {
            driveBackwardsForTime(1, 1000, 0);
        } else {
            driveForwardForTime(1, 1000, 0);
        }

        util.moveSideArm(0);
    }


    public void driveForwardForTime(double power, long millis, int nanos) {
        leftDrive.setPower(power);
        rightDrive.setPower(power);

        try {
            Thread.sleep(millis, nanos);
        } catch (Exception e) {
            telemetry.addData("ERROR!", "InterruptedException");
            telemetry.addData("Stack Trace:", e.getStackTrace());
            telemetry.update();
            requestOpModeStop();
        } finally {
            leftDrive.setPower(0);
            rightDrive.setPower(0);
        }
    }

    public void driveBackwardsForTime(double power, long millis, int nanos) {
        leftDrive.setPower(-power);
        rightDrive.setPower(-power);

        try {
            Thread.sleep(millis, nanos);
        } catch (Exception e) {
            telemetry.addData("ERROR!", "InterruptedException");
            telemetry.addData("Stack Trace:", e.getStackTrace());
            telemetry.update();
            requestOpModeStop();
        } finally {
            leftDrive.setPower(0);
            rightDrive.setPower(0);
        }
    }

}
