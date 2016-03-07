package org.usfirst.frc.team5496.robot.commands;

import org.usfirst.frc.team5496.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5496.robot.commands.*;
/**
 *
 */
public class UseRamp extends Command {

	boolean shootStarting = false;
	double time;
	Timer timeer = new Timer();
    public UseRamp() {
        // Use requires() here to declare subsystem dependencies
       //eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	boolean shootStarting = false;
    	double time;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	//This method only exists only if limit switched not in place
    	/*if(Robot.oi.myJoystick2.getRawButton(1))
    	{
    		Robot.oi.rampUp = !Robot.oi.rampUp;
    		System.out.println("RampUp: " + Robot.oi.rampUp);
    	}*/
    	//System.out.println(Robot.oi.enc.get());
    	
    	/*if(Robot.oi.rampUp)
        {
    		if(Robot.oi.myJoystick2.getRawButton(1))
        	{
    			Robot.oi.time = Timer.getFPGATimestamp();
    			while(Timer.getFPGATimestamp() < Robot.oi.time + 1
    					//Robot.oi.enc.get() > 5
    					)
    			{
    				Robot.oi.RampTalon.set(0.2);
    			}
    			Robot.oi.RampTalon.set(0);
        	    Robot.oi.rampUp = !Robot.oi.rampUp;
        	}
        }
        else if(!Robot.oi.rampUp)
        {
        	if(Robot.oi.myJoystick2.getRawButton(2))
        	{
    			Robot.oi.time = Timer.getFPGATimestamp();
    			while(Timer.getFPGATimestamp() < Robot.oi.time + 0.25
    					//Robot.oi.enc.get() < 32
    					)
    			{
    				System.out.println(Robot.oi.enc.get());
    				Robot.oi.RampTalon.set(0.9);
    			}
    			System.out.println("Ramp at top, Ramp Value: " + Robot.oi.enc.get());
    			Robot.oi.RampTalon.set(0.5);
    			Robot.oi.rampUp = !Robot.oi.rampUp;
        	}
        	
        }
    	*/
    	//System.out.println(Robot.oi.enc.get());
    	
    	//	if(Robot.oi.myJoystick2.getRawButton(4))
    	//	{
    		/*	if(Robot.oi.enc.get() < 20)
    			{ */
    				//System.out.println(Robot.oi.enc.get());
    		//		Robot.oi.RampTalon.set(0.3);
    		/*	}
    			Robot.oi.RampTalon.set(0.2); */
    	//	} 
    
    		if(Robot.oi.myJoystick2.getRawButton(1))
    		{
    	/*		if(Robot.oi.enc.get() > -35)
    			{ */
    				//System.out.println(Robot.oi.enc.get());
    				Robot.oi.RampTalon.set(-1);
    		/*	}
    			Robot.oi.RampTalon.set(-0.3); */
    		}
    		else
    		{
    			Robot.oi.RampTalon.set(0);
    		}
    	
    	
    	
    	if(Robot.oi.myJoystick1.getRawButton(5) && Robot.oi.myJoystick1.getRawButton(6))
		{
			Robot.oi.topShootTalon.set(-1);
			Robot.oi.bottomShootTalon.set(0.65);
			if(!shootStarting)
			{
				time = Timer.getMatchTime();
				shootStarting = true;
			}
			//System.out.println("shooting");
		}
		else
		{
			Robot.oi.topShootTalon.set(0);
			Robot.oi.bottomShootTalon.set(0);
			//System.out.println("stopped");
		}
    	
    	if(Robot.oi.myJoystick2.getRawButton(5))
		{
			Robot.oi.OutakeTalon.set(0.5);
		//	System.out.println(Timer.getMatchTime());
			shootStarting = false;
		}
		else if(Robot.oi.myJoystick2.getRawAxis(2) > 0.8)
		{
			Robot.oi.OutakeTalon.set(-0.3);
		}
		else
		{
			Robot.oi.OutakeTalon.set(0);
		}
    	
    	if(Robot.oi.myJoystick2.getRawButton(6))
		{
			Robot.oi.IntakeTalon.set(-0.5);
		}
		else if(Robot.oi.myJoystick2.getRawAxis(3) > 0.8)
		{
			Robot.oi.IntakeTalon.set(0.5);
		}
		else
		{
			Robot.oi.IntakeTalon.set(0);
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
