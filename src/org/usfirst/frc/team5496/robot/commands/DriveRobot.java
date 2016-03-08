package org.usfirst.frc.team5496.robot.commands;

import org.usfirst.frc.team5496.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveRobot extends Command {
	int driveType;
	double lefty, righty;
	Boolean TurboButton1, TurboButton2, turboheld;
    public DriveRobot() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	driveType = 1;
    	turboheld = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	lefty = Robot.oi.myJoystick1.getRawAxis(1);
    	righty = Robot.oi.myJoystick1.getRawAxis(5);
    	//boolean Shiftbutton = Robot.oi.myJoystick1.getRawButton(1);
      /* 
    	if(Shiftbutton)
    	{
    		turboheld = true;
    	}
    	if(!Shiftbutton && turboheld)
    	{
    		if(driveType == 1)
    		{
    			Robot.driveTrain.setDriveType(2);
    			driveType = 2;
    		}
    		else if(driveType == 2)
    		{
    			Robot.driveTrain.setDriveType(1);
    			driveType = 1;
    		}
    		turboheld = false;
    	}
        */
    	double leftSpeed = -Robot.driveTrain.leftSpeed(lefty);
    	double rightSpeed = Robot.driveTrain.rightSpeed(righty) * 0.92;
    	Robot.oi.leftDriveTalon1.set(leftSpeed);
    	Robot.oi.leftDriveTalon2.set(leftSpeed);
    	Robot.oi.rightDriveTalon1.set(rightSpeed);
    	Robot.oi.rightDriveTalon2.set(rightSpeed);
    	
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
