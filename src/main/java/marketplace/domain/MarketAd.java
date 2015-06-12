package marketplace.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

@Entity
public class MarketAd {

    @Id 
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    @NotNull  
    private String title;

    @Column(nullable = false)
    @NotNull
    private String description;
    
    @Column(nullable = false)
    @NotNull
    @Range(min = 0, max = 100000000)
    private Integer priceCents;
	
    @Column(nullable = false)
    @NotNull
    private String phone;
    
    @Column(nullable = false)
    @NotNull
    @Email
    private String email;
    
    @Column
    @URL
    private String imageUrl;
	
	protected MarketAd() {
		
	}
	
	public MarketAd(String title, String description, Integer priceCents, String phone, String email, String imageUrl) {
		this.title = title;
		this.description = description;
		this.priceCents = priceCents;
		this.phone = phone;
		this.email = email;
		this.imageUrl = imageUrl;		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPriceCents() {
		return priceCents;
	}

	public void setPriceCents(Integer priceCents) {
		this.priceCents = priceCents;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String toString() {
		return new String("id:" + this.id + ", title:" + this.title + ", description:" + this.description + 
				", priceCents: " + this.priceCents + ", phone: " + this.phone + ", email: " +
				this.email + ", imageUrl: " + this.imageUrl);
	}
 
}
