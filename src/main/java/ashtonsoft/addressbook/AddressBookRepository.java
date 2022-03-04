package ashtonsoft.addressbook;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Ashton Mohns - 101074479
 */
@RepositoryRestResource(collectionResourceRel = "addressBooks", path = "addressBooks")
public interface AddressBookRepository extends PagingAndSortingRepository<AddressBook, UUID> {

    <S extends BuddyInfo> void save(S s);

    Optional<AddressBook> findById(UUID id);

    void deleteById(UUID id);

}
