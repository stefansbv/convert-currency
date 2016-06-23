package ro.s2i2.exchange;

import static org.hamcrest.CoreMatchers.*;

//import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class RatesTest {
    private static final double DELTA = 1e-15;

    @Test
    public void testInstance() {
        System.out.println("Test with existent file.");
        Rates rates = new Rates("curs.xml");
        assertNotNull(rates);

        Double eur = rates.getRate("EUR");
        assertEquals(4.4919, eur, DELTA);

        Double huf = rates.getRate("HUF");
        assertEquals(143.52, huf, DELTA);

        Double jpy = rates.getRate("JPY");
        assertEquals(366.29999999999995, jpy, DELTA);
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testExpectedException() {
        Rates rates = new Rates("curs.xml");
        assertNotNull(rates);

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(containsString("currency"));

        Double nnn = rates.getRate("NNN");
    }
}
