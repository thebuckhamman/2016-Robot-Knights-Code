package org.usfirst.frc.team5496.robot.commands;

import org.usfirst.frc.team5496.robot.Robot;
import org.usfirst.frc.team5496.robot.subsystems.camera;

import com.ni.vision.NIVision;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AimCamera extends Command {
	int bigParticleIndex;
	
	int firstx;
	int firsty;
	boolean robotRight;
	NIVision.Point firstPoint;
	
    public AimCamera() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	int bigParticleIndex = -1;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	NIVision.imaqColorThreshold(camera.frame2, camera.frame, 255, 
				NIVision.ColorMode.HSV, 
				camera.TOTE_HUE_RANGE, 
				camera.TOTE_SAT_RANGE, 
				camera.TOTE_VAL_RANGE);
    	
    	
    	
    	firstx = (int)NIVision.imaqMeasureParticle(camera.frame2, 1, 1, NIVision.MeasurementType.MT_FIRST_PIXEL_X);
    	firsty = (int)NIVision.imaqMeasureParticle(camera.frame2, 1, 1, NIVision.MeasurementType.MT_FIRST_PIXEL_Y);
    	firstPoint = new NIVision.Point(firstx, firsty);
    	
    	if(firsty >= (int)NIVision.imaqMeasureParticle(camera.frame2, 1, 1, NIVision.MeasurementType.MT_BOUNDING_RECT_TOP) - 5)
    	{
    		robotRight = true;
    	}
    	else
    	{
    		robotRight = false;
    	}
    	
    	if(robotRight)
    	{
    		Robot.oi.leftDriveTalon1.set(-0.5);
        	Robot.oi.leftDriveTalon2.set(-0.5);
        	Robot.oi.rightDriveTalon1.set(-0.5 * 0.92);
        	Robot.oi.rightDriveTalon2.set(-0.5 * 0.92);
    	}
    	else
    	{
    		Robot.oi.leftDriveTalon1.set(0.5);
        	Robot.oi.leftDriveTalon2.set(0.5);
        	Robot.oi.rightDriveTalon1.set(0.5 * 0.92);
        	Robot.oi.rightDriveTalon2.set(0.5 * 0.92);
    	}
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
