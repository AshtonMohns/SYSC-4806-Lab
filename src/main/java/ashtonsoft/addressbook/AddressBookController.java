package ashtonsoft.addressbook;

import javax.swing.JOptionPane;

/**
 * The Controller portion of the MVC pattern.
 *
 * @author Ashton Mohns - 101074479
 */
public class AddressBookController {

    private final AddressBook model;

    public AddressBookController(AddressBook model) {
        this.model = model;
    }

    /**
     * Handle a create addressBook.BuddyInfo request from the view.
     */
    public void handleCreate() {
        String name = JOptionPane.showInputDialog("Please input the name for your new Buddy.", "John Smith");
        model.addBuddy(new BuddyInfo(name));
    }

    /**
     * Handle delete addressBook.BuddyInfo request from the view.
     * @param selectedIndex index of addressBook.BuddyInfo to be deleted within the addressBook.AddressBook
     */
    public void handleDelete(int selectedIndex) {
        if(selectedIndex == -1) {
            // Handle case where a addressBook.BuddyInfo was not selected before selecting delete.
            JOptionPane.showMessageDialog(null, "Invalid selection to delete", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        model.removeBuddy(selectedIndex);
    }
}
