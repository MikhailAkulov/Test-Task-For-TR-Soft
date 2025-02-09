package telros.test_task.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import telros.test_task.model.Contact;
import telros.test_task.service.ContactService;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/contact")
public class ContactController {
    @Autowired
    private ContactService contactService;

    //  Загружает список пользователей, зарегистрированных в системе
    @GetMapping()
    public ResponseEntity<List<Contact>> getAllContacts() {
        return new ResponseEntity<>(contactService.getAllContacts(), HttpStatus.OK);
    }

    //  Загружает информацию о запрашиваемом пользователе
    @GetMapping("/{id}")
    public ResponseEntity<Contact> getReaderInfo(@PathVariable Long id) {
        final Contact contact;
        try {
            contact = contactService.getContactById(id);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(contact);
    }

    //  Добавляет нового пользователя в систему
    //  {
    //      "id":null,
    //      "firstName":"Пётр",
    //      "middleName":"Петрович",
    //      "lastName":"Петров",
    //      "birthdayDate":"78.91.26",
    //      "phoneNumber":"+7-951-852-14-87",
    //      "email":"petrov@mail.com"
    //  }
    @PostMapping()
    public ResponseEntity<Contact> addNewContact(@RequestBody ContactRequest request) {
        final Contact contact;
        try {
            contact = contactService.addNewContact(request);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(contact);
    }

    //  Удаляет пользователя из системы по Id
    @DeleteMapping("/{id}")
    public ResponseEntity<Contact> deleteContact(@PathVariable Long id) {
        final Contact contact;
        try {
            contact = contactService.deleteContact(id);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(contact);
    }

    //  Обновляет Имя пользователя
    //  {
    //      "firstName": "Тарас"
    //  }
    @PatchMapping("/{id}/changeFirstName")
    public ResponseEntity<Contact> changeFirstName(@PathVariable Long id, @RequestBody ContactRequest request) {
        final Contact contact;
        try {
            contact = contactService.updateFirstName(id, request);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(contact);
    }

    //  Обновляет Отчество пользователя
    //  {
    //      "middleName": "Григорьевич"
    //  }
    @PatchMapping("/{id}/changeMiddleName")
    public ResponseEntity<Contact> changeMiddleName(@PathVariable Long id, @RequestBody ContactRequest request) {
        final Contact contact;
        try {
            contact = contactService.updateMiddleName(id, request);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(contact);
    }

    //  Обновляет Фамилию пользователя
    //  {
    //      "lastName": "Вампиренко"
    //  }
    @PatchMapping("/{id}/changeLastName")
    public ResponseEntity<Contact> changeLastName(@PathVariable Long id, @RequestBody ContactRequest request) {
        final Contact contact;
        try {
            contact = contactService.updateLastName(id, request);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(contact);
    }

    //  Обновляет Дату рождения пользователя
    //  {
    //      "birthdayDate": "02.05.1675"
    //  }
    @PatchMapping("/{id}/changeBirthday")
    public ResponseEntity<Contact> changeBirthdayDate(@PathVariable Long id, @RequestBody ContactRequest request) {
        final Contact contact;
        try {
            contact = contactService.updateBirthdayDate(id, request);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(contact);
    }

    //  Обновляет электронную почту пользователя
    //  {
    //      "email":"1111111@mail.com"
    //  }
    @PatchMapping("/{id}/changeMail")
    public ResponseEntity<Contact> changeEmail(@PathVariable Long id, @RequestBody ContactRequest request) {
        final Contact contact;
        try {
            contact = contactService.updateEmail(id, request);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(contact);
    }

    //  Обновляет номер телефона пользователя
    //  {
    //      "phoneNumber": "+3-333-333-33-33"
    //  }
    @PatchMapping("/{id}/changePhone")
    public ResponseEntity<Contact> changeNumber(@PathVariable Long id, @RequestBody ContactRequest request) {
        final Contact contact;
        try {
            contact = contactService.updatePhoneNumber(id, request);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(contact);
    }
}
