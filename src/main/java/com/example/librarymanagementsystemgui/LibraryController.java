package com.example.librarymanagementsystemgui;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.ButtonType;
import java.util.Optional;
import java.time.format.DateTimeFormatter;

public class LibraryController {

    @FXML
    private TextField bookIdField;

    @FXML
    private TextField titleField;

    @FXML
    private TextField authorField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField categoryField;
    @FXML
    private TableView<BOOK> bookTable;

    @FXML
    private TableColumn<BOOK, Integer> idColumn;

    @FXML
    private TableColumn<BOOK, String> titleColumn;

    @FXML
    private TableColumn<BOOK, String> authorColumn;

    @FXML
    private TableColumn<BOOK, Double> priceColumn;

    @FXML
    private TableColumn<BOOK, String> categoryColumn;

    @FXML
    private TableColumn<BOOK, Boolean> statusColumn;

    @FXML
    private TableColumn<BOOK, java.time.LocalDate> issueDateColumn;

    @FXML
    private TableColumn<BOOK, java.time.LocalDate> returnDateColumn;

    @FXML
    private Label totalBooksLabel;
    private Library library = new Library();

    @FXML
    public void initialize() {

        idColumn.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("issued"));
        statusColumn.setCellFactory(column -> new javafx.scene.control.TableCell<BOOK, Boolean>() {

            @Override

            protected void updateItem(Boolean item, boolean empty) {

                super.updateItem(item, empty);

                if (empty || item == null) {

                    setText(null);

                    setStyle("");

                } else {

                    if (item) {

                        setText("Issued");

                        setStyle("-fx-text-fill: red; -fx-font-weight: bold;");

                    } else {

                        setText("Available");

                        setStyle("-fx-text-fill: green; -fx-font-weight: bold;");

                    }

                }

            }

        });

        issueDateColumn.setCellValueFactory(new PropertyValueFactory<>("issueDate"));

        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

        bookTable.setPlaceholder(new Label("No Books Available"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");

        issueDateColumn.setCellFactory(column -> new javafx.scene.control.TableCell<BOOK, java.time.LocalDate>() {
            @Override
            protected void updateItem(java.time.LocalDate item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText("");
                } else {
                    setText(item.format(formatter));
                }
            }
        });

        returnDateColumn.setCellFactory(column -> new javafx.scene.control.TableCell<BOOK, java.time.LocalDate>() {
            @Override
            protected void updateItem(java.time.LocalDate item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText("");
                } else {
                    setText(item.format(formatter));
                }
            }
        });
    }
    @FXML
    private void addBook() {

        try {

            int id = Integer.parseInt(bookIdField.getText());
            String title = titleField.getText();
            String author = authorField.getText();
            double price = Double.parseDouble(priceField.getText());
            String category = categoryField.getText();

            if (title.isEmpty() || author.isEmpty() || category.isEmpty()) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Please fill all fields.");
                alert.showAndWait();
                return;
            }

            BOOK book = new BOOK(id, title, author, price, false, category);
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);

            confirm.setTitle("Confirm Add");
            confirm.setHeaderText(null);
            confirm.setContentText("Do you want to add this book?");

            Optional<ButtonType> result = confirm.showAndWait();

            if (result.isEmpty() || result.get() != ButtonType.OK) {
                return;
            }

            if (library.addBook(book)) {
                displayBooks();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Book Added Successfully!");
                alert.showAndWait();

                bookIdField.clear();
                titleField.clear();
                authorField.clear();
                priceField.clear();
                categoryField.clear();

            } else {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Book ID Already Exists!");
                alert.showAndWait();

            }

        } catch (NumberFormatException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Book ID and Price must be numeric.");
            alert.showAndWait();

        }


    }
    @FXML
    private void displayBooks() {

        ObservableList<BOOK> list =
                FXCollections.observableArrayList(library.getBooks());

        bookTable.setItems(list);

        totalBooksLabel.setText("Total Books : " + library.totalBooks());

    }
    @FXML
    private void searchBook() {
        try {

            int id = Integer.parseInt(bookIdField.getText());

            BOOK book = library.searchBook(id);

            if (book == null) {
                titleField.clear();

                authorField.clear();

                priceField.clear();

                categoryField.clear();

                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setHeaderText(null);

                alert.setContentText("Book Not Found!");

                alert.showAndWait();

            } else {

                titleField.setText(book.getTitle());

                authorField.setText(book.getAuthor());

                priceField.setText(String.valueOf(book.getPrice()));

                categoryField.setText(book.getCategory());

                bookTable.getSelectionModel().select(book);

                bookTable.scrollTo(book);

            }

        } catch (NumberFormatException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setHeaderText(null);

            alert.setContentText("Please enter a valid Book ID.");

            alert.showAndWait();

        }

    }
    @FXML
    private void deleteBook() {
        try {

            int id = Integer.parseInt(bookIdField.getText());
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);

            confirm.setTitle("Confirm Delete");
            confirm.setHeaderText(null);
            confirm.setContentText("Are you sure you want to delete this book?");

            Optional<ButtonType> result = confirm.showAndWait();

            if (result.isEmpty() || result.get() != ButtonType.OK) {
                return;
            }

            if (library.deleteBook(id)) {

                displayBooks();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setHeaderText(null);

                alert.setContentText("Book Deleted Successfully!");

                alert.showAndWait();

                bookIdField.clear();

                titleField.clear();

                authorField.clear();

                priceField.clear();

                categoryField.clear();

            } else {

                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setHeaderText(null);

                alert.setContentText("Book Not Found!");

                alert.showAndWait();

            }

        } catch (NumberFormatException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setHeaderText(null);

            alert.setContentText("Please enter a valid Book ID.");

            alert.showAndWait();

        }

    }
    @FXML
    private void issueBook() {

        try {

            int id = Integer.parseInt(bookIdField.getText());
            Alert confirm = new Alert(
                    Alert.AlertType.CONFIRMATION,
                    "Issue this book?",
                    ButtonType.YES,
                    ButtonType.NO
            );

            confirm.setHeaderText(null);

            Optional<ButtonType> result = confirm.showAndWait();

            if (result.isEmpty() || result.get() != ButtonType.YES) {
                return;
            }
            BOOK book = library.searchBook(id);

            if (book == null) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Book Not Found!");
                alert.showAndWait();

            } else if (book.isIssued()) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Book is already issued!");
                alert.showAndWait();

            } else {

                library.issueBook(id);
                displayBooks();
                bookTable.refresh();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Book Issued Successfully!");
                alert.showAndWait();

            }

        } catch (NumberFormatException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid Book ID.");
            alert.showAndWait();

        }

    }
    @FXML
    private void returnBook() {

        try {

            int id = Integer.parseInt(bookIdField.getText());
            Alert confirm = new Alert(
                    Alert.AlertType.CONFIRMATION,
                    "Return this book?",
                    ButtonType.YES,
                    ButtonType.NO
            );

            confirm.setHeaderText(null);

            Optional<ButtonType> result = confirm.showAndWait();

            if (result.isEmpty() || result.get() != ButtonType.YES) {
                return;
            }
            BOOK book = library.searchBook(id);

            if (book == null) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Book Not Found!");
                alert.showAndWait();

            } else if (!book.isIssued()) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Book is already available!");
                alert.showAndWait();

            } else {

                library.returnBook(id);
                displayBooks();
                bookTable.refresh();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Book Returned Successfully!");
                alert.showAndWait();

            }

        } catch (NumberFormatException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid Book ID.");
            alert.showAndWait();

        }

    }



        }