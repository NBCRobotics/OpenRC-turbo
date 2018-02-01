package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by KasaiYuki on 1/17/2018.
 */

public class Util
{
    //Global Variables for all classes
    public static final double SERVO_ROT = 0.3;

    public Gamepad gamepad1, gamepad2;
    public DcMotor leftDrive, rightDrive, leftArm, rightArm;
    public Servo leftServo, rightServo, sideServo;

    public Util(Gamepad gp1, Gamepad gp2, DcMotor mot1, DcMotor mot2, DcMotor arm1, DcMotor arm2, Servo servo1, Servo servo2)
    {
        this.gamepad1 = gp1;
        this.gamepad2 = gp2;
        this.leftDrive = mot1;
        this.rightDrive = mot2;
        this.leftArm = arm1;
        this.rightArm = arm2;
        this.leftServo = servo1;
        this.rightServo = servo2;
    }

    //Second Constructor for autonomous with sideServo
    public Util(Gamepad gp1, Gamepad gp2, DcMotor mot1, DcMotor mot2, DcMotor arm1, DcMotor arm2, Servo servo1, Servo servo2, Servo sideServo)
    {
        this.gamepad1 = gp1;
        this.gamepad2 = gp2;
        this.leftDrive = mot1;
        this.rightDrive = mot2;
        this.leftArm = arm1;
        this.rightArm = arm2;
        this.leftServo = servo1;
        this.rightServo = servo2;
        this.sideServo = sideServo;
    }

    public boolean overClock()
    {
        return (gamepad2.left_bumper && gamepad2.a);
    }

    public boolean overDrive()
    {
        return gamepad2.b;
    }

    public void setArmPower(float power) {
        leftArm.setPower(power);
        rightArm.setPower(power);
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

    public void brakeArm() {
        this.setArmPower(0);
    }

    //OVERLOADED
    public void brakeArm(DcMotor motor, int pos)
    {
        motor.setTargetPosition(pos);
    }


    public void moveSideArm(double pos)
    {
        sideServo.setPosition(pos);
    }

    //opens up the servos if the a button on gamepad2 is held
    public void spinServo() {
        if (gamepad2.a) {
            leftServo.setPosition(Util.SERVO_ROT);
            rightServo.setPosition(Util.SERVO_ROT);
        } else {
            leftServo.setPosition(0);
            rightServo.setPosition(0);
        }
    }

    //OVERLOADED
    public void spinServo(double pos)
    {
        leftServo.setPosition(pos);
        rightServo.setPosition(pos);
    }


}
