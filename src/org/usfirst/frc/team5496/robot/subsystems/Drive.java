package org.usfirst.frc.team5496.robot.subsystems;

import org.usfirst.frc.team5496.robot.Robot;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Drive extends Subsystem {

	/**
	 * Left talon #1
	 */
	private Talon leftDriveTalon1 = new Talon(2);
	/**
	 * Left talon #2
	 */
	private Talon leftDriveTalon2 = new Talon(3);
	/**
	 * Right talon #1
	 */
	private Talon rightDriveTalon1 = new Talon(0);
	/**
	 * Right talon #2
	 */
	private Talon rightDriveTalon2 = new Talon(1);

	/**
	 * Set the speed for the right talons
	 * 
	 * @param rightYValue
	 *            the new value of the right talons
	 * @return Value to set the right talons to
	 */
	private double rightSpeed(double rightYValue) {
		if (rightYValue > 0.2) {
			return 0.9 * rightYValue;

		} else if (rightYValue < -0.2) {
			return 0.9 * rightYValue;
		} else {
			return 0;
		}
	}

	/**
	 * Set the speed for the left talons
	 * 
	 * @param leftYValue
	 *            thew new value of the left talons
	 * @return Value to set the left talons to
	 */
	private double leftSpeed(double leftYValue) {
		if (leftYValue > 0.2) {
			return 0.9 * leftYValue;
		} else if (leftYValue < -0.2) {
			return 0.85 * leftYValue;
		} else {
			return 0;
		}
	}

	/**
	 * Set right speed based on the rightSpeed method's value given an input
	 * Used for joystick axises
	 * @param speed
	 */
	public void setRightSpeedAdjusted(double speed) {
		double speedToSet = rightSpeed(speed) * 0.92;
		rightDriveTalon1.set(speedToSet);
		rightDriveTalon2.set(speedToSet);
	}

	/**
	 * Set left speed based on the leftSpeed method's value given an input
	 * Used for joystick axises
	 * @param speed
	 */
	public void setLeftSpeedAdjusted(double speed) {
		double speedToSet = -leftSpeed(speed);
		leftDriveTalon1.set(speedToSet);
		leftDriveTalon2.set(speedToSet);
	}

	/**
	 * Sets right speed to a certain value
	 * @param speedToSet
	 */
	public void setRightSpeed(double speedToSet) {
		rightDriveTalon1.set(speedToSet);
		rightDriveTalon2.set(speedToSet);
	}

	/**
	 * Sets left speed to a certain value
	 * @param speedToSet
	 */
	public void setLeftSpeed(double speedToSet) {
		leftDriveTalon1.set(speedToSet);
		leftDriveTalon2.set(speedToSet);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}
}
