package org.usfirst.frc.team5496.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;


import org.usfirst.frc.team5496.robot.commands.DriveRobot;
import org.usfirst.frc.team5496.robot.commands.Intake;
import org.usfirst.frc.team5496.robot.commands.MoveRamp;
import org.usfirst.frc.team5496.robot.commands.SameTime;
import org.usfirst.frc.team5496.robot.commands.SampleGoalImage;
import org.usfirst.frc.team5496.robot.commands.ShootBall;
import org.usfirst.frc.team5496.robot.commands.UseRamp;
import org.usfirst.frc.team5496.robot.subsystems.DriveTrain;
import org.usfirst.frc.team5496.robot.subsystems.camera;

import com.ni.vision.NIVision;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	/**
	 * Initializes and stores motors, joysticks, etc
	 */
	public static OI oi;
	
	/**
	 * Used to control the left and right tank treads.
	 */
	public static DriveTrain driveTrain = new DriveTrain();
	/**
	 * Used to control camera
	 */
	public static camera cam;
		
	/**
	 * Used to drive robot
	 */
	public static DriveRobot driveRobot = new DriveRobot();
	/**
	 * TODO: REMOVE
	 */
	public static Intake intake = new Intake();
	/**
	 * TODO: REMOVE
	 */
	public static MoveRamp moveRamp = new MoveRamp();
	/**
	 * Used to control the ramp
	 */
	public static UseRamp useRamp = new UseRamp();
	
	/**
	 * Used to run UseRamp and drive robot at the same time
	 */
	public static SameTime sameTime = new SameTime();
	/**
	 * TODO: REMOVE
	 */
	public static ShootBall shootBall = new ShootBall();
	/**
	 * Will be used for showing a rectangle where the goal should be on the camera image
	 */
	public static SampleGoalImage sampImage;
	
	/**
	 * The array of points used to show the goal image
	 */
	NIVision.Point[] rectImage = 
			 {cam.calculateCoordinate(cam.TOP_LEFT_TAPE_POINT_CAM),
			  cam.calculateCoordinate(cam.TOP_RIGHT_TAPE_POINT_CAM),
			  cam.calculateCoordinate(cam.BOTTOM_RIGHT_TAPE_POINT),
			  cam.calculateCoordinate(cam.BOTTOM_LEFT_TAPE_POINT)};
	/**
	 * Used for robot positioning
	 * Auto position 1 - 4, 0 for simple auto
	 */
	public static int autoPosition = 0;
	
	/**
	 * TODO: Remove?
	 */
    Command autonomousCommand;
    /**
     * Used to organize running of functions
     */
    SendableChooser chooser;
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
		oi = new OI();
		
		try{
			cam = new camera();
		}
		catch(Throwable t)
		{
			System.out.println("Camera constructor is the problem: " + t.toString());
		}
		
		try{
			sampImage = new SampleGoalImage();
		}
		catch(Throwable t)
		{
			System.out.println("SampleGoal contructing is problem: " + t.toString());
		}
		
		
        chooser = new SendableChooser();
        chooser.addDefault("Default Auto", new DriveRobot());
//        chooser.addObject("My Auto", new MyAutoCommand());
        SmartDashboard.putData("Auto mode", chooser);
        
        oi.rampUp = true;
        oi.time = Timer.getFPGATimestamp();
        oi.originalServoValue = oi.leftServo.get();
        
        //Test if robot can access camera methods. May just give up on the whole sample goal image thing
        double[] exampleArray = cam.addArrays(cam.BOTTOM_LEFT_TAPE_POINT, cam.BOTTOM_LEFT_TAPE_POINT);
        System.out.println(exampleArray[0]);
    }
	
	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
     */
    public void disabledInit(){
    	oi.leftServo.set(oi.originalServoValue);    	oi.rightServo.set(oi.originalServoValue);
    }
	/**
	 * Run when robot is disabled
	 */
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString code to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the chooser code above (like the commented example)
	 * or additional comparisons to the switch structure below with additional strings & commands.
	 */
    public void autonomousInit() {
        autonomousCommand = (Command) chooser.getSelected();
        
		/* String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		switch(autoSelected) {
		case "My Auto":
			autonomousCommand = new MyAutoCommand();
			break;
		case "Default Auto":
		default:
			autonomousCommand = new ExampleCommand();
			break;
		} */
    	
    	// schedule the autonomous command (example)
        if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    /**
     * Used to start remote operation
     */
    public void teleopInit() {
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
        System.out.println("Teleop init starting");
        sameTime.start();
        
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        if(!sameTime.isRunning())
        {
        	System.out.println("Same time not running");
        }
        cam.drawQuad(rectImage);
        
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
