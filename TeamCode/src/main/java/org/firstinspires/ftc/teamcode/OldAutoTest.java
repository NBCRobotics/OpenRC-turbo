package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by d3mon on 11/18/2017.
 */


//Test Autonomous Opmode to place glyph in the Crytobox
@Autonomous(name = "OldAutoTest", group = "Linear Opmode")
@Disabled
public class OldAutoTest extends LinearOpMode
{
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDrive, rightDrive, leftArm, rightArm;
    private Servo leftServo, rightServo;

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
        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        rightArm.setDirection(DcMotor.Direction.FORWARD);
        leftArm.setDirection(DcMotor.Direction.REVERSE);
        leftServo.setDirection(Servo.Direction.REVERSE);
        rightServo.setDirection(Servo.Direction.FORWARD);


        waitForStart();
        runtime.reset();

        //Starting Autonomous
        clampServo();
        sleep(100);
        liftArm(250);
        sleep(250);
        driveForward(0.25);
        sleep(1300);

    }
    public DcMotor configMotor(String motor) {
        return hardwareMap.get(DcMotor.class, motor);
    }

    public void driveForward(double power) {
        leftDrive.setPower(power);
        rightDrive.setPower(power);
        //sleep(time);
    }
    public void clampServo()
    {
        leftServo.setPosition(Util.SERVO_ROT);
        rightServo.setPosition(Util.SERVO_ROT);
    }

    public void liftArm(long time)
    {
        leftArm.setPower(0.25);
        rightArm.setPower(0.25);
        sleep(time);
        breakArm();
    }
    public void breakArm()
    {
        leftArm.setPower(0);
        rightArm.setPower(0);
    }
}