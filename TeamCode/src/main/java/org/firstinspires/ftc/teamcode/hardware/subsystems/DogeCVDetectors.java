package org.firstinspires.ftc.teamcode.hardware.subsystems;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.detectors.*;
import com.disnodeteam.dogecv.OpenCVPipeline;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class DogeCVDetectors {
    
    private CryptoboxDetector cryptoboxDetector;
    private GlyphDetector glyphDetector;
    private JewelDetector jewelDetector;
    
    private OpenCVPipeline[] detectors = new OpenCVPipeline[]{cryptoboxDetector, glyphDetector, jewelDetector};
    
    public DogeCVDetectors(HardwareMap hwd) {
        
    }
    
    private void init(HardwareMap hwd) {
        cryptoboxDetector.init(hwd.appContext, CameraViewDisplay.getInstance());
        glyphDetector.init(hwd.appContext, CameraViewDisplay.getInstance());
        jewelDetector.init(hwd.appContext, CameraViewDisplay.getInstance());
    }
}
