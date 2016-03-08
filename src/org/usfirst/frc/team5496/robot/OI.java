package org.usfirst.frc.team5496.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	/**
	 * The driver controller
	 */
	public Joystick myJoystick1 = new Joystick(0);
	/**
	 * Ramp controller
	 */
	public Joystick myJoystick2 = new Joystick(1);

	public Encoder enc = new Encoder(0, 1, false, Encoder.EncodingType.k4X);

	/**
	 * Used to track the ramp state true is up false is down
	 */
	public boolean rampUp;
	/**
	 * Used for timing various things
	 */
	public double time;
}
