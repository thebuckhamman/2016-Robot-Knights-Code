package org.usfirst.frc.team5496.robot.subsystems;

import org.usfirst.frc.team5496.robot.Robot;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class camera extends Subsystem {
	public static  Image frame;
	public static  Image frame2;
	public  int session;
	public int imaqError;
	double minPartArea = 0.5;
	public static NIVision.Range TOTE_HUE_RANGE = new NIVision.Range(110, 130);	//Green sat for dark image
	public static NIVision.Range TOTE_SAT_RANGE = new NIVision.Range(88, 255);	//Default saturation range for yellow tote
	public static NIVision.Range TOTE_VAL_RANGE = new NIVision.Range(134, 255);
	
	/*-------------------------------------------------------------------------------------------
	 * DECLARING CONSTANTS
	 * ------------------------------------------------------------------------------------------*/
	 //diagonal angle of camera in degrees
	 public final double CAMERA_DIAGONAL_ANGLE_DEGREES = 68.5; 
	 //diagonal angle of camera in radians
	 public final double CAMERA_DIAGONAL_ANGLE_RADIANS = CAMERA_DIAGONAL_ANGLE_DEGREES*2*(Math.PI)/360; 
	 
	 /*Angle created by the intersection of a line parallel to the ground and the line of sight of the camera;
		angle camera tilted up from ground, negative if tilted down, in degrees*/
	 public final double CAMERA_TILT_ANGLE_DEGREES = 45; 
	 //Camera tilt angel in radians
	 public final double CAMERA_TILT_ANGLE_RADIANS = CAMERA_TILT_ANGLE_DEGREES*2*(Math.PI)/360;
	 
	 
	 /*In adjusted pixels:
		Total distance of the camera's x-axis(0),
		Total distance of the camera's y-axis(1)
	  ; the camera's resolution is represented by CAMERA_RESOLUTION[0]xCAMERA_RESOLUTION[1] 
	  (should be (2560/3)x480), and is adjusted to be closest to screen resolution*/
	 public final double[] CAMERA_RESOLUTION = {2560/3, 480};
	 
	 /*In pixels:
		Total distance of the screen's x-axis(0),
		Total distance of the screen's y-axis(1)
	  ; the screen's resolution is represented by 
	  CAMERA_RESOLUTION[0]xCAMERA_RESOLUTION[1] (should be 640x480)*/
	 public final double[] SCREEN_RESOLUTION = {640, 480};
	 
	 
	 /*NOTE: SUBJECT TO CHANGE GIVEN NEW INFORMATION
	  * In inches, camera's:
		Distance ahead of launcher, negative for behind(0), 
		Right distance away, negative for left(1), 
		Distance above, negative for lower(2)*/
	 public final double[] CAMERA_LAUNCHER_DISPLACEMENT = {0, -9.25, 0};
	 
	 /*NOTE: SUBJECT TO CHANGE GIVEN NEW INFORMATION
	  * Standard displacement of launcher from point on floor directly below middle of goal
	  * Launcher, from reference point, in inches:
			Distance backwards(0), 
			Right distance away, negative for left(1), 
			Distance below(2)
		*/
	 public final double[] STANDARD_LAUNCHER_DISPLACEMENT = {140, 0, -19.5};
	 public final double[] STANDARD_CAMERA_DISPLACEMENT = pointFromCamera(STANDARD_LAUNCHER_DISPLACEMENT);
	 
	 /*
	  * Points, from the point on the ground directly below the middle of the goal, in inches:
	  * 	Distance behind(0),
	  * 	Left distance away, negative for right(1),
	  * 	Distance above(2)
	  * Values representing positions so you can add them to another displacement to get the total displacement
	  * (Point)_CAM represents respctive point displacement from camera*/
	 public final double[] TOP_LEFT_TAPE_POINT = {0, 10, 95};
	 public final double[] TOP_LEFT_TAPE_POINT_CAM = addArrays(STANDARD_CAMERA_DISPLACEMENT, TOP_LEFT_TAPE_POINT);
	 
	 public final double[] BOTTOM_LEFT_TAPE_POINT = {0, 10, 83};
	 public final double[] BOTTOM_LEFT_TAPE_POINT_CAM = addArrays(STANDARD_CAMERA_DISPLACEMENT, BOTTOM_LEFT_TAPE_POINT);
	 
	 public final double[] TOP_RIGHT_TAPE_POINT = {0, -10, 95};
	 public final double[] TOP_RIGHT_TAPE_POINT_CAM = addArrays(STANDARD_CAMERA_DISPLACEMENT, TOP_RIGHT_TAPE_POINT);

	 public final double[] BOTTOM_RIGHT_TAPE_POINT = {0, -10, 83};
	 public final double[] BOTTOM_RIGHT_TAPE_POINT_CAM = addArrays(STANDARD_CAMERA_DISPLACEMENT, BOTTOM_RIGHT_TAPE_POINT);

	 //Brightness values for USB camera
	 public final int defaultBrightVal = 50;
	 public final int minBrightVal = 0;
	 
	 /*=====================================================================================================================================
	  * STARTING COMMANDS
	  * ====================================================================================================================================*/
	 
    public void initDefaultCommand() {
        System.out.println("$$$");
    	//Initializing the two frames and session for the usb cam
    	frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
    	frame2 = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_U8, 0);
        session = NIVision.IMAQdxOpenCamera("cam0",
                NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        
        NIVision.IMAQdxConfigureGrab(session);
        
        //Setting brightness to minimum amount to help with tape
        NIVision.IMAQdxSetAttributeString(session, "CameraAttributes::Brightness::Mode", "Manual");
    	long minv = NIVision.IMAQdxGetAttributeMinimumI64(session, "CameraAttributes::Brightness::Value");//30
        long maxv = NIVision.IMAQdxGetAttributeMaximumI64(session, "CameraAttributes::Brightness::Value");//255
        long val = minv + (long) (((double) (maxv - minv)) * (((double) defaultBrightVal) / 100.0));
        NIVision.IMAQdxSetAttributeI64(session, "CameraAttributes::Brightness::Value", val);
        
        //Declaring the start of the camera
        System.out.println("starting camera");
       
        
        NIVision.IMAQdxStartAcquisition(session);
      
    }
    
    /**
     * Takes string "min" or "def" and applies minimum or default brightness to image
     * */
    public void setBrightness(String brightness)
    {
    	if(brightness.equals("min"))
    	{
    		NIVision.IMAQdxSetAttributeString(session, "CameraAttributes::Brightness::Mode", "Manual");
        	long minv = NIVision.IMAQdxGetAttributeMinimumI64(session, "CameraAttributes::Brightness::Value");
            long maxv = NIVision.IMAQdxGetAttributeMaximumI64(session, "CameraAttributes::Brightness::Value");
            long val = minv + (long) (((double) (maxv - minv)) * (((double) minBrightVal) / 100.0));
            NIVision.IMAQdxSetAttributeI64(session, "CameraAttributes::Brightness::Value", val);
    	}
    	else if(brightness.equals("def"))
    	{
    		NIVision.IMAQdxSetAttributeString(session, "CameraAttributes::Brightness::Mode", "Manual");
        	long minv = NIVision.IMAQdxGetAttributeMinimumI64(session, "CameraAttributes::Brightness::Value");
            long maxv = NIVision.IMAQdxGetAttributeMaximumI64(session, "CameraAttributes::Brightness::Value");
            long val = minv + (long) (((double) (maxv - minv)) * (((double) defaultBrightVal) / 100.0));
            NIVision.IMAQdxSetAttributeI64(session, "CameraAttributes::Brightness::Value", val);
    	}
    }
    
    /**
     * Adds to equal-lengthed arrays together and returns an array of their length
     **/
    public double[] addArrays(double[] a, double[] b)
    {
    	double c[];
    	//checks if the arrays are the same length 
    	if(a.length == b.length)
    	{
    		//adds the values
    		c = new double[b.length];
    		for(int i = 0; i < c.length; i++)
    		{
    			c[i] = a[i] + b[i];
    		}
    	}
    	//Returns an empty array and prints an error message
    	else
    	{
    		c = new double[0];
    		System.out.println("Arrays are not equal");
    	}
    	return c;
    }
    
    /** ------------------------------------------------------------------------------------------------------
     * Takes the displacement of the launcher from a reference point and uses the displacement
     * of the camera from the launcher to returns an array representing the displacement
     * of the camera from the reference point.
     * ------------------------------------------------------------------------------------------------------
     * */
    public double[] pointFromCamera
    (/*Launcher, from reference point, in inches:
			Distance backwards(0), 
			Right distance away, negative for left(1), 
			Distance below(2)*/
    double[] launcherDisp	 	 
    )
    {
    /*Camera distance, in inches:
		Distance backwards(0), 
		Right distance away, negative for left(1), 
		Distance below(2)*/     	
    double[] totalDisp = new double[3];		
    
    //Adjust to figure out total displacement
    totalDisp[0] = launcherDisp[0] - CAMERA_LAUNCHER_DISPLACEMENT[0];
    totalDisp[1] = launcherDisp[1] + CAMERA_LAUNCHER_DISPLACEMENT[1];
    totalDisp[2] = launcherDisp[2] - CAMERA_LAUNCHER_DISPLACEMENT[2];
    
    return totalDisp;
    }
    
    
    
    /** ------------------------------------------------------------------------------------------------------
     * Takes the displacement of the camera from a reference point and uses the displacement
     * of the camera from the launcher to returns an array representing the displacement
     * of the launcher from the reference point.
     * ------------------------------------------------------------------------------------------------------
     * */
    public double[] pointFromLauncher
    (/*Camera, from reference point, in inches:
			Distance backwards(0), 
			Right distance away, negative for left(1), 
			Distance below(2)*/
    double[] camDisp	 	 
    )
    {
    /*Launcher distance, in inches:
		Distance backwards(0), 
		Right distance away, negative for left(1), 
		Distance below(2)*/     	
    double[] totalDisp = new double[3];		
    
    //Adjust to figure out total displacement
    totalDisp[0] = camDisp[0] + CAMERA_LAUNCHER_DISPLACEMENT[0];
    totalDisp[1] = camDisp[1] - CAMERA_LAUNCHER_DISPLACEMENT[1];
    totalDisp[2] = camDisp[2] + CAMERA_LAUNCHER_DISPLACEMENT[2];
    
    return totalDisp;
    }
    
    /**Calculates the horizontal and vertical screen angles, returning an array
     * hvScreenAngles in which hvScreenAngles[0] is the horizontal angle and 
     * hvScreenAngles[1] is the vertical angle.  
     * */
    public  double[] hvScreenAngle()
    {
    	double[] hvScreenAngles = new double[2];
    	/* To calculate the angles, I use this formula:
    	 * For a lens projecting a rectangular image (focused at infinity, which I am assuming),
    	 *  the angle of view (@ in radians) can be calculated from the chosen dimension (d, in this case in pixels),
    	 *  and effective focal length (f, in pixels) as follows: @ = 2arctan(d/2f)
    	 *  
    	 * */
    	
    	//Diagonal for camera resolution in pixels
    	double totalDiag = Math.sqrt(CAMERA_RESOLUTION[0]*CAMERA_RESOLUTION[0] + CAMERA_RESOLUTION[1]*CAMERA_RESOLUTION[1]);
    	
    	//Given @ = 2arctan(d/2f), it also follows 2f = d/(tan(@/2)). 
    	//So we use the information we have to calculate 2f.
    	double twoF = totalDiag/(Math.tan(CAMERA_DIAGONAL_ANGLE_RADIANS/2));
    	
    	//Use 2f to calculate horizontal and vertical angles of display on screen
    	hvScreenAngles[0] =  2*Math.atan(SCREEN_RESOLUTION[0]/twoF);
    	hvScreenAngles[1] =  2*Math.atan(SCREEN_RESOLUTION[1]/twoF);
    	
    	return hvScreenAngles;
    }
    
    /**
     * Takes the camera displacement from reference point and calculates what the coordinates on the camera
     * the point would show up on and returns a NIVision.Point representing it.
     *
     * */
    public  NIVision.Point calculateCoordinate
    (
    double[] camDisp 		    /*Camera, from reference point, in inches:
    								Distance backwards(0), 
    								Right distance away, negative for left(1), 
    								Distance below(2)*/
    )
    {
    	NIVision.Point coordinate;
    	int x;
    	int y;
    	
    	//Retrieve the horizontal and vertical screen angles for the calculations
    	double[] angles = hvScreenAngle();
    	double camHorzAngle =  angles[0];
    	double camVertAngle =  angles[1];
    	
    	//Calculate distance between reference point and camera (D^2 = A^2 + B^2 + C^2)
    	double camDist = Math.sqrt(camDisp[0]*camDisp[0] + camDisp[1]*camDisp[1] + camDisp[2]*camDisp[2]);
    	
    	//Use camDist to calculate horizontal and vertical angle of reference point from camera (adjust vertical angle with camTiltAngle)
    	//Couldn't think of good variable names
    	//EDIT 2/19/16: Due to new information on the fact we couldn't rotate the screen, whe flipped camDisp[1] 
    	//and cam[2] in these equations
    	//EDIT 2/23/16: Changed "- CAMERA_TILT_ANGLE_RADIANS" to distHorzAngle due to fliping the screen
    	double distHorzAngle = Math.asin(camDisp[2]/camDist) - CAMERA_TILT_ANGLE_RADIANS;
    	double distVertAngle = Math.asin(camDisp[1]/camDist);
    	
    	/*The center x is SCREEN_RESOLUTION[0]/2 and the center y is SCREEN_RESOLUTION[1]/2
    	 *To adjust x, ADD(the x-axis correct) the portion of the angle(dhorz/camhorz) multiplied by the entirety of the x-axis in pixels
    	 *To adjust y, SUBTRACT(the y-axis is inverted in dashboard) the portion of the angle(dvert/camvert) multiplied by the entirety of the y-axis in pixels
    	 *Round doubles to integers by adding 0.5 and casting them to integers*/
    	x = (int)(SCREEN_RESOLUTION[0]/2 - (distHorzAngle/camHorzAngle)*(SCREEN_RESOLUTION[0]) + 0.5);
    	y = (int)(SCREEN_RESOLUTION[1]/2 - (distVertAngle/camVertAngle)*(SCREEN_RESOLUTION[1]) + 0.5);
    	
    	coordinate = new NIVision.Point(x, y);
    	return coordinate;
    }
    
    
    /**
     * Takes an NIVision point and an integer and returns an array of two three values
     * 
     * The point is a reference point shown on the camera
     * 
     * The integer refers to the index of the returned array that will have the value of 1
     * 
     * These three values, if represented by a,b, and c, fit the equation ax = by = cz where, where x,y,z represent
     * the camera's, from the reference point, in inches:
     * 	 Distance backwards(x), 
     *   Right distance away, negative for left(y),
     *   Distance below(z)
     *    
     *  Used to determine a range of camera displacements a point could represent in reference to whatever 
     *  the index entered would represent	
     *   
     *  @param coor, constIndex
     * */
    public  double[] displacementRatios
    ( 		
    NIVision.Point coor  //Point on screen in which displacement range is calculated
    ,int constIndex      //Index on returned array in which the value will be 1; should be values 0,1, or 2
    )
    {
    	double[] ratios = new double[3];
    	
    	//Retrieve horizontal and vertical screen angles to run calculations
    	double[] angles = hvScreenAngle();
    	double camHorzAngle =  angles[0];
    	double camVertAngle =  angles[1];
    	
    	/*In the method calculateCoordinates, if I had not rounded I would have use the equations:
    	 *	x = SCREEN_RESOLUTION[0]/2 + (distHorzAngle/camHorzAngle)*(SCREEN_RESOLUTION[0])
    	 *	y = SCREEN_RESOLUTION[1]/2 - (distVertAngle/camVertAngle)*(SCREEN_RESOLUTION[1])
    	 *As such, to solve for the dist angles, we could reform the equations to read as below */
    	double distHorzAngle = (SCREEN_RESOLUTION[0]/2 - coor.x) * camHorzAngle / SCREEN_RESOLUTION[0];
    	double distVertAngle = (coor.y - SCREEN_RESOLUTION[1]/2) * camVertAngle / SCREEN_RESOLUTION[1];
    	
    	/*MATHEMATICAL REASONS FOR JAVA CODE, REFERENCES EQUATIONS FROM calculateCoordinates:
    	 *Calculations will be made under the presumption that the costIndex value will be 1 unit, as that will get the correct ratios.
    	 *  camDisp[0,1,2] = a,b,c for simplicity
    	 *  distVertAngle + CAMERA_TILT_ANGLE_RADIANS = totalVertAngle and
    	 *  sin(totalVertAngle)/sin(distHorzAngle) = angleRatio for more simplicity
    	 *  EDIT 2/19/16: cam[1] and cam[2] (and therefore b and c) have been switched in these equations
    	 *  due to the fact that the camera is flipped
    	 *
    	 * double distVerzAngle = Math.asin(c/camDist);
    	 * double totalHorzAngle = Math.asin(b/camDist) - CAMERA_TILT_ANGLE_RADIANS; 
    	 * 
    	 * b/camDist = b/SQRT(a^2 + b^2 + c^2) = sin(distHorzAngle)
    	 * c/camDist = c/SQRT(a^2 + b^2 + c^2) = sin(totalVertAngle)
    	 * 
    	 * SQRT(a^2 + b^2 + c^2) = c/sin(distHorzAngle) = b/sin(totalVertAngle)
    	 * c = angleRatio^-1*b, b = angleRatio*c
    	 * 
    	 * SQRT(a^2 + c^2 + angleRatio^2*c^2) = c/sin(distHorzAngle)
    	 * a^2 + c^2 + angleRatio^2*c^2 = c^2/sin^2(distHorzAngle)
    	 * a^2 = (cosec^2(distHorzAngle) - 1 - angleRatio^2)*c^2
    	 * a = c*SQRT((cosec^2(totalHorzAngle) - 1 - angleRatio^2)), c = a/SQRT((cosec^2(totalHorzAngle) - 1 - angleRatio^2)) 
    	 * 
    	 * SQRT(a^2 + angleRatio^-2*b^2 + b^2) = b/sin(totalVertAngle)
    	 * a^2 + angleRatio^-2*b^2 + b^2 = b^2/sin^2(totalVertAngle)
    	 * a^2 = (cos
    	 * ec^2(totalVertAngle) - 1 - angleRatio^-2)*b^2
    	 * a = b*SQRT((cosec^2(distVertAngle) - 1 - angleRatio^-2)), b = a/SQRT((cosec^2(distVertAngle) - 1 - angleRatio^-2))
    	 * 
    	 * For a=1:
    	 * c = 1/SQRT((cosec^2(totalHorzAngle) - 1 - angleRatio^2))  
    	 * b = 1/SQRT((cosec^2(distVertAngle) - 1 - angleRatio^-2))
    	 * 
    	 * For c=1:
    	 * a = SQRT((cosec^2(totalHorzAngle) - 1 - angleRatio^2))
    	 * b = angleRatio
    	 * 
    	 * For b=1:
    	 * a = SQRT((cosec^2(distVertAngle) - 1 - angleRatio^-2))
    	 * c = angleRatio^-1
    	 * */
    	double totalHorzAngle = distHorzAngle - CAMERA_TILT_ANGLE_RADIANS;
    	double angleRatio = Math.sin(distVertAngle)/Math.sin(totalHorzAngle);
    	//ratio[0], or a, = 1
    	if(constIndex == 0)
    	{
    		ratios[0] = 1;
    		ratios[2] = 1/Math.sqrt(1/(Math.sin(totalHorzAngle)*Math.sin(totalHorzAngle)) - 1 - angleRatio*angleRatio);
    		ratios[1] = 1/Math.sqrt(1/(Math.sin(distVertAngle)*Math.sin(distVertAngle)) - 1 - 1/(angleRatio*angleRatio));
    	}
    	//ratio[1], or b, = 1
    	else if(constIndex == 2)
    	{
    		ratios[0] = Math.sqrt(1/(Math.sin(totalHorzAngle)*Math.sin(totalHorzAngle)) - 1 - angleRatio*angleRatio);
    		ratios[2] = 1;
    		ratios[1] = angleRatio;
    	}
    	//ratio[2], or c, = 1
    	else if(constIndex == 1)
    	{
    		ratios[0] = Math.sqrt(1/(Math.sin(distVertAngle)*Math.sin(distVertAngle)) - 1 - 1/(angleRatio*angleRatio));
    		ratios[2] = 1/angleRatio;
    		ratios[1] = 1;
    	}
    	
    	return ratios;
    }
    
    /**
     * Draws a quadrilateral from an array of four points. Make sure arrayOfPoints[0] and [2], as well as [1] and 
     * [3] are opposite corners.  
     * @param arrayOfPoints
     */
    public void drawQuad(NIVision.Point[] arrayOfPoints)
    {
    	NIVision.IMAQdxGrab(session, frame, 1);
    	
    	NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, arrayOfPoints[0], arrayOfPoints[1], 255.0f);
    	NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, arrayOfPoints[1], arrayOfPoints[2], 255.0f);
    	NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, arrayOfPoints[2], arrayOfPoints[3], 255.0f);
    	NIVision.imaqDrawLineOnImage(frame, frame, DrawMode.DRAW_VALUE, arrayOfPoints[3], arrayOfPoints[0], 255.0f);
    	
    	CameraServer.getInstance().setImage(frame);
    }
}

