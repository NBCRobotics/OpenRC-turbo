package org.firstinspires.ftc.teamcode.hardware.bots;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.subsystems.*;
/**
 * Created by 20Avva on 1/26/2018.
 */

public abstract class BotBase {
    public HardwareMap hardwareMap;

    public double P = 0.1;
    public double I = 0.1;
    public double D = 0.1;
    public double PID_THRESH = 1;

    public DriveTrain driveTrain = null;
    public Grabbers grabbers = null;
    public Lift lift = null;
    public JewelArm jewelArm = null;
    public NavigationHardware navigationHardware = null;
    public DogeCVDetectors detectors = null;

    public BotBase(HardwareMap hwd) {
        hardwareMap = hwd;
    }

    public abstract void Init();
}
