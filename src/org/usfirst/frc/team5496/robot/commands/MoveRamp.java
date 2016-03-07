
package org.usfirst.frc.team5496.robot.commands;

import org.usfirst.frc.team5496.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveRamp extends Command {
	boolean moveRamp;
	double upRampSpeed, downRampSpeed, steadyRampSpeed;
	
    public MoveRamp() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	upRampSpeed = 0.9;
    	steadyRampSpeed = 0.3;
    	downRampSpeed = 0.2;
    	moveRamp = true;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(Robot.oi.rampUp)
    	{
    		System.out.println("Ramp lowering start");
    		while(Timer.getFPGATimestamp() > Robot.oi.time + 0.5)
    		{
    			Robot.oi.RampTalon.set(0.2);
    			//System.out.println("Ramp lowering");
    		}
    		Robot.oi.RampTalon.set(0);
    		Robot.oi.rampUp = false;
    		System.out.println("Ramp down");
    		
    	}
    	else if(!Robot.oi.rampUp)
    	{
    		System.out.println("Ramp raising start");
    		while(Timer.getFPGATimestamp() > Robot.oi.time + 0.5)
    		{
    			Robot.oi.RampTalon.set(upRampSpeed);
    			System.out.println("Ramp raising");
    		}
    		//Robot.oi.RampTalon.set(steadyRampSpeed);
    		Robot.oi.rampUp = true;
    		System.out.println("Ramp raised");
    	}
    	moveRamp = false;
    	
    	
    }

    
    
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(!moveRamp)
    	{
    		return true;
    	}
        return false;
        
    }

    // Called once after isFinished returns true
    protected void end() {
    	moveRamp = true;
    	System.out.println("Stopped moving ramp");
    } 

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
