package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
// import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.ClosableVuforiaLocalizer;
import org.firstinspires.ftc.teamcode.hardware.bots.Shortround;
import org.firstinspires.ftc.teamcode.BuildConfig;

@Autonomous(name = "VuforiaOpModeTest", group = "AutoOpMode")
//@Disabled
public class VuforiaOpModeTest extends LinearOpMode {

    // public static final String TAG = "Vuforia VuMark Sample";
    private ElapsedTime              runtime = new ElapsedTime();
    private Shortround               bot     = new Shortround(hardwareMap);
    // private int slowTrig;
    // OpenGLMatrix lastLocation = null;

    private ClosableVuforiaLocalizer vuforia;

    @Override
    public void runOpMode() throws InterruptedException {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        /*
         * To start up Vuforia, tell it the view that we wish to use for camera monitor (on the RC phone);
         * If no camera monitor is desired, use the parameterless constructor instead (commented out below).
         */
        int cameraMonitorViewId =
                                  hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id",
                                                                                      hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        /*
         * OR...  Do Not Activate the Camera Monitor View, to save power
         * VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
         */

        /*
         * License key
         */
        parameters.vuforiaLicenseKey = BuildConfig.VUFORIA_KEY; // References VUFROIA_KEY value in {project.root.dir}/TeamCode/vuforia.properties

        /*
         * Using the back camera
         */
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = new ClosableVuforiaLocalizer(parameters);

        /*
         * Load the data set containing the VuMarks for Relic Recovery. There's only one trackable
         * in this data set: all three of the VuMarks in the game were created from this one template,
         * but differ in their instance id information.
         * @see VuMarkInstanceId
         */
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary

        telemetry.addData(">", "Press Play to start");
        telemetry.update();
        waitForStart();

        relicTrackables.activate();

        runtime.reset();
        while (opModeIsActive()) {

            bot.grabbers.openGrabbers();

            /*
             * See if any of the instances of {@link relicTemplate} are currently visible.
             * {@link RelicRecoveryVuMark} is an enum which can have the following values:
             * UNKNOWN, LEFT, CENTER, and RIGHT. When a VuMark is visible, something other than
             * UNKNOWN will be returned by {@link RelicRecoveryVuMark#from(VuforiaTrackable)}.
             */
            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
            if (vuMark != RelicRecoveryVuMark.UNKNOWN) {

                /* Found an instance of the template. In the actual game, you will probably
                 * loop until this condition occurs, then move on to act accordingly depending
                 * on which VuMark was visible.
                 * Will be one of RelicRecoveryVuMark.LEFT or CENTER or RIGHT or UNKOWN
                 */
                telemetry.addData("VuMark", "%s visible", vuMark);
            } else {
                telemetry.addData("VuMark", "not visible");
            }

            if (vuMark == RelicRecoveryVuMark.LEFT) {
                doTheRoutine(vuMark);
            } else {
                requestOpModeStop();
            }
            
            telemetry.update();

            requestOpModeStop();
        }
    }


    private void setMotorPower(double power) {
        bot.driveTrain.setLeftPower(power);
        bot.driveTrain.setRightPower(power);
    }

    private void driveToPosition(RelicRecoveryVuMark position) {
        switch (position) {
            case LEFT:
                setMotorPower(1);
                sleep(3500);
                setMotorPower(0);
                break;

            case CENTER:
                setMotorPower(1);
                sleep(3000);
                setMotorPower(0);
                break;

            case RIGHT:
                setMotorPower(1);
                sleep(2500);
                setMotorPower(0);
                break;

            case UNKNOWN:
                setMotorPower(1);
                sleep(3000);
                setMotorPower(0);
                break;

        }
    }

    private void turnRight() throws InterruptedException {
        bot.driveTrain.setLeftPower(1);
        bot.driveTrain.setRightPower(-1);
        sleep(250);
        bot.driveTrain.stopMotors();
    }

    private void placeGlyphInBox() throws InterruptedException {
        bot.driveTrain.setLeftPower(1);
        bot.driveTrain.setRightPower(1);
        sleep(250);
        bot.driveTrain.stopMotors();
    }

    private void getOutOfBox() throws InterruptedException {
        bot.driveTrain.setLeftPower(-1);
        bot.driveTrain.setRightPower(1);
        sleep(100);
        bot.driveTrain.stopMotors();
    }

    private void doTheRoutine(RelicRecoveryVuMark vuMark) throws InterruptedException {
        driveToPosition(vuMark);
        turnRight();
        placeGlyphInBox();
        getOutOfBox();
        bot.grabbers.closeGrabbers();
    }

}
