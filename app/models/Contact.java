package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class Contact extends Model {
	
	public String name;
	
	public String address;
	
	public String email;
	
	@Temporal(TemporalType.DATE)
	public Date birthday;
	
	public String telephone;
	
	public Contact(String name, String address, String email, Date birthday, String telephone) {
		this.name = name;
		this.address = address;
		this.email = email;
		this.birthday = birthday;
		this.telephone = telephone;
	}
}