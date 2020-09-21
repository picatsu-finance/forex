package com.picatsu.financeforex.web;


import com.picatsu.financeforex.service.ForexService;
import com.picatsu.financeforex.utils.CurrencyCode;
import lombok.extern.slf4j.Slf4j;
import org.patriques.output.exchange.data.CurrencyExchangeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/api/v1/forex")
@RestController("ForexController")
@Slf4j
public class ForexController {

    @Autowired
    private ForexService forexService;


    @GetMapping(value = "/{from}/{to}")
    public CurrencyExchangeData getExchange(@PathVariable CurrencyCode from, @PathVariable CurrencyCode to){
        log.info("Exchange by value "+ from + to);
        return forexService.getExchange(from.name(), to.name());
    }



    @GetMapping(value = "/currencies")
    public Object getCurrencies(){
        log.info(" Currencies list ");
        return CurrencyCode.values();
    }

}
