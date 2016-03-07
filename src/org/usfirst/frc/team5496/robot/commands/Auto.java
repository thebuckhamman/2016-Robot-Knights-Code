package org.usfirst.frc.team5496.robot.commands;

import org.usfirst.frc.team5496.robot.Robot;

import com.analog.adis16448.frc.ADIS16448_IMU;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Auto extends Command {

	double initCompPosition;
	ADIS16448_IMU imu;
	double[] AccelForwardInfo = new double[100];
	double[] AccelHorzInfo = new double[100];
	double[] AccelUpInfo = new double[100];
	
	double AverageAccelForward;
	double AverageAccelHorz;
	double AverageAccelUpInfo;

	boolean tooLeft = false;
	boolean tooRight = false;
	boolean can = false;
	
	double time0;
    public Auto() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }
  
    // Called just before this Command runs the first time
    protected void initialize() {
        imu = new ADIS16448_IMU();
    	initCompPosition = imu.getMagZ();
    	time0 = 5;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//very simple autoMode
    	if(Robot.autoPosition == 0)
    	{
    		if(Timer.getMatchTime() < time0)
    		{
    		Robot.oi.leftDriveTalon1.set(1);
        	Robot.oi.leftDriveTalon2.set(1);
        	Robot.oi.rightDriveTalon1.set(0.92);
        	Robot.oi.rightDriveTalon2.set(0.92);
    		}
    	}
    	else
    	{
	   		for(int i = 0; i < AccelForwardInfo.length; i++)
	    	{
	    		
	    		
	    	}
	    	
	    	
	    	if(imu.getAccelY() < time0)
	    	{
	        			
	    	}
	    	else
	    	{
	    		
	    	}
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
