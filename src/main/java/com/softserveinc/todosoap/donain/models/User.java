package com.softserveinc.todosoap.donain.models;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "user")
@XmlType(propOrder = {
		"id",
		"firstName",
		"lastName",
		"userEmail"
})
public class User {

	private long id;
	private String firstName;
	private String lastName;
	private String userEmail;

	public long getId() {
		return id;
	}

	@XmlElement(name = "id")
	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	@XmlElement(name = "firstName")
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	@XmlElement(name = "lastName")
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	@XmlElement(name = "userEmail")
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof User)) return false;

		User user = (User) o;

		if (id != user.id) return false;
		if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
		if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
		return userEmail != null ? userEmail.equals(user.userEmail) : user.userEmail == null;
	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
		result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
		result = 31 * result + (userEmail != null ? userEmail.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", userEmail='" + userEmail + '\'' +
				'}';
	}
}
