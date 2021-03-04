package com.robot.simulator.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

/**
 * @author dchhabra
 * Response class
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ResponseTO {
	
	private String statusMessage;
	private Object response;

}