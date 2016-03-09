package org.usfirst.frc.team5496.robot.subsystems;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Ramp extends Subsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	/**
	 * Ramp pitch controllr
	 */
	private CANTalon RampTalon = new CANTalon(1);
	/**
	 * Intake wheel
	 */
	private CANTalon IntakeTalon = new CANTalon(4);
	/**
	 * Assists loading and firing
	 */
	private CANTalon OutakeTalon = new CANTalon(0);
	/**
	 * Bottom firing motor
	 */
	private CANTalon bottomShootTalon = new CANTalon(2);
	/**
	 * Top firing motor
	 */
	private CANTalon topShootTalon = new CANTalon(3);
	
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	/**
	 * Used to set the speed of the ramp pitch talon
	 * @param speed the speed the talon is set to
	 */
	public void setRampTalonSpeed(double speed) {
		RampTalon.set(speed);
	}

	/**
	 * Used to set the intake talon speed.
	 * @param speed The speed the talon is set to
	 */
	public void setIntakeTalonSpeed(double speed) {
		IntakeTalon.set(speed);
	}

	/**
	 * Used to set the outtake talon speed.
	 * @param speed The speed the talon is set to
	 */
	public void setOuttakeTalonSpeed(double speed) {
		OutakeTalon.set(speed);
	}

	/**
	 * Used to set the bottom shooting talon speed
	 * @param speed The speed the talon is set to
	 */
	public void setBottomShootTalonSpeed(double speed) {
		bottomShootTalon.set(speed);
	}

	/**
	 * Used to set the top shooting talon speed
	 * @param speed The speed the talon is set to
	 */
	public void setTopShootTalonSpeed(double speed) {
		topShootTalon.set(speed);
	}
}
