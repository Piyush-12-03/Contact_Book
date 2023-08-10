
//contact book handling project java
import java.io.*;
import java.util.*;

class Contact implements Serializable {
    private String name;
    private String phoneNumber;

    public Contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Phone: " + phoneNumber;
    }
}

public class contactbook {
    private List<Contact> contacts;
    private File file;

    public contactbook(String fileName) {
        contacts = new ArrayList<>();
        file = new File(fileName);
        loadContacts();
    }

    private void loadContacts() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file))) {
            contacts = (List<Contact>) inputStream.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Address book file not found. Starting with an empty address book.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading contacts: " + e.getMessage());
        }
    }

    private void saveContacts() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            outputStream.writeObject(contacts);
        } catch (IOException e) {
            System.out.println("Error saving contacts: " + e.getMessage());
        }
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
        saveContacts();
    }

    public void deleteContact(int index) {
        if (index >= 0 && index < contacts.size()) {
            contacts.remove(index);
            saveContacts();
        } else {
            System.out.println("Invalid contact index.");
        }
    }

    public void updateContact(int index, Contact updatedContact) {
        if (index >= 0 && index < contacts.size()) {
            contacts.set(index, updatedContact);
            saveContacts();
        } else {
            System.out.println("Invalid contact index.");
        }
    }

    public void viewContacts() {
        if (contacts.isEmpty()) {
            System.out.println("Address book is empty.");
        } else {
            System.out.println("Address book contacts:");
            for (int i = 0; i < contacts.size(); i++) {
                System.out.println((i + 1) + ". " + contacts.get(i));
            }
        }
    }

    public static void main(String[] args) {
        contactbook addressBook = new contactbook("address_book.dat");

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("Address Book");
            System.out.println("1. Add Contact");
            System.out.println("2. Delete Contact");
            System.out.println("3. Update Contact");
            System.out.println("4. View Contacts");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter phone number: ");
                    String phoneNumber = scanner.nextLine();
                    Contact contact = new Contact(name, phoneNumber);
                    addressBook.addContact(contact);
                    System.out.println("Contact added successfully.");
                    break;
                case 2:
                    System.out.print("Enter contact index: ");
                    int index = scanner.nextInt();
                    addressBook.deleteContact(index - 1);
                    System.out.println("Contact deleted successfully.");
                    break;
                case 3:
                    System.out.print("Enter contact index: ");
                    index = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    System.out.print("Enter new name: ");
                    name = scanner.nextLine();
                    System.out.print("Enter new phone number: ");
                    phoneNumber = scanner.nextLine();
                    contact = new Contact(name, phoneNumber);
                    addressBook.updateContact(index - 1, contact);
                    System.out.println("Contact updated successfully.");
                    break;
                case 4:
                    addressBook.viewContacts();
                    break;
                case 5:
                    exit = true;
                    System.out.println("Exiting...");

                    break;
                default:
                    System.out.println("Invalid choice...");
                    break;
            }
        }

        scanner.close();
    }
}
