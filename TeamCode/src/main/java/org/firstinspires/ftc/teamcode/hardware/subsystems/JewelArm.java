package org.firstinspires.ftc.teamcode.hardware.subsystems;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by 20Avva on 1/26/2018.
 */

public class JewelArm {
    public Servo servo;
    public String name;
    public double UP_POS;
    public double DOWN_POS;

    public float[] hsvValues = new float[3];

    public JewelArm(HardwareMap hwd, String servoName, double upPosition, double downPosition) {
        servo = hwd.servo.get(name);

        UP_POS = upPosition;
        DOWN_POS = downPosition;
    }

    public void armUp() {
        servo.setPosition(UP_POS);
    }

    public void armDown() {
        servo.setPosition(DOWN_POS);
    }
}
