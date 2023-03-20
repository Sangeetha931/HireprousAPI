package com.promantus.hireprous.service;

import java.util.List;

import com.promantus.hireprous.dto.TimeSheetDto;

public interface TimeSheetService {

	TimeSheetDto updateTimeSheet(TimeSheetDto timeSheetDto, String lang) throws Exception;

	TimeSheetDto addTimeSheet(TimeSheetDto timeSheetDto, String lang) throws Exception;

	List<TimeSheetDto> getAllTimeSheets() throws Exception;

	TimeSheetDto submitTimeSheet(List<TimeSheetDto> timeSheetDtoList, String lang) throws Exception;

	TimeSheetDto getTimeSheetById(String timeSheetId) throws Exception;

	TimeSheetDto deleteTimeSheetById(String timeSheetId) throws Exception;

	List<TimeSheetDto> getTimeSheetByUserIdAndApproval(String userId, boolean isApproval) throws Exception;

}
