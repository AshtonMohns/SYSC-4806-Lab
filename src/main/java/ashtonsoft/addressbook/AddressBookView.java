package ashtonsoft.addressbook;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

/**
 * The View portion of the MVC pattern.
 *
 * @author Ashton Mohns - 101074479
 */
public class AddressBookView implements AddressBookListener {

    public DefaultListModel<BuddyInfo> buddyInfos;

    /**
     * Constructor for the address book view
     *
     * @param controller controller to handle actions of buttons
     * @param model model to listen to
     */
    public AddressBookView(AddressBookController controller, AddressBook model) {
        buddyInfos = new DefaultListModel<>();

        // Ensure the view updates on changes to the model
        model.addListener(this);

        JFrame frame = new JFrame("addressBook.AddressBookView");
        frame.setBounds(100, 100, 500, 500);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JList<BuddyInfo> list = new JList<>(buddyInfos);
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        listScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        listScroller.setViewportView(list);
        list.setLayoutOrientation(JList.VERTICAL);

        frame.add(list);

        // Handle create and delete by passing events to the controller.
        JMenuItem createButton = new JMenuItem("Create");
        createButton.addActionListener(e -> controller.handleCreate());

        JMenuItem deleteButton = new JMenuItem("Delete");
        deleteButton.addActionListener(e -> controller.handleDelete(list.getSelectedIndex()));

        menuBar.add(createButton);
        menuBar.add(deleteButton);

        frame.setVisible(true);
    }

    /**
     * Handle an add event in the model
     * @param b addressBook.BuddyInfo that was added
     */
    @Override
    public void handleAdd(BuddyInfo b) {
        buddyInfos.addElement(b);
    }

    /**
     * Handle a remove event in the model
     * @param index specific addressBook.BuddyInfo to be removed
     */
    @Override
    public void handleRemove(int index) {
        buddyInfos.remove(index);
    }

    public static void main(String[] args) {
        // Code from Part 1 - MVC without DI
        // addressBook.AddressBook model = new addressBook.AddressBook();
        // new addressBook.AddressBookView(new addressBook.AddressBookController(model), model);

        String[] contextPaths = new String[] {"app-context.xml"};
        new ClassPathXmlApplicationContext(contextPaths);
    }
}
