package ashtonsoft.addressbook;

import org.junit.Assert;
import org.junit.Before;

public class AddressBookTest {

    private AddressBook underTest;

    @Before
    public void setUp() {
        underTest = new AddressBook();
    }

    @org.junit.Test
    public void testAddBuddy() {
        BuddyInfo expected = new BuddyInfo();

        underTest.addBuddy(expected);

        Assert.assertEquals(expected.getName(), underTest.toString());
    }
}