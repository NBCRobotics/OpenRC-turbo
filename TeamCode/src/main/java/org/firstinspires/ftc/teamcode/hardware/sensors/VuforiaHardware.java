package org.firstinspires.ftc.teamcode.hardware.sensors;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.ClosableVuforiaLocalizer;
import org.firstinspires.ftc.teamcode.BuildConfig;

/**
 * Created by Victo on 12/10/2017.
 */

public class VuforiaHardware {
    OpenGLMatrix lastLocation = null;

    ClosableVuforiaLocalizer vuforia;
    RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.UNKNOWN;
    VuforiaTrackable relicTemplate;
    public void Init(HardwareMap hardwareMap){
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = BuildConfig.VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = new ClosableVuforiaLocalizer(parameters);
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        relicTemplate= relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary
        relicTrackables.activate();


    }

    public void Loop(){
        if(RelicRecoveryVuMark.from(relicTemplate) != RelicRecoveryVuMark.UNKNOWN){
            vuMark = RelicRecoveryVuMark.from(relicTemplate);
        }
    }

    public void Stop(){
        vuforia.close();
    }

    public RelicRecoveryVuMark getVuMark() {
        return vuMark;
    }
}