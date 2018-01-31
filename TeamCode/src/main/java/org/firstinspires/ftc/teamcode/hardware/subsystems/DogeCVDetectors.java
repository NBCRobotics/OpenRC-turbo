package org.firstinspires.ftc.teamcode.hardware.subsystems;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.detectors.*;
import com.disnodeteam.dogecv.OpenCVPipeline;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class DogeCVDetectors {
    
    private static CryptoboxDetector cryptoboxDetector;
    private static GlyphDetector glyphDetector;
    private static JewelDetector jewelDetector;
    
    /**
     * 0 - Cryptobox
     * 1 - Glyph
     * 2 - Jewel
     */
    public OpenCVPipeline[] detectors = new OpenCVPipeline[]{cryptoboxDetector, glyphDetector, jewelDetector};
    
    public DogeCVDetectors(HardwareMap hwd) {
        cryptoboxDetector = new CryptoboxDetector();
        glyphDetector = new GlyphDetector();
        init(hwd);
    }
    
    public void init(HardwareMap hwd) {
        
        //Jewel Detector Settings
        jewelDetector.areaWeight = 0.02;
        jewelDetector.detectionMode = JewelDetector.JewelDetectionMode.MAX_AREA; // PERFECT_AREA
        //jewelDetector.perfectArea = 6500; <- Needed for PERFECT_AREA
        jewelDetector.debugContours = true;
        jewelDetector.maxDiffrence = 15;
        jewelDetector.ratioWeight = 15;
        jewelDetector.minArea = 700;
        
        //Glyph Detector settings
        glyphDetector.minScore = 1;
        glyphDetector.downScaleFactor = 0.3;
        glyphDetector.speed = GlyphDetector.GlyphDetectionSpeed.SLOW;
        
        cryptoboxDetector.init(hwd.appContext, CameraViewDisplay.getInstance());
        glyphDetector.init(hwd.appContext, CameraViewDisplay.getInstance());
        jewelDetector.init(hwd.appContext, CameraViewDisplay.getInstance());
        
    }
    
    public void enable(OpenCVPipeline detector) {
        detector.enable();
    }
    
    public void disable(OpenCVPipeline detector) {
        detector.disable();
    }
}
