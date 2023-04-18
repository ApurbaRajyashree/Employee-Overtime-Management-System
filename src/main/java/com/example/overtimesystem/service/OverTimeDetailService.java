package com.example.overtimesystem.service;


import com.example.overtimesystem.dto.OverTimeDetailDto;

import java.util.List;

public interface OverTimeDetailService {

    OverTimeDetailDto createOverTimeDetail(OverTimeDetailDto overTimeDetailDto);

    String deleteOverTimeDetail(int id);

    List<OverTimeDetailDto> getAll();
}
