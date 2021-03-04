package com.robot.simulator.model;

import lombok.Data;

/**
 * @author dchhabra
 * Simulator Request
 */
@Data
public class SimutatorRequest {
	public String starting_position;
    public String direction_facing;
    public String instruction;

}
