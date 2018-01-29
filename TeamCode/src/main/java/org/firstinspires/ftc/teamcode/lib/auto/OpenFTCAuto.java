package org.firstinspires.ftc.teamcode.lib.auto;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.hardware.bots.BotBase;

/**
 * Created by pranav on 1/27/2018.
 */

public abstract class OpenFTCAuto {
    public OpenFTCAutoOpMode opMode;
    public Telemetry telemetry;
    public int autoSpeed;
    public boolean useDogeCV;
    public BotBase bot;

    public OpenFTCAuto(OpenFTCAutoOpMode parent){
        opMode = parent;
        telemetry = opMode.telemetry;
        bot = opMode.bot;
        autoSpeed = opMode.autoSpeed;
        useDogeCV = opMode.useDogeCV;



    }

    public abstract void Init();

    public abstract void Run();

    public abstract void Stop();
}