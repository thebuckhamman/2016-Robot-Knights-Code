package org.usfirst.frc.team5496.robot.subsystems;

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
     * @return Value to set the right talons to
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
     * @return Value to set the left talons to
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
}

