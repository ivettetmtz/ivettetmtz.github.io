package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import main.java.model.ContactService;

public class ContactServiceTest {
  protected String contactId, firstName, lastName, phoneNumber,
  address;
  protected String IDLong, firstNameLong, lastNameLong,
  phoneNumberLong, addressLong;

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
  void newContactTest() {
    ContactService service = new ContactService();
    service.newContact();
    assertAll(
        "service",
        ()
            -> assertNotNull(service.getContactList().get(0).getContactId()),
        ()
            -> assertEquals("INITIAL",
                            service.getContactList().get(0).getFirstName()),
        ()
            -> assertEquals("INITIAL",
                            service.getContactList().get(0).getLastName()),
        ()
            -> assertEquals("1235559999",
                            service.getContactList().get(0).getPhoneNumber()),
        ()
            -> assertEquals("INITIAL",
                            service.getContactList().get(0).getAddress()));
    service.newContact(firstName);
    assertAll(
        "service",
        ()
            -> assertNotNull(service.getContactList().get(1).getContactId()),
        ()
            -> assertEquals(firstName,
                            service.getContactList().get(1).getFirstName()),
        ()
            -> assertEquals("INITIAL",
                            service.getContactList().get(1).getLastName()),
        ()
            -> assertEquals("1235559999",
                            service.getContactList().get(1).getPhoneNumber()),
        ()
            -> assertEquals("INITIAL",
                            service.getContactList().get(1).getAddress()));
    service.newContact(firstName, lastName);
    assertAll(
        "service",
        ()
            -> assertNotNull(service.getContactList().get(2).getContactId()),
        ()
            -> assertEquals(firstName,
                            service.getContactList().get(2).getFirstName()),
        ()
            -> assertEquals(lastName,
                            service.getContactList().get(2).getLastName()),
        ()
            -> assertEquals("1235559999",
                            service.getContactList().get(2).getPhoneNumber()),
        ()
            -> assertEquals("INITIAL",
                            service.getContactList().get(2).getAddress()));
    service.newContact(firstName, lastName, phoneNumber);
    assertAll(
        "service",
        ()
            -> assertNotNull(service.getContactList().get(3).getContactId()),
        ()
            -> assertEquals(firstName,
                            service.getContactList().get(3).getFirstName()),
        ()
            -> assertEquals(lastName,
                            service.getContactList().get(3).getLastName()),
        ()
            -> assertEquals(phoneNumber,
                            service.getContactList().get(3).getPhoneNumber()),
        ()
            -> assertEquals("INITIAL",
                            service.getContactList().get(3).getAddress()));
    service.newContact(firstName, lastName, phoneNumber,
                       address);
    assertAll(
        "service",
        ()
            -> assertNotNull(service.getContactList().get(4).getContactId()),
        ()
            -> assertEquals(firstName,
                            service.getContactList().get(4).getFirstName()),
        ()
            -> assertEquals(lastName,
                            service.getContactList().get(4).getLastName()),
        ()
            -> assertEquals(phoneNumber,
                            service.getContactList().get(4).getPhoneNumber()),
        ()
            -> assertEquals(address,
                            service.getContactList().get(4).getAddress()));
  }

  @Test
  void deleteContactTest() {
    ContactService service = new ContactService();
    service.newContact();
    assertThrows(Exception.class, () -> service.deleteContact(contactId));
    assertAll(()
                  -> service.deleteContact(
                      service.getContactList().get(0).getContactId()));
  }

  @Test
  void updateFirstNameTest() throws Exception {
    ContactService service = new ContactService();
    service.newContact();
    service.updateFirstName(service.getContactList().get(0).getContactId(),
                            firstName);
    assertEquals(firstName, service.getContactList().get(0).getFirstName());
    assertThrows(IllegalArgumentException.class,
                 ()
                     -> service.updateFirstName(
                         service.getContactList().get(0).getContactId(),
                         firstNameLong));
    assertThrows(IllegalArgumentException.class,
                 ()
                     -> service.updateFirstName(
                         service.getContactList().get(0).getContactId(), null));
    assertThrows(Exception.class,
                 () -> service.updateFirstName(contactId, firstName));
  }

  @Test
  void updateLastNameTest() throws Exception {
    ContactService service = new ContactService();
    service.newContact();
    service.updateLastName(service.getContactList().get(0).getContactId(),
                           lastName);
    assertEquals(lastName, service.getContactList().get(0).getLastName());
    assertThrows(IllegalArgumentException.class,
                 ()
                     -> service.updateLastName(
                         service.getContactList().get(0).getContactId(),
                         lastNameLong));
    assertThrows(IllegalArgumentException.class,
                 ()
                     -> service.updateLastName(
                         service.getContactList().get(0).getContactId(), null));
    assertThrows(Exception.class,
                 () -> service.updateLastName(contactId, lastName));
  }

  @Test
  void updatePhoneNumberTest() throws Exception {
    ContactService service = new ContactService();
    service.newContact();
    service.updatePhoneNumber(service.getContactList().get(0).getContactId(),
                              phoneNumber);
    assertEquals(phoneNumber,
                 service.getContactList().get(0).getPhoneNumber());
    assertThrows(IllegalArgumentException.class,
                 ()
                     -> service.updatePhoneNumber(
                         service.getContactList().get(0).getContactId(),
                         phoneNumberLong));
    assertThrows(
        IllegalArgumentException.class,
        ()
            -> service.updatePhoneNumber(
                service.getContactList().get(0).getContactId(), contactId));
    assertThrows(IllegalArgumentException.class,
                 ()
                     -> service.updatePhoneNumber(
                         service.getContactList().get(0).getContactId(), null));
    assertThrows(Exception.class,
                 () -> service.updatePhoneNumber(contactId, lastName));
  }

  @Test
  void updateAddressTest() throws Exception {
    ContactService service = new ContactService();
    service.newContact();
    service.updateAddress(service.getContactList().get(0).getContactId(),
                          address);
    assertEquals(address, service.getContactList().get(0).getAddress());
    assertThrows(IllegalArgumentException.class,
                 ()
                     -> service.updateAddress(
                         service.getContactList().get(0).getContactId(),
                         addressLong));
    assertThrows(IllegalArgumentException.class,
                 ()
                     -> service.updateAddress(
                         service.getContactList().get(0).getContactId(), null));
    assertThrows(Exception.class,
                 () -> service.updateAddress(contactId, address));
  }
}