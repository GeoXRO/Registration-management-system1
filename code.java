import java.util.Scanner;
import java.util.List;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;


class Guest {

    private String lastName;
    private String firstName;
    private String email;
    private String phoneNumber;

    public Guest(String lastName, String firstName, String email, String phoneNumber) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastName, firstName, email, phoneNumber);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Guest guest = (Guest) obj;
        return Objects.equals(lastName, guest.lastName) &&
               Objects.equals(firstName, guest.firstName) &&
               Objects.equals(email, guest.email) &&
               Objects.equals(phoneNumber, guest.phoneNumber);
    }

    @Override
    public String toString() {
        return "Nume: " + firstName + " " + lastName  +", Email: " + email +  ", Telefon: " + phoneNumber;
    }

    public String fullName() {
        return firstName + " " + lastName;
    }
}

class GuestsList {

    private int guestsCapacity;
    private List<Guest> guests;
    private List<Guest> waitingList;

    public GuestsList(int guestsCapacity) {
        this.guestsCapacity = guestsCapacity;
        this.guests = new ArrayList<>();
        this.waitingList = new ArrayList<>();
    }

    public int add(Guest g) {
        if (isOnTheListAlready(g))
            return -1;

        if (guests.size() < guestsCapacity) {
            guests.add(g);
            return 0;
        } else {
            waitingList.add(g);
            return waitingList.size();
        }
    }

    private boolean isOnTheListAlready(Guest g) {
        return guests.contains(g) || waitingList.contains(g);
    }

    public Guest search(String firstName, String lastName) {
        for (Guest guest : guests) {
            if (guest.getFirstName().equalsIgnoreCase(firstName) && guest.getLastName().equalsIgnoreCase(lastName)) {
                return guest;
            }
        }
        for (Guest guest : waitingList) {
            if (guest.getFirstName().equalsIgnoreCase(firstName) && guest.getLastName().equalsIgnoreCase(lastName)) {
                return guest;
            }
        }
        return null;
    }

    public Guest search(int opt, String match) {
        for (Guest guest : guests) {
            if ((opt == 2 && guest.getEmail().equalsIgnoreCase(match)) ||
                (opt == 3 && guest.getPhoneNumber().equals(match))) {
                return guest;
            }
        }
        for (Guest guest : waitingList) {
            if ((opt == 2 && guest.getEmail().equalsIgnoreCase(match)) ||
                (opt == 3 && guest.getPhoneNumber().equals(match))) {
                return guest;
            }
        }
        return null;
    }

    public boolean remove(String firstName, String lastName) {
        Guest guestToRemove = search(firstName, lastName);
        if (guestToRemove != null) {
            if (guests.remove(guestToRemove)) {
                if (!waitingList.isEmpty()) {
                    guests.add(waitingList.remove(0));
                }
                return true;
            } else {
                return waitingList.remove(guestToRemove);
            }
        }
        return false;
    }

    public boolean remove(int opt, String match) {
    Guest foundGuest = search(opt, match);
    if (foundGuest != null) {
        if (guests.remove(foundGuest)) {
            if (!waitingList.isEmpty()) {
                guests.add(waitingList.remove(0));
            }
            return true;
        } else {
            return waitingList.remove(foundGuest);
        }
    }
    return false;
}

    public void showGuestsList() {
        int count = 1;
    for (Guest guest : guests) {
        System.out.println(count + ". " + guest);
        count++;
    }
    }

    public void showWaitingList() {
        for (Guest guest : waitingList) {
            System.out.println(guest);
        }
    }


    public int numberOfAvailableSpots() {
        return guestsCapacity - guests.size();
    }

    public int numberOfGuests() {
        return guests.size();
    }

    public int numberOfPeopleWaiting() {
        return waitingList.size();
    }

    public int numberOfPeopleTotal() {
        return guests.size() + waitingList.size();
    }


