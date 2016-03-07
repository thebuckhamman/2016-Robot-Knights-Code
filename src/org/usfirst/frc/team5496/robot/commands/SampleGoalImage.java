package org.usfirst.frc.team5496.robot.commands;

import org.usfirst.frc.team5496.robot.Robot;
import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SampleGoalImage extends Command {
	
	NIVision.Point lowerLeft, lowerRight, upperLeft, upperRight, testPointStart, testPointFinish;
	double[] lowerLeftDisp, lowerRightDisp, upperLeftDisp, upperRightDisp = new double[3];
	
    public SampleGoalImage() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	System.out.println("SampleGoalImage contructing");
    	//requires(Robot.camera);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    /*	System.out.println("$$$");
    	//Initializing the two frames and session for the usb cam
    	Robot.cam.frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
    	Robot.cam.frame2 = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_U8, 0);
        Robot.cam.session = NIVision.IMAQdxOpenCamera("cam0",
                NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        
        NIVision.IMAQdxConfigureGrab(Robot.cam.session);
        
        //Setting brightness to minimum amount to help with tape
         
        NIVision.IMAQdxSetAttributeString(Robot.cam.session, "CameraAttributes::Brightness::Mode", "Manual");
    	long minv = NIVision.IMAQdxGetAttributeMinimumI64(Robot.cam.session, "CameraAttributes::Brightness::Value");//30
        long maxv = NIVision.IMAQdxGetAttributeMaximumI64(Robot.cam.session, "CameraAttributes::Brightness::Value");//255
        long val = minv + (long) (((double) (maxv - minv)) * (((double) Robot.cam.defaultBrightVal) / 100.0));
        NIVision.IMAQdxSetAttributeI64(Robot.cam.session, "CameraAttributes::Brightness::Value", val);
        
        //Declaring the start of the camera
        System.out.println("starting camera");
       
        
        NIVision.IMAQdxStartAcquisition(Robot.cam.session);
    	*/
    	
    	//adding the arrays of the standard camera displacement to the point displacements to get
    	//the displacements from the camera to the points
    	
    	lowerLeftDisp = Robot.cam.addArrays(Robot.cam.STANDARD_CAMERA_DISPLACEMENT, Robot.cam.BOTTOM_LEFT_TAPE_POINT);
    	lowerRightDisp = Robot.cam.addArrays(Robot.cam.STANDARD_CAMERA_DISPLACEMENT, Robot.cam.BOTTOM_RIGHT_TAPE_POINT);
    	upperLeftDisp = Robot.cam.addArrays(Robot.cam.STANDARD_CAMERA_DISPLACEMENT, Robot.cam.TOP_LEFT_TAPE_POINT);
    	upperRightDisp = Robot.cam.addArrays(Robot.cam.STANDARD_CAMERA_DISPLACEMENT, Robot.cam.TOP_RIGHT_TAPE_POINT);

    	//Uses the point displacements to calculate the points representing them on the screen
    	lowerLeft = Robot.cam.calculateCoordinate(lowerLeftDisp);
    	lowerRight = Robot.cam.calculateCoordinate(lowerRightDisp);
    	upperLeft = Robot.cam.calculateCoordinate(upperLeftDisp);
    	upperRight = Robot.cam.calculateCoordinate(upperRightDisp);
    	
    	testPointStart = new NIVision.Point(0,0);
    	testPointFinish = new NIVision.Point(239,239);
    	
        System.out.println("LowerLeft: (" + lowerLeft.x + ", " + lowerLeft.y + ")");
        System.out.println("UpperLeft: (" + upperLeft.x + ", " + upperLeft.y + ")");
        System.out.println("LowerRight: (" + lowerRight.x + ", " + lowerRight.y + ")");
   	    System.out.println("UpperRight: (" + upperRight.x + ", " + upperRight.y + ")");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    //Draws a square representing the retroflective tape
   
    NIVision.IMAQdxGrab(Robot.cam.session, Robot.cam.frame, 1);
    
    //System.out.println("Is this starting?");
    
    NIVision.imaqDrawLineOnImage(Robot.cam.frame, Robot.cam.frame, DrawMode.DRAW_VALUE, lowerLeft, lowerRight, 255.0f);
    NIVision.imaqDrawLineOnImage(Robot.cam.frame, Robot.cam.frame, DrawMode.DRAW_VALUE, lowerLeft, upperLeft, 255.0f);
    NIVision.imaqDrawLineOnImage(Robot.cam.frame, Robot.cam.frame, DrawMode.DRAW_VALUE, upperLeft, upperRight, 255.0f);
    NIVision.imaqDrawLineOnImage(Robot.cam.frame, Robot.cam.frame, DrawMode.DRAW_VALUE, lowerRight, upperRight, 255.0f);
    
    NIVision.imaqDrawLineOnImage(Robot.cam.frame, Robot.cam.frame, DrawMode.DRAW_VALUE, testPointStart, testPointFinish, 255.0f);
    
    CameraServer.getInstance().setImage(Robot.cam.frame);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
