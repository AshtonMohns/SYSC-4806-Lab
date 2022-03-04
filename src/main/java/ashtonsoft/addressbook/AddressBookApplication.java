package ashtonsoft.addressbook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AddressBookApplication {

	private static final Logger log = LoggerFactory.getLogger(AddressBookApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(AddressBookApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(AddressBookRepository repository) {
		return (args) -> {
			AddressBook book = new AddressBook();

			book.addBuddy(new BuddyInfo("Buddy 1"));
			book.addBuddy(new BuddyInfo("Buddy 2"));
			book.addBuddy(new BuddyInfo("Buddy 3"));
			book.addBuddy(new BuddyInfo("Buddy 4"));

			repository.save(book);

			log.info("Created new Address Book");
		};
	}

}
