
package org.usfirst.frc.team1155.robot.subsystems;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class DriveSubsystem extends PIDSubsystem {

	public CANTalon frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor;

	public Gyro gyro;

	public enum SensorMode {
		GYRO, ENCODER;
	}

	public enum DriveMode {
		TURN, DRIVE;
	}

	public SensorMode sensorMode;
	public DriveMode driveMode;

	public final int WHEEL_RADIUS = 2;

	public DriveSubsystem() {
		super("Drive", 0.1, 0, 0.1);
		// Constructor sets default P, I, D values.

		sensorMode = SensorMode.GYRO;
		driveMode = DriveMode.TURN;

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
		gyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);
		gyro.reset();

		// Initializes and reset gyro.
	}

	public void initDefaultCommand() {
	}

	public void startAdjustment(double current, double setPoint) {
		if (sensorMode == SensorMode.GYRO) {
			// Enables PIDController with given setpoint.
			setPoint %= 360;
			// Sets angle to corresponding reference angle.
			setSetpoint((int) (((current - setPoint >= 0 ? 180 : -180) + current - setPoint) / 360) * 360 + setPoint);
			// Rounds difference of current and desired angle to nearest 360
			// degrees, then adds the desired angle to make final set point.
		} else {
			setSetpoint(setPoint);
		}
		enable();

	}

	public void endAdjustment() {
		// Disable PID Controller
		getPIDController().disable();
	}

	public double getEncDistance() {
		return (frontRightMotor.getEncPosition() / 1023.0) * (Math.PI * 2 * WHEEL_RADIUS);
	}

	@Override
	protected double returnPIDInput() {
		// Returns input to be used by the PIDController
		if (sensorMode == SensorMode.GYRO)
			return gyro.getAngle();
		return getEncDistance();
	}

	@Override
	protected void usePIDOutput(double output) {
		// Set wheels to values given by PIDController
		output *= 0.5;
		frontLeftMotor.pidWrite((sensorMode == SensorMode.GYRO) ? output : -output);
		frontRightMotor.pidWrite((sensorMode == SensorMode.GYRO) ? output : -output);
		backRightMotor.pidWrite(output);
		backLeftMotor.pidWrite(output);

	}

	public void setSpeed(double speed) {
		// Sets wheels to given speed.
		frontLeftMotor.set((driveMode == DriveMode.TURN) ? speed : -speed);
		frontRightMotor.set((driveMode == DriveMode.TURN) ? speed : -speed);
		backRightMotor.set(speed);
		backLeftMotor.set(speed);
	}

	public void resetGyro() {
		// Resets gyro.
		gyro.reset();
	}

	public void resetEnc() {
		frontRightMotor.reset();
	}
}
