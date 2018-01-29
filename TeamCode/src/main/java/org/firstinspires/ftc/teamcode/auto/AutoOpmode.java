package org.firstinspires.ftc.teamcode.auto;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.bots.Shortround;

/**
 * Created by d3mon on 11/18/2017.
 */

//Working Autonmous that was used in the competition
//The slow down throttle does not work
@Autonomous(name = "AutoOpmode", group = "Linear Opmode")
//@Disabled
public class AutoOpmode extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private Shortround bot = new Shortround(hardwareMap);

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        //Configures the motors/servos and sets the distance

        waitForStart();
        runtime.reset();

        clampServo(0.3);
        driveForward(0.25);
        sleep(1250);

        requestOpModeStop();
    }
    public void driveForward(double power) {
        bot.driveTrain.setLeftPower(power);
        bot.driveTrain.setRightPower(power);
        //sleep(time);
    }

    public void clampServo(double pos) {
        bot.grabbers.servos[0].setPosition(pos);
        bot.grabbers.servos[1].setPosition(pos);
    }


}
