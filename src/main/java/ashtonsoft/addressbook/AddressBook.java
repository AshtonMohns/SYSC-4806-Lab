package ashtonsoft.addressbook;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.UUID;

/**
 * Simple address book which stores a list of buddies per the SYSC 4806 Lab 1 specifications.
 *
 * @author Ashton Mohns - 101074479
 */
@Entity
public class AddressBook {

    @Id
    private UUID id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<BuddyInfo> buddies;

    @Transient
    private final List<AddressBookListener> listeners;

    /**
     * Create an empty addressBook.AddressBook
     */
    public AddressBook() {
        this.id = UUID.randomUUID();
        buddies = new ArrayList<>();
        listeners = new ArrayList<>();
    }

    /**
     * Add an element to the addressBook.AddressBook
     *
     * @param buddy element to add
     */
    public void addBuddy(BuddyInfo buddy) {
        this.buddies.add(buddy);
        for(AddressBookListener listener : listeners) {
            listener.handleAdd(buddy);
        }
    }

    /**
     * Get id
     * @return
     */
    public UUID getId() {
        return id;
    }

    /**
     * Set id
     * @param id
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Get all buddies in address book
     * @return
     */
    @OneToMany(mappedBy = "addressBook")
    public List<BuddyInfo> getBuddies() {
        return this.buddies;
    }

    /**
     * Set buddies in address book
     * @param buddies
     */
    public void setBuddies(List<BuddyInfo> buddies) {
        this.buddies = buddies;
    }

    /**
     * Remove a buddy from the address book.
     *
     * @param index index to remove
     */
    public void removeBuddy(int index) {
        buddies.remove(index);
        for(AddressBookListener listener : listeners) {
            listener.handleRemove(index);
        }
    }

    public boolean removeBuddy(UUID id) {
        for(int i  = 0; i < buddies.size(); i++) {
            if(buddies.get(i).getId().equals(id)) {
                removeBuddy(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Add a listener to update on adds / removes
     *
     * @param listener listener to be updated
     */
    public void addListener(AddressBookListener listener) {
        listeners.add(listener);
    }

    /**
     * Print the contents of the addressBook.AddressBook
     */
    @Override
    public String toString() {
        StringJoiner j = new StringJoiner("\n");

        for(BuddyInfo buddy : buddies) {
            j.add(buddy.toString());
        }

        return j.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        AddressBook other = (AddressBook) o;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (buddies == null) {
            return other.buddies == null;
        } else return buddies.equals(other.buddies);
    }

    public static void main(String[] args) {
        AddressBook a = new AddressBook();

        a.addBuddy(new BuddyInfo("Buddy One"));
        a.addBuddy(new BuddyInfo());

        System.out.println(a.toString());
    }

    /**
     * Create json data of content
     * @return json data
     */
    public String serialize() {
        StringJoiner b = new StringJoiner(",",
                "{" +
                        "\"data\": [" +
                        "\"type\": \"addressbook\"" +
                        "\"id\": " + this.id +
                        "\"attributes\": {[",
                "]}]}");
        for(BuddyInfo buddy : buddies) {
            b.add(buddy.serialize());
        }
        return b.toString();
    }
}
