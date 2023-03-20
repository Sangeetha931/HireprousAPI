package com.promantus.hireprous.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;


import com.promantus.hireprous.entity.TimeSheet;

public interface TimeSheetRepository extends MongoRepository<TimeSheet, String> {

	TimeSheet findById(Long id);

	TimeSheet deleteById(long parseLong);

	List<TimeSheet> findAllByUserIdAndIsSubmittedForApproval(long l, boolean isApproval);

}
