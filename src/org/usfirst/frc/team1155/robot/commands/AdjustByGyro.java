package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AdjustByGyro extends Command{

	public AdjustByGyro() {
		requires(Robot.drive);
		// Makes command interruptible
		setInterruptible(true);
		
	}
	@Override
	protected void initialize() {
		// Calibrates the turn angle
		Robot.drive.startAdjustment(Robot.drive.gyro.getAngle(), SmartDashboard.getNumber("TurnAngle"));
		// Sets PID of the PID controller to the values given on the SmartDashboard
		Robot.drive.getPIDController().setPID(SmartDashboard.getNumber("P"), SmartDashboard.getNumber("I"), SmartDashboard.getNumber("D"));
	}

	@Override
	protected void execute() {
		// Inserts the current angle of the gyro onto the SmartDashboard
		SmartDashboard.putNumber("GyroValue", Robot.drive.gyro.getAngle());
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
