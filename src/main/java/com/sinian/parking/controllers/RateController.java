package com.sinian.parking.controllers;

import com.sinian.parking.entity.Rate;
import com.sinian.parking.service.rate.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rates")
public class RateController {
    @Autowired
    private RateService rateService;

    @GetMapping
    public ResponseEntity<List<Rate>> getAllRates() {
        List<Rate> rates = rateService.getAllRates();
        return new ResponseEntity<>(rates, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Rate> createRate(@RequestBody Rate rate) {
        Rate createdRate = rateService.createRate(rate);
        return new ResponseEntity<>(createdRate, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rate> updateRate(@PathVariable int id, @RequestBody Rate rateDetails) {
        Rate updatedRate = rateService.updateRate(id, rateDetails);
        return new ResponseEntity<>(updatedRate, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRate(@PathVariable int id) {
        rateService.deleteRate(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
