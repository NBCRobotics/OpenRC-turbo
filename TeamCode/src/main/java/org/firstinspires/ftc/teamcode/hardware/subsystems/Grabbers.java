package org.firstinspires.ftc.teamcode.hardware.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by 20Avva on 1/26/2018.
 */

public class Grabbers {
    
    // 0 - left
    // 1 - Right
    public Servo[] servos = new Servo[2];
    public double positionOpen[] = new double[2];
    public double positionClose[] = new double[2];

    public Grabbers(HardwareMap hwd, String servoNames[], double openPos[], double closePos[], boolean reversed) {
        for (int i = 0; i < servos.length; i++) {
            servos[i] = hwd.servo.get(servoNames[i]);
        }

        this.positionOpen = openPos;
        this.positionClose = closePos;
        
        if (reversed) {
            servos[0].setDirection(Servo.Direction.REVERSE);
            servos[1].setDirection(Servo.Direction.FORWARD);
        } else {
            servos[0].setDirection(Servo.Direction.FORWARD);
            servos[1].setDirection(Servo.Direction.REVERSE);
        }
    }

    public void openGrabbers(){
        for(int i=0;i<servos.length;i++){
            servos[i].setPosition(positionOpen[i]);
        }
    }

    public void closeGrabbers(){
        for(int i=0;i<servos.length;i++){
            servos[i].setPosition(positionClose[i]);
        }
    }

    public void clampGrabbers() {
        for (int i = 0;i<servos.length;i++) {
            servos[i].setPosition(0.3);
        }
    }
}
