package org.usfirst.frc.team5496.robot.commands;

import org.usfirst.frc.team5496.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShootBall extends Command {
	boolean shootAble;
	double getTime;
	double shootSpeed;
	double outakeSpeed;
	double totalShootTime;
	double wheelPrepTime;
	
    public ShootBall() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	shootSpeed = 1;
    	outakeSpeed = 0.5;
    	totalShootTime = 1;
    	wheelPrepTime = 0.5;
    	shootAble = true;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    	if(Robot.oi.rampUp) 
    	{
    		getTime = Timer.getFPGATimestamp();
    		
    		//Total shooting time lasts a second after the command is called
    		while(Timer.getFPGATimestamp() < (getTime + totalShootTime))
    		{
    			Robot.oi.topShootTalon.set(shootSpeed);
    			Robot.oi.bottomShootTalon.set(-shootSpeed);
    			
    			//After wheel preping the shooter wheels, the outake wheels run to feed the ball into them  
    			if(Timer.getFPGATimestamp() >= (getTime + wheelPrepTime))
    			{
    				Robot.oi.OutakeTalon.set(-outakeSpeed);
    			}
    		}
    		
    		Robot.oi.topShootTalon.set(0);
			Robot.oi.bottomShootTalon.set(0);
			Robot.oi.OutakeTalon.set(0);
			
    	}
    	else
    	{
    		System.out.println("Ramp Not Up");
    	}
    	shootAble = false;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(!shootAble)
    	{
    		return true;
    	}
        return false;
    }
    

    // Called once after isFinished returns true
    protected void end() {
    	shootAble = true;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
