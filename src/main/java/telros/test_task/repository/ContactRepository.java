package telros.test_task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import telros.test_task.model.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    Contact findContactByLastName(String lastName);
    Contact findContactByFirstName(String firstName);
    Contact findContactByMiddleName(String middleName);
    Contact findContactByBirthdayDate(String birthdayDate);
}
