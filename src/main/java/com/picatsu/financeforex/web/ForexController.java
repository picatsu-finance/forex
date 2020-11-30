package com.picatsu.financeforex.web;


import com.picatsu.financeforex.model.ForexModel;
import com.picatsu.financeforex.repository.ForexModelRepository;
import com.picatsu.financeforex.service.ForexService;
import com.picatsu.financeforex.utils.CurrencyCode;
import com.picatsu.financeforex.utils.CustomFunctions;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.patriques.output.exchange.Daily;
import org.patriques.output.exchange.data.CurrencyExchangeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
    @Operation(summary = "exchange rate from x  to y ")
    public CurrencyExchangeData getExchange(@PathVariable CurrencyCode from, @PathVariable CurrencyCode to){
        log.info("Exchange by value "+ from + to);
        return forexService.getExchange(from.name(), to.name());
    }

    @GetMapping(value = "/daily/{from}/{to}")
    @Operation(summary = "Daily rate from x  to y ")
    public Daily getTss(@PathVariable CurrencyCode from, @PathVariable CurrencyCode to){
        log.info("Exchange by value "+ from + to);
        return forexService.getTSS(from.name(), to.name());
    }


    @GetMapping(value = "/load")
    @Deprecated
    @Operation(summary = "load all currency")
    public void getExchange(){
      for( CurrencyCode s : CurrencyCode.values()) {

          forexModelRepository.save(new ForexModel(s.toString(), s.getDescription()));
      }
    }

    @GetMapping(value = "/all")
    @Operation(summary = "retrieve all forex model")
    public List<ForexModel> getAllForexCode( HttpServletRequest request)  {
        customFunctions.displayStackTraceIP("/api/v1/forex/create", request);
        return forexModelRepository.findAll();
    }



    @GetMapping(value = "/paginate")
    @Operation(summary = "retrieve all paginated forex model")
    public Page<ForexModel> getPaginated(@RequestParam int page, @RequestParam int size, HttpServletRequest request)  {
        customFunctions.displayStackTraceIP("/api/v1/forex/create", request);
        return forexModelRepository.findAll(PageRequest.of(page, size));
    }

    @GetMapping(value = "/search-forex/{str}")
    @Operation(summary = "search all forexcode")
    public List<ForexModel> findCode(@PathVariable String str, HttpServletRequest request) {

        customFunctions.displayStackTraceIP("/search-forex/{str}", request);
        return new ArrayList<>(
                Stream.of(forexModelRepository.findByCodeContainsIgnoreCase(str),
                        forexModelRepository.findByNameContainsIgnoreCase(str))
                        .flatMap(List::stream)
                        .collect(Collectors.toMap(ForexModel::getCode,
                                d -> d, (ForexModel x, ForexModel y) -> x == null ? y : x))
                        .values());

    }

    @PostMapping(value = "/create")
    @Operation(summary = "save forex code to db")
    public ForexModel addForexCode(@RequestBody ForexModel forex, HttpServletRequest request) {

        customFunctions.displayStackTraceIP("/api/v1/forex/create", request);
        return forexModelRepository.insert(forex);
    }

    @DeleteMapping(value= "/{forex-code}")
    @Operation(summary = "delete forex from db")
    public ResponseEntity<?> deleteForexCode(@PathVariable(value= "forex-code") String code, HttpServletRequest request) {

        customFunctions.displayStackTraceIP("/api/v1/forex/{forex-code}/delete", request);


        long val =  forexModelRepository.deleteAllByCode(code);

        if ( val == 1) {
            return new ResponseEntity<>("Deleted successfully ", HttpStatus.OK);
        }
        if( val == 0 ) {
            return new ResponseEntity<>("Cannot find Symbol : " + code, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Obscure error ", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
