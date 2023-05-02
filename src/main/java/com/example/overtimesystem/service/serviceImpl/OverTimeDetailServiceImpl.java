package com.example.overtimesystem.service.serviceImpl;

import com.example.overtimesystem.dto.OverTimeDetailDto;
import com.example.overtimesystem.dto.OverTimeMasterDto;
import com.example.overtimesystem.entity.Month;
import com.example.overtimesystem.entity.OverTimeDetail;
import com.example.overtimesystem.entity.OverTimeMaster;
import com.example.overtimesystem.repository.OverTimeDetailRepository;
import com.example.overtimesystem.repository.OverTimeMasterRepository;
import com.example.overtimesystem.repository.UserRepository;
import com.example.overtimesystem.service.OverTimeDetailService;
import com.example.overtimesystem.service.OverTimeMasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OverTimeDetailServiceImpl implements OverTimeDetailService {

    private final OverTimeDetailRepository overTimeDetailRepository;

    private final OverTimeMasterRepository overTimeMasterRepository;

    private final UserRepository userRepository;

    private final OverTimeMasterService overTimeMasterService;


    @Override
    public OverTimeDetailDto createOverTimeDetail(OverTimeDetailDto overTimeDetailDto, String username) {
        OverTimeDetail overTimeDetail = new OverTimeDetail(overTimeDetailDto);
        overTimeDetail.setDate(LocalDate.now());
        int userId = this.userRepository.getUserByUserName(username).getId();
        OverTimeMaster overTimeMaster = overTimeMasterRepository.findByUserYearAndMonth(LocalDate.now().getYear(), Month.valueOfMonthNumber(LocalDate.now().getMonthValue()).toString(), userId);

        if(overTimeDetailDto.getStartTime().compareTo(overTimeDetail.getEndTime())>0){
                throw new RuntimeException("End time should be greater than Start Time.");
        } else if (overTimeDetailDto.getStartTime().compareTo(overTimeDetail.getEndTime())==0) {
            throw new RuntimeException("Start time and End Time are equal!!!");
        }
        if (overTimeMaster == null) {
            OverTimeMasterDto createdMaster = overTimeMasterService.createOverTimeMaster(userId);
            overTimeDetail.setOverTimeMaster(new OverTimeMaster(createdMaster));
        } else {
            overTimeDetail.setOverTimeMaster(overTimeMaster);
        }
        overTimeDetailRepository.save(overTimeDetail);
        return new OverTimeDetailDto(overTimeDetail);
    }

    @Override
    public String deleteOverTimeDetail(int id) {
        return null;
    }

    @Override
    public List<OverTimeDetailDto> getAll() {
        List<OverTimeDetail> overTimeDetailList = this.overTimeDetailRepository.findAll();
        return overTimeDetailList.stream().map(x -> new OverTimeDetailDto(x)).collect(Collectors.toList());
    }

    @Override
    public List<OverTimeDetailDto> getAllByOverTimeMaster(int id) {
        List<OverTimeDetail> overTimeDetailList = overTimeDetailRepository.findAllByOverTimeMaster(id);
        return overTimeDetailList.stream().map(x -> new OverTimeDetailDto(x)).collect(Collectors.toList());
    }
}
