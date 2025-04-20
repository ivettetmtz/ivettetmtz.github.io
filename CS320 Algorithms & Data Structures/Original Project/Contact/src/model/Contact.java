package main.java.model;
public class Contact {

	private static final int numberLength = 10;
	private static final byte contactIDLength = 10;
	private static final byte firstNameLength = 10;
	private static final byte lastNameLength = 10;
	private static final byte addressLength = 30;
	private static final String initializer = "INITIAL";
	private static final String initializerNum = "1235559999";
	private String contactID;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String address;

	public Contact() {
	    this.contactID = initializer;
	    this.firstName = initializer;
	    this.lastName = initializer;
	    this.phoneNumber = initializerNum;
	    this.address = initializer;
	}

	  public Contact(String contactID) {
	    updateContactId(contactID);
	    this.firstName = initializer;
	    this.lastName = initializer;
	    this.phoneNumber = initializerNum;
	    this.address = initializer;
	  }

	  public Contact(String contactID, String firstName) {
	    updateContactId(contactID);
	    updateFirstName(firstName);
	    this.lastName = initializer;
	    this.phoneNumber = initializerNum;
	    this.address = initializer;
	  }

	  public Contact(String contactID, String firstName, String lastName) {
	    updateContactId(contactID);
	    updateFirstName(firstName);
	    updateLastName(lastName);
	    this.phoneNumber = initializerNum;
	    this.address = initializer;
	  }

	  public Contact(String contactID, String firstName, String lastName,
	          String phoneNumber) {
	    updateContactId(contactID);
	    updateFirstName(firstName);
	    updateLastName(lastName);
	    updatePhoneNumber(phoneNumber);
	    this.address = initializer;
	  }

	  public Contact(String contactID, String firstName, String lastName,
	          String phoneNumber, String address) {
	    updateContactId(contactID);
	    updateFirstName(firstName);
	    updateLastName(lastName);
	    updatePhoneNumber(phoneNumber);
	    updateAddress(address);
	  }

	  public final String getContactId(){ 
		  return contactID; 
	  }

	  public final String getFirstName(){ 
		  return firstName; 
	  }

	  public final String getLastName(){ 
		  return lastName; 
	  }

	  public final String getPhoneNumber(){ 
		  return phoneNumber; 
	  }

	  public final String getAddress(){ 
		  return address; 
	  }

	  public void updateFirstName(String firstName) {
	    if (firstName == null) {
	      throw new IllegalArgumentException("First name cannot be empty");
	    } else if (firstName.length() > firstNameLength) {
	      throw new IllegalArgumentException("First name cannot be longer than " +
	    		  firstNameLength + " characters");
	    } else {
	      this.firstName = firstName;
	    }
	  }

	  public void updateLastName(String lastName) {
	    if (lastName == null) {
	      throw new IllegalArgumentException("Last name cannot be empty");
	    } else if (lastName.length() > lastNameLength) {
	      throw new IllegalArgumentException("Last name cannot be longer than " + lastNameLength + " characters");
	    } else {
	      this.lastName = lastName;
	    }
	  }

	  public void updatePhoneNumber(String phoneNumber) {
	    String regex = "[0-9]+";
	    if (phoneNumber == null) { 
	      throw new IllegalArgumentException("Phone number cannot be empty."); 
	    } else if (phoneNumber.length() != numberLength) {
	      throw new IllegalArgumentException(
	          "Phone number length invalid. Ensure it is " + numberLength + " digits.");
	    } else if (!phoneNumber.matches(regex)) {
	      throw new IllegalArgumentException(
	          "Phone number cannot have anything but numbers");
	    } else {
	      this.phoneNumber = phoneNumber;
	    }
	  }

	  public void updateAddress(String address) {
	    if (address == null) {
	      throw new IllegalArgumentException("Address cannot be empty");
	    } else if (address.length() > addressLength) {
	      throw new IllegalArgumentException("Address cannot be longer than " +
	    		  addressLength + " characters");
	    } else {
	      this.address = address;
	    }
	  }

	  public void updateContactId(String contactId) {
	    if (contactId == null) {
	      throw new IllegalArgumentException("Contact ID cannot be empty");
	    } else if (contactId.length() > contactIDLength) {
	      throw new IllegalArgumentException("Contact ID cannot be longer than " +
	    		  contactIDLength + " characters");
	    } else {
	      this.contactID = contactId;
	    }
	 }
}
