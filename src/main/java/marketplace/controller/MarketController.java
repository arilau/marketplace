package marketplace.controller;

import java.util.List;

import javax.validation.ConstraintViolationException;

import marketplace.domain.MarketAd;
import marketplace.service.MarketEmailViolationException;
import marketplace.service.MarketImageURLViolationException;
import marketplace.service.MarketPriceViolationException;
import marketplace.service.MarketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MarketController {
	
	@Autowired
	private MarketService service;
	
	@RequestMapping(method={RequestMethod.GET}, value={"/ads/{id}"})
	public MarketAd getAdById(@PathVariable("id") long id) {
		return service.getAd(id);
	}
		
	@RequestMapping(method={RequestMethod.POST}, value={"/ads"})
	public MarketAd saveAd(@RequestBody MarketAd ad) throws Exception {		
		return service.saveAd(ad);		
	}
	
	@RequestMapping(method={RequestMethod.GET}, value={"/ads"})
	public List<MarketAd> findAdsByParameters(@RequestParam("text") String text, @RequestParam("minPrice") String minPrice, @RequestParam("maxPrice") String maxPrice) throws Exception {		
		return service.getAds(text, minPrice, maxPrice);
	}
	
	/*
	 * 	Exception handling
	 */
	
	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "invalid email")
	@ExceptionHandler(MarketEmailViolationException.class)
	public void emailViolation() { }

	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "invalid price")
	@ExceptionHandler(MarketPriceViolationException.class)
	public void priceViolation() { }
	
	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "invalid url")
	@ExceptionHandler(MarketImageURLViolationException.class)
	public void imageURLViolation() { }
	
	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "unknown error")
	@ExceptionHandler(ConstraintViolationException.class)
	public void constraintViolation() { }
	
}


