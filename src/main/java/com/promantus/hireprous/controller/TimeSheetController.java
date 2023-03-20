/**********************************************************************************************
 * Copyright 2021 Promantus Private Limited.
 * All rights reserved.
 **********************************************************************************************/
package com.promantus.hireprous.controller;



import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.promantus.hireprous.HireProUsConstants;
import com.promantus.hireprous.dto.TimeSheetDto;
import com.promantus.hireprous.service.TimeSheetService;
import com.promantus.hireprous.util.HireProUsUtil;

/**
 * Controller class to handle Projects related APIs.
 * 
 * @author Sihab.
 *
 */
@RestController
@RequestMapping("/api/v1")
public class TimeSheetController extends CommonController {
	
	private static final Logger logger = LoggerFactory.getLogger(TimeSheetController.class);

	@Autowired
	private TimeSheetService timeSheetService;

	@PostMapping("/addTimesheet")
	public TimeSheetDto addTimesheet(@RequestBody TimeSheetDto timeSheetDto,
			@RequestHeader(name = "lang", required = false) String lang) {
		
		TimeSheetDto resultDto = new TimeSheetDto();
		try {
			StringBuilder errorParam = new StringBuilder();
			
			// Time Sheet Date
			if (timeSheetDto.getDate() == null || timeSheetDto.getDate().isEmpty()) {
				errorParam.append(errorParam.length() > 0 ? ", Timesheet Date" : "Timesheet Date");
			}
			// Project Id.
			if (timeSheetDto.getProjectId() == null || timeSheetDto.getProjectId().isEmpty()) {
				errorParam.append("Project Id");
			}
			// User Id.
			if (timeSheetDto.getUserId() == 0) {
				errorParam.append(errorParam.length() > 0 ? ", user id" : "user id");
			}
			// Manager Id.
			if (timeSheetDto.getManagerId() == 0) {
				errorParam.append(errorParam.length() > 0 ? ", Manager Id" : "Manager Id");
			}
			// Task.
			if (timeSheetDto.getTask() == null || timeSheetDto.getTask().isEmpty()) {
				errorParam.append(errorParam.length() > 0 ? ", Task" : "Task");
			}
			// Start Time.
			if (timeSheetDto.getStartTime() == null || timeSheetDto.getStartTime().isEmpty()) {
				errorParam.append(errorParam.length() > 0 ? ", Start Time" : "Start Time");
			}
			// End Time.
			if (timeSheetDto.getEndTime() == null || timeSheetDto.getEndTime().isEmpty()) {
				errorParam.append(errorParam.length() > 0 ? ", End Time" : "End Time");
			}
			// Calculated Hours.
			if (timeSheetDto.getCalHrs() == null || timeSheetDto.getCalHrs().isEmpty()) {
				errorParam.append(errorParam.length() > 0 ? ", Calculated Hours" : "Calculated Hours");
			}
			// Description.
			if (timeSheetDto.getDescription() == null || timeSheetDto.getDescription().isEmpty()) {
				errorParam.append(errorParam.length() > 0 ? ", Description" : "Description");
			}
			if (errorParam.length() > 0) {
				resultDto.setStatus(HireProUsConstants.RETURN_STATUS_ERROR);
				resultDto.setMessage(
						super.getMessage("mandatory.input.param", new String[] { errorParam.toString() }, lang));

				logger.info(resultDto.getMessage());
				return resultDto;
			}
			
			resultDto = timeSheetService.addTimeSheet(timeSheetDto, lang);
		} catch (Exception e) {
			
			resultDto.setStatus(HireProUsConstants.RETURN_STATUS_ERROR);
			resultDto.setMessage(e.getMessage());

			logger.error(HireProUsUtil.getErrorMessage(e));
		}
		
		return resultDto;
	}
	/**
	 * @param projectDto
	 * @return
	 */
	@PutMapping("/updateTimeSheet")
	public TimeSheetDto updateTimeSheet(@RequestBody TimeSheetDto timeSheetDto,
			@RequestHeader(name = "lang", required = false) String lang) {

		TimeSheetDto resultDto = new TimeSheetDto();
		try {

			// Mandatory check.
			StringBuilder errorParam = new StringBuilder();
			// TimeSheet Id.
			if (timeSheetDto.getId() == 0) {
				errorParam.append("TimeSheet Id");
			}
			// Time Sheet Date
			if (timeSheetDto.getDate() == null || timeSheetDto.getDate().isEmpty()) {
				errorParam.append(errorParam.length() > 0 ? ", Timesheet Date" : "Timesheet Date");
			}
			// Project Id.
			if (timeSheetDto.getProjectId() == null || timeSheetDto.getProjectId().isEmpty()) {
				errorParam.append("Project Id");
			}
			// User Id.
			if (timeSheetDto.getUserId() == 0) {
				errorParam.append(errorParam.length() > 0 ? ", user id" : "user id");
			}
			// Manager Id.
			if (timeSheetDto.getManagerId() == 0) {
				errorParam.append(errorParam.length() > 0 ? ", Manager Id" : "Manager Id");
			}
			// Task.
			if (timeSheetDto.getTask() == null || timeSheetDto.getTask().isEmpty()) {
				errorParam.append(errorParam.length() > 0 ? ", Task" : "Task");
			}

			// Start Time.
			if (timeSheetDto.getStartTime() == null || timeSheetDto.getStartTime().isEmpty()) {
				errorParam.append(errorParam.length() > 0 ? ", Start Time" : "Start Time");
			}
			// End Time.
			if (timeSheetDto.getEndTime() == null || timeSheetDto.getEndTime().isEmpty()) {
				errorParam.append(errorParam.length() > 0 ? ", End Time" : "End Time");
			}
			// Calculated Hours.
			if (timeSheetDto.getCalHrs() == null || timeSheetDto.getCalHrs().isEmpty()) {
				errorParam.append(errorParam.length() > 0 ? ", Calculated Hours" : "Calculated Hours");
			}
			// Description.
			if (timeSheetDto.getDescription() == null || timeSheetDto.getDescription().isEmpty()) {
				errorParam.append(errorParam.length() > 0 ? ", Description" : "Description");
			}
			if (errorParam.length() > 0) {
				resultDto.setStatus(HireProUsConstants.RETURN_STATUS_ERROR);
				resultDto.setMessage(
						super.getMessage("mandatory.input.param", new String[] { errorParam.toString() }, lang));

				logger.info(resultDto.getMessage());
				return resultDto;
			}

			resultDto = timeSheetService.updateTimeSheet(timeSheetDto, lang);
			resultDto.setStatus(HireProUsConstants.RETURN_STATUS_OK);
			resultDto.setMessage("TimeSheet Updated Successfully");

		} catch (final Exception e) {

			resultDto.setStatus(HireProUsConstants.RETURN_STATUS_ERROR);
			resultDto.setMessage(e.getMessage());

			logger.error(HireProUsUtil.getErrorMessage(e));
			return resultDto;
		}

		return resultDto;
	}

