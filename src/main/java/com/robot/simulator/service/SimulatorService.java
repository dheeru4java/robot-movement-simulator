package com.robot.simulator.service;

import static com.robot.simulator.util.Constants.COMMA;
import static com.robot.simulator.util.Constants.EAST;
import static com.robot.simulator.util.Constants.LEFT;
import static com.robot.simulator.util.Constants.NORTH;
import static com.robot.simulator.util.Constants.RIGHT;
import static com.robot.simulator.util.Constants.SOUTH;
import static com.robot.simulator.util.Constants.WEST;

import org.springframework.stereotype.Service;

import com.robot.simulator.model.SimulatorResponse;
import com.robot.simulator.model.SimutatorRequest;

/**
 * @author dchhabra
 * 
 *         Service class that contains all the required apis to serve the robot
 *         simulator movement
 */
@Service
public class SimulatorService {

	private String getDirection(String turn, String currentDirection) {
		String direction = null;
		if (currentDirection.equals(NORTH)) {
			if (turn.equals(LEFT)) {
				direction = WEST;
			} else if (turn.equals(RIGHT)) {
				direction = EAST;
			}
		} else if (currentDirection.equals(EAST)) {
			if (turn.equals(LEFT)) {
				direction = NORTH;
			} else if (turn.equals(RIGHT)) {
				direction = SOUTH;
			}
		} else if (currentDirection.equals(SOUTH)) {
			if (turn.equals(LEFT)) {
				direction = EAST;
			} else if (turn.equals(RIGHT)) {
				direction = WEST;
			}
		} else if (currentDirection.equals(WEST)) {
			if (turn.equals(LEFT)) {
				direction = SOUTH;
			} else if (turn.equals(RIGHT)) {
				direction = NORTH;
			}
		}
		return direction;
	}

	private String getPosition(String currentPosition, String currentDirection) {
		String finalPosition = null;
		String[] split = currentPosition.split(COMMA);
		int x = Integer.parseInt(split[0]);
		int y = Integer.parseInt(split[1]);

		if (currentDirection.equals(NORTH)) {
			y = y + 1;
		} else if (currentDirection.equals(SOUTH)) {
			y = y - 1;
		} else if (currentDirection.equals(EAST)) {
			x = x + 1;
		} else if (currentDirection.equals(WEST)) {
			x = x - 1;
		}

		finalPosition = x + COMMA + y;

		return finalPosition;
	}

	/**
	 * @param simulatorRequest
	 * @return <code>SimulatorResponse</code>
	 * process the provided instructions and gets the final position of the robot
	 */
	public SimulatorResponse getFinalPosition(SimutatorRequest simulatorRequest) {
		String direction_facing = simulatorRequest.getDirection_facing();
		String instruction = simulatorRequest.getInstruction();
		String currentPosition = simulatorRequest.getStarting_position();

		for (Character move : instruction.toCharArray()) {
			if (move == 'R') {
				direction_facing = getDirection(move.toString(), direction_facing);
			} else if (move == 'L') {
				direction_facing = getDirection(move.toString(), direction_facing);
			} else if (move == 'A') {
				currentPosition = getPosition(currentPosition, direction_facing);
			}
		}

		SimulatorResponse response = new SimulatorResponse();
		response.setDirection_facing(direction_facing);
		response.setFinal_position(currentPosition);

		return response;
	}

	/**
	 * @param simulatorRequest
	 * @return <code>String</code> errorMessage in case of any invalid parameter provided in input
	 * validate the robot simulator request
	 */
	public String validateRequest(SimutatorRequest simulatorRequest) {

		String errorMessage = validateInstruction(simulatorRequest.getInstruction());

		if (errorMessage == null) {
			errorMessage = validateDirection(simulatorRequest.getDirection_facing());
		}

		if (errorMessage == null) {
			errorMessage = validatePosition(simulatorRequest.getStarting_position());
		}

		return errorMessage;
	}

	/**
	 * @param starting_position
	 * @return <code>String</code> errorMessage in case of any invalid parameter given in position
	 */
	private String validatePosition(String starting_position) {
		String errorMessage = null;
		if (starting_position != null && !starting_position.isEmpty() && starting_position.contains(COMMA)) {
			String[] split = starting_position.split(COMMA);
			if (split.length == 2) {
				try {
					int x = Integer.parseInt(split[0]);
					int y = Integer.parseInt(split[1]);
				} catch (NumberFormatException nfe) {
					errorMessage = "The starting_position is not valid x,y cordinates in integer";
				}
			} else {
				errorMessage = "The starting_position is not valid form of x,y cordinates";
			}

		} else {
			errorMessage = "The starting_position is not provided or it is not valid x,y cordinates";
		}

		return errorMessage;
	}

	/**
	 * @param direction_facing
	 * @return <code>String</code> errorMessage in case of any invalid parameter given in direction
	 */
	private String validateDirection(String direction_facing) {
		String errorMessage = null;
		if (direction_facing != null && !direction_facing.isEmpty()) {
			if (!(direction_facing.equals(NORTH) || direction_facing.equals(EAST) || direction_facing.equals(SOUTH)
					|| direction_facing.equals(WEST))) {
				errorMessage = "The direction_facing value should be: north | east | south | west";
			}
		} else {
			errorMessage = "The direction_facing is not provided, it should be: north | east | south | west";
		}
		return errorMessage;
	}

	/**
	 * @param instruction
	 * @return <code>String</code> errorMessage in case of any invalid parameter given in instruction
	 */
	private String validateInstruction(String instruction) {
		String errorMessage = null;
		if (instruction != null && !instruction.isEmpty()) {
			for (char c : instruction.toCharArray()) {
				if ("RLA".indexOf(c) < 0) {
					errorMessage = "The instruction should contain characters: RLA";
				}
			}
		} else {
			errorMessage = "The instruction is not provided, it should contain characters: RLA";
		}

		return errorMessage;
	}

}
