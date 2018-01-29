package org.firstinspires.ftc.teamcode.lib.auto;

import org.firstinspires.ftc.teamcode.hardware.bots.BotBase;

/**
 * Created by pranav on 1/27/2018.
 */

public abstract class CommandBase {
    public BotBase bot;
    public OpenFTCAutoOpMode opMode;
    public boolean overrideLoop = false;
    public CommandBase(OpenFTCAutoOpMode opMode){
        this.opMode = opMode;
        this.bot = opMode.bot;
    }
    public void setOverrideLoop(boolean va){
        overrideLoop = overrideLoop;
    }




    public void Run(){
        Start();
        if(!overrideLoop){
            while(canRunLoop()){
                Loop();
            }
            Stop();
        }

    }
    public abstract void Start();
    public abstract void Loop();
    public abstract void Stop();

    public abstract boolean IsTaskRunning();
    public boolean canRunLoop(){
        return !opMode.isStopRequested() && opMode.opModeIsActive() && IsTaskRunning();
    }
}