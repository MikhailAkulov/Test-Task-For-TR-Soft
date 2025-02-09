package telros.test_task.api;

import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.reactive.server.WebTestClient;
import telros.test_task.JUnitSpringBootBase;
import telros.test_task.model.Contact;
import telros.test_task.repository.ContactRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

class ContactControllerTest extends JUnitSpringBootBase {
    @Autowired
    WebTestClient webTestClient;
    @Autowired
    ContactRepository contactRepository;
    @Autowired
    JdbcTemplate jdbcTemplate;

    static class JUnitContactResponse {
        private Long id;
        private String firstName;
        private String middleName;
        private String lastName;
        private String birthdayDate;
        private String phoneNumber;
        private String email;
        private String photoUrl;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getMiddleName() {
            return middleName;
        }

        public void setMiddleName(String middleName) {
            this.middleName = middleName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getBirthdayDate() {
            return birthdayDate;
        }

        public void setBirthdayDate(String birthdayDate) {
            this.birthdayDate = birthdayDate;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhotoUrl() {
            return photoUrl;
        }

        public void setPhotoUrl(String photoUrl) {
            this.photoUrl = photoUrl;
        }
    }

    @BeforeEach
    void clean() {
        contactRepository.deleteAll();
    }

    @Test
    void testGetAllContactSuccess() {
        contactRepository.save(new Contact(null, "Имя1", "Отчество1", "Фамилия1", "Д.Р.1",
                        "Телефон1", "Почта1", "Ссылка на фото1"));
        contactRepository.save(new Contact(null, "Имя2", "Отчество2", "Фамилия2", "Д.Р.2",
                        "Телефон2", "Почта2", "Ссылка на фото2"));

        List<Contact> expected = contactRepository.findAll();

        List<JUnitContactResponse> responseBody = webTestClient.get()
                .uri("/contact")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<JUnitContactResponse>>() {})
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(expected.size(), responseBody.size());
        for (JUnitContactResponse contactResponse : responseBody) {
            boolean foundFirstName = expected.stream()
                    .filter(it -> Objects.equals(it.getId(), contactResponse.getId()))
                    .anyMatch(it -> Objects.equals(it.getFirstName(), contactResponse.getFirstName()));
            boolean foundMiddleName = expected.stream()
                    .filter(it -> Objects.equals(it.getId(), contactResponse.getId()))
                    .anyMatch(it -> Objects.equals(it.getMiddleName(), contactResponse.getMiddleName()));
            boolean foundLastName = expected.stream()
                    .filter(it -> Objects.equals(it.getId(), contactResponse.getId()))
                    .anyMatch(it -> Objects.equals(it.getLastName(), contactResponse.getLastName()));
            boolean dateOfBirth = expected.stream()
                    .filter(it -> Objects.equals(it.getId(), contactResponse.getId()))
                    .anyMatch(it -> Objects.equals(it.getBirthdayDate(), contactResponse.getBirthdayDate()));
            Assertions.assertTrue(foundFirstName);
            Assertions.assertTrue(foundMiddleName);
            Assertions.assertTrue(foundLastName);
            Assertions.assertTrue(dateOfBirth);
        }
    }

    //  Нужно придумать другой способ, этот не совсем подходит
//    @Test
//    void testGetAllContactFail() {
//        webTestClient.get()
//                .uri("/contact")
//                .exchange()
//                .expectStatus().isNoContent();
//    }

    @Test
    void testFindContactByIdSuccess() {
        Contact expected = contactRepository.save(new Contact(null, "Имя1", "Отчество1", "Фамилия1", "Д.Р.1",
                "Телефон1", "Почта1", "Ссылка на фото1"));

        JUnitContactResponse responseBody = webTestClient.get()
                .uri("/contact/" + expected.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(JUnitContactResponse.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(expected.getId(), responseBody.getId());
        Assertions.assertEquals(expected.getFirstName(), responseBody.getFirstName());
        Assertions.assertEquals(expected.getMiddleName(), responseBody.getMiddleName());
        Assertions.assertEquals(expected.getLastName(), responseBody.getLastName());
        Assertions.assertEquals(expected.getBirthdayDate(), responseBody.getBirthdayDate());
    }

    @Test
    void testFindContactByIdNotFound() {
        contactRepository.save(new Contact(null, "Имя1", "Отчество1", "Фамилия1", "Д.Р.1",
                "Телефон1", "Почта1", "Ссылка на фото1"));

        Long nonExisting = jdbcTemplate.queryForObject("select max(id) from contacts", Long.class);
        nonExisting++;

        webTestClient.get()
                .uri("/contact/" + nonExisting)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testSaveContactSuccess() {
        JUnitContactResponse request = new JUnitContactResponse();
        request.setFirstName("Иван");
        request.setMiddleName("Иванович");
        request.setLastName("Иванов");
        request.setBirthdayDate("12.05.1687");
        request.setPhoneNumber("+1234567891");
        request.setEmail("ivanov123@mail.com");
        request.setPhotoUrl("https://someURL.de");

        JUnitContactResponse responseBody = webTestClient.post()
                .uri("/contact")
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(JUnitContactResponse.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertNotNull(responseBody.getId());
        Assertions.assertEquals(request.getFirstName(), responseBody.getFirstName());
        Assertions.assertEquals(request.getMiddleName(), responseBody.getMiddleName());
        Assertions.assertEquals(request.getLastName(), responseBody.getLastName());
        Assertions.assertEquals(request.getBirthdayDate(), responseBody.getBirthdayDate());
        Assertions.assertEquals(request.getPhoneNumber(), responseBody.getPhoneNumber());
        Assertions.assertEquals(request.getEmail(), responseBody.getEmail());
        Assertions.assertEquals(request.getPhotoUrl(), responseBody.getPhotoUrl());
    }

    @Test
    void testSaveContactFail() {
        webTestClient.post()
                .uri("/contact")
                .bodyValue(contactRepository.save(new Contact()))
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody(IllegalArgumentException.class);
    }

    @Test
    void testDeleteContactSuccess() {
        contactRepository.save(new Contact(null, "Имя1", "Отчество1", "Фамилия1", "Д.Р.1",
                "Телефон1", "Почта1", "Ссылка на фото1"));
        contactRepository.save(new Contact(null, "Имя2", "Отчество2", "Фамилия2", "Д.Р.2",
                "Телефон2", "Почта2", "Ссылка на фото2"));

        Long deletedContactId = jdbcTemplate.queryForObject("select max(id) from contacts", Long.class);

        webTestClient.delete()
                .uri("/contact/" + deletedContactId)
                .exchange()
                .expectStatus().isOk();

        Assertions.assertFalse(contactRepository.existsById(deletedContactId));
    }

    @Test
    void testDeleteContactNotFound() {
        contactRepository.save(new Contact(null, "Имя1", "Отчество1", "Фамилия1", "Д.Р.1",
                "Телефон1", "Почта1", "Ссылка на фото1"));
        contactRepository.save(new Contact(null, "Имя2", "Отчество2", "Фамилия2", "Д.Р.2",
                "Телефон2", "Почта2", "Ссылка на фото2"));

        Long nonExistingId = jdbcTemplate.queryForObject("select max(id) from contacts", Long.class);
        nonExistingId++;

        webTestClient.delete()
                .uri("/contact/" + nonExistingId)
                .exchange()
                .expectStatus().isNotFound();
    }
}