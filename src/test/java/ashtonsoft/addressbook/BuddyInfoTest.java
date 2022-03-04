package ashtonsoft.addressbook;

import org.junit.Assert;
import org.junit.Test;

public class BuddyInfoTest {

    @Test
    public void testGetName() {
        String expected = "testName1";
        BuddyInfo underTest = new BuddyInfo(expected);

        Assert.assertEquals(expected, underTest.getName());
    }

}