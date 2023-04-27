package com.example.overtimesystem.service;

import com.example.overtimesystem.dto.OverTimeDetailDto;
import com.example.overtimesystem.dto.OverTimeMasterDto;

import java.util.List;

public interface OverTimeMasterService {

    OverTimeMasterDto createOverTimeMaster(int userId);

    List<OverTimeMasterDto> getAllOverTimeMasterofLogedInUser();

    List<OverTimeDetailDto> getAllOverTimeDetailofLogedInUser(List<OverTimeDetailDto> overTimeDetailDtos);
}
