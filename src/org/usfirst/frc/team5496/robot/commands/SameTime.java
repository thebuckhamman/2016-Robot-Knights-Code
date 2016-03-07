package org.usfirst.frc.team5496.robot.commands;

import org.usfirst.frc.team5496.robot.Robot;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */


public class SameTime extends CommandGroup {
    
    public  SameTime() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	
    	addParallel(Robot.driveRobot);
    	addParallel(Robot.useRamp);
    	
    	try{
    	System.out.println("sampImage starting");
    	addSequential(Robot.sampImage);
    	} catch(Throwable t)
    	{
    		System.out.println(t.toString());
    		System.out.println("SampleImage is the problem");
    	}
    	
    }
   
}
