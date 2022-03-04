package ashtonsoft.addressbook;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class JPATest {

    private Map<UUID, BuddyInfo> expectedBuddies;
    private BuddyInfo buddy1, buddy2;
    private EntityManager em;
    private EntityTransaction tx;

    @Before
    public void setup() {
        expectedBuddies = new HashMap<>();

        // Create buddies for testing. Save to a map for checking later.
        buddy1 = new BuddyInfo("Buddy One");
        expectedBuddies.put(buddy1.getId(), buddy1);

        buddy2 = new BuddyInfo("Not Buddy One");
        expectedBuddies.put(buddy2.getId(), buddy2);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-test");
        em = emf.createEntityManager();
        tx = em.getTransaction();

        // Save buddies to DB
        tx.begin();

        em.persist(buddy1);
        em.persist(buddy2);
    }

    @Test
    public void testPersistingBuddyInfo() {
        // Get buddies from DB
        Query q = em.createQuery("SELECT b from BuddyInfo b");

        @SuppressWarnings("unchecked")
        List<BuddyInfo> buddies = q.getResultList();

        for(BuddyInfo buddy : buddies) {
            // Ensure returned buddy batches saved buddy.

            Assert.assertTrue(expectedBuddies.containsKey(buddy.getId()));
            BuddyInfo expected = expectedBuddies.get(buddy.getId());

            System.out.println("Testing persistence of addressBook.BuddyInfo '" + expected.getName() + "'");
            Assert.assertEquals("addressBook.BuddyInfo has been altered.", expected, buddy);

            expectedBuddies.remove(buddy.getId());
        }

        // Ensure all saved buddies were returned.
        Assert.assertEquals("Some buddies not persisted.", 0, expectedBuddies.size());
    }

    @Test
    public void testPersistingAddressBook() {
        AddressBook expected = new AddressBook();
        expected.addBuddy(buddy1);
        expected.addBuddy(buddy2);

        em.persist(expected);

        Query q = em.createQuery("SELECT a FROM AddressBook a");

        @SuppressWarnings("unchecked")
        List<AddressBook> addressBooks = q.getResultList();
        Assert.assertEquals(1, addressBooks.size());

        AddressBook addressBook = addressBooks.get(0);

        System.out.println("Testing contents of addressBook.AddressBook");
        Assert.assertEquals(expected, addressBook);
    }

    @Test
    public void testBonusProblem() {
        AddressBook expected = new AddressBook();
        expected.addBuddy(buddy1);
        expected.addBuddy(buddy2);

        em.persist(expected);

        // Adding a new buddy to ensure that it will be persisted.
        // An exception will be thrown if not.
        BuddyInfo newBuddy = new BuddyInfo("Bonus Buddy");
        expected.addBuddy(newBuddy);

        Query q = em.createQuery("SELECT a FROM AddressBook a");

        @SuppressWarnings("unchecked")
        List<AddressBook> addressBooks = q.getResultList();
        Assert.assertEquals(1, addressBooks.size());

        AddressBook addressBook = addressBooks.get(0);

        System.out.println("Testing contents of addressBook.AddressBook for bonus problem");
        Assert.assertEquals(expected, addressBook);
    }

    @After
    public void tearDown() {
        // Reset changes for next unit test
        tx.rollback();
    }

}
