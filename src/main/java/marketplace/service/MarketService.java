package marketplace.service;

import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import marketplace.dao.MarketDAO;
import marketplace.domain.MarketAd;

@Service
public class MarketService {

	@Autowired
	private MarketDAO dao;
	
	public List<MarketAd> getAds(String text, String minPrice, String maxPrice) {
		List<MarketAd> ads = new ArrayList<MarketAd>();
		try {			
			ads = dao.findAll(text, Integer.parseInt(minPrice), Integer.parseInt(maxPrice));
		} catch (Exception e) {
			// TODO
		}		
		return ads;
	}
	
	public MarketAd saveAd(MarketAd ad) throws ConstraintViolationException, MarketPriceViolationException, MarketEmailViolationException, MarketImageURLViolationException{
		
		try {
			return dao.save(ad);
		} catch (Exception e) {
			String msg = e.getMessage();			
			if( msg.contains("not a well-formed email address")) {
				throw new MarketEmailViolationException();
			} 
			else if( msg.contains("may not be null") && msg.contains("priceCents")) {
				throw new MarketPriceViolationException();
			}
			else if( msg.contains("must be between") && msg.contains("priceCents")) {
				throw new MarketPriceViolationException();
			}
			else if( msg.contains("must be a valid URL") && msg.contains("imageUrl")) {
				throw new MarketImageURLViolationException();
			}	
			else {
				throw new ConstraintViolationException(null, null);
			}			
		}
	}

	public MarketAd getAd(long id) {
		return dao.findById(id);
	}

}
