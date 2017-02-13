
package org.usfirst.frc.team1155.robot.subsystems;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 *
 */
public class DriveSubsystem extends PIDSubsystem {

	public CANTalon frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor;

	public Gyro gyro;

	public DriveSubsystem() {
		super("Drive", 0.1, 0, 0.1);
		frontLeftMotor = new CANTalon(0);
		frontRightMotor = new CANTalon(1);
		backRightMotor = new CANTalon(2);
		backLeftMotor = new CANTalon(3);
		LiveWindow.addActuator("Drive", "PIDSubsystem Controller", getPIDController());
		getPIDController().setContinuous(true);
		getPIDController().setPercentTolerance(20.0);
		// frontLeftMotor.setEncPosition(0);
		gyro = new AnalogGyro(0);
		gyro.reset();
	}

	public void startAdjustment(double current, double setPoint) {
		setPoint%=360;
		setSetpoint((int) (((current - setPoint >= 0 ? 180 : -180) + current - setPoint) / 360) * 360 + setPoint);
		enable();
	}

	public void endAdjustment() {
		getPIDController().disable();
	}

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	@Override
	protected double returnPIDInput() {
		return gyro.getAngle();
	}

	@Override
	protected void usePIDOutput(double output) {
		output *= 0.3;
		frontLeftMotor.pidWrite(output);
		frontRightMotor.pidWrite(output);
		backRightMotor.pidWrite(output);
		backLeftMotor.pidWrite(output);

	}

	public void setSpeed(double speed) {
		frontLeftMotor.set(speed);
		frontRightMotor.set(speed);
		backRightMotor.set(speed);
		backLeftMotor.set(speed);
	}

	public void resetGyro() {
		gyro.reset();
	}
}
