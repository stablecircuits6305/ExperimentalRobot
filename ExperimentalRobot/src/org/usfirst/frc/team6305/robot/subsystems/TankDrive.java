package org.usfirst.frc.team6305.robot.subsystems;

import org.usfirst.frc.team6305.robot.RobotMap;

import org.usfirst.frc.team6305.robot.functions.DoubleToDoubleFunction;
import org.usfirst.frc.team6305.robot.util.Values;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */

public class TankDrive extends Subsystem {
	
	public static final double min = .02;
	public static final double max = 1;
	public static final DoubleToDoubleFunction DEFAULT_SPEED_LIMITER = Values.symmetricLimiter(min, max);
	

	private static final double SENSITIVITY_TURN = 1.0;
	
	private final Spark frontLeft = new Spark(RobotMap.frontLeft);
	private final Spark frontRight = new Spark(RobotMap.frontRight);
	private final Spark backLeft = new Spark(RobotMap.backLeft);
	private final Spark backRight = new Spark(RobotMap.backRight);
	
	public static TankDrive instance;
	
	private DoubleToDoubleFunction speedLimiter;
	
	private double quickStopAccumulator = 0;
	
	public void setLeftSpeed(double leftSpeed) {
		frontLeft.set(-leftSpeed);
		backLeft.set(-leftSpeed);
	}
	
	public void setRightSpeed(double rightSpeed) {
		frontRight.set(rightSpeed);
		backRight.set(rightSpeed);
	}
	
	public void wheel(double wheel) {
		frontRight.set(wheel);
		frontLeft.set(-wheel);
	}
	
	public void wheelThrottle(double throttle, double wheel, boolean isQuickTurn) {
		
		if(isQuickTurn){
			frontLeft.set(throttle + wheel);
			backLeft.set(throttle + wheel);
			frontRight.set(throttle-wheel);
			backRight.set(throttle-wheel)

		}

		else{
			frontLeft.set(throttle + abs(throttle) * wheel);
			backLeft.set(throttle + abs(throttle) * wheel);
			frontRight.set(throttle - abs(throttle) * wheel);
			backRight.set(throttle - abs(throttle) * wheel); 
		}
		
		
// 		double overPower;
// 		double angularPower;
		
// 		if(isQuickTurn) {
// 			if(Math.abs(throttle) < min) {
// 				double alpha = .1;
// 				quickStopAccumulator = (1- alpha) * quickStopAccumulator + alpha * Values.symmetricLimit(0, wheel, 1) * 2;
// 			}
// 			overPower = 1.0;
// 			angularPower = wheel;
// 		}
// 			else {
// 				overPower = 0;
// 				angularPower = Math.abs(throttle) * wheel * SENSITIVITY_TURN - quickStopAccumulator;
// 				if(quickStopAccumulator > 1) quickStopAccumulator -= 1;
// 				else if(quickStopAccumulator < -1) quickStopAccumulator += 1;
// 				else quickStopAccumulator = 0.0;
// 			}
// 		double rightPwm = throttle - angularPower;
// 		double leftPwm = throttle + angularPower;
// 		if(leftPwm > 1.0) {
// 			rightPwm -= overPower * (leftPwm - 1);
// 			leftPwm = 1.0;
// 		}
// 		else if(rightPwm > 1.0) {
// 			leftPwm -= overPower * (rightPwm -1);
// 			rightPwm = 1.0;
// 		}
// 		else if(leftPwm < -1.0) {
// 			rightPwm += overPower * (-1 - leftPwm);
// 			leftPwm = -1.0;
// 		}
// 		else if(rightPwm < -1.0) {
// 			leftPwm += overPower * (-1 - rightPwm);
// 			rightPwm = -1.0;
// 		}
		
// 		TankDrive tank = new TankDrive();
// 		tank.setLeftSpeed(leftPwm);
// 		tank.setRightSpeed(rightPwm);
	}
	
	public void sendSparkData() {
		double frontLeftSpeed = frontLeft.get();
		double frontRightSpeed = frontRight.get();
		
		SmartDashboard.putNumber("Front Left", frontLeftSpeed);
		SmartDashboard.putNumber("Front Right", frontRightSpeed);
		
	}
	
	public static TankDrive getInstance() {
		return instance;
		
	}
	

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

