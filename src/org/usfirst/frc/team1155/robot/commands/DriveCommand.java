
package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;
import org.usfirst.frc.team1155.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 *
 */
public class DriveCommand extends Command {
	
	private Joystick movementStick;
    public DriveCommand(Joystick stick) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drive);
        // Initialize joystick used for setting speed.
        movementStick = stick;
        // Makes command interruptible.
        setInterruptible(true);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	// Puts the current value of the gyro onto the Smart Dashboard
    	SmartDashboard.putNumber("GyroValue", Robot.drive.gyro.getAngle());
    	// Rotates the robot; speed correlated with magnitude of joystick on the y axis
    	Robot.drive.setSpeed(-movementStick.getY());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	// Ghetto while(true){}
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drive.setSpeed(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
    
}
