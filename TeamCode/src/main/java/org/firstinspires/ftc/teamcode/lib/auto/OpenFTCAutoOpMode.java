package org.firstinspires.ftc.teamcode.lib.auto;

import android.app.Activity;

import com.qualcomm.ftccommon.configuration.RobotConfigFileManager;
import com.qualcomm.ftcrobotcontroller.FieldPositonData;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.bots.Shortround;
import org.firstinspires.ftc.teamcode.hardware.bots.BotBase;

import org.firstinspires.ftc.teamcode.lib.logging.UniLogger;

/**
 * Created by pranav on 1/27/2018.
 */

public abstract class OpenFTCAutoOpMode extends LinearOpMode {

    public BotBase bot;
    public FieldPositonData.FieldPostion fieldPostion;
    public boolean useDogeCV;
    public int autoSpeed;
    public String currentConfig;
    @Override
    public void runOpMode() throws InterruptedException {

        fieldPostion = FieldPositonData.fieldPostion;
        autoSpeed = FieldPositonData.speed;
        useDogeCV = FieldPositonData.dogeCVCrypto;

        RobotConfigFileManager configManager;

        configManager = new RobotConfigFileManager((Activity) hardwareMap.appContext);

        currentConfig = configManager.getActiveConfig().getName();

        UniLogger.Log("DOGE-AUTO", "Hardware Config Selected: " + currentConfig);
        UniLogger.Log("DOGE-AUTO", "Field Position Selected: " + fieldPostion.toString());
        UniLogger.Log("DOGE-AUTO", "Auto Speed: " + autoSpeed);
        UniLogger.Log("DOGE-AUTO", "Use DogeCV: " + useDogeCV);

        switch (currentConfig){
            case "Shortround":
                UniLogger.Log("DOGE-AUTO", "Creating Shortround bot for OpModes");
                bot = new Shortround(hardwareMap);

                break;
        }

    }

}