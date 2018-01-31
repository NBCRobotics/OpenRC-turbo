package org.firstinspires.ftc.teamcode.hardware.subsystems;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DriveTrain {

    // 0 = Left
    // 1 = Right
    public DcMotor    dcMotors[] = new DcMotor[2];

    public ColorSensor colorSensor;

    public float[] hsvValues = new float[3];

    public DriveTrain(HardwareMap hwd, String motorNames[], String colorSensorName, boolean reverse) {
        for (int i = 0; i < dcMotors.length; i++) {
            dcMotors[i] = hwd.dcMotor.get(motorNames[i]);
        }
        if (reverse) {
            dcMotors[0].setDirection(DcMotorSimple.Direction.FORWARD);
            dcMotors[1].setDirection(DcMotorSimple.Direction.REVERSE);
        } else {
            dcMotors[0].setDirection(DcMotorSimple.Direction.REVERSE);
            dcMotors[1].setDirection(DcMotorSimple.Direction.FORWARD);
        }

        setMotorBreak(DcMotor.ZeroPowerBehavior.BRAKE);
        setMotorModes(DcMotor.RunMode.RUN_USING_ENCODER);

        colorSensor = hwd.colorSensor.get(colorSensorName);
    }

    public boolean isMotorBusy() {
        boolean isBusy = false;
        for (DcMotor _motor : dcMotors) {
            if (_motor.isBusy()) {
                isBusy = true;
            }
        }

        return isBusy;
    }

    public void setMotorModes(DcMotor.RunMode mode) {
        for (DcMotor _motor : dcMotors) {
            _motor.setMode(mode);
        }
    }

    public void setMotorBreak(DcMotor.ZeroPowerBehavior mode) {
        for (DcMotor _motor : dcMotors) {
            _motor.setZeroPowerBehavior(mode);
        }
    }

    public void setLeftPower(double power) {
        dcMotors[0].setPower(power);
    }

    public void setRightPower(double power) {
        dcMotors[1].setPower(power);
    }

    public void stopMotors() {
        for (DcMotor _motor : dcMotors) {
            _motor.setPower(0);
        }
    }

    public void lightOn() {
        colorSensor.enableLed(true);
    }

    public void lightOff() {
        colorSensor.enableLed(false);
    }

    public int getColor() {
        Color.RGBToHSV(colorSensor.red() * 8, colorSensor.green() * 8, colorSensor.blue() * 8, hsvValues);

        return Color.HSVToColor(hsvValues);
    }
}
