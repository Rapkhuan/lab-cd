package th.mfu;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import th.mfu.domain.Concert;

@Controller
public class ConcertController {

    private final ConcertRepository repository;

    public ConcertController(ConcertRepository repository) {
        this.repository = repository;
    }

    @InitBinder
    public final void initBinderUsuariosFormValidator(
            final WebDataBinder binder, final Locale locale) {
        final SimpleDateFormat dateFormat =
                new SimpleDateFormat("yyyy-MM-dd", locale);
        dateFormat.setLenient(false);
        binder.registerCustomEditor(
                Date.class, new CustomDateEditor(dateFormat, true));
    }

    @GetMapping("/concerts")
    public String listConcerts(Model model) {
        model.addAttribute("concerts", repository.findAll());
        return "list-concert";
    }

    @GetMapping("/add-concert")
    public String addAConcertForm(Model model) {
        model.addAttribute("concert", new Concert());
        return "add-concert-form";
    }

    @PostMapping("/concerts")
    public String saveConcert(@ModelAttribute Concert concert) {
        repository.save(concert);
        return "redirect:/concerts";
    }

    @GetMapping("/concerts/{id}")
    public String getConcert(Model model, @PathVariable Long id) {
        Concert concert = repository.findById(id).get();
        model.addAttribute("concert", concert);
        return "add-concert-form";
    }

    @GetMapping("/delete-concert/{id}")
    public String deleteConcert(@PathVariable long id) {
        repository.deleteById(id);
        return "redirect:/concerts";
    }

    @GetMapping("/delete-concert")
    public String removeAllConcerts() {
        repository.deleteAll();
        return "redirect:/concerts";
    }
}