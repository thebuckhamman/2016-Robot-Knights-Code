package org.usfirst.frc.team5496.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.buttons.Button;
import org.usfirst.frc.team5496.robot.commands.DriveRobot;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);
    
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
    
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
    
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand()); 
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
	
	/**
	 * The driver controller
	 */
	public Joystick myJoystick1 = new Joystick(0);
	/**
	 * Ramp controller
	 */
	public Joystick myJoystick2 = new Joystick(1);
	
	/**
	 * Left talon #1
	 */
	public Talon leftDriveTalon1 = new Talon(2);
	/**
	 * Left talon #2
	 */
	public Talon leftDriveTalon2 = new Talon(3);
	/**
	 * Right talon #1
	 */
	public Talon rightDriveTalon1 = new Talon(0);
	/**
	 * Right talon #2
	 */
	public Talon rightDriveTalon2 = new Talon(1);
	
	/**
	 * Ramp pitch controllr
	 */
	public CANTalon RampTalon = new CANTalon(1);
	/**
	 * Intake wheel
	 */
	public CANTalon IntakeTalon = new CANTalon(4);
	/**
	 * Assists loading and firing
	 */
	public CANTalon OutakeTalon = new CANTalon(0);
	/**
	 * Bottom firing motor
	 */
	public CANTalon bottomShootTalon = new CANTalon(2);
	/**
	 * Top firing motor
	 */
	public CANTalon topShootTalon = new CANTalon(3);
	
	public Servo leftServo = new Servo(5);
	public Servo rightServo = new Servo(4);
	
	public Encoder enc = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
	
	/**
	 * Used to track the ramp state
	 * true is up
	 * false is down
	 */
	public boolean rampUp;
	/**
	 * Used for timing various things
	 */
	public double time;
	public double originalServoValue;

}


