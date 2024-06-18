package com.sinian.parking.service.rate;

import com.sinian.parking.entity.Rate;
import com.sinian.parking.repository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RateServiceImpl implements RateService{

    @Autowired
    private RateRepository rateRepository;

    @Override
    public List<Rate> getAllRates() {
        return rateRepository.findAll();
    }

    @Override
    public Optional<Rate> getRateById(int id) {
        return rateRepository.findById(id);
    }

    @Override
    public Rate createRate(Rate rate) {
        return rateRepository.save(rate);
    }

    @Override
    public Rate updateRate(int id, Rate rateDetails) {
        Optional<Rate> optionalRate = rateRepository.findById(id);
        if (optionalRate.isPresent()) {
            Rate existingRate = optionalRate.get();
            // Update data rate
            existingRate.setType(rateDetails.getType());
            existingRate.setRate(rateDetails.getRate());
            return rateRepository.save(existingRate);
        } else {
            throw new RuntimeException("Rate not found");
        }
    }

    @Override
    public void deleteRate(int id) {
        rateRepository.deleteById(id);
    }
}
