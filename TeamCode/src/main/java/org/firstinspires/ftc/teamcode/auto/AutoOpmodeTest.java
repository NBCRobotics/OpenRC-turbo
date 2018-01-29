package org.firstinspires.ftc.teamcode.auto;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.bots.Shortround;


//Test Autonomous Opmode to place glyph in the Crytobox
@Autonomous(name = "AutoOpmodeTest", group = "Linear Opmode")
@Disabled
public class AutoOpmodeTest extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private Shortround bot = new Shortround(hardwareMap);
    private ColorSensor revSensorColor = null; // floor sensor is rev CDS

    private ColorSensor sideSensor = null; //side sensor is MR color

    // side color sensor raw
    private float sideHsvValues[] = {0F, 0F, 0F};

    // rev color sensor raw
    private float revHsvValues[] = {0F, 0f, 0F};

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        //Configures the motors/servos and sets the ditance
        revSensorColor = hardwareMap.get(ColorSensor.class, "floorSensor");
        sideSensor = hardwareMap.get(ColorSensor.class, "sideSensor");

        waitForStart();
        runtime.reset();

        /**
         * Start Autonomous
         */
        revSensorColor.enableLed(true);
        sideSensor.enableLed(true);

        moveArm(1);
        getColor();
        knockOffJewel();
        if (sideHsvValues == revHsvValues) {
            driveForwardForTime(1, 2000, 0);
        }
        moveArm(0);
        bot.grabbers.clampGrabbers();
        sleep(100);
        liftArm(250);
        sleep(250);
        driveForward(0.25);
        sleep(1300);

        requestOpModeStop();

    }

    public DcMotor configMotor(String motor) {
        return hardwareMap.get(DcMotor.class, motor);
    }

    public void driveForward(double power) {
        bot.driveTrain.setLeftPower(power);
        bot.driveTrain.setRightPower(power);
        //sleep(time);
    }


    public void liftArm(long time) {
        bot.driveTrain.setLeftPower(0.25);
        bot.driveTrain.setRightPower(0.25);
        sleep(time);
        breakArm();
    }

    public void breakArm() {
        bot.lift.setPower(0);
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

        moveArm(0);
    }

    public void moveArm(double pos) {
        bot.jewelArm.servo.setPosition(pos);
    }

    public void driveForwardForTime(double power, long millis, int nanos) {
        bot.driveTrain.setLeftPower(power);
        bot.driveTrain.setRightPower(power);

        try {
            Thread.sleep(millis, nanos);
        } catch (Exception e) {
            telemetry.addData("ERROR!", "InterruptedException");
            telemetry.addData("Stack Trace:", e.getStackTrace());
            telemetry.update();
            requestOpModeStop();
        } finally {
            bot.driveTrain.setLeftPower(0);
            bot.driveTrain.setRightPower(0);
        }
    }

    public void driveBackwardsForTime(double power, long millis, int nanos) {
        bot.driveTrain.setLeftPower(-power);
        bot.driveTrain.setRightPower(-power);

        try {
            Thread.sleep(millis, nanos);
        } catch (Exception e) {
            telemetry.addData("ERROR!", "InterruptedException");
            telemetry.addData("Stack Trace:", e.getStackTrace());
            telemetry.update();
            requestOpModeStop();
        } finally {
            bot.driveTrain.setLeftPower(0);
            bot.driveTrain.setRightPower(0);
        }
    }

}