	@PutMapping("/submitTimeSheet")
	public TimeSheetDto submitTimeSheet(@RequestBody List<TimeSheetDto> timeSheetDtoList,
			@RequestHeader(name = "lang", required = false) String lang) {

		TimeSheetDto resultDto = new TimeSheetDto();
		
		try {
			
			resultDto = timeSheetService.submitTimeSheet(timeSheetDtoList , lang);
			return resultDto;
		} catch (Exception e) {
			resultDto.setStatus(HireProUsConstants.RETURN_STATUS_ERROR);
			resultDto.setMessage(e.getMessage());

			logger.error(HireProUsUtil.getErrorMessage(e));
			return resultDto;
		}
	}
	
	@GetMapping("/getAllTimeSheets")
	public List<TimeSheetDto> getAllTimeSheets(@RequestHeader(name = "lang", required = false) String lang) {

		List<TimeSheetDto> timeSheetsDtoList = new ArrayList<TimeSheetDto>();
		try {
			timeSheetsDtoList = timeSheetService.getAllTimeSheets();
		} catch (final Exception e) {
			logger.error(HireProUsUtil.getErrorMessage(e));
		}

		return timeSheetsDtoList;
	}
	
	@GetMapping("/getTimeSheet/{timeSheetId}")
	public TimeSheetDto getTimeSheetById(@PathVariable String timeSheetId,
			@RequestHeader(name = "lang", required = false) String lang) {

		TimeSheetDto timeSheetDto = new TimeSheetDto();
		try {
			
			timeSheetDto = timeSheetService.getTimeSheetById(timeSheetId);
			
		} catch (final Exception e) {
			
			logger.error(HireProUsUtil.getErrorMessage(e));
		}
		return timeSheetDto;
	}
	
	@GetMapping("/getTimeSheetByUserIdAndApproval/{userId}/{isApproval}")
	public List<TimeSheetDto> getTimeSheetByUserIdAndApproval(@PathVariable String userId,@PathVariable boolean isApproval,
			@RequestHeader(name = "lang", required = false) String lang) {

		List<TimeSheetDto> timeSheetDto = new ArrayList<TimeSheetDto>();
		try {
			
			timeSheetDto = timeSheetService.getTimeSheetByUserIdAndApproval(userId,isApproval);
			
		} catch (final Exception e) {
			
			logger.error(HireProUsUtil.getErrorMessage(e));
		}
		return timeSheetDto;
	}
	
	@DeleteMapping("/deleteTimeSheetById/{timeSheetId}")
	public Boolean deleteTimeSheetById(@PathVariable String timeSheetId,
			@RequestHeader(name = "lang", required = false) String lang) {

		try {
			timeSheetService.deleteTimeSheetById(timeSheetId);
		} catch (final Exception e) {
			logger.error(HireProUsUtil.getErrorMessage(e));
		}

		return true;
	}
	
}

