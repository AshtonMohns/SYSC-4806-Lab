package ashtonsoft.addressbook;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WebController.class)
public class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressBookRepository mockRepo;

    private final UUID id = UUID.randomUUID();

    @Test
    public void testCreateAddressBook() throws Exception {
        mockMvc.perform(post("/addressBook")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("\"buddies\":[]")));
    }

    @Test
    public void testGetAddressBook() throws Exception {
        String expectedBuddy = "testBuddy";

        AddressBook data = new AddressBook();
        data.addBuddy(new BuddyInfo(expectedBuddy));

        when(mockRepo.findById(id)).thenReturn(Optional.of(data));

        mockMvc.perform(get("/addressBook/" + id)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString(expectedBuddy)));
    }

    @Test
    public void testCreateBuddy() throws Exception {
        BuddyInfo expectedBuddy = new BuddyInfo("new buddy");
        AddressBook data = new AddressBook();

        when(mockRepo.findById(id)).thenReturn(Optional.empty()).thenReturn(Optional.of(data));

        mockMvc.perform(post("/addressBook/" + id).content("{\"name\":\"" + expectedBuddy + "\"}").contentType("application/json"))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("\"success\":false")));
        mockMvc.perform(post("/addressBook/" + id).content("{\"name\":\"" + expectedBuddy + "\"}").contentType("application/json"))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("\"success\":true")));

        Assertions.assertEquals(1, data.getBuddies().size());
    }

    @Test
    public void testDeleteBuddy() throws Exception {
        BuddyInfo b = new BuddyInfo();

        AddressBook data = new AddressBook();
        data.addBuddy(b);

        when(mockRepo.findById(id)).thenReturn(Optional.of(data));

        mockMvc.perform(delete("/addressBook/" + id + "/buddy/" + b.getId())).andDo(print())
                .andExpect(status().isOk()).andExpect(content().string(containsString("\"success\":true")));

        Assertions.assertEquals(0, data.getBuddies().size());
    }

}
