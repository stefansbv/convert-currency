package ro.s2i2.exchange;

import ro.s2i2.exchange.Rates;

public class ConvertCurrency {
    public static void main(String[] args) {
        Rates rates = new Rates("curs.xml");
        System.out.println("Rate for EUR = " + rates.getRate("EUR"));
        System.out.println("Rate for HUF = " + rates.getRate("HUF"));
        System.out.println("Rate for NNN = " + rates.getRate("NNN"));
    }
}
