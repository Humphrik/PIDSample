package org.usfirst.frc.team1155.robot.commands;

import org.usfirst.frc.team1155.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class AdjustByEncoder extends Command{

	public final double SENSOR_BUFFER = 50;
	public AdjustByEncoder() {
		requires(Robot.drive);        
		setInterruptible(true);
		
	}
	@Override
	protected void initialize() {
		Robot.drive.startAdjustment(0);
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean isFinished() {
		return Math.abs(Robot.drive.getPIDController().getError()) <= SENSOR_BUFFER;
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
