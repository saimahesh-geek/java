package sm.designpatterns.creational.builder;

import java.time.LocalDate;

// This is the client method also works as 'directory'
public class Client {
	
	public static void main(String[] args) {
		User user = createUser();
		UserDTOBuilder builder =  new UserWebDTOBuilder();
		UserDTO dto = directBuild(builder, user);
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
