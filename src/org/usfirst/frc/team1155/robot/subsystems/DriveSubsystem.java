
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
		// Constructor sets default P, I, D values.
		frontLeftMotor = new CANTalon(0);
		frontRightMotor = new CANTalon(1);
		backRightMotor = new CANTalon(2);
		backLeftMotor = new CANTalon(3);
		// Initializes wheels.
		LiveWindow.addActuator("Drive", "PIDSubsystem Controller", getPIDController());
		// Used in test mode; not implemented yet
		getPIDController().setContinuous(true);
		// Sets value to singular setpoint
		getPIDController().setPercentTolerance(20.0);
		// 20% tolerable percent error.
		gyro = new AnalogGyro(0);
		gyro.reset();
		// Initializes and reset gyro.
	}

	public void startAdjustment(double current, double setPoint) {
		// Enables PIDController with given setpoint.
		setPoint %= 360;
		// Sets angle to corresponding reference angle.
		setSetpoint((int) (((current - setPoint >= 0 ? 180 : -180) + current - setPoint) / 360) * 360 + setPoint);
		// Rounds difference of current and desired angle to nearest 360
		// degrees, then adds the desired angle to make final set point.
		enable();
	}

	public void endAdjustment() {
		// Disable PID Controller
		getPIDController().disable();
	}

	public void initDefaultCommand() {
	}

	@Override
	protected double returnPIDInput() {
		// Returns input to be used by the PIDController
		return gyro.getAngle();
	}

	@Override
	protected void usePIDOutput(double output) {
		// Set wheels to values given by PIDController
		output *= 0.3;
		frontLeftMotor.pidWrite(output);
		frontRightMotor.pidWrite(output);
		backRightMotor.pidWrite(output);
		backLeftMotor.pidWrite(output);

	}

	public void setSpeed(double speed) {
		// Sets wheels to given speed.
		frontLeftMotor.set(speed);
		frontRightMotor.set(speed);
		backRightMotor.set(speed);
		backLeftMotor.set(speed);
	}

	public void resetGyro() {
		// Resets gyro.
		gyro.reset();
	}
}
