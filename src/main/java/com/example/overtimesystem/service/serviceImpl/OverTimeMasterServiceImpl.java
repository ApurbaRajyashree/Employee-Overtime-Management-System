package com.example.overtimesystem.service.serviceImpl;

import com.example.overtimesystem.dto.OverTimeDetailDto;
import com.example.overtimesystem.dto.OverTimeMasterDto;
import com.example.overtimesystem.entity.*;
import com.example.overtimesystem.repository.OverTimeDetailRepository;
import com.example.overtimesystem.repository.OverTimeMasterRepository;
import com.example.overtimesystem.repository.UserRepository;
import com.example.overtimesystem.service.OverTimeDetailService;
import com.example.overtimesystem.service.OverTimeMasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OverTimeMasterServiceImpl implements OverTimeMasterService {

    private final UserRepository userRepository;

    private final OverTimeMasterRepository overTimeMasterRepository;

    @Override
    public OverTimeMasterDto createOverTimeMaster(int userId) {
        int presentMonth = LocalDate.now().getMonthValue();
        int presentYear = LocalDate.now().getYear();
        OverTimeMaster overTimeMaster = new OverTimeMaster();
        overTimeMaster.setUser(userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User does not exist")
        ));
        overTimeMaster.setYear(presentYear);
        overTimeMaster.setMonth(Month.valueOfMonthNumber(presentMonth));
        overTimeMaster.setStatus(1);
        overTimeMasterRepository.save(overTimeMaster);
        return new OverTimeMasterDto(overTimeMaster);
    }

    @Override
    public List<OverTimeMasterDto> getAllOverTimeMasterofLogedInUser() {
        int presentMonth = LocalDate.now().getMonthValue();
        int presentYear = LocalDate.now().getYear();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<OverTimeMaster> usersOverTimeMaster = new ArrayList<>();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            List<OverTimeMaster> overTimeMasterList = overTimeMasterRepository.findAll();
            User user = userRepository.findByEmail(userDetails.getUsername());
            for (OverTimeMaster eachMaster : overTimeMasterList) {
                if (eachMaster.getUser().getEmail().equals(userDetails.getUsername())) {
                    usersOverTimeMaster.add(eachMaster);
                    if ((eachMaster.getYear() < presentYear) || eachMaster.getMonth().monthNumber < presentMonth) {
                        createOverTimeMaster(user.getId());
                    }
                }
            }

        }
        return usersOverTimeMaster.stream().map(x -> new OverTimeMasterDto(x)).collect(Collectors.toList());
    }

    @Override
    public List<OverTimeDetailDto> getAllOverTimeDetailofLogedInUser(List<OverTimeDetailDto> overTimeDetailDtos) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<OverTimeDetailDto> overTimeDetailDtoList = new ArrayList<>();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = userRepository.findByEmail(userDetails.getUsername());
            for (OverTimeDetailDto eachDetailDto : overTimeDetailDtos) {
                if (eachDetailDto.getOverTimeMaster().getUser().getEmail().equals(userDetails.getUsername())) {
                    overTimeDetailDtoList.add(eachDetailDto);
                }
            }
        }
        return overTimeDetailDtoList;
    }
}
