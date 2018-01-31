package org.firstinspires.ftc.teamcode.hardware.bots;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.subsystems.*;
import org.firstinspires.ftc.teamcode.hardware.sensors.IMU;

/**
 * Created by 20Avva on 1/26/2018.
 */

public class Shortround extends BotBase {

    private String leftDriveMotor = "leftDrive";
    private String rightDriveMotor = "rightDrive";
    private String[] driveMotorNames = new String[]{leftDriveMotor, rightDriveMotor};
    
    private String leftGrabber = "rightServo";
    private String rightGrabber = "rightServo";
    private String[] grabberNames = new String[]{leftGrabber, rightGrabber};
    
    private String rightArmMotor = "rightMotor";
    private String[] armMotorNames = new String[]{rightArmMotor};
    
    private double GRABBER_LEFT_OPEN = 0.25;
    private double GRABBER_RIGHT_OPEN = 0.25;
    private double[] grabberOpenPositions = new double[]{GRABBER_LEFT_OPEN, GRABBER_RIGHT_OPEN};
    
    private double GRABBER_LEFT_CLOSED = 0.00;
    private double GRABBER_RIGHT_CLOSED = 0.00;
    private double[] grabberClosedPositions = new double[]{GRABBER_LEFT_CLOSED, GRABBER_RIGHT_CLOSED};
    
    private String jewelArmName = "sideServo";
    private double JEWEL_UP = 0.00;
    private double JEWEL_DOWN = 0.00;
    
    private String imuName = "imu";
    
    public Shortround(HardwareMap hwd) {
        super(hwd);
        P = 0.5;
        I = 0;
        D = 0.9;
        
        PID_THRESH = 1;
        
        driveTrain = new DriveTrain(hardwareMap, driveMotorNames, "colorSensor", false);
        grabbers = new Grabbers(hardwareMap, grabberNames, grabberOpenPositions, grabberClosedPositions, false);
        jewelArm = new JewelArm(hardwareMap, jewelArmName, JEWEL_UP, JEWEL_DOWN);
        lift = new Lift(hardwareMap, armMotorNames, false);
        navigationHardware = new IMU(hwd, imuName);
    }


    @Override
    public void Init() {

    }
}
