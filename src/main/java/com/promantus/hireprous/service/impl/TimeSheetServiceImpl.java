package com.promantus.hireprous.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.promantus.hireprous.HireProUsConstants;
import com.promantus.hireprous.dto.TimeSheetDto;
import com.promantus.hireprous.entity.TimeSheet;
import com.promantus.hireprous.repository.TimeSheetRepository;
import com.promantus.hireprous.service.CommonService;
import com.promantus.hireprous.service.TimeSheetService;
import com.promantus.hireprous.util.CacheUtil;
import com.promantus.hireprous.util.HireProUsUtil;

@Service
public class TimeSheetServiceImpl implements TimeSheetService{

	private static final Logger logger = LoggerFactory.getLogger(TimeSheetServiceImpl.class);

	@Autowired
	TimeSheetRepository timeSheetRepository;
	
	@Autowired
	CommonService commonService;

	@Override
	public TimeSheetDto addTimeSheet(TimeSheetDto timeSheetDto, String lang) throws Exception {
		
		TimeSheetDto resultDto = new TimeSheetDto();
		
		TimeSheet timesheet = new TimeSheet();
		timesheet.setId(commonService.nextSequenceNumber());
		timesheet.setDate(timeSheetDto.getDate());
		timesheet.setProjectId(timeSheetDto.getProjectId());
		timesheet.setUserId(timeSheetDto.getUserId());
		timesheet.setManagerId(timeSheetDto.getManagerId());
		timesheet.setTask(timeSheetDto.getTask());
		timesheet.setStartTime(timeSheetDto.getStartTime());
		timesheet.setEndTime(timeSheetDto.getEndTime());
		timesheet.setCalHrs(timeSheetDto.getCalHrs());
		timesheet.setDescription(timeSheetDto.getDescription());
		timesheet.setSubmittedForApproval(false);
		timesheet.setTimesheetStatus(timeSheetDto.getTimesheetStatus());
		
		timesheet.setCreatedBy(timeSheetDto.getCreatedBy());
		timesheet.setUpdatedBy(timeSheetDto.getUpdatedBy());
		timesheet.setCreatedDateTime(LocalDateTime.now());
		timesheet.setUpdatedDateTime(LocalDateTime.now());
		timeSheetRepository.save(timesheet);
		
		resultDto.setStatus(HireProUsConstants.RETURN_STATUS_OK);
		resultDto.setMessage("Timesheet added");
		return resultDto;
	}

	@Override
	public TimeSheetDto updateTimeSheet(final TimeSheetDto timeSheetDto, final String lang) throws Exception {

		TimeSheetDto resultDto = new TimeSheetDto();

		TimeSheet timeSheet = timeSheetRepository.findById(timeSheetDto.getId());

		if (timeSheet == null) {
			resultDto.setStatus(HireProUsConstants.RETURN_STATUS_ERROR);
			resultDto.setMessage(commonService.getMessage("invalid", new String[] { "TimeSheet Id" }, lang));

			logger.info(resultDto.getMessage());
			return resultDto;
		}
		
		timeSheet.setDate(timeSheetDto.getDate());
		timeSheet.setProjectId(timeSheetDto.getProjectId());
		timeSheet.setUserId(timeSheetDto.getUserId());
		timeSheet.setManagerId(timeSheetDto.getUserId());
		
		timeSheet.setTask(timeSheetDto.getTask());
		timeSheet.setDescription(timeSheetDto.getDescription());
		
		timeSheet.setStartTime(timeSheetDto.getStartTime());
		timeSheet.setEndTime(timeSheetDto.getEndTime());
		timeSheet.setCalHrs(timeSheetDto.getCalHrs());
		
		timeSheet.setSubmittedForApproval(timeSheetDto.isSubmittedForApproval());
		timeSheet.setTimesheetStatus(timeSheetDto.getTimesheetStatus());
		

		timeSheet.setUpdatedBy(timeSheetDto.getUpdatedBy());
		timeSheet.setUpdatedDateTime(LocalDateTime.now());

		timeSheetRepository.save(timeSheet);

		
		resultDto.setStatus(HireProUsConstants.RETURN_STATUS_OK);
		return resultDto;
	}

	@Override
	public List<TimeSheetDto> getAllTimeSheets() throws Exception {
		
		List<TimeSheetDto> timeSheetsDtoList = new ArrayList<TimeSheetDto>();
		
		List<TimeSheet> timeSheetsList = timeSheetRepository.findAll();
		for (TimeSheet timeSheet : timeSheetsList) {
			timeSheetsDtoList.add(this.getTimeSheetDto(timeSheet));
		}
		return timeSheetsDtoList;
	}
	
