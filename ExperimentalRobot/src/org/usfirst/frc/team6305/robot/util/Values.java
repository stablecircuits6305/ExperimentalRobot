package org.usfirst.frc.team6305.robot.util;

import org.usfirst.frc.team6305.robot.functions.DoubleToDoubleFunction;

public final class Values {
	
	public static final int DEFAULT_NUMBER_OF_BITS = 12;
	
	
	
	public static DoubleToDoubleFunction mapRange(double minInputValue, double maxInputValue, double minOutputValue, double maxOutputValue){
		double factor = (maxOutputValue - minOutputValue)/(maxInputValue - minInputValue);
		return new DoubleToDoubleFunction(){
			@Override
			public double applyAsDouble(double num){
				if(num <= minInputValue) return minOutputValue;
				if(num >= maxInputValue) return maxOutputValue;
				double output= minOutputValue + ((num - minInputValue) * factor);
				if(output < minOutputValue){
					output = minOutputValue;
				}
				else if(output > maxOutputValue) output = maxOutputValue;
				return output;
				}
			};
	}
	public static interface RangeMaker{
		DoubleToDoubleFunction toRange(double minOutputValue, double maxOutputValue);
	}
	
	public static RangeMaker mapRange(double minInputValue, double maxInputValue){
		return(minOutput, maxOutput)->{
			return mapRange(minInputValue, maxInputValue, minOutput, maxOutput);
		};
	}
	
	public static DoubleToDoubleFunction symmetricLimiter(double min, double max){
		if(min < 0) throw new IllegalArgumentException("The minimum value can't be negative");
		if(max < 0) throw new IllegalArgumentException("The maximum value can't be negative");
		if(max < min) throw new IllegalArgumentException("The min must be less than or equal to max");
		return new DoubleToDoubleFunction(){
			@Override
			public double applyAsDouble(double num){
				if(num > max)return max;
				double positiveNum = Math.abs(num);
				if(positiveNum > max)return -max;
				return positiveNum > min ? num : 0.0;
			}
		};
	}
	
	public static double symmetricLimit(double min, double num, double max){
		if(min < 0) throw new IllegalArgumentException("The min can't be negative");
		if(max < 0) throw new IllegalArgumentException("The max can't be negative");
		if(max < min) throw new IllegalArgumentException("The min must be less than or equal to max");
		if(num > max)return max;
		double positiveNum = Math.abs(num);
		if(positiveNum > max){
			return -max;
		}
		return positiveNum > min ? num : 0.0;
	}
	
	public static DoubleToDoubleFunction limiter(double min, double max){
		if(max < min) throw new IllegalArgumentException("The min must be less than or equal to max");
		return new DoubleToDoubleFunction(){
			@Override
			public double applyAsDouble(double value){
				if(value > max) return max;
				if(value < min) return min;
				return value;
			}
		};
	}
	
	public static double limit(double min, double num, double max){
		if(max < min) throw new IllegalArgumentException("The min must be less than or equal to max");
		if(num > max) return max;
		if(num < min) return min;
		return min;
	}
	
	public static int fuzzyCompare(double a, double b, double tolerance){
		if(tolerance < 0.0)throw new IllegalArgumentException("The tolerance can't be negative");
		double difference = a-b;
		return(Math.abs(difference) <= tolerance ? 0 : (difference > 0 ? 1 : -1));
		}
	
	private static double calcTolerance(int bits){
		return 1.0/(1 << bits);
	}
	
	public static int fuzzyCompare(double first, double second, int bits){
		return fuzzyCompare(first, second, calcTolerance(bits));
	}
	
	public static int fuzzyCompare(double first, double second) {
		return fuzzyCompare(first, second, DEFAULT_NUMBER_OF_BITS);
	}
	
	
	
}
