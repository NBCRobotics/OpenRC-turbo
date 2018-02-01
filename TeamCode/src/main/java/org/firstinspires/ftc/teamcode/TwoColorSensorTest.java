
package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@SuppressWarnings("unused")
@Autonomous(name = "TwoColorSensorTest", group = "Linear Opmode")
//@Disabled
public class TwoColorSensorTest extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDrive = null, rightDrive = null;
    private Servo sideServo = null;

    private ColorSensor revSensorColor = null; // floor sensor is rev CDS
    private DistanceSensor revSensorDistance = null; // however both of these sensors share the same name

    private ModernRoboticsI2cColorSensor sideSensor = null; //side sensor is MR color

    // side color sensor raw
    private float sideHsvValues[] = {0F, 0F, 0F};

    // rev color sensor raw
    private float revHsvValues[] = {0F, 0f, 0F};

    private double leftPower = 0;
    private double rightPower = 0;

    @Override
    public void runOpMode() throws InterruptedException{
        telemetry.addData("Status", "Initialized");

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        leftDrive = hardwareMap.get(DcMotor.class, "leftDrive");
        rightDrive = hardwareMap.get(DcMotor.class, "rightDrive");

        // Get a reference to the side arm servo
        sideServo = hardwareMap.get(Servo.class, "sideServo");

        // Get a reference to the REV CDS Sensor.
        // One Reference for the Color
        // One for the distance
        revSensorColor = hardwareMap.get(ColorSensor.class, "floorSensor");
        revSensorDistance = hardwareMap.get(DistanceSensor.class, "floorSensor");

        sideSensor = hardwareMap.get(ModernRoboticsI2cColorSensor.class, "sideSensor");

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();


        // Turn on LEDs
        revSensorColor.enableLed(true);
        sideSensor.enableLed(true);

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Set the Arm down
            armDown();

            // Get the Color
            getColor();

            // Knock off correct Jewel
            knockOffJewel();

            if (sideHsvValues == revHsvValues) {
                driveForwardForTime(1, 2000, 0);
            }

            requestOpModeStop();
        }
    }

    private void armDown() {
        sideServo.setPosition(1);
    }

    private void armUp() {
        sideServo.setPosition(0);
    }

    private void getColor() {
        Color.RGBToHSV(sideSensor.red() * 8, sideSensor.green() * 8, sideSensor.blue() * 8, sideHsvValues);
        Color.RGBToHSV(revSensorColor.red() * 8, revSensorColor.green() * 8, revSensorColor.blue() * 8, revHsvValues);
    }

    private void knockOffJewel() {
        if (sideHsvValues == revHsvValues) {
            driveBackwardsForTime(1, 1000, 0);
        } else {
            driveForwardForTime(1, 1000, 0);
        }

        armUp();
    }

    private void driveForwardForTime(double power, long millis, int nanos) {
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

    private void driveBackwardsForTime(double power, long millis, int nanos) {
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
