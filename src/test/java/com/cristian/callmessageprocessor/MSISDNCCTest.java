package com.cristian.callmessageprocessor;

import org.junit.jupiter.api.Test;

import com.cristian.callmessageprocessor.utils.MSISDNCC;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MSISDNCCTest {

    @Test
    public void testGetCountryFromMSISDN() {

        assertEquals("SPAIN", MSISDNCC.getCountryFromMSISDN("34123456789"));
        assertEquals("GERMANY", MSISDNCC.getCountryFromMSISDN("49123456789"));
        assertEquals("UNITED KINGDOM", MSISDNCC.getCountryFromMSISDN("44123456789"));
        assertEquals("ITALY", MSISDNCC.getCountryFromMSISDN("39123456789"));
        assertEquals("FRANCE", MSISDNCC.getCountryFromMSISDN("33123456789"));
        assertEquals("UNKNOWN", MSISDNCC.getCountryFromMSISDN("1234567890"));

    }
}

