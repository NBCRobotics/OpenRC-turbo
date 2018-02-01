package org.firstinspires.ftc.teamcode;

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

@Autonomous(name = "Concept: VuMark Id", group = "Concept")
//@Disabled
public class VuforiaOpMode extends LinearOpMode {

    // public static final String TAG = "Vuforia VuMark Sample";
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDrive, rightDrive, leftArm, rightArm;
    private Servo leftServo, rightServo;
    // private int slowTrig;
    // OpenGLMatrix lastLocation = null;

    private ClosableVuforiaLocalizer vuforia;

    @Override
    public void runOpMode() throws InterruptedException {

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        //Configures the motors
        leftDrive = configMotor("leftDrive");
        rightDrive = configMotor("rightDrive");
        leftArm = configMotor("leftArm");
        rightArm = configMotor("rightArm");
        leftServo = hardwareMap.get(Servo.class, "leftServo");
        rightServo = hardwareMap.get(Servo.class, "rightServo");

        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        rightArm.setDirection(DcMotor.Direction.FORWARD);
        leftArm.setDirection(DcMotor.Direction.REVERSE);
        leftServo.setDirection(Servo.Direction.REVERSE);
        rightServo.setDirection(Servo.Direction.FORWARD);

        /*
         * To start up Vuforia, tell it the view that we wish to use for camera monitor (on the RC phone);
         * If no camera monitor is desired, use the parameterless constructor instead (commented out below).
         */
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        /*
         * OR...  Do Not Activate the Camera Monitor View, to save power
         * VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        */

        /*
         * License key
         */
        parameters.vuforiaLicenseKey = "AYCFV6H/////AAAAmekk6YIrl0eXiv6v/NSJkjV9eTSHNlS21VtbOvMpT/DU+eSVKRogBQ23cQ+qHItgdLdAyG0XIKpVWBewwHog581BgoBEyhLGzhnxI/57o+CYFi372QAb8faGRN/tE22Pm9BTinccijuCDITIS/9W4mQeUOGOMIC5rB76NZPeNT20Oj65AaG2s5N90hvh2+5xeQ4nhW3w34eez9C3tmO8A9ErqPG+CfDgKPhGZmI7SkAGvUlfzQDFvxPNeK8nYpD3ZnBYq+jytcTR5ch9MjrE0Oqbp5m+RnUIDNC7fP/4JPZ8l5i4JP6dvF1MAhpeJcAU2dIP7umddnO1M/mOOCZNwBD1o1qUMWjXSkvtBFtTstNl";

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

            setServoPos(0.25);

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
            } else if (vuMark == RelicRecoveryVuMark.CENTER) {
                doTheRoutine(vuMark);
            } else if (vuMark == RelicRecoveryVuMark.RIGHT) {
                doTheRoutine(vuMark);
            } else {
                requestOpModeStop();
            }
            telemetry.update();

            requestOpModeStop();
        }
    }
    /*
        String format(OpenGLMatrix transformationMatrix) {
            return (transformationMatrix != null) ? transformationMatrix.formatAsTransform() : "null";
        }
     */

    private DcMotor configMotor(String motor) {
        return hardwareMap.get(DcMotor.class, motor);
    }

    private void setServoPos(double pos) {
        leftServo.setPosition(pos);
        rightServo.setPosition(pos);
    }


    private void setMotorPower(double power) {
        rightDrive.setPower(power);
        leftDrive.setPower(power);
    }

    private void driveToPosition(RelicRecoveryVuMark position) throws InterruptedException {
        if (position == RelicRecoveryVuMark.LEFT) {
            setMotorPower(1);
            Thread.sleep(3500);
            setMotorPower(0);
        } else if (position == RelicRecoveryVuMark.CENTER) {
            setMotorPower(1);
            Thread.sleep(3000);
            setMotorPower(0);
        } else if (position == RelicRecoveryVuMark.RIGHT) {
            setMotorPower(1);
            Thread.sleep(2500);
            setMotorPower(0);
        }
    }

    private void turnRight() throws InterruptedException {
        leftDrive.setPower(1);
        rightDrive.setPower(-1);
        Thread.sleep(500);
        leftDrive.setPower(0);
        rightDrive.setPower(0);
    }

    private void placeGlyphInBox() throws InterruptedException {
        leftDrive.setPower(1);
        rightDrive.setPower(1);
        Thread.sleep(250);
        leftDrive.setPower(0);
        rightDrive.setPower(0);
    }

    private void getOutOfBox() throws InterruptedException {
        leftDrive.setPower(-1);
        rightDrive.setPower(-1);
        Thread.sleep(100);
        leftDrive.setPower(0);
        rightDrive.setPower(0);
    }

    private void doTheRoutine(RelicRecoveryVuMark vuMark) throws InterruptedException {
        driveToPosition(vuMark);
        turnRight();
        placeGlyphInBox();
        getOutOfBox();
        setServoPos(0);
    }

}