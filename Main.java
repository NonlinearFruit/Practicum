import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author NonlinearFruit
 */
public class Main extends Application
{   
   public static void main(String[] args)
   {
      Application.launch(args);
   }

   @Override
   public void start(Stage stage) throws Exception
   {
      Parent root = FXMLLoader.load(getClass().getResource("Gui.fxml"));
      Scene scene = new Scene(root);

      // File css = new File("Stylier.css");
      // scene.getStylesheets().clear();
      // scene.getStylesheets().add("file:///"+f.getAbsolutePath().replace("\\","/"));
      stage.setScene(scene);
      stage.show();
   }
}