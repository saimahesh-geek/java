package sm.designpatterns.creational.builder;

import java.time.LocalDate;

// abstract builder
public interface UserDTOBuilder {
	// methods to build part of product at a time
	UserDTOBuilder withFirstName(String firstName);
	
	UserDTOBuilder withLastName(String lastName);
	
	UserDTOBuilder withBirthday(LocalDate date);
	
	UserDTOBuilder withAddress(Address address);
	
	// method to assemble final product
	UserDTO build();
	
	// (optional) method to fetch already build object
	UserDTO getUserDTO();
}
