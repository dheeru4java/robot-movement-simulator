package com.robot.simulator.util;

import lombok.experimental.UtilityClass;

/**
 * @author dchhabra
 * Utility Class
 */
@UtilityClass
public class Constants {
	public static final String NORTH= "north";
	public static final String EAST= "east";
	public static final String SOUTH= "south";
	public static final String WEST= "west";
	public static final String LEFT= "L";
	public static final String RIGHT= "R";
	public static final String ADVANCE= "A";
	
	public static final String COMMA= ",";
	
	public static final String INVALID_DIRECTION = "The direction_facing value should be: north | east | south | west";
	public static final String NO_DIRECTION = "The direction_facing is not provided, it should be: north | east | south | west";
	
	public static final String INVALID_INSTRUCTION = "The instruction should contain characters: RLA";
	public static final String NO_INSTRUCTION = "The instruction is not provided, it should contain characters: RLA";
	
	public static final String NO_POSITION = "The starting_position is not provided or it is not valid x,y cordinates";
	public static final String INVALID_POSITION = "The starting_position is not valid form of x,y cordinates";
	public static final String INVALID_INTEGER_IN_POSITION = "The starting_position is not valid x,y cordinates in integer";
	
	
}
