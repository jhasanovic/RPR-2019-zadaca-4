package ba.unsa.etf.rpr.t7;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;
import static javafx.stage.Window.getWindows;

public class KorisnikController {
    public TextField fldIme;
    public TextField fldPrezime;
    public TextField fldEmail;
    public TextField fldUsername;
    public ListView<Korisnik> listKorisnici;
    public PasswordField fldPassword;
    @FXML
    private Label ime;
    @FXML
    private Label prezime;
    @FXML
    private Label username;
    @FXML
    private Label lozinka;
    @FXML
    private Button btnObrisi;
    @FXML
    private Button btnDodaj;
    @FXML
    private Button btnKraj;
    @FXML
    private MenuItem aboutMenuItem;
    @FXML
    private MenuItem helpItem;
    @FXML
    private MenuItem exitMenuBtn;
    @FXML
    private MenuItem langItem;
    @FXML
    private MenuItem saveItem;
    @FXML
    private MenuItem printItem;
    @FXML
    private MenuItem fileItem;

    private Locale locale;
    private ResourceBundle bundle;
    private KorisniciModel model;

    public KorisnikController(KorisniciModel model) {
        this.model = model;
    }

    @FXML
    public void initialize() {
        listKorisnici.setItems(model.getKorisnici());
        listKorisnici.getSelectionModel().selectedItemProperty().addListener((obs, oldKorisnik, newKorisnik) -> {
            model.setTrenutniKorisnik(newKorisnik);
            listKorisnici.refresh();
         });

        model.trenutniKorisnikProperty().addListener((obs, oldKorisnik, newKorisnik) -> {
            if (oldKorisnik != null) {
                fldIme.textProperty().unbindBidirectional(oldKorisnik.imeProperty() );
                fldPrezime.textProperty().unbindBidirectional(oldKorisnik.prezimeProperty() );
                fldEmail.textProperty().unbindBidirectional(oldKorisnik.emailProperty() );
                fldUsername.textProperty().unbindBidirectional(oldKorisnik.usernameProperty() );
                fldPassword.textProperty().unbindBidirectional(oldKorisnik.passwordProperty() );
                model.izmijeni(oldKorisnik);
            }
            if (newKorisnik == null) {
                fldIme.setText("");
                fldPrezime.setText("");
                fldEmail.setText("");
                fldUsername.setText("");
                fldPassword.setText("");
            }
            else {
                fldIme.textProperty().bindBidirectional( newKorisnik.imeProperty() );
                fldPrezime.textProperty().bindBidirectional( newKorisnik.prezimeProperty() );
                fldEmail.textProperty().bindBidirectional( newKorisnik.emailProperty() );
                fldUsername.textProperty().bindBidirectional( newKorisnik.usernameProperty() );
                fldPassword.textProperty().bindBidirectional( newKorisnik.passwordProperty() );
            }
        });

        fldIme.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty()) {
                fldIme.getStyleClass().removeAll("poljeNijeIspravno");
                fldIme.getStyleClass().add("poljeIspravno");
            } else {
                fldIme.getStyleClass().removeAll("poljeIspravno");
                fldIme.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fldPrezime.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty()) {
                fldPrezime.getStyleClass().removeAll("poljeNijeIspravno");
                fldPrezime.getStyleClass().add("poljeIspravno");
            } else {
                fldPrezime.getStyleClass().removeAll("poljeIspravno");
                fldPrezime.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fldEmail.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty()) {
                fldEmail.getStyleClass().removeAll("poljeNijeIspravno");
                fldEmail.getStyleClass().add("poljeIspravno");
            } else {
                fldEmail.getStyleClass().removeAll("poljeIspravno");
                fldEmail.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fldUsername.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty()) {
                fldUsername.getStyleClass().removeAll("poljeNijeIspravno");
                fldUsername.getStyleClass().add("poljeIspravno");
            } else {
                fldUsername.getStyleClass().removeAll("poljeIspravno");
                fldUsername.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fldPassword.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty()) {
                fldPassword.getStyleClass().removeAll("poljeNijeIspravno");
                fldPassword.getStyleClass().add("poljeIspravno");
            } else {
                fldPassword.getStyleClass().removeAll("poljeIspravno");
                fldPassword.getStyleClass().add("poljeNijeIspravno");
            }
        });
    }

    public void dodajAction(ActionEvent actionEvent) {
        model.getKorisnici().add(new Korisnik("", "", "", "", ""));
        listKorisnici.getSelectionModel().selectLast();
    }

    public void krajAction(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void btnObrisiClick(ActionEvent actionEvent) {
    }

    public void exitClick(ActionEvent actionEvent) { System.exit(0); }

    public void aboutClick(ActionEvent actionEvent) throws IOException {
        Stage stage=new Stage();
        Parent root= FXMLLoader.load(getClass().getResource("/fxml/about.fxml"));
        stage.setTitle("About");
        stage.setScene(new Scene(root,USE_COMPUTED_SIZE,USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
    }
    public void btnBsClick(ActionEvent actionEvent){
    locale=new Locale("bs");
    bundle=ResourceBundle.getBundle("Translation_bs",locale);
    ime.setText(bundle.getString("first_name"));
    prezime.setText(bundle.getString("last_name"));
    username.setText(bundle.getString("username"));
    lozinka.setText(bundle.getString("password"));
    btnDodaj.setText(bundle.getString("add"));
    btnObrisi.setText(bundle.getString("delete"));
    btnKraj.setText(bundle.getString("end"));
        aboutMenuItem.setText(bundle.getString("about"));
        helpItem.setText(bundle.getString("help"));
        exitMenuBtn.setText(bundle.getString("exit"));
        langItem.setText(bundle.getString("language"));
        saveItem.setText(bundle.getString("save"));
        printItem.setText(bundle.getString("print"));
        fileItem.setText(bundle.getString("file"));
    }
    public void btnEnClick(ActionEvent actionEvent){
    locale=new Locale("en","US");
        bundle=ResourceBundle.getBundle("Translation_en_US",locale);
        ime.setText(bundle.getString("first_name"));
        prezime.setText(bundle.getString("last_name"));
        username.setText(bundle.getString("username"));
        lozinka.setText(bundle.getString("password"));
        btnDodaj.setText(bundle.getString("add"));
        btnObrisi.setText(bundle.getString("delete"));
        btnKraj.setText(bundle.getString("end"));
        aboutMenuItem.setText(bundle.getString("about"));
        helpItem.setText(bundle.getString("help"));
        exitMenuBtn.setText(bundle.getString("exit"));
        langItem.setText(bundle.getString("language"));
        saveItem.setText(bundle.getString("save"));
        printItem.setText(bundle.getString("print"));
        fileItem.setText(bundle.getString("file"));

    }

}
