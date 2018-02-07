package org.firstinspires.ftc.teamcode.hardware.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Lift {

    public DcMotor[]  armMotors = new DcMotor[1];

    public Lift(HardwareMap hwd, String[] motorNames, boolean reverse) {
        for (int i = 0; i < armMotors.length; i++) {
            armMotors[i] = hwd.dcMotor.get(motorNames[i]);
            armMotors[i].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            armMotors[i].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

        if (reverse) {
            armMotors[0].setDirection(DcMotor.Direction.REVERSE);
        } else {
            armMotors[0].setDirection(DcMotor.Direction.FORWARD);
        }
    }

    public boolean isMotorBusy() {
        boolean isBusy = false;
        for (DcMotor _motor : armMotors) {
            if (_motor.isBusy()) {
                isBusy = true;
            }
        }

        return isBusy;
    }

    public void setMotorModes(DcMotor.RunMode mode) {
        for (DcMotor _motor : armMotors) {
            _motor.setMode(mode);
        }
    }

    public void setMotorBreak(DcMotor.ZeroPowerBehavior mode) {
        for (DcMotor _motor : armMotors) {
            _motor.setZeroPowerBehavior(mode);
        }
    }

    public void setPower(double power) {
        for (DcMotor _motor : armMotors) {
            _motor.setPower(power);
        }
    }

}
