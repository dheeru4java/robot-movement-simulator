package com.robot.simulator.rest;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.robot.simulator.model.ResponseTO;
import com.robot.simulator.model.SimulatorResponse;
import com.robot.simulator.model.SimutatorRequest;
import com.robot.simulator.service.SimulatorService;

/**
 * @author dchhabra Rest Interface for Robot Simulator
 */
@RestController
public class RobotSimulatorController {

	@Autowired
	SimulatorService service;

	private static final Logger logger = LogManager.getLogger(RobotSimulatorController.class);

	@RequestMapping(value = { "/simulator" }, produces = { "application/json" }, method = { RequestMethod.POST })
	public ResponseEntity<?> getRobotMovement(@RequestBody SimutatorRequest simulatorRequest,
			HttpServletRequest request) {
		logger.info("Received request {}", simulatorRequest);
		String validationMessage = service.validateRequest(simulatorRequest);
		ResponseTO responseTO = new ResponseTO();
		if (validationMessage != null) {
			responseTO.setStatusMessage(validationMessage);
			return new ResponseEntity<Object>(responseTO, HttpStatus.BAD_REQUEST);
		}

//		SimulatorResponse simulatorResponse;
		try {
			SimulatorResponse simulatorResponse = service.getFinalPosition(simulatorRequest);
			responseTO.setResponse(simulatorResponse);
		} catch (Exception ex) {
			logger.error("Error in robot simulator, error is {}", ex.getMessage());
			logger.debug("Error in robot simulator", ex);
			responseTO.setStatusMessage("Internal server error while getting final movement of robot");
			return new ResponseEntity<Object>(responseTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Object>(responseTO, HttpStatus.OK);
	}
}
