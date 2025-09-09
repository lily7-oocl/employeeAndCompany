package org.oocl.springdemo.controller;

import org.oocl.springdemo.common.Result;
import org.oocl.springdemo.pojo.ParkingBoy;
import org.oocl.springdemo.service.ParkingBoyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parking-boy")
public class parkingBoyController {
    @Autowired
    private ParkingBoyService parkingBoyService;
    @PostMapping
    public Result<Integer> addParkingBoy(@RequestBody ParkingBoy parkingBoy) {
        ParkingBoy newParkingboy = parkingBoyService.addParkingBoy(parkingBoy);
        return Result.success(newParkingboy.getId());
    }
}
