package test;

import model.Contact;
import model.ContactService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class ContactServiceTest {
	// declares all the needed variables
	protected String contactId;
    protected String firstName;
    protected String lastName;
    protected String phoneNumber;
    protected String address;
    protected String IDLong;
    protected String firstNameLong;
    protected String lastNameLong;
    protected String phoneNumberLong;
    protected String addressLong;

    public ContactServiceTest() {
    }

    @BeforeEach
    //initiates each test for each variable
    void setUp() {
        this.contactId = "1029F847A6";
        this.firstName = "Jessica";
        this.lastName = "Martinez";
        this.phoneNumber = "2145689745";
        this.address = "Address1";
        this.IDLong = "112233445566778899";
        this.firstNameLong = "Juan Jacob Carlos";
        this.lastNameLong = "Beaver Carter";
        this.phoneNumberLong = "55512341234";
        this.addressLong = "17 Halvey Ville Drive, Fort Worth, TX 18605";
    }

    @Test
    void newContactTest() {
    	// gets the first contact ID based on the hash contact map
        ContactService service = new ContactService();
        Contact contact0 = service.newContact();
        String contactId0 = contact0.getContactId();
        Assertions.assertAll("service", new Executable[]{() -> {
            Assertions.assertNotNull(service.getContactMap().get(contactId0).getContactId());
        }, () -> {
            Assertions.assertEquals("INITIAL", service.getContactMap().get(contactId0).getFirstName());
        }, () -> {
            Assertions.assertEquals("INITIAL", service.getContactMap().get(contactId0).getLastName());
        }, () -> {
            Assertions.assertEquals("1235559999", service.getContactMap().get(contactId0).getPhoneNumber());
        }, () -> {
            Assertions.assertEquals("INITIAL", service.getContactMap().get(contactId0).getAddress());
        }});
        
     // gets the second contact ID based on the hash contact map
        Contact contact1 = service.newContact(this.firstName);
        String contactId1 = contact1.getContactId();
        Assertions.assertAll("service", new Executable[]{() -> {
            Assertions.assertNotNull(service.getContactMap().get(contactId1).getContactId());
        }, () -> {
            Assertions.assertEquals(this.firstName, service.getContactMap().get(contactId1).getFirstName());
        }, () -> {
            Assertions.assertEquals("INITIAL", service.getContactMap().get(contactId1).getLastName());
        }, () -> {
            Assertions.assertEquals("1235559999", service.getContactMap().get(contactId1).getPhoneNumber());
        }, () -> {
            Assertions.assertEquals("INITIAL", service.getContactMap().get(contactId1).getAddress());
        }});
        
     // gets the third contact ID based on the hash contact map
        Contact contact2 = service.newContact(this.firstName, this.lastName);
        String contactId2 = contact2.getContactId();
        Assertions.assertAll("service", new Executable[]{() -> {
            Assertions.assertNotNull(service.getContactMap().get(contactId2).getContactId());
        }, () -> {
            Assertions.assertEquals(this.firstName, service.getContactMap().get(contactId2).getFirstName());
        }, () -> {
            Assertions.assertEquals(this.lastName, service.getContactMap().get(contactId2).getLastName());
        }, () -> {
            Assertions.assertEquals("1235559999", service.getContactMap().get(contactId2).getPhoneNumber());
        }, () -> {
            Assertions.assertEquals("INITIAL", service.getContactMap().get(contactId2).getAddress());
        }});

        // gets the fourth contact ID based on the hash contact map
        Contact contact3 = service.newContact(this.firstName, this.lastName, this.phoneNumber);
        String contactId3 = contact3.getContactId();
        Assertions.assertAll("service", new Executable[]{() -> {
            Assertions.assertNotNull(service.getContactMap().get(contactId3).getContactId());
        }, () -> {
            Assertions.assertEquals(this.firstName, service.getContactMap().get(contactId3).getFirstName());
        }, () -> {
            Assertions.assertEquals(this.lastName, service.getContactMap().get(contactId3).getLastName());
        }, () -> {
            Assertions.assertEquals(this.phoneNumber, service.getContactMap().get(contactId3).getPhoneNumber());
        }, () -> {
            Assertions.assertEquals("INITIAL", service.getContactMap().get(contactId3).getAddress());
        }});

        // gets the fifth contact ID based on the hash contact map
        Contact contact4 = service.newContact(this.firstName, this.lastName, this.phoneNumber, this.address);
        String contactId4 = contact4.getContactId();
        Assertions.assertAll("service", new Executable[]{() -> {
            Assertions.assertNotNull(service.getContactMap().get(contactId4).getContactId());
        }, () -> {
            Assertions.assertEquals(this.firstName, service.getContactMap().get(contactId4).getFirstName());
        }, () -> {
            Assertions.assertEquals(this.lastName, service.getContactMap().get(contactId4).getLastName());
        }, () -> {
            Assertions.assertEquals(this.phoneNumber, service.getContactMap().get(contactId4).getPhoneNumber());
        }, () -> {
            Assertions.assertEquals(this.address, service.getContactMap().get(contactId4).getAddress());
        }});
    }

    @Test
    // delete test retrieves the first contact ID and deletes it
    void deleteContactTest() {
        ContactService service = new ContactService();
        service.newContact();
        String contactId0 = (String)service.getContactMap().keySet().toArray()[0];
        Assertions.assertThrows(Exception.class, () -> {
            service.deleteContact(this.contactId);
        });
        Assertions.assertAll("service", new Executable[]{() -> {
            service.deleteContact(contactId0);
        }});
    }

    @Test
    // update test gets the first contact ID and updates the first name
    void updateFirstNameTest() throws Exception {
        ContactService service = new ContactService();
        service.newContact();
        String contactId0 = (String)service.getContactMap().keySet().toArray()[0];
        service.updateFirstName(contactId0, this.firstName);
        Assertions.assertEquals(this.firstName, service.getContactMap().get(contactId0).getFirstName());
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.updateFirstName(contactId0, this.firstNameLong);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.updateFirstName(contactId0, (String)null);
        });
        Assertions.assertThrows(Exception.class, () -> {
            service.updateFirstName(this.contactId, this.firstName);
        });
    }

    @Test
 // update test gets the first contact ID and updates the last name
    void updateLastNameTest() throws Exception {
        ContactService service = new ContactService();
        service.newContact();
        String contactId0 = (String)service.getContactMap().keySet().toArray()[0];
        service.updateLastName(contactId0, this.lastName);
        Assertions.assertEquals(this.lastName, service.getContactMap().get(contactId0).getLastName());
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.updateLastName(contactId0, this.lastNameLong);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.updateLastName(contactId0, (String)null);
        });
        Assertions.assertThrows(Exception.class, () -> {
            service.updateLastName(this.contactId, this.lastName);
        });
    }

    @Test
    // update test gets the first contact ID and updates the phone number
    void updatePhoneNumberTest() throws Exception {
        ContactService service = new ContactService();
        service.newContact();
        String contactId0 = (String)service.getContactMap().keySet().toArray()[0];
        service.updatePhoneNumber(contactId0, this.phoneNumber);
        Assertions.assertEquals(this.phoneNumber, service.getContactMap().get(contactId0).getPhoneNumber());
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.updatePhoneNumber(contactId0, this.phoneNumberLong);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.updatePhoneNumber(contactId0, this.contactId);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.updatePhoneNumber(contactId0, (String)null);
        });
        Assertions.assertThrows(Exception.class, () -> {
            service.updatePhoneNumber(this.contactId, this.lastName);
        });
    }

    @Test
    // update test gets the first contact ID and updates the address
    void updateAddressTest() throws Exception {
        ContactService service = new ContactService();
        service.newContact();
        String contactId0 = (String)service.getContactMap().keySet().toArray()[0];
        service.updateAddress(contactId0, this.address);
        Assertions.assertEquals(this.address, service.getContactMap().get(contactId0).getAddress());
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.updateAddress(contactId0, this.addressLong);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.updateAddress(contactId0, (String)null);
        });
        Assertions.assertThrows(Exception.class, () -> {
            service.updateAddress(this.contactId, this.address);
        });
    }
}
