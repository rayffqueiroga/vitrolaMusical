package models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import models.exception.NewAdException;

@Entity(name = "user")
public class User implements Serializable {
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "profile")
	private String profile;
	
	@Column(name = "city", nullable = false)
	private String city;
	
	@Column(name = "neighborhood", nullable = false)
	private String neighborhood;
					
	@ManyToMany (cascade = CascadeType.ALL)
	private List<Instrument> instruments;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="user_goodstyle")
	private List<Style> goodStyles;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="user_badstyle")
	private List<Style> badStyles;
	
	@Transient
	private static final long serialVersionUID = 1L;
	
	protected User() {
	}
	
	public User(String email, String profile, String city, String neighborhood, List<Instrument> instruments, 
			List<Style> bad, List<Style> good) throws NewAdException {
		
		checkRequiredInfo(city, neighborhood, instruments);
		checkContactInfo(email, profile);
		checkStyleInfo(good, bad);
				
		this.city = city;
		this.email = email;
		this.profile = profile;
		this.neighborhood = neighborhood;
		
		this.badStyles = bad;
		this.goodStyles = good;
		this.instruments = instruments;		
	}
	
	// Getters and Setters
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public List<Instrument> getInstruments() {
		return instruments;
	}

	public void setInstruments(List<Instrument> instruments) {
		this.instruments = instruments;
	}

	public List<Style> getGoodStyles() {
		return goodStyles;
	}

	public void setGoodStyles(List<Style> goodStyles) {
		this.goodStyles = goodStyles;
	}

	public List<Style> getBadStyles() {
		return badStyles;
	}

	public void setBadStyles(List<Style> badStyles) {
		this.badStyles = badStyles;
	}
	
	private void checkRequiredInfo(String city, String neighborhood, 
			List<Instrument> instruments) throws NewAdException {
		
		if(city == null || neighborhood == null || city.trim().isEmpty() || 
					neighborhood.trim().isEmpty() || instruments.isEmpty()) {
			throw new NewAdException("os campos de cidade, bairro e os "
						+ "instrumentos que você toca são obrigatórios");
		}
	}
	
	private void checkContactInfo(String email, String profile) throws NewAdException {
		if((email == null && profile == null) || 
				(email.trim().isEmpty() && profile.isEmpty())) {
			throw new NewAdException("é necessário pelo menos uma forma de contato: "
						+ "email ou perfil no facebook");
		}
		
		if(!email.trim().isEmpty()) {
			Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
					+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		
			Matcher matcher = pattern.matcher(email);
			if(!matcher.matches()) {
				throw new NewAdException("o email formecido é inválido, por favor verifique!");
			}
		}
	}
	
	private void checkStyleInfo(List<Style> good, List<Style> bad) throws NewAdException {
		List<String> badStyles = new ArrayList<String>();
		List<String> goodStyles = new ArrayList<String>();
		
		for(Style style : bad) {
			badStyles.add(style.getNome());
		}
		
		for(Style style : good) {
			goodStyles.add(style.getNome());
		}
		
		for(String styleName : goodStyles) {
			if(badStyles.contains(styleName)) {
				throw new NewAdException("existe um ou mais estilos nas duas listas, "
						+ "por favor verifique!");
			}
		}
	}
}
