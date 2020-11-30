package com.picatsu.financeforex.service;


import org.patriques.AlphaVantageConnector;
import org.patriques.ForeignExchange;
import org.patriques.input.timeseries.OutputSize;
import org.patriques.output.AlphaVantageException;
import org.patriques.output.exchange.CurrencyExchange;
import org.patriques.output.exchange.Daily;
import org.patriques.output.exchange.data.CurrencyExchangeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ForexService {

    @Autowired
    private AlphaVantageConnector apiConnector;


    public CurrencyExchangeData getExchange(String lhs, String rhs ) {
        ForeignExchange foreignExchange = new ForeignExchange(apiConnector);
        CurrencyExchangeData currencyExchangeData = null;
        try {
            CurrencyExchange currencyExchange = foreignExchange.currencyExchangeRate(lhs, rhs);
            currencyExchangeData = currencyExchange.getData();

            // System.out.println( foreignExchange.daily( lhs, rhs, OutputSize.COMPACT    ) );


        } catch (AlphaVantageException e) {
            System.out.println("something went wrong");
        }
        return currencyExchangeData;
    }

    public Daily getTSS(String lhs, String rhs ) {
        ForeignExchange foreignExchange = new ForeignExchange(apiConnector);
        Daily d = null;
        try {
            d = foreignExchange.daily( lhs, rhs, OutputSize.COMPACT    );




            // System.out.println( foreignExchange.daily( lhs, rhs, OutputSize.COMPACT    ) );


        } catch (AlphaVantageException e) {
            System.out.println("something went wrong");
        }
        return d;
    }

}
