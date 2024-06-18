package com.sinian.parking.service.rate;

import com.sinian.parking.entity.Rate;
import com.sinian.parking.repository.RateRepository;


import java.util.List;
import java.util.Optional;

public interface RateService {
    List<Rate> getAllRates();
    Optional<Rate> getRateById(int id);
    Rate createRate(Rate rate);
    Rate updateRate(int id, Rate rateDetails);
    void deleteRate(int id);
}
