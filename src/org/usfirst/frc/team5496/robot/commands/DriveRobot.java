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
		requires(Robot.drive);
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
		
		Robot.drive.setLeftSpeedAdjusted(lefty);
		Robot.drive.setRightSpeedAdjusted(righty);

	}

	/**
	 * @returns true when the command no longer needs to run execute(). Since this is a class that always needs to run to drive, it always returns false.
	 */
	protected boolean isFinished() {

		return false;
	}

	/**
	 * Called when the command no longer will run.
	 * Stops the robot.
	 */
	protected void end() {
		Robot.drive.setLeftSpeed(0);
		Robot.drive.setRightSpeed(0);
	}

	/**
	 * Runs when another command needs the subsystems that this command uses.
	 */
	protected void interrupted() {
	}
}
