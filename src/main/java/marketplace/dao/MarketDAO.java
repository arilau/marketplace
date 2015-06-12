package marketplace.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import javax.transaction.Transactional;

import java.util.List;

import marketplace.domain.MarketAd;

@Transactional
public interface MarketDAO extends Repository<MarketAd, Long> {
	
	List<MarketAd> findAll();
	
	MarketAd save(MarketAd ad);
	
	MarketAd findById(long id);
	
	@Query("SELECT m FROM MarketAd m "
			+ "WHERE ( m.title LIKE %?1% OR m.description LIKE %?1% ) "
			+ "AND (m.priceCents BETWEEN ?2 AND ?3)")
	List<MarketAd> findAll(String text, int minPrice, int maxPrice);

}