	 private TimeSheetDto getTimeSheetDto(final TimeSheet timeSheet) throws Exception {
		 
		TimeSheetDto timeSheetDto = new TimeSheetDto();
		timeSheetDto.setId(timeSheet.getId());
		timeSheetDto.setDate(timeSheet.getDate());
		timeSheetDto.setManagerId(timeSheet.getManagerId());
		timeSheetDto.setProjectId(timeSheet.getProjectId());
		timeSheetDto.setProjectName(CacheUtil.getProjectsMap().get(timeSheet.getProjectId()));
		timeSheetDto.setUserId(timeSheet.getUserId());
		timeSheetDto.setTask(timeSheet.getTask());
		timeSheetDto.setDescription(timeSheet.getDescription());
		timeSheetDto.setStartTime(timeSheet.getStartTime());
		timeSheetDto.setEndTime(timeSheet.getEndTime());
		timeSheetDto.setCalHrs(timeSheet.getCalHrs());
		timeSheetDto.setSubmittedForApproval(timeSheet.isSubmittedForApproval());
		timeSheetDto.setTimesheetStatus(timeSheet.getTimesheetStatus());
		timeSheetDto.setCreatedBy(timeSheet.getCreatedBy());
		timeSheetDto.setCreatedByName(CacheUtil.getUsersMap().get(timeSheet.getCreatedBy()));
		timeSheetDto.setCreatedDateTime(HireProUsUtil.getGMTDateTime(timeSheet.getCreatedDateTime()));
		timeSheetDto.setUpdatedBy(timeSheet.getUpdatedBy());
		timeSheetDto.setUpdatedByName(CacheUtil.getUsersMap().get(timeSheet.getUpdatedBy()));
		timeSheetDto.setUpdatedDateTime(HireProUsUtil.getGMTDateTime(timeSheet.getUpdatedDateTime()));
		
		return timeSheetDto;
		
	}

	@Override
	public TimeSheetDto submitTimeSheet(List<TimeSheetDto> timeSheetDtoList, String lang) throws Exception {
		
		TimeSheetDto resultDto = new TimeSheetDto();
		
		for(TimeSheetDto timeSheetDto : timeSheetDtoList) {
			
			TimeSheet timeSheet = timeSheetRepository.findById(timeSheetDto.getId());
			
			timeSheet.setSubmittedForApproval(true);
			timeSheet.setTimesheetStatus("Pending");
			
			timeSheet.setUpdatedBy(timeSheetDto.getUpdatedBy());
			timeSheet.setUpdatedDateTime(LocalDateTime.now());

			timeSheetRepository.save(timeSheet);
		}
		
		resultDto.setStatus(HireProUsConstants.RETURN_STATUS_OK);
		resultDto.setMessage("TimeSheet Submitted Sucessfully");
		return resultDto;
	}

	@Override
	public TimeSheetDto getTimeSheetById(String timeSheetId) throws Exception {
		
		TimeSheet timeSheet = timeSheetRepository.findById(Long.parseLong(timeSheetId));

		return timeSheet != null ? this.getTimeSheetDto(timeSheet) : new TimeSheetDto();
	}

	@Override
	public TimeSheetDto deleteTimeSheetById(String timeSheetId) throws Exception {
		
		TimeSheetDto resultDto = new TimeSheetDto();

		TimeSheet timeSheet = timeSheetRepository.findById(Long.parseLong(timeSheetId));

		if (timeSheet == null) {
			resultDto.setStatus(HireProUsConstants.RETURN_STATUS_ERROR);
			resultDto.setMessage(commonService.getMessage("invalid", new String[] { "TimeSheet Id" }, null));

			logger.info(resultDto.getMessage());
			return resultDto;
		}

		timeSheetRepository.deleteById(Long.parseLong(timeSheetId));

		CacheUtil.getProjectsMap().remove(Long.parseLong(timeSheetId));
		resultDto.setStatus(HireProUsConstants.RETURN_STATUS_OK);

		return resultDto;
	}

	@Override
	public List<TimeSheetDto> getTimeSheetByUserIdAndApproval(String userId, boolean isApproval) throws Exception {
		
		List<TimeSheetDto> timeSheetsDtoList = new ArrayList<TimeSheetDto>();
		
		List<TimeSheet> list = timeSheetRepository.findAllByUserIdAndIsSubmittedForApproval(Long.parseLong(userId),isApproval); 
		for (TimeSheet timeSheet : list) {
			timeSheetsDtoList.add(this.getTimeSheetDto(timeSheet));
		}
		return timeSheetsDtoList;
	}

}
