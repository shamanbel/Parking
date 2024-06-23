package com.sinian.parking.ui;

import com.sinian.parking.entity.Rate;
import com.sinian.parking.service.rate.RateService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

@Route("rate")
public class RateView extends VerticalLayout {

    @Autowired
    private RateService rateService;

    private Grid<Rate> grid = new Grid<>(Rate.class);
    private TextField typeField = new TextField("Type");
    private NumberField rateField = new NumberField("Rate");
    private Button saveButton = new Button("Save");
    private Button deleteButton = new Button("Delete");

    public RateView() {
        add(typeField, rateField, saveButton, deleteButton, grid);
        configureGrid();
        configureButtons();
    }

    @PostConstruct
    private void init() {
        updateList();
    }

    private void configureGrid() {
        grid.setColumns("id", "type", "rate");
    }

    private void configureButtons() {
        saveButton.addClickListener(e -> saveRate());
        deleteButton.addClickListener(e -> deleteRate());
    }

    private void saveRate() {
        String type = typeField.getValue();
        Double rateValue = rateField.getValue();
        if (type.isEmpty() || rateValue == null) {
            Notification.show("Please enter both type and rate");
            return;
        }
        Rate rate = new Rate();
        rate.setType(type);
        rate.setRate(rateValue);
        rateService.createRate(rate);
        updateList();
    }

    private void deleteRate() {
        Rate selectedRate = grid.asSingleSelect().getValue();
        if (selectedRate != null) {
            rateService.deleteRate(selectedRate.getId());
            updateList();
        } else {
            Notification.show("Please select a rate to delete");
        }
    }

    private void updateList() {
        List<Rate> rates = rateService.getAllRates();
        grid.setItems(rates);
    }
}