package com.robot.simulator.rest;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.robot.simulator.model.ResponseTO;
import com.robot.simulator.model.SimulatorResponse;
import com.robot.simulator.model.SimutatorRequest;
import com.robot.simulator.util.Constants;

/**
 * @author dchhabra
 * Test class for RobotSimulatorController 
 */
@TestInstance(Lifecycle.PER_CLASS)
public class RobotSimulatorControllerTest extends AbstractTest {

	@BeforeAll
	public void init() {
		super.setUp();
	}

	/**
	 * @throws Exception Tests the valid move
	 */
	@Test
	public void testValidMove() throws Exception {
		String uri = "/simulator";
		SimutatorRequest request = new SimutatorRequest();
		request.setDirection_facing("north");
		request.setInstruction("RAALAL");
		request.setStarting_position("7,3");

		String inputJson = super.mapToJson(request);

		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		ResponseTO mapFromJson = super.mapFromJson(mvcResult.getResponse().getContentAsString(), ResponseTO.class);
		String mapToJson = super.mapToJson(mapFromJson.getResponse());
		SimulatorResponse simulatorResponse = super.mapFromJson(mapToJson, SimulatorResponse.class);
		assertEquals(simulatorResponse.getDirection_facing(), "west");
		assertEquals(simulatorResponse.getFinal_position(), "9,4");
	}

	/**
	 * @throws Exception To test invalid directions
	 */
	@Test
	public void testInvalidDirection() throws Exception {
		String uri = "/simulator";
		SimutatorRequest request = new SimutatorRequest();
		request.setInstruction("RAALAL");
		request.setStarting_position("7,3");

		// invalid value
		request.setDirection_facing("invalid");

		String inputJson = super.mapToJson(request);

		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(400, status);
		ResponseTO mapFromJson = super.mapFromJson(mvcResult.getResponse().getContentAsString(), ResponseTO.class);
		assertEquals(mapFromJson.getStatusMessage(), Constants.INVALID_DIRECTION);

		// invalid value
		request.setDirection_facing(null);

		inputJson = super.mapToJson(request);

		mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		status = mvcResult.getResponse().getStatus();
		assertEquals(400, status);
		mapFromJson = super.mapFromJson(mvcResult.getResponse().getContentAsString(), ResponseTO.class);
		assertEquals(mapFromJson.getStatusMessage(), Constants.NO_DIRECTION);
	}

	/**
	 * @throws Exception To test invalid value in position field
	 */
	@Test
	public void testInvalidPosition() throws Exception {
		String uri = "/simulator";
		SimutatorRequest request = new SimutatorRequest();
		request.setDirection_facing("north");
		request.setInstruction("RAALAL");

		// invalid value
		request.setStarting_position("7,3.1");

		String inputJson = super.mapToJson(request);

		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(400, status);
		ResponseTO mapFromJson = super.mapFromJson(mvcResult.getResponse().getContentAsString(), ResponseTO.class);
		assertEquals(mapFromJson.getStatusMessage(), Constants.INVALID_INTEGER_IN_POSITION);

		// invalid format
		request.setStarting_position("7,3,5");

		inputJson = super.mapToJson(request);

		mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		status = mvcResult.getResponse().getStatus();
		assertEquals(400, status);
		mapFromJson = super.mapFromJson(mvcResult.getResponse().getContentAsString(), ResponseTO.class);
		assertEquals(mapFromJson.getStatusMessage(), Constants.INVALID_POSITION);

		// No Position
		request.setStarting_position(null);

		inputJson = super.mapToJson(request);

		mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		status = mvcResult.getResponse().getStatus();
		assertEquals(400, status);
		mapFromJson = super.mapFromJson(mvcResult.getResponse().getContentAsString(), ResponseTO.class);
		assertEquals(mapFromJson.getStatusMessage(), Constants.NO_POSITION);
	}

	/**
	 * @throws Exception To test invalid value in instruction field
	 */
	@Test
	public void testInvalidInstruction() throws Exception {
		String uri = "/simulator";
		SimutatorRequest request = new SimutatorRequest();
		request.setStarting_position("7,3");
		request.setDirection_facing("north");

		// invalid value
		request.setInstruction("RAALALX");

		String inputJson = super.mapToJson(request);

		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(400, status);
		ResponseTO mapFromJson = super.mapFromJson(mvcResult.getResponse().getContentAsString(), ResponseTO.class);
		assertEquals(mapFromJson.getStatusMessage(), Constants.INVALID_INSTRUCTION);

		// no instruction
		request.setInstruction(null);

		inputJson = super.mapToJson(request);

		mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		status = mvcResult.getResponse().getStatus();
		assertEquals(400, status);
		mapFromJson = super.mapFromJson(mvcResult.getResponse().getContentAsString(), ResponseTO.class);
		assertEquals(mapFromJson.getStatusMessage(), Constants.NO_INSTRUCTION);
	}

}