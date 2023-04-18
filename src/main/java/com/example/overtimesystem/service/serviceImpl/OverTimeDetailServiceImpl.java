package com.example.overtimesystem.service.serviceImpl;

import com.example.overtimesystem.dto.OverTimeDetailDto;
import com.example.overtimesystem.entity.OverTimeDetail;
import com.example.overtimesystem.repository.OverTimeDetailRepository;
import com.example.overtimesystem.service.OverTimeDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<OverTimeDetailDto> getAll() {
        List<OverTimeDetail> overTimeDetailList=this.overTimeDetailRepository.findAll();
        return overTimeDetailList.stream().map(x->new OverTimeDetailDto(x)).collect(Collectors.toList());
    }
}
