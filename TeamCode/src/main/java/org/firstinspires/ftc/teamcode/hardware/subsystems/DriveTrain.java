package org.firstinspires.ftc.teamcode.hardware.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by 20Avva on 1/26/2018.
 */

public class DriveTrain {

    // 0 = Left
    // 1 = Right
    public DcMotor dcMotors[] = new DcMotor[2];

    private Telemetry telemetry;

    public DriveTrain(HardwareMap hwd, Telemetry tel, String motorNames[], boolean reverse){
        for(int i = 0; i< dcMotors.length; i++){
            dcMotors[i] = hwd.dcMotor.get(motorNames[i]);
        }
        if(!reverse){
            dcMotors[2].setDirection(DcMotorSimple.Direction.REVERSE); // Set FrontRight
            dcMotors[3].setDirection(DcMotorSimple.Direction.REVERSE); // Set BottomRight
        }else{
            dcMotors[0].setDirection(DcMotorSimple.Direction.REVERSE); // Set FrontLeft
            dcMotors[1].setDirection(DcMotorSimple.Direction.REVERSE); // Set FrontRight
        }

        telemetry = tel;

        setMotorBreak(DcMotor.ZeroPowerBehavior.BRAKE);
        setMotorModes(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public boolean isMotorBusy(){
        boolean isBusy = false;
        for (DcMotor _motor : dcMotors){
            if(_motor.isBusy()){
                isBusy = true;
            }
        }

        return isBusy;
    }

    public void setMotorModes(DcMotor.RunMode mode){
        for (DcMotor _motor : dcMotors){
            _motor.setMode(mode);
        }
    }

    public void setMotorBreak(DcMotor.ZeroPowerBehavior mode){
        for (DcMotor _motor : dcMotors){
            _motor.setZeroPowerBehavior(mode);
        }
    }

    public void setLeftPower(double power){
        dcMotors[0].setPower(power);
    }

    public void setRightPower(double power){
        dcMotors[1].setPower(power);
    }
    
    public void stopMotors() {
        for (DcMotor _motor : dcMotors) {
            _motor.setPower(0);
        }
    }
}
