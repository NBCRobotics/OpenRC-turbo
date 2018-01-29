package org.firstinspires.ftc.teamcode.tele;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.lib.auto.OpenFTCAutoOpMode;
import org.firstinspires.ftc.teamcode.lib.control.Controller;


/**
 * Created by pranav on 1/27/2018.
 */

@TeleOp(name="MainTeleOp", group="OpenFTCOpMode")
public class MainTeleOp extends OpenFTCAutoOpMode {

    private double speed = 1.0;

    private Controller controller1;
    private Controller controller2;

    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();

        while (opModeIsActive()) {
            controller1.Update();
            controller2.Update();

            // Slow Down Function
            slowDown();

            bot.driveTrain.setLeftPower(gamepad1.left_stick_y * speed);
            bot.driveTrain.setRightPower(gamepad1.right_stick_y * speed);

            if(controller2.YState == Controller.ButtonState.JUST_PRESSED){
                bot.jewelArm.armUp();
            }

            if(controller2.XState == Controller.ButtonState.JUST_PRESSED){
                bot.jewelArm.armDown();
            }

            if(controller2.AState == Controller.ButtonState.JUST_PRESSED){
                bot.grabbers.closeGrabbers();
            }

            if(controller2.AState == Controller.ButtonState.JUST_RELEASED){
                bot.grabbers.openGrabbers();
            }

            bot.lift.setPower(gamepad2.left_stick_y);
        }
    }

    private void slowDown() {
        if (gamepad1.left_bumper) {
            speed = 0.5;
        } else if (gamepad1.right_bumper) {
            speed = 0.25;
        } else {
            speed = 1;
        }
    }
}
