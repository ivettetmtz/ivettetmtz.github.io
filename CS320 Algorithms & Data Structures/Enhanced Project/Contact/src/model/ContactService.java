package model;

//import java.util.ArrayList;
//import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;

public class ContactService {
    private String uniqueId;
    // Creates a HashMap replacing the ArrayList
    private Map<String, Contact> contactMap = new HashMap<>();

    {
        uniqueId = UUID.randomUUID().toString().substring(0, 10);
    }

    // creates a new contact with no details
    public Contact newContact() {
        Contact contact = new Contact(newUniqueId());
        contactMap.put(contact.getContactId(), contact);
        return contact;
    }

    // creates new contact with first name
    public Contact newContact(String firstName) {
        Contact contact = new Contact(newUniqueId(), firstName);
        contactMap.put(contact.getContactId(), contact);
        return contact;
    }

    // creates new contact with first name and last name
    public Contact newContact(String firstName, String lastName) {
        Contact contact = new Contact(newUniqueId(), firstName, lastName);
        contactMap.put(contact.getContactId(), contact);
        return contact;
    }

    // creates new contact with first name, last name, and phone number
    public Contact newContact(String firstName, String lastName, String phoneNumber) {
        Contact contact = new Contact(newUniqueId(), firstName, lastName, phoneNumber);
        contactMap.put(contact.getContactId(), contact);
        return contact;
    }

    // creates new contact with full details: first name, last name, phone number, address
    public Contact newContact(String firstName, String lastName, String phoneNumber, String address) {
        Contact contact = new Contact(newUniqueId(), firstName, lastName, phoneNumber, address);
        contactMap.put(contact.getContactId(), contact);
        return contact;
    }

    // deletes the contact by ID
    public void deleteContact(String id) throws Exception {
        if (contactMap.containsKey(id)) {
            contactMap.remove(id);
        } else {
            throw new Exception("Contact with ID " + id + " does not exist.");
        }
    }

    // updates the first name of a contact by ID
    public void updateFirstName(String id, String firstName) throws Exception {
        Contact contact = searchForContact(id);
        contact.updateFirstName(firstName);
    }

    // updates the last name of a contact by ID
    public void updateLastName(String id, String lastName) throws Exception {
        Contact contact = searchForContact(id);
        contact.updateLastName(lastName);
    }

    // updates the phone number of a contact by ID
    public void updatePhoneNumber(String id, String phoneNumber) throws Exception {
        Contact contact = searchForContact(id);
        contact.updatePhoneNumber(phoneNumber);
    }

    // updates the address of a contact by ID
    public void updateAddress(String id, String address) throws Exception {
        Contact contact = searchForContact(id);
        contact.updateAddress(address);
    }

    // gets the map of contacts
    public Map<String, Contact> getContactMap() {
        return contactMap;
    }

    // generating a new unique ID
    private String newUniqueId() {
        return UUID.randomUUID().toString().substring(0, Math.min(toString().length(), 10));
    }

    // searches for a contact by ID
    private Contact searchForContact(String id) throws Exception {
        Contact contact = contactMap.get(id);
        if (contact == null) {
            throw new Exception("Contact with ID " + id + " does not exist.");
        }
        return contact;
    }
}