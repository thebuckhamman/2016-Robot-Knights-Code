package org.usfirst.frc.team5496.robot.subsystems;

import org.usfirst.frc.team5496.robot.Robot;
import edu.wpi.first.wpilibj.command.Subsystem;


/**
 *
 */
public class DriveTrain extends Subsystem {
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    /**
     * Set the speed for the right talons
     * @param rightYValue the new value of the right talons
     */
    public double rightSpeed(double rightYValue)
    {	
    	if(rightYValue > 0.2)
    	{
    		return 0.9*rightYValue;
    		
    	}
    	else if(rightYValue < -0.2)
    	{
    		return 0.9*rightYValue;
    	}
    	else
    	{
    	return 0;
    	}
    }
    
    /**
     * Set the speed for the left talons
     * @param leftYValue thew new value of the left talons
     */
    public double leftSpeed(double leftYValue)
    {
    	if(leftYValue > 0.2)
    	{
    		return 0.9*leftYValue;
    		
    	}
    	else if(leftYValue < -0.2)
    	{
    		return 0.85*leftYValue;
    	}
    	else
    	{
    	return 0;
    	}
    		
    	
    }
    
    /**
     * Not used. Exists for no reason
     */
     //TODO: REMOVED
    public int boostPower(boolean leftBumperPressed, boolean rightBumperPressed)
    {
    	if(leftBumperPressed && rightBumperPressed)
    	{
    		return 3;
    	}
    	return 1;
    }
    
    /**
     * Again, never used. Should be removed.
     */
     //TODO: REMOVE
    public void setDriveType(int type)
    {
    	if(type == 1)
    	{
    		Robot.oi.leftServo.setAngle(Robot.oi.originalServoValue);
    		Robot.oi.rightServo.setAngle(Robot.oi.originalServoValue);
    	}
    	else if(type == 2)
    	{
    		Robot.oi.leftServo.setAngle(Robot.oi.originalServoValue + 20);
        	Robot.oi.rightServo.setAngle(Robot.oi.originalServoValue + 20);    	
        }
    	else
    	{
    		System.out.println("Error: Choose either type 1 or 2");
    	}
    	
    }
    	
   
}

