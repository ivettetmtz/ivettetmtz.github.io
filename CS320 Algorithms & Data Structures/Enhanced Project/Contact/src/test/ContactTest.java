package test;

import model.Contact;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class ContactTest {
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

    public ContactTest() {
    }

    @BeforeEach
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
    void contactTest() {
        Contact contact = new Contact();
        Assertions.assertAll("Test", new Executable[]{() -> {
            Assertions.assertNotNull(contact.getContactId());
        }, () -> {
            Assertions.assertNotNull(contact.getFirstName());
        }, () -> {
            Assertions.assertNotNull(contact.getLastName());
        }, () -> {
            Assertions.assertNotNull(contact.getPhoneNumber());
        }, () -> {
            Assertions.assertNotNull(contact.getAddress());
        }});
    }

    @Test
    void FirstTest() {
        Contact contact = new Contact(this.contactId);
        Assertions.assertAll("Test One:", new Executable[]{() -> {
            Assertions.assertEquals(this.contactId, contact.getContactId());
        }, () -> {
            Assertions.assertNotNull(contact.getFirstName());
        }, () -> {
            Assertions.assertNotNull(contact.getLastName());
        }, () -> {
            Assertions.assertNotNull(contact.getPhoneNumber());
        }, () -> {
            Assertions.assertNotNull(contact.getAddress());
        }});
    }

    @Test
    void SecondTest() {
        Contact contact = new Contact(this.contactId, this.firstName);
        Assertions.assertAll("Test Two:", new Executable[]{() -> {
            Assertions.assertEquals(this.contactId, contact.getContactId());
        }, () -> {
            Assertions.assertEquals(this.firstName, contact.getFirstName());
        }, () -> {
            Assertions.assertNotNull(contact.getLastName());
        }, () -> {
            Assertions.assertNotNull(contact.getPhoneNumber());
        }, () -> {
            Assertions.assertNotNull(contact.getAddress());
        }});
    }

    @Test
    void ThirdTest() {
        Contact contact = new Contact(this.contactId, this.firstName, this.lastName);
        Assertions.assertAll("Test Three:", new Executable[]{() -> {
            Assertions.assertEquals(this.contactId, contact.getContactId());
        }, () -> {
            Assertions.assertEquals(this.firstName, contact.getFirstName());
        }, () -> {
            Assertions.assertEquals(this.lastName, contact.getLastName());
        }, () -> {
            Assertions.assertNotNull(contact.getPhoneNumber());
        }, () -> {
            Assertions.assertNotNull(contact.getAddress());
        }});
    }

    @Test
    void FourthTest() {
        Contact contact = new Contact(this.contactId, this.firstName, this.lastName, this.phoneNumber);
        Assertions.assertAll("Test Four:", new Executable[]{() -> {
            Assertions.assertEquals(this.contactId, contact.getContactId());
        }, () -> {
            Assertions.assertEquals(this.firstName, contact.getFirstName());
        }, () -> {
            Assertions.assertEquals(this.lastName, contact.getLastName());
        }, () -> {
            Assertions.assertEquals(this.phoneNumber, contact.getPhoneNumber());
        }, () -> {
            Assertions.assertNotNull(contact.getAddress());
        }});
    }

    @Test
    void AllTest() {
        Contact contact = new Contact(this.contactId, this.firstName, this.lastName, this.phoneNumber, this.address);
        Assertions.assertAll("Testing All:", new Executable[]{() -> {
            Assertions.assertEquals(this.contactId, contact.getContactId());
        }, () -> {
            Assertions.assertEquals(this.firstName, contact.getFirstName());
        }, () -> {
            Assertions.assertEquals(this.lastName, contact.getLastName());
        }, () -> {
            Assertions.assertEquals(this.phoneNumber, contact.getPhoneNumber());
        }, () -> {
            Assertions.assertEquals(this.address, contact.getAddress());
        }});
    }

    @Test
    void updateFirstNameTest() {
        Contact contact = new Contact();
        contact.updateFirstName(this.firstName);
        Assertions.assertAll("First Name:", new Executable[]{() -> {
            Assertions.assertEquals(this.firstName, contact.getFirstName());
        }, () -> {
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                contact.updateFirstName((String)null);
            });
        }, () -> {
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                contact.updateFirstName(this.firstNameLong);
            });
        }});
    }

    @Test
    void updateLastNameTest() {
        Contact contact = new Contact();
        contact.updateLastName(this.lastName);
        Assertions.assertAll("Last Name:", new Executable[]{() -> {
            Assertions.assertEquals(this.lastName, contact.getLastName());
        }, () -> {
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                contact.updateLastName((String)null);
            });
        }, () -> {
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                contact.updateLastName(this.lastNameLong);
            });
        }});
    }

    @Test
    void updatePhoneNumberTest() {
        Contact contact = new Contact();
        contact.updatePhoneNumber(this.phoneNumber);
        Assertions.assertAll("Phone Number:", new Executable[]{() -> {
            Assertions.assertEquals(this.phoneNumber, contact.getPhoneNumber());
        }, () -> {
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                contact.updatePhoneNumber((String)null);
            });
        }, () -> {
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                contact.updatePhoneNumber(this.phoneNumberLong);
            });
        }, () -> {
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                contact.updatePhoneNumber(this.contactId);
            });
        }});
    }

    @Test
    void updateAddressTest() {
        Contact contact = new Contact();
        contact.updateAddress(this.address);
        Assertions.assertAll("Address:", new Executable[]{() -> {
            Assertions.assertEquals(this.address, contact.getAddress());
        }, () -> {
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                contact.updateAddress((String)null);
            });
        }, () -> {
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                contact.updateAddress(this.addressLong);
            });
        }});
    }

    @Test
    void updateContactIdTest() {
        Contact contact = new Contact();
        contact.updateContactId(this.contactId);
        Assertions.assertAll("Contact ID:", new Executable[]{() -> {
            Assertions.assertEquals(this.contactId, contact.getContactId());
        }, () -> {
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                contact.updateContactId((String)null);
            });
        }, () -> {
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                contact.updateContactId(this.IDLong);
            });
        }});
    }
}
