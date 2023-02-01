package com.kodilla.hibernate.invoice.dao;

import com.kodilla.hibernate.invoice.Invoice;
import com.kodilla.hibernate.invoice.Item;
import com.kodilla.hibernate.invoice.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class InvoiceDaoTestSuite {

    @Autowired
    private InvoiceDao invoiceDao;

    @Test
    void testInvoiceDaoSave() {
        //Given
        Product whitePlc = new Product("White PLC");
        Product blackPlc = new Product("Black PLC");
        Product motionPlc = new Product("Motion PLC");

        Item singleWhitePlc = new Item(whitePlc, new BigDecimal(120), 1);
        Item singleBlackPlc = new Item(blackPlc, new BigDecimal(240), 1);
        Item singleMotionPlc = new Item(motionPlc, new BigDecimal(360), 1);
        Item fiveWhitePlcs = new Item(whitePlc, new BigDecimal(120), 5);
        Item fiveBlackPlcs = new Item(blackPlc, new BigDecimal(250), 5);
        Item fiveMotionPlcs = new Item(motionPlc, new BigDecimal(360), 5);
        whitePlc.getItems().add(singleWhitePlc);
        whitePlc.getItems().add(fiveWhitePlcs);
        blackPlc.getItems().add(singleBlackPlc);
        blackPlc.getItems().add(fiveBlackPlcs);
        motionPlc.getItems().add(singleMotionPlc);
        motionPlc.getItems().add(fiveMotionPlcs);

        Invoice invoiceForSingleWhitePlc = new Invoice("0001");
        invoiceForSingleWhitePlc.getItems().add(singleWhitePlc);
        singleWhitePlc.setInvoice(invoiceForSingleWhitePlc);

        Invoice invoiceForSingleBlackPlc = new Invoice("0002");
        invoiceForSingleBlackPlc.getItems().add(singleBlackPlc);
        singleBlackPlc.setInvoice(invoiceForSingleBlackPlc);

        Invoice invoiceForSingleMotionPlc = new Invoice("0003");
        invoiceForSingleMotionPlc.getItems().add(singleMotionPlc);
        singleMotionPlc.setInvoice(invoiceForSingleMotionPlc);

        Invoice invoiceForExtraPlcPackage = new Invoice("0004");
        invoiceForExtraPlcPackage.getItems().addAll(List.of(fiveWhitePlcs, fiveMotionPlcs, fiveBlackPlcs));
        fiveWhitePlcs.setInvoice(invoiceForExtraPlcPackage);
        fiveBlackPlcs.setInvoice(invoiceForExtraPlcPackage);
        fiveMotionPlcs.setInvoice(invoiceForExtraPlcPackage);

        //When
        invoiceDao.saveAll(List.of(invoiceForSingleWhitePlc, invoiceForSingleBlackPlc,
                invoiceForSingleMotionPlc, invoiceForExtraPlcPackage));

        int invoiceForSingleWhitePlcId = invoiceForSingleWhitePlc.getId();
        int invoiceForSingleBlackPlcId = invoiceForSingleBlackPlc.getId();
        int invoiceForSingleMotionPlcId = invoiceForSingleMotionPlc.getId();
        int invoiceForExtraPlcPackageId = invoiceForExtraPlcPackage.getId();

        //Then
        assertAll(() -> assertNotEquals(0, invoiceForSingleWhitePlcId),
                () -> assertNotEquals(0, invoiceForSingleBlackPlcId),
                () -> assertNotEquals(0, invoiceForSingleMotionPlcId),
                () -> assertNotEquals(0, invoiceForExtraPlcPackageId));

        //CleanUp
        try {
            invoiceDao.deleteAllById(List.of(invoiceForSingleWhitePlcId, invoiceForSingleBlackPlcId,
                    invoiceForSingleMotionPlcId, invoiceForExtraPlcPackageId));
        } catch (Exception e) {
            //do nothing
        }
    }
}
