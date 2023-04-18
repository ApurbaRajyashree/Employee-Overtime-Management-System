package com.example.overtimesystem.service.serviceImpl;

import com.example.overtimesystem.dto.OverTimeDetailDto;
import com.example.overtimesystem.entity.OverTimeDetail;
import com.example.overtimesystem.repository.OverTimeDetailRepository;
import com.example.overtimesystem.service.OverTimeDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OverTimeDetailServiceImpl implements OverTimeDetailService {

    private final OverTimeDetailRepository overTimeDetailRepository;
    @Override
    public OverTimeDetailDto createOverTimeDetail(OverTimeDetailDto overTimeDetailDto) {
        OverTimeDetail overTimeDetail=new OverTimeDetail(overTimeDetailDto);
        overTimeDetailRepository.save(overTimeDetail);
        return new OverTimeDetailDto(overTimeDetail);
    }

    @Override
    public String deleteOverTimeDetail(int id) {
        return null;
    }
}
