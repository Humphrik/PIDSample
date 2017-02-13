package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AdjustByGyro extends Command{

	public final double SENSOR_BUFFER = 50;
	public AdjustByGyro() {
		requires(Robot.drive);        
		setInterruptible(true);
		
	}
	@SuppressWarnings("deprecation")
	@Override
	protected void initialize() {
		Robot.drive.startAdjustment(Robot.drive.gyro.getAngle());
		Robot.drive.getPIDController().setPID(SmartDashboard.getDouble("P"), SmartDashboard.getDouble("I"), SmartDashboard.getDouble("D"));
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		// System.out.println(Robot.drive.frontLeftMotor.getEncPosition());
	}

	@Override
	protected boolean isFinished() {
		// return Math.abs(Robot.drive.getPIDController().getAvgError()) <= SENSOR_BUFFER;
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
