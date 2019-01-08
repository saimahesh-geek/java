package sm.designpatterns.creational.builder2;

import java.time.LocalDate;
import java.time.Period;

import sm.designpatterns.creational.builder.Address;

// product class
// make setters private to make userDTO immutable
public class UserDTO {
	
	private String name;
	private String address;
	private String age;
	
	public String getName() {
		return name;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getAge() {
		return age;
	}
	
	private void setName(String name) {
		this.name = name;
	}
	
	private void setAddress(String address) {
		this.address = address;
	}
	
	private void setAge(String age) {
		this.age = age;
	}
	
	@Override
	public String toString() {
		return "name=" + name + "\nage=" + age + "\naddress=" + address ;
	}
	
	// get builder instance
	public static UserDTOBuilder getBuilder() {
		return new UserDTOBuilder();
	}
	
	// builder
	public static class UserDTOBuilder {
		
		private String firstName;
		
		private String lastName;
		
		private String age;
		
		private String address;
		
		private UserDTO userDTO;
		
		public UserDTOBuilder withFirstName(String firstName) {
			this.firstName = firstName;
			return this;
		}
		
		public UserDTOBuilder withLastName(String lastName) {
			this.lastName = lastName;
			return this;
		}
		
		public UserDTOBuilder withBirthday(LocalDate date) {
			age = Integer.toString(Period.between(date, LocalDate.now()).getYears());
			return this;
		}
		
		public UserDTOBuilder withAddress(Address address) {
			this.address = address.getHouseNumber() + " " +address.getStreet()
						+ "\n"+address.getCity()+", "+address.getState()+" "+address.getZipcode(); 

			return this;
		}
		
		public UserDTO build() {
			this.userDTO = new UserDTO();
			userDTO.setName(firstName + " " + lastName);
			userDTO.setAddress(address);
			userDTO.setAge(age);
			return this.userDTO;
		}
		
		public UserDTO getUserDTO() {
			return this.userDTO;
		}
	}
	
}
