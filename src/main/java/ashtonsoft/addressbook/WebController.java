package ashtonsoft.addressbook;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
public class WebController {

    private final AddressBookRepository repository;

    public WebController(AddressBookRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/addressBook")
    public AddressBook createAddressBook(@RequestBody(required = false) List<BuddyInfo> buddyInfoList) {
        AddressBook a = new AddressBook();
        if(buddyInfoList != null) {
            for(BuddyInfo b : buddyInfoList) {
                a.addBuddy(b);
            }
        }
        repository.save(a);

        return a;
    }

    @GetMapping("/addressBook/{id}")
    public AddressBook getAddressBook(@PathVariable String id) {
        Optional<AddressBook> response = repository.findById(UUID.fromString(id));
        return response.orElse(null);
    }

    @PostMapping("/addressBook/{id}")
    public PrimitiveResponse<Boolean> createBuddy(@PathVariable String id, @RequestBody BuddyInfo buddy) {
        Optional<AddressBook> response = repository.findById(UUID.fromString(id));
        if(response.isEmpty()) {
            return new PrimitiveResponse<>("success", false);
        }
        AddressBook book = response.get();
        book.addBuddy(buddy);
        repository.save(book);
        return new PrimitiveResponse<>("success", true);
    }

    @DeleteMapping("/addressBook/{id}/buddy/{buddyId}")
    public PrimitiveResponse<Boolean> deleteBuddy(@PathVariable String id, @PathVariable String buddyId) {
        Optional<AddressBook> response = repository.findById(UUID.fromString(id));
        if(response.isEmpty()) {
            return new PrimitiveResponse<>("success", false);
        }
        AddressBook book = response.get();
        if(book.removeBuddy(UUID.fromString(buddyId))) {
            repository.save(book);
            return new PrimitiveResponse<>("success", true);
        }
        return new PrimitiveResponse<>("success", false);
    }
}

class PrimitiveResponse<T> {

    private final Map<String, T> body;

    public PrimitiveResponse(String key, T value) {
        this.body = new HashMap<>(1);
        this.body.put(key, value);
    }

    public Map<String, T> getBody() {
        return this.body;
    }
}
