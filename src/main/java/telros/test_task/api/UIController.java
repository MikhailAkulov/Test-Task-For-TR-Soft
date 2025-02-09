package telros.test_task.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import telros.test_task.service.ContactService;

/**
 * Авторизация настроена на страницы этого контроллера
 * доступ admin:admin
 */
@Controller
@RequestMapping("/ui")
public class UIController {

    @Autowired
    private ContactService contactService;

    //  GET  /ui
    //  Загружает домашнюю страницу в браузере
    @GetMapping
    public String home() {
        return "home";
    }

    //  GET  /ui/contact
    //  Загружает страницу со списком пользователей, внесённых в систему
    @GetMapping("/contact")
    public String contactsList(Model model) {
        model.addAttribute("contacts", contactService.getAllContacts());
        return "contacts";
    }

    //  GET  /ui/contact/id
    //  Загружает страницу с информацией о конкретном пользователе
    @GetMapping("/contact/{id}")
    public String getContact(@PathVariable Long id, Model model) {
        model.addAttribute("contact", contactService.getContactById(id));
        return "contactProfile";
    }
}
