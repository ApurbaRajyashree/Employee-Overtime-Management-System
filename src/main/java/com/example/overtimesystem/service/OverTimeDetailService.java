package com.example.overtimesystem.service;


import com.example.overtimesystem.dto.OverTimeDetailDto;

import java.util.List;

public interface OverTimeDetailService {

    OverTimeDetailDto createOverTimeDetail(OverTimeDetailDto overTimeDetailDto,String username);

    String deleteOverTimeDetail(int id);

    List<OverTimeDetailDto> getAll();

    List<OverTimeDetailDto> getAllByOverTimeMaster(int id);
}
