package main.java.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ContactService {

  private String uniqueId;
  private List<Contact> contactList = new ArrayList<>();

  {
    uniqueId = UUID.randomUUID().toString().substring(
        0, Math.min(toString().length(), 10));
  }

  public void newContact() {
    Contact contact = new Contact(newUniqueId());
    contactList.add(contact);
  }

  public void newContact(String firstName) {
    Contact contact = new Contact(newUniqueId(), firstName);
    contactList.add(contact);
  }

  public void newContact(String firstName, String lastName) {
    Contact contact = new Contact(newUniqueId(), firstName, lastName);
    contactList.add(contact);
  }

  public void newContact(String firstName, String lastName,
                         String phoneNumber) {
    Contact contact =
        new Contact(newUniqueId(), firstName, lastName, phoneNumber);
    contactList.add(contact);
  }

  public void newContact(String firstName, String lastName, String phoneNumber,
                         String address) {
    Contact contact =
        new Contact(newUniqueId(), firstName, lastName, phoneNumber, address);
    contactList.add(contact);
  }

  public void deleteContact(String id) throws Exception {
    contactList.remove(searchForContact(id));
  }

  public void updateFirstName(String id, String firstName) throws Exception {
    searchForContact(id).updateFirstName(firstName);
  }

  public void updateLastName(String id, String lastName) throws Exception {
    searchForContact(id).updateLastName(lastName);
  }

  public void updatePhoneNumber(String id, String phoneNumber)
      throws Exception {
    searchForContact(id).updatePhoneNumber(phoneNumber);
  }

  public void updateAddress(String id, String address) throws Exception {
    searchForContact(id).updateAddress(address);
  }

  public List<Contact> getContactList() { return contactList; }

  private String newUniqueId() {
    return uniqueId = UUID.randomUUID().toString().substring(
               0, Math.min(toString().length(), 10));
  }

  private Contact searchForContact(String id) throws Exception {
    int index = 0;
    while (index < contactList.size()) {
      if (id.equals(contactList.get(index).getContactId())) {
        return contactList.get(index);
      }
      index++;
    }
    throw new Exception("This does not exist.");
  }
}