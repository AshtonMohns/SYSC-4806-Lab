package ashtonsoft.addressbook;

/**
 * @author Ashton Mohns - 101074479
 */
public interface AddressBookListener {

    /**
     * Handle an add event in the model
     * @param b addressBook.BuddyInfo that was added
     */
    void handleAdd(BuddyInfo b);

    /**
     * Handle a remove event in the model
     * @param index specific addressBook.BuddyInfo to be removed
     */
    void handleRemove(int index);

}
