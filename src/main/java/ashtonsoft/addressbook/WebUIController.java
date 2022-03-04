package ashtonsoft.addressbook;

import ashtonsoft.addressbook.data.BuddyForm;
import ashtonsoft.addressbook.data.DataForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;
import java.util.UUID;

@Controller
public class WebUIController {

    private final AddressBookRepository repository;

    public WebUIController(AddressBookRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/ui")
    public String selectAddressBook(Model model) {
        model.addAttribute("dataForm", new DataForm());
        return "dataForm";
    }

    @PostMapping("/ui")
    public String getAddressBook(@ModelAttribute DataForm dataForm, Model model) {
        Optional<AddressBook> result = repository.findById(UUID.fromString(dataForm.getContent()));

        model.addAttribute("AddressBook", result.orElse(null));
        return "result";
    }

    @GetMapping("/create")
    public String createAddressBook(Model model) {
        AddressBook addressBook = new AddressBook();
        repository.save(addressBook);

        model.addAttribute("AddressBook", addressBook);
        return "result";
    }

    @GetMapping("/buddy")
    public String inputBuddyData(Model model) {
        model.addAttribute("buddyForm", new BuddyForm());
        return "buddyInfo";
    }

    @PostMapping("/buddy")
    public String createBuddyInfo(@ModelAttribute BuddyForm dataForm, Model model) {
        Optional<AddressBook> result = repository.findById(UUID.fromString(dataForm.getId()));

        result.ifPresent(addressBook -> {
                addressBook.addBuddy(new BuddyInfo(dataForm.getBuddy()));
                repository.save(addressBook);
        });

        model.addAttribute("AddressBook", result.get());

        return "result";
    }

}
