package ashtonsoft.addressbook;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

/**
 * A class containing the information of a single buddy
 *
 * @author Ashton Mohns - 101074479
 */
@Entity
public class BuddyInfo {

    @Id
    private UUID id;

    private String name;

    /**
     * Create a generic addressBook.BuddyInfo
     */
    public BuddyInfo() {
        this("John Smith");
    }

    /**
     * Create a addressBook.BuddyInfo with the specified name
     * @param name
     */
    public BuddyInfo(String name) {
        this.name = name;
        this.id = UUID.randomUUID();
    }

    /**
     * Set id value
     * @param id
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Set name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the id of the current Buddy
     * @return
     */
    public UUID getId() {
        return this.id;
    }

    /**
     * Get the name attribute
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * Print the addressBook.BuddyInfo details
     */
    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        BuddyInfo other = (BuddyInfo) o;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            return other.name == null;
        } else return name.equals(other.name);
    }

    public String serialize() {
        return name;
    }
}
