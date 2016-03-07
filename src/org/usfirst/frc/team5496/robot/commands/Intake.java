package org.usfirst.frc.team5496.robot.commands;

import org.usfirst.frc.team5496.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Intake extends Command {
	boolean intakeAble;
	double getTime;
	double intakeSpeed;
	double outakeSpeed;
	double totalShootTime;
	double wheelPrepTime;
	
    public Intake() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	intakeAble = true;
    	intakeSpeed = 0.5;
    	outakeSpeed = -0.1;
    	totalShootTime = 1;
    	wheelPrepTime = 0.5;
    	
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(!Robot.oi.rampUp)
    	{
    			Robot.oi.IntakeTalon.set(intakeSpeed);
    			Robot.oi.OutakeTalon.set(outakeSpeed);
    			System.out.println(Robot.oi.IntakeTalon.get() + " " + Robot.oi.OutakeTalon.get());
    	}
    	else
    	{
    		System.out.println("Can't intake if ramp is up");
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(!Robot.oi.myJoystick2.getRawButton(5) || !Robot.oi.myJoystick2.getRawButton(6))
        {
     	   return true;
        }
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
		Robot.oi.IntakeTalon.set(0);
		Robot.oi.OutakeTalon.set(0);
		System.out.println("Intake ended");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

