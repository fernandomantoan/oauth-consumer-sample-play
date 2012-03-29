package controllers;

import java.util.Date;
import java.util.List;

import models.Contact;

import play.mvc.*;

public class ContactController extends Controller {
	
	@Before
	static void checkAuthentication() throws Exception {
		if (session.get("user") == null) AuthenticationController.auth();
	}

    public static void index() {
    	List<Contact> contacts = Contact.find("order by name asc").fetch();
        render(contacts);
    }
    
    public static void form(Long id) {
    	
    	if (id != null) {
    		Contact contact = Contact.findById(id);
    		render(contact);
    	}
    	
    	render();
    }
    
	public static void processForm(Long id, String name, String address, String email, Date birthday, String telephone) {
		Contact contact;
		
		if (id == null) {
			contact = new Contact(name, address, email, birthday, telephone);
		} else {
			contact = Contact.findById(id);
			contact.name = name;
			contact.address = address;
			contact.email = email;
			contact.birthday = birthday;
			contact.telephone = telephone;
		}
		
		validation.valid(contact);
		
		if (validation.hasErrors())
			render("@form", contact);
		
		contact.save();
		flash.success("Contato saved successfully");
		index();
	}
	
	public static void delete(Long id) {
		if (id != null) {
			Contact contact = Contact.findById(id);
			contact.delete();
			flash.success("Contact removed successfully");
		}
		index();
	}
}