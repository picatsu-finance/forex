package com.picatsu.financeforex.web;


import com.picatsu.financeforex.model.ForexModel;
import com.picatsu.financeforex.repository.ForexModelRepository;
import com.picatsu.financeforex.service.ForexService;
import com.picatsu.financeforex.utils.CurrencyCode;
import com.picatsu.financeforex.utils.CustomFunctions;
import lombok.extern.slf4j.Slf4j;
import org.patriques.output.exchange.data.CurrencyExchangeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequestMapping(value = "/api/v1/forex")
@RestController("ForexController")
@Slf4j
@CrossOrigin
public class ForexController {

    @Autowired
    private ForexService forexService;

    @Autowired
    private ForexModelRepository forexModelRepository;

    @Autowired
    private CustomFunctions customFunctions;

    @GetMapping(value = "/{from}/{to}")
    public CurrencyExchangeData getExchange(@PathVariable CurrencyCode from, @PathVariable CurrencyCode to){
        log.info("Exchange by value "+ from + to);
        return forexService.getExchange(from.name(), to.name());
    }

    @GetMapping(value = "/load")
    @Deprecated
    public void getExchange(){
      for( CurrencyCode s : CurrencyCode.values()) {

          forexModelRepository.save(new ForexModel(s.toString(), s.getDescription()));
      }
    }


    @GetMapping(value = "/paginate")
    public Page<ForexModel> getPaginated(@RequestParam int page, @RequestParam int size, HttpServletRequest request)  {
        customFunctions.displayStackTraceIP("/api/v1/forex/create", request);
        return forexModelRepository.findAll(PageRequest.of(page, size));
    }

    @GetMapping(value = "/search-forex/{str}")
    public List<ForexModel> findCode(@PathVariable String str, HttpServletRequest request) {
        customFunctions.displayStackTraceIP("/search-forex/{str}", request);
        return Stream.concat( forexModelRepository.findByCodeContainsIgnoreCase(str).stream(),
                forexModelRepository.findByNameContainsIgnoreCase(str).stream()
        ).collect(Collectors.toList());
    }

    @PostMapping(value = "/create")
    public ForexModel addForexCode(@RequestBody ForexModel forex, HttpServletRequest request) {

        customFunctions.displayStackTraceIP("/api/v1/forex/create", request);
        return forexModelRepository.insert(forex);
    }

    @DeleteMapping(value= "/{forex-code}/delete")
    public Boolean deleteForexCode(@PathVariable(value= "forex-code") String code, HttpServletRequest request) {

        customFunctions.displayStackTraceIP("/api/v1/forex/{forex-code}/delete", request);
        try {
            return forexModelRepository.deleteAllByCode(code);
        } catch (Exception e) {
            return null;
        }
    }

}
