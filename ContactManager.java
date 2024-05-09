import java.io.*;
import java.util.*;

class Contact {
    private String name;
    private String phoneNumber;
    private String emailAddress;

    public Contact(String name, String phoneNumber, String emailAddress) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Phone: " + phoneNumber + ", Email: " + emailAddress;
    }
}

public class ContactManager {
    private Map<String, Contact> contacts;
    private Scanner scanner;

    public ContactManager() {
        contacts = new HashMap<>();
        scanner = new Scanner(System.in);
    }

    public void addContact() {
        System.out.println("Enter contact details:");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Phone Number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Email Address: ");
        String emailAddress = scanner.nextLine();

        Contact contact = new Contact(name, phoneNumber, emailAddress);
        contacts.put(name, contact);
        System.out.println("Contact added successfully!");
    }

    public void viewContacts() {
        if (contacts.isEmpty()) {
            System.out.println("Contact list is empty.");
        } else {
            System.out.println("Contact List:");
            for (Contact contact : contacts.values()) {
                System.out.println(contact);
            }
        }
    }

    public void editContact() {
        System.out.print("Enter the name of the contact you want to edit: ");
        String name = scanner.nextLine();
        Contact contact = contacts.get(name);
        if (contact == null) {
            System.out.println("Contact not found.");
        } else {
            System.out.println("Enter new details for " + name + ":");
            System.out.print("Phone Number: ");
            String phoneNumber = scanner.nextLine();
            System.out.print("Email Address: ");
            String emailAddress = scanner.nextLine();

            contact.setPhoneNumber(phoneNumber);
            contact.setEmailAddress(emailAddress);
            System.out.println("Contact updated successfully!");
        }
    }

    public void deleteContact() {
        System.out.print("Enter the name of the contact you want to delete: ");
        String name = scanner.nextLine();
        if (contacts.containsKey(name)) {
            contacts.remove(name);
            System.out.println("Contact deleted successfully!");
        } else {
            System.out.println("Contact not found.");
        }
    }

    public void saveContactsToFile(String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(contacts);
            System.out.println("Contacts saved to file: " + fileName);
        } catch (IOException e) {
            System.out.println("Error saving contacts to file: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadContactsFromFile(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            contacts = (Map<String, Contact>) ois.readObject();
            System.out.println("Contacts loaded from file: " + fileName);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading contacts from file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        ContactManager contactManager = new ContactManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nContact Manager Menu:");
            System.out.println("1. Add Contact");
            System.out.println("2. View Contacts");
            System.out.println("3. Edit Contact");
            System.out.println("4. Delete Contact");
            System.out.println("5. Save Contacts to File");
            System.out.println("6. Load Contacts from File");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    contactManager.addContact();
                    break;
                case 2:
                    contactManager.viewContacts();
                    break;
                case 3:
                    contactManager.editContact();
                    break;
                case 4:
                    contactManager.deleteContact();
                    break;
                case 5:
                    System.out.print("Enter file name to save contacts: ");
                    String saveFileName = scanner.nextLine();
                    contactManager.saveContactsToFile(saveFileName);
                    break;
                case 6:
                    System.out.print("Enter file name to load contacts: ");
                    String loadFileName = scanner.nextLine();
                    contactManager.loadContactsFromFile(loadFileName);
                    break;
                case 7:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 7.");
            }
        }
    }
}
