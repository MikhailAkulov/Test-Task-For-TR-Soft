package telros.test_task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import telros.test_task.api.ContactRequest;
import telros.test_task.model.Contact;
import telros.test_task.repository.ContactRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    public Contact getContactById(Long id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Не найден пользователь с id: \"" + id + "\""));
    }

    public Contact addNewContact(ContactRequest request) {
        if (contactRepository.findContactByLastName(request.getLastName()) != null &&
                contactRepository.findContactByFirstName(request.getFirstName()) != null &&
                contactRepository.findContactByMiddleName(request.getMiddleName()) != null &&
                contactRepository.findContactByBirthdayDate(request.getBirthdayDate()) != null) {
            throw new IllegalArgumentException("Данный пользователь уже есть в системе");
        }
        Contact contact = new Contact(null, request.getFirstName(), request.getMiddleName(), request.getLastName(),
                request.getBirthdayDate(), request.getPhoneNumber(), request.getEmail(), request.getPhotoUrl());
        contactRepository.save(contact);
        return contact;
    }

    public Contact deleteContact(Long id) {
        Contact contact = getContactById(id);
        if (contact == null) {
            throw new NoSuchElementException("Не найден пользователь с id: \"" + id + "\"");
        }
        contactRepository.deleteById(id);
        return contact;
    }

    public Contact updateFirstName(Long id, ContactRequest request) {
        Contact contact = getContactById(id);
        if (contact == null) {
            throw new NoSuchElementException("Не найден пользователь id: \"" + id + "\"");
        }
        contact.setFirstName(request.getFirstName());
        return contactRepository.save(contact);
    }

    public Contact updateMiddleName(Long id, ContactRequest request) {
        Contact contact = getContactById(id);
        if (contact == null) {
            throw new NoSuchElementException("Не найден пользователь id: \"" + id + "\"");
        }
        contact.setMiddleName(request.getMiddleName());
        return contactRepository.save(contact);
    }

    public Contact updateLastName(Long id, ContactRequest request) {
        Contact contact = getContactById(id);
        if (contact == null) {
            throw new NoSuchElementException("Не найден пользователь id: \"" + id + "\"");
        }
        contact.setLastName(request.getLastName());
        return contactRepository.save(contact);
    }

    public Contact updateBirthdayDate(Long id, ContactRequest request) {
        Contact contact = getContactById(id);
        if (contact == null) {
            throw new NoSuchElementException("Не найден пользователь id: \"" + id + "\"");
        }
        contact.setBirthdayDate(request.getBirthdayDate());
        return contactRepository.save(contact);
    }

    public Contact updateEmail(Long id, ContactRequest request) {
        Contact contact = getContactById(id);
        if (contact == null) {
            throw new NoSuchElementException("Не найден пользователь id: \"" + id + "\"");
        }
        contact.setEmail(request.getEmail());
        return contactRepository.save(contact);
    }

    public Contact updatePhoneNumber(Long id, ContactRequest request) {
        Contact contact = getContactById(id);
        if (contact == null) {
            throw new NoSuchElementException("Не найден пользователь id: \"" + id + "\"");
        }
        contact.setPhoneNumber(request.getPhoneNumber());
        return contactRepository.save(contact);
    }
}
