package org.firstinspires.ftc.teamcode.auto;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.detectors.*;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.io.IOException;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
// import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.ClosableVuforiaLocalizer;
import org.firstinspires.ftc.teamcode.hardware.bots.Shortround;
//import org.firstinspires.ftc.teamcode.BuildConfig;

@Disabled
@Autonomous(name = "Jewel and Vuforia Op Mode", group = "DogeCV+Vuforia")
public class JewelAndVuforiaOpMode extends OpMode {
    // Declare OpMode members.
    private ElapsedTime   runtime       = new ElapsedTime();
    private Shortround    bot           = null;
    private JewelDetector jewelDetector = null;
    private ClosableVuforiaLocalizer vuforia = null;
    private VuforiaLocalizer.Parameters parameters = null;
    private VuforiaTrackables relicTrackables = null;
    private RelicRecoveryVuMark vuMark = null;
    private VuforiaTrackable relicTemplate = null;
    
    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        jewelDetector = new JewelDetector();
        jewelDetector.init(hardwareMap.appContext, CameraViewDisplay.getInstance());

        bot = new Shortround(hardwareMap);

        //Jewel Detector Settings
        jewelDetector.areaWeight = 0.02;
        jewelDetector.detectionMode = JewelDetector.JewelDetectionMode.MAX_AREA; // PERFECT_AREA
        //jewelDetector.perfectArea = 6500; <- Needed for PERFECT_AREA
        jewelDetector.debugContours = true;
        jewelDetector.maxDiffrence = 15;
        jewelDetector.ratioWeight = 15;
        jewelDetector.minArea = 700;

        jewelDetector.enable();
        
        
        int cameraMonitorViewId =
                                  hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id",
                                                                                      hardwareMap.appContext.getPackageName());
        parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        
        //parameters.vuforiaLicenseKey = BuildConfig.VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = new ClosableVuforiaLocalizer(parameters);
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);

    }

    @Override
    public void init_loop() {
        telemetry.addData("Status", "Initialized.");
        String jewelOrder = jewelDetector.getCurrentOrder().toString();
        telemetry.addData("Current order", jewelOrder.toString());
    }

    @Override
    public void start() {
        runtime.reset();
        jewelDetector.disable();
        
        relicTemplate = relicTrackables.get(0);
        vuMark = RelicRecoveryVuMark.from(relicTemplate);
        
    }

    @Override
    public void loop() {
        relicTrackables.activate();
        
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
                try {
                    doTheRoutine(vuMark);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                requestOpModeStop();
            }
            
            telemetry.update();
    }

    @Override
    public void stop() {
        
    }
    
    private void setMotorPower(double power) {
        bot.driveTrain.setLeftPower(power);
        bot.driveTrain.setRightPower(power);
    }

    private void driveToPosition(RelicRecoveryVuMark position) throws InterruptedException{
        switch (position) {
            case LEFT:
                setMotorPower(1);
                Thread.sleep(3500);
                setMotorPower(0);
                break;

            case CENTER:
                setMotorPower(1);
                Thread.sleep(3000);
                setMotorPower(0);
                break;

            case RIGHT:
                setMotorPower(1);
                Thread.sleep(2500);
                setMotorPower(0);
                break;

            case UNKNOWN:
                setMotorPower(1);
                Thread.sleep(3000);
                setMotorPower(0);
                break;

        }
    }

    private void turnRight() throws InterruptedException {
        bot.driveTrain.setLeftPower(1);
        bot.driveTrain.setRightPower(-1);
        Thread.sleep(250);
        bot.driveTrain.stopMotors();
    }

    private void placeGlyphInBox() throws InterruptedException {
        bot.driveTrain.setLeftPower(1);
        bot.driveTrain.setRightPower(1);
        Thread.sleep(250);
        bot.driveTrain.stopMotors();
    }

    private void getOutOfBox() throws InterruptedException {
        bot.driveTrain.setLeftPower(-1);
        bot.driveTrain.setRightPower(1);
        Thread.sleep(100);
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
