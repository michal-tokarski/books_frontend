package com.kodilla.books_frontend.domain;

import com.kodilla.books_frontend.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

@Route
public class BookForm extends FormLayout {

    // Fields :
    private TextField title = new TextField("Title");
    private TextField author = new TextField("Author");
    private TextField publicationYear = new TextField("Publication year");
    private ComboBox type = new ComboBox<>("Book type");

    // Buttons :
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");

    // Binder :
    private Binder<Book> binder = new Binder<>(Book.class);

    // Other :
    private MainView mainView;
    private BookService service = BookService.getInstance();


    public BookForm(MainView mainView) {

        // Fields :
        type.setItems(BookType.values());
        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(title, author, publicationYear, type, buttons);

        // Buttons :
        save.addClickListener(event -> save());
        delete.addClickListener(event -> delete());

        // Binder :
        binder.bindInstanceFields(this);

        // Other :
        this.mainView = mainView;

    }

    private void save() {
        Book book = binder.getBean();
        service.save(book);
        mainView.refresh();
        setBook(null);
    }

    private void delete() {
        Book book = binder.getBean();
        service.delete(book);
        mainView.refresh();
        setBook(null);
    }

    public void setBook(Book book) {
        binder.setBean(book);

        if (book == null) {
            setVisible(false);
        } else {
            setVisible(true);
            title.focus();
        }
    }


}
