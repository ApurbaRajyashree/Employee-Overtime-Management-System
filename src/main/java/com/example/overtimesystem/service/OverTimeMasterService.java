package com.example.overtimesystem.service;

import com.example.overtimesystem.dto.OverTimeMasterDto;

import java.util.List;

public interface OverTimeMasterService {

    OverTimeMasterDto createOverTimeMaster(int userId);

    List<OverTimeMasterDto> getAllOverTimeMasterofLogedInUser();
}
