
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by 20Seethalla on 11/16/2017.
 */

/*
** Used to test SlowDown and freeze Arm methods
*/
//SlowDown function not working
@TeleOp(name = "TeleOpMode", group = "Iterative Opmode")
public class TeleOpMode extends OpMode {
    //Declares motors, servo, runtime counter and variables
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDrive, rightDrive, leftArm, rightArm;
    private Servo leftServo, rightServo, sideServo;
    private ColorSensor cSensor = null;
    Util util = new Util(gamepad1, gamepad2, leftDrive, rightDrive, leftArm, rightArm, leftServo, rightServo);

    //int startPos = (leftArm.getCurrentPosition() + rightArm.getCurrentPosition()) / 2;


    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        //Configures the motors
        leftDrive = configMotor("leftDrive");
        rightDrive = configMotor("rightDrive");
        leftArm = configMotor("leftArm");
        rightArm = configMotor("rightArm");
        leftServo = hardwareMap.get(Servo.class, "leftServo");
        rightServo = hardwareMap.get(Servo.class, "rightServo");
        sideServo = hardwareMap.get(Servo.class, "sideServo");
        cSensor = hardwareMap.get(ColorSensor.class, "colorSensor");

        //Sets the direction of all motors and servos
        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        rightArm.setDirection(DcMotor.Direction.FORWARD);
        leftArm.setDirection(DcMotor.Direction.REVERSE);
        leftServo.setDirection(Servo.Direction.REVERSE);
        rightServo.setDirection(Servo.Direction.FORWARD);
        sideServo.setDirection(Servo.Direction.FORWARD);

        //leftArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //rightArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rightArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }

    //runs REPEATEDLY after the driver hits INIT, but before they hit PLAY
    @Override
    public void init_loop() {
    }

    //runs  ONCE when the driver hits PLAY
    @Override
    public void start() {
        runtime.reset();
        leftServo.setPosition(0.2);
        rightServo.setPosition(0.2);
        cSensor.enableLed(true);
        rightArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }


    int startPos = rightArm.getCurrentPosition();
    int lowPos = 10, highPos = 35, tarPos;
    //runs REPEATEDLY after the driver hits PLAY but before they hit STOP
    @Override
    public void loop() {
        try {
            setEncoderValues();
        }
        catch (Exception e)
        {
            telemetry.addData("ERROR!!! Exception: ", e.getMessage());
            telemetry.addData("Stack Trace: ", e.getStackTrace());
        }

        //Sets up power for Tank Drive
        double leftPower = (-gamepad1.left_stick_y / 1.25);
        double rightPower = (-gamepad1.right_stick_y / 1.25);
        double servoPower = -gamepad2.right_stick_y; //Side servo control


        leftDrive.setPower(leftPower);
        rightDrive.setPower(rightPower);
        sideServo.setPosition(servoPower);

        try {
            //Uses the joystick for gamepad2 for the arm
            runArm();
        }
        catch (Exception e)
        {
            telemetry.addData("ERROR!!! Exception: ", e.getMessage());
            telemetry.addData("Stack Trace: ", e.getStackTrace());
        }
        finally {
            util.spinServo(); //runs the servo method
        }

        int position = rightArm.getCurrentPosition();
        telemetry.addData("Encoder Position", position);

        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
        telemetry.addData("Color Number", cSensor.argb());
        //telemetry.addData("Color Number", cSensor.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER));
    }


    //runs ONCE after the driver hits STOP
    @Override
    public void stop() {
    }

    public DcMotor configMotor(String motor) {
        return hardwareMap.get(DcMotor.class, motor);
    }


    //Override of util.brakeArm
    public void stopArm()
    {
        rightArm.setTargetPosition(tarPos);
        telemetry.addData("Motors", "Arm should move to position and freeze");
    }



    public void runArm() {
        //int currentPos = (leftArm.getCurrentPosition() + rightArm.getCurrentPosition()) / 2;
        if ((-gamepad2.left_stick_y == 0) || (leftArm.getCurrentPosition() == startPos))
        { //|| (startPos == currentPos)) {
           stopArm();
        }
        else if(-gamepad2.left_stick_y < 0)
        {
            util.setArmPower(-gamepad2.left_stick_y / 8);
        }
        else
           util.setArmPower(((-gamepad2.left_stick_y) / 4));
    }

    //Sets values for the arm to hold at if controller stick is neutral
    public void setEncoderValues()
    {
        if(gamepad2.x)
            tarPos = lowPos;
        else if(gamepad2.y)
            tarPos = highPos;
        else if(gamepad2.right_bumper)
            tarPos = startPos;
    }
}