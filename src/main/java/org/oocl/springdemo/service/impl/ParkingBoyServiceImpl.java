package org.oocl.springdemo.service.impl;

import ch.qos.logback.core.testUtil.RandomUtil;
import org.oocl.springdemo.common.Result;
import org.oocl.springdemo.pojo.ParkingBoy;
import org.oocl.springdemo.service.ParkingBoyService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkingBoyServiceImpl implements ParkingBoyService {
    List<ParkingBoy> parkingBoys = new ArrayList<>();

    @Override
    public ParkingBoy addParkingBoy(ParkingBoy parkingBoy) {
        parkingBoy.setId(RandomUtil.getPositiveInt());
        parkingBoys.add(parkingBoy);
        return parkingBoy;
    }
}
