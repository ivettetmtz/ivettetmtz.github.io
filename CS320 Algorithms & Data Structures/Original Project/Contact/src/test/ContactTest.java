package test;
import org.junit.jupiter.api.Test;
import main.java.model.Contact;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

public class ContactTest {
	
	protected String contactId, firstName, lastName, phoneNumber, address;
	protected String IDLong, firstNameLong, lastNameLong, phoneNumberLong, addressLong;
	
	@BeforeEach
	void setUp() {
		contactId = "1029F847A6";
		firstName = "Jessica";
		lastName = "Martinez";
		phoneNumber = "2145689745";
		address = "Address1";
	    IDLong = "112233445566778899";
	    firstNameLong = "Juan Jacob Carlos";
	    lastNameLong = "Beaver Carter";
	    phoneNumberLong = "55512341234";
	    addressLong = "17 Halvey Ville Drive, Fort Worth, TX 18605";
	}
	
	@Test
	  void contactTest() {
	    Contact contact = new Contact();
	    assertAll("Test",
	              ()
	                  -> assertNotNull(contact.getContactId()),
	              ()
	                  -> assertNotNull(contact.getFirstName()),
	              ()
	                  -> assertNotNull(contact.getLastName()),
	              ()
	                  -> assertNotNull(contact.getPhoneNumber()),
	              () -> assertNotNull(contact.getAddress()));
	  }

	  @Test
	  void FirstTest() {
	    Contact contact = new Contact(contactId);
	    assertAll("Test One:",
	              ()
	                  -> assertEquals(contactId, contact.getContactId()),
	              ()
	                  -> assertNotNull(contact.getFirstName()),
	              ()
	                  -> assertNotNull(contact.getLastName()),
	              ()
	                  -> assertNotNull(contact.getPhoneNumber()),
	              () -> assertNotNull(contact.getAddress()));
	  }

	  @Test
	  void SecondTest() {
	    Contact contact = new Contact(contactId, firstName);
	    assertAll("Test Two:",
	              ()
	                  -> assertEquals(contactId, contact.getContactId()),
	              ()
	                  -> assertEquals(firstName, contact.getFirstName()),
	              ()
	                  -> assertNotNull(contact.getLastName()),
	              ()
	                  -> assertNotNull(contact.getPhoneNumber()),
	              () -> assertNotNull(contact.getAddress()));
	  }

	  @Test
	  void ThirdTest() {
	    Contact contact = new Contact(contactId, firstName, lastName);
	    assertAll("Test Three:",
	              ()
	                  -> assertEquals(contactId, contact.getContactId()),
	              ()
	                  -> assertEquals(firstName, contact.getFirstName()),
	              ()
	                  -> assertEquals(lastName, contact.getLastName()),
	              ()
	                  -> assertNotNull(contact.getPhoneNumber()),
	              () -> assertNotNull(contact.getAddress()));
	  }

	  @Test
	  void FourthTest() {
	    Contact contact =
	        new Contact(contactId, firstName, lastName, phoneNumber);
	    assertAll("Test Four:",
	              ()
	                  -> assertEquals(contactId, contact.getContactId()),
	              ()
	                  -> assertEquals(firstName, contact.getFirstName()),
	              ()
	                  -> assertEquals(lastName, contact.getLastName()),
	              ()
	                  -> assertEquals(phoneNumber, contact.getPhoneNumber()),
	              () -> assertNotNull(contact.getAddress()));
	  }

	  @Test
	  void AllTest() {
	    Contact contact = new Contact(contactId, firstName, lastName,
	                                  phoneNumber, address);
	    assertAll("Testing All:",
	              ()
	                  -> assertEquals(contactId, contact.getContactId()),
	              ()
	                  -> assertEquals(firstName, contact.getFirstName()),
	              ()
	                  -> assertEquals(lastName, contact.getLastName()),
	              ()
	                  -> assertEquals(phoneNumber, contact.getPhoneNumber()),
	              () -> assertEquals(address, contact.getAddress()));
	  }
	    
	@Test
	  void updateFirstNameTest() {
	    Contact contact = new Contact();
	    contact.updateFirstName(firstName);
	    assertAll(
	        "First Name:",
	        ()
	            -> assertEquals(firstName, contact.getFirstName()),
	        ()
	            -> assertThrows(IllegalArgumentException.class,
	                            () -> contact.updateFirstName(null)),
	        ()
	            -> assertThrows(IllegalArgumentException.class,
	                            () -> contact.updateFirstName(firstNameLong)));
	  }

	  @Test
	  void updateLastNameTest() {
	    Contact contact = new Contact();
	    contact.updateLastName(lastName);
	    assertAll(
	        "Last Name:",
	        ()
	            -> assertEquals(lastName, contact.getLastName()),
	        ()
	            -> assertThrows(IllegalArgumentException.class,
	                            () -> contact.updateLastName(null)),
	        ()
	            -> assertThrows(IllegalArgumentException.class,
	                            () -> contact.updateLastName(lastNameLong)));
	  }

	  @Test
	  void updatePhoneNumberTest() {
	    Contact contact = new Contact();
	    contact.updatePhoneNumber(phoneNumber);
	    assertAll("Phone Number:",
	              ()
	                  -> assertEquals(phoneNumber, contact.getPhoneNumber()),
	              ()
	                  -> assertThrows(IllegalArgumentException.class,
	                                  () -> contact.updatePhoneNumber(null)),
	              ()
	                  -> assertThrows(
	                      IllegalArgumentException.class,
	                      () -> contact.updatePhoneNumber(phoneNumberLong)),
	              ()
	                  -> assertThrows(IllegalArgumentException.class,
	                                  () -> contact.updatePhoneNumber(contactId)));
	  }

	  @Test
	  void updateAddressTest() {
	    Contact contact = new Contact();
	    contact.updateAddress(address);
	    assertAll("Address:",
	              ()
	                  -> assertEquals(address, contact.getAddress()),
	              ()
	                  -> assertThrows(IllegalArgumentException.class,
	                                  () -> contact.updateAddress(null)),
	              ()
	                  -> assertThrows(IllegalArgumentException.class,
	                                  () -> contact.updateAddress(addressLong)));
	  }

	  @Test
	  void updateContactIdTest() {
	    Contact contact = new Contact();
	    contact.updateContactId(contactId);
	    assertAll(
	        "Contact ID:",
	        ()
	            -> assertEquals(contactId, contact.getContactId()),
	        ()
	            -> assertThrows(IllegalArgumentException.class,
	                            () -> contact.updateContactId(null)),
	        ()
	            -> assertThrows(IllegalArgumentException.class,
	                            () -> contact.updateContactId(IDLong)));
	  }
}
