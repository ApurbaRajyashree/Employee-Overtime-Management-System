package com.example.overtimesystem.service;


import com.example.overtimesystem.dto.OverTimeDetailDto;

public interface OverTimeDetailService {

    OverTimeDetailDto createOverTimeDetail(OverTimeDetailDto overTimeDetailDto);

    String deleteOverTimeDetail(int id);
}
