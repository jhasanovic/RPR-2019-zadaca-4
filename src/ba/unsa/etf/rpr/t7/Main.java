package ba.unsa.etf.rpr.t7;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        KorisniciModel model = KorisniciModel.getInstance();
        //model.napuni();

        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/korisnici.fxml"), bundle);
        loader.setController(new KorisnikController(model));
        Parent root = loader.load();
        primaryStage.setTitle(bundle.getString("appname"));
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);

    }
}
