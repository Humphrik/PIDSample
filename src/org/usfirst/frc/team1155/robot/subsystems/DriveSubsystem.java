
package org.usfirst.frc.team1155.robot.subsystems;

import edu.wpi.first.wpilibj.CANTalon;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class DriveSubsystem extends PIDSubsystem {
	
	public CANTalon frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor;	
	
    
	public DriveSubsystem() {
		super("Drive", 1.0, 0, 0);
		frontLeftMotor = new CANTalon(0);
		frontRightMotor = new CANTalon(1);
		backRightMotor = new CANTalon(2);
		backLeftMotor = new CANTalon(3);
		LiveWindow.addActuator("Drive", "PIDSubsystem Controller", getPIDController());
		getPIDController().setContinuous(true);
	}
	
	
	public void startAdjustment(int setPoint) {
		setSetpoint(setPoint);
		enable();
	}
	
	public void endAdjustment() {
		getPIDController().disable();
	}
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

	@Override
	protected double returnPIDInput() {
		return frontLeftMotor.getEncPosition();
	}

	@Override
	protected void usePIDOutput(double output) {
		frontLeftMotor.pidWrite(output);
		frontRightMotor.pidWrite(output);
		backRightMotor.pidWrite(output);
		backLeftMotor.pidWrite(output);
		
	}
	
	public void setSpeed(double speed){
		frontLeftMotor.set(speed);
		frontRightMotor.set(speed);
		backRightMotor.set(speed);
		backLeftMotor.set(speed);
	}
}