    public List<Guest> partialSearch(String match) {
        List<Guest> result = new ArrayList<>();
        String lowerCaseMatch = match.toLowerCase();
        for (Guest guest : guests) {
            if (guest.getFirstName().toLowerCase().contains(lowerCaseMatch) ||
                guest.getLastName().toLowerCase().contains(lowerCaseMatch) ||
                guest.getEmail().toLowerCase().contains(lowerCaseMatch) ||
                guest.getPhoneNumber().contains(lowerCaseMatch)) {
                result.add(guest);
            }
        }
        for (Guest guest : waitingList) {
            if (guest.getFirstName().toLowerCase().contains(lowerCaseMatch) ||
                guest.getLastName().toLowerCase().contains(lowerCaseMatch) ||
                guest.getEmail().toLowerCase().contains(lowerCaseMatch) ||
                guest.getPhoneNumber().contains(lowerCaseMatch)) {
                result.add(guest);
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "GuestsList{" + "guestsCapacity=" + guestsCapacity + ", guests=" + guests + ", waitingList=" + waitingList + '}';
    }
}

public class Main {
    private static void showCommands() {
        System.out.println("help         - Afiseaza aceasta lista de comenzi");
        System.out.println("add          - Adauga o noua persoana (inscriere)");
        System.out.println("check        - Verifica daca o persoana este inscrisa la eveniment");
        System.out.println("remove       - Sterge o persoana existenta din lista");
        System.out.println("update       - Actualizeaza detaliile unei persoane");
        System.out.println("guests       - Lista de persoane care participa la eveniment");
        System.out.println("waitlist     - Persoanele din lista de asteptare");
        System.out.println("available    - Numarul de locuri libere");
        System.out.println("guests_no    - Numarul de persoane care participa la eveniment");
        System.out.println("waitlist_no  - Numarul de persoane din lista de asteptare");
        System.out.println("subscribe_no - Numarul total de persoane inscrise");
        System.out.println("search       - Cauta toti invitatii conform sirului de caractere introdus");
        System.out.println("save         - Salveaza lista cu invitati");
        System.out.println("restore      - Completeaza lista cu informatii salvate anterior");
        System.out.println("reset        - Sterge informatiile salvate despre invitati");
        System.out.println("quit         - Inchide aplicatia");
    }

    private static void addNewGuest(Scanner sc, GuestsList list) {

        String firstName = sc.nextLine();
        String lastName = sc.nextLine();
        String email = sc.nextLine();
        String phoneNumber = sc.nextLine();

        Guest newGuest = new Guest(lastName, firstName, email, phoneNumber);
        int result = list.add(newGuest);

        if (result == -1) {
            System.out.println("Eroare: Persoana este deja prezenta.");
        } else if (result == 0) {
            System.out.println("[" + newGuest.fullName() + "] Felicitari! Locul tau la eveniment este confirmat. Te asteptam!");
        } else {
            System.out.println("[" + newGuest.fullName() + "] Te-ai inscris cu succes in lista de asteptare si ai primit numarul de ordine " + result + ". Te vom notifica daca un loc devine disponibil.");
        }
    }

    private static void checkGuest(Scanner sc, GuestsList list) {
        String firstName = sc.nextLine();
        String lastName = sc.nextLine();

        Guest guest = list.search(firstName, lastName);
        if (guest != null) {
        } else {
        }
    }

    private static void removeGuest(Scanner sc, GuestsList list) {
        int option = sc.nextInt();
        sc.nextLine();

        String match;
        switch (option) {
            case 1:
                System.out.println("Introduceti numele de familie:");
                String lastName = sc.nextLine();
                System.out.println("Introduceti prenumele:");
                String firstName = sc.nextLine();
                match = lastName + " " + firstName;
                break;
            case 2:
                System.out.println("Introduceti email:");
                match = sc.nextLine();
                break;
            case 3:
                System.out.println("Introduceti numar de telefon (format \"+40733386463\"):");
                match = sc.nextLine();
                break;
            default:
                System.out.println("Optiune invalida.");
                return;
    }

    boolean removed = list.remove(option, match);
    if (removed) {
        System.out.println("Stergerea persoanei s-a realizat cu succes.");
    } else {
        System.out.println("Eroare: Persoana nu este inscrisa la eveniment.");
    }
}

    private static void updateGuest(Scanner sc, GuestsList list) {
    System.out.println("Se actualizeaza detaliile unei persoane…");
    System.out.println("Alege modul de autentificare, tastand:");
    System.out.println("\"1\" - Nume si prenume");
    System.out.println("\"2\" - Email");
    System.out.println("\"3\" - Numar de telefon (format \"+40733386463\")");
    int option = sc.nextInt();
    sc.nextLine(); // Consume newline

    String match;
    switch (option) {
        case 1:
            System.out.println("Introduceti numele de familie:");
            String lastName = sc.nextLine();
            System.out.println("Introduceti prenumele:");
            String firstName = sc.nextLine();
            match = lastName + " " + firstName;
            break;
        case 2:
            System.out.println("Introduceti email-ul curent:");
            match = sc.nextLine();
            break;
        case 3:
            System.out.println("Introduceti numarul de telefon curent (format \"+40733386463\"):");
            match = sc.nextLine();
            break;
        default:
            System.out.println("Optiune invalida.");
            return;
    }

    Guest guestToUpdate = list.search(option, match);
    if (guestToUpdate != null) {
        System.out.println("Introduceti noile detalii:");
        System.out.println("Introduceti noul nume de familie:");
        String newLastName = sc.nextLine();
        System.out.println("Introduceti noul prenume:");
        String newFirstName = sc.nextLine();
        System.out.println("Introduceti noul email:");
        String newEmail = sc.nextLine();
        System.out.println("Introduceti noul numar de telefon (format \"+40733386463\"):");
        String newPhoneNumber = sc.nextLine();

        guestToUpdate.setLastName(newLastName);
        guestToUpdate.setFirstName(newFirstName);
        guestToUpdate.setEmail(newEmail);
        guestToUpdate.setPhoneNumber(newPhoneNumber);

        System.out.println("Actualizare cu succes: " + guestToUpdate);
    } else {
        System.out.println("Eroare: Persoana nu este inscrisa la eveniment.");
    }
}

private static void searchList(Scanner sc, GuestsList list) {
    System.out.println("Enter search term:");
    String searchTerm = sc.nextLine();

    List<Guest> searchResults = list.partialSearch(searchTerm);
    if (!searchResults.isEmpty()) {
        System.out.println("Search results:");
        for (Guest guest : searchResults) {
            System.out.println(guest);
        }
    } else {
        System.out.println("No guests found matching the search term.");
    }
}

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int size = scanner.nextInt();
        scanner.nextLine();

        GuestsList list = new GuestsList(size);

        boolean running = true;
        while (running) {
            String command = scanner.nextLine();

            switch (command) {
                case "help":
                    showCommands();
                    break;
                case "add":
                    addNewGuest(scanner, list);
                    break;
                case "check":
                    checkGuest(scanner, list);
                    break;
                case "remove":
                    removeGuest(scanner, list);
                    break;
                case "update":
                    updateGuest(scanner, list);
                    break;
                case "guests":
                    list.showGuestsList();
                    break;
                case "waitlist":
                    list.showWaitingList();
                    break;
                case "available":
                    System.out.println("Numarul de locuri ramase: " + list.numberOfAvailableSpots());
                    break;
                case "guests_no":
                    System.out.println("Numarul de participanti: " + list.numberOfGuests());
                    break;
                case "waitlist_no":
                    System.out.println("Dimensiunea listei de asteptare: " + list.numberOfPeopleWaiting());
                    break;
                case "subscribe_no":
                    System.out.println("Numarul total de persoane: " + list.numberOfPeopleTotal());
                    break;
                case "search":
                    searchList(scanner, list);
                    break;
                case "quit":
                    System.out.println("Aplicatia se inchide...");
                    scanner.close();
                    running = false;
                    break;
                default:
                    System.out.println("Comanda introdusa nu este valida.");
                    System.out.println("Incercati inca o data.");

            }
        }
    }
}
