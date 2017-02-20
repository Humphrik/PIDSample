package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;
import org.usfirst.frc.team1155.robot.subsystems.DriveSubsystem.SensorMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AdjustBySensor extends Command{

	public AdjustBySensor() {
		requires(Robot.drive);
		// Makes command interruptible
		setInterruptible(true);
		
	}
	@Override
	protected void initialize() {
		// Calibrates the turn angle
		if(Robot.drive.sensorMode == SensorMode.GYRO)
			Robot.drive.startAdjustment(Robot.drive.gyro.getAngle(), SmartDashboard.getNumber("TurnAngle", 0));
		else
			Robot.drive.startAdjustment(Robot.drive.getEncDistance(), SmartDashboard.getNumber("DriveDistance", 0));
		// Sets PID of the PID controller to the values given on the SmartDashboard
		Robot.drive.getPIDController().setPID(SmartDashboard.getNumber("P", 0.1), SmartDashboard.getNumber("I", 0), SmartDashboard.getNumber("D", 0.1));
	}

	@Override
	protected void execute() {
		// Inserts the current angle of the gyro onto the SmartDashboard
		SmartDashboard.putNumber("GyroValue", Robot.drive.gyro.getAngle());
		SmartDashboard.putNumber("EncoderValue", Robot.drive.getEncDistance());
	}

	@Override
	protected boolean isFinished() {
		// Ends when the robot is at the desired angle
		return Robot.drive.getPIDController().onTarget();
	}

	@Override
	protected void end() {
		Robot.drive.endAdjustment();
		
	}

	@Override
	protected void interrupted() {
		end();
	}

}
