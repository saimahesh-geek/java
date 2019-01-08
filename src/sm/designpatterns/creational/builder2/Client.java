package sm.designpatterns.creational.builder2;

import java.time.LocalDate;

import sm.designpatterns.creational.builder.Address;
import sm.designpatterns.creational.builder.User;
import sm.designpatterns.creational.builder2.UserDTO.UserDTOBuilder;

// This is the client method also works as 'directory'
public class Client {
	
	public static void main(String[] args) {
		User user = createUser();
		// client has to provide director with concrete builder
		UserDTO dto = directBuild(UserDTO.getBuilder(), user);
		System.out.println(dto);
	}
	
	// director
	private static UserDTO directBuild(UserDTOBuilder builder, User user) {
		return builder.withFirstName(user.getFirstName())
			   .withLastName(user.getLastName())
			   .withBirthday(user.getBirthday())
			   .withAddress(user.getAddress())
			   .build();
			   
	}
	
	// returns a sample user (or may use persistence layer)
	public static User createUser() {
		User user = new User();
		user.setBirthday(LocalDate.of(1992, 12, 2));
		user.setFirstName("Peter");
		user.setLastName("Parker");
		Address address = new Address();
		address.setHouseNumber("100");
		address.setStreet("State Street");
		address.setCity("NY");
		address.setState("US");
		address.setZipcode("00001");
		user.setAddress(address);
		return user;
	}
}
