package in.brewcode.api.persistence.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
@Embeddable
public class PersonalDetails {
	
	@Column(name="FIRST_NAME")
	private String firstName;
	
	@Column(name="LAST_NAME")
	private String lastName;
	
	@Column(name="MOBILE_NUMBER")
	private String mobileNumber;
	
	@Column(name="DATE_OF_BIRTH")
	private Date dateOfBirth;
	
	@Column(name="ADDRESS")
	private String address;
	
	@Column(name="EMAIL_ADDRESS")
	private String emailAddress;
	
	
	

}
