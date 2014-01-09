/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Talon;

public class RobotTemplate extends SimpleRobot {

    Joystick driveStick;
    Talon frontLeft;
    Talon rearLeft; 
    Talon frontRight;
    Talon rearRight;
    RobotDrive mainDrive;
    double DEADZONE=.05;
    
    //Counter for teleOp loops
    int count=0;
    
    public void robotInit(){
        driveStick= new Joystick(1);
        frontLeft= new Talon(1);
        rearLeft= new Talon(2);
        frontRight= new Talon(3);
        rearRight= new Talon(4);
        mainDrive=new RobotDrive(frontLeft,rearLeft,frontRight,rearRight);
    }
    
    public void autonomous() {
            
    }

    public void operatorControl() {
        while(isOperatorControl()&&isEnabled()){
            //Simple Cartesian Drive
            /* mainDrive.mecanumDrive_Cartesian(
            driveStick.getAxis(Joystick.AxisType.kX),
            driveStick.getAxis(Joystick.AxisType.kY), 0,0);
            */
            
            //Cartesian Drive with Deadzones and Turning
            mainDrive.mecanumDrive_Cartesian(
                  joystickDeadzone(driveStick.getAxis(Joystick.AxisType.kX)),
                  joystickDeadzone(driveStick.getAxis(Joystick.AxisType.kY)),
                  driveStick.getAxis(Joystick.AxisType.kTwist),0);
          
               
            //Simple Polar test Drive with throttle as magnitude, NOTE: is backwards
            /*mainDrive.mecanumDrive_Polar(
             * driveStick.getAxis(Joystick.AxisType.kThrottle),
             * 0, 0);*/
            
            
            //Simple curve Test Drive
            //mainDrive.drive(driveStick.getAxis(Joystick.AxisType.kThrottle), 0);
            
            //logger
            if(count%500==0){System.out.println(count+": "+
                    frontLeft.getSpeed()+", "+
                    rearLeft.getSpeed()+", "+
                    frontRight.getSpeed()+", "+
                    rearRight.getSpeed());
            }
            count++;
        }
    }
    
    
    //creates deadzones ie. returns zero when within DEADZONE from center
    private double joystickDeadzone(double a){
        if(Math.abs(a)>DEADZONE){
           return a ;
        }
        
        return 0;
    }
    
    
    public void disabled(){
        mainDrive.mecanumDrive_Cartesian(0, 0, 0, 0);
    }
}
