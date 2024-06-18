package com.sinian.parking.ui;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("status")
public class StatusView extends VerticalLayout {
    public StatusView() {
        Div statusDiv = new Div();
        statusDiv.setText("Status of the OCR processing will be shown here.");

        // Example notification for status
        Notification notification = new Notification("OCR Processing...", 3000);
        notification.open();

        add(statusDiv);
    }
}
