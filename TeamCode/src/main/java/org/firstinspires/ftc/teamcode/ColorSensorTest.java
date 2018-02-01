/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/*
 *
 * This is an example LinearOpMode that shows how to use
 * a Modern Robotics Color Sensor.
 *
 * The op mode assumes that the color sensor
 * is configured with a name of "sensor_color".
 *
 * You can use the X button on gamepad1 to toggle the LED on and off.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */
@TeleOp(name = "ColorSensorTest", group = "Sensor")
@Disabled
public class ColorSensorTest extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDrive, rightDrive;
    private Servo leftServo, rightServo, sideServo;
    private ModernRoboticsI2cColorSensor cSensor = null;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        //Configures the motors/servos and sets the ditance
        leftDrive = configMotor("leftDrive");
        rightDrive = configMotor("rightDrive");
        leftServo = hardwareMap.get(Servo.class, "leftServo");
        rightServo = hardwareMap.get(Servo.class, "rightServo");
        sideServo = hardwareMap.get(Servo.class, "sideServo");
        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        leftServo.setDirection(Servo.Direction.REVERSE);
        rightServo.setDirection(Servo.Direction.FORWARD);
        sideServo.setDirection(Servo.Direction.FORWARD);
        cSensor = hardwareMap.get(ModernRoboticsI2cColorSensor.class, "colorSensor");

        waitForStart();
        runtime.reset();

        cSensor.enableLed(true);
        while (opModeIsActive()) {
            telemetry.addData("Color Number", cSensor.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER));
            telemetry.update();
            print("Testing booleans", "");
            int redValue = cSensor.red();
            int greenValue = cSensor.green();
            int blueValue = cSensor.blue();
            boolean isRed = redValue > blueValue && redValue > greenValue;
            boolean isBlue = blueValue > redValue && blueValue > greenValue;
            if(isRed)
                print("Sensing red...", "");
            else if(isBlue)
                print("Sensing blue...", "");
        }

        /*
        clampServo();
        driveForward(0.25);
        sleep(1250);
        sideServo.setPosition(0.5);
        int redValue = cSensor.red();
        int greenValue = cSensor.green();
        int blueValue = cSensor.blue();
        boolean isRed = redValue > blueValue && redValue > greenValue;
        boolean isBlue = blueValue > redValue && blueValue > greenValue;
        if(isRed)
            pushBall(-0.25);
        else if (isBlue)
            pushBall(0.25);
        else
            pushBall(0);
            */
    }

    public DcMotor configMotor(String motor) {
        return hardwareMap.get(DcMotor.class, motor);
    }

    public void driveForward(double power) {
        leftDrive.setPower(power);
        rightDrive.setPower(power);
        //sleep(time);
    }

    public void clampServo() {
        leftServo.setPosition(Util.SERVO_ROT);
        rightServo.setPosition(Util.SERVO_ROT);
    }

    public void pushBall(double direction)
    {
        driveForward(direction);
        sleep(250);
    }

    public void print(String message, String value)
    {
        telemetry.addData(message, value);
        telemetry.update();
    }
}
