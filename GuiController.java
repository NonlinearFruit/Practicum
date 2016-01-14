import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import java.util.Random;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.input.KeyCombination;
import javafx.geometry.Pos;
import java.util.List;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.util.StringConverter;
import javafx.scene.control.ToggleButton;

/**
 *
 * @author NonlinearFruit
 */
public class GuiController implements Initializable
{      
	private static final Random RANDOM = new Random();

	/* Normal Attributes */
	private double level;
	private HotKey guessME;
	private List<HotKey> possibleKeys;
	private List<HotKey> possibleCmds;
	private int streakCount;
	private int streakRecord=0;
	/* FXML Attributes! */
	// @FXML private ToggleButton cmdToggle;
	// @FXML private ToggleButton hotkeyToggle;
	// @FXML private ToggleButton typingToggle;
	// @FXML private ToggleButton customToggle;
	@FXML private Label hint;
	@FXML private Label answer;
	@FXML private Button btn;
	@FXML private Label streak;
	@FXML private Label statLeft;
	@FXML private Label statRight;
	@FXML private TextField input;
	@FXML private ComboBox<Model.Json> chooser; 
	@FXML private ComboBox<Model.Type> typeChooser; 
	@FXML private Slider slider;
   
   /* Normal Methods */
   /**
    * Initializes everything
    * @param url 
    * @param rb  
    */
   @Override
   public void initialize(URL url, ResourceBundle rb)
   {
   		hint.setText("Hint");
   		btn.setText("   ");
   		answer.setText("Ans");

   		streak.setText("0");
   		streakCount = -1;

   		// for (Model.Json json : Model.Json.values()) {
   		// 	chooser.getItems().add(json);
   		// }

   		chooser.getItems().addAll(Model.Json.values()); // Should be in listener on type chooser
   		typeChooser.getItems().addAll(Model.Type.values()); 

   		slider.setMin(0);
        slider.setMax(3);
        slider.setValue(3);
        slider.setMinorTickCount(0);
        slider.setMajorTickUnit(1);
        slider.setSnapToTicks(true);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);

        slider.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double n) {
                if (n < 0.5) return "Novice";
                if (n < 1.5) return "Intermediate";
                if (n < 2.5) return "Advanced";

                return "Expert";
            }

            @Override
            public Double fromString(String s) {
                switch (s) {
                    case "Novice":
                        return 0d;
                    case "Intermediate":
                        return 1d;
                    case "Advanced":
                        return 2d;
                    case "Expert":
                        return 3d;

                    default:
                        return 3d;
                }
            }
        });
   }

    /**
     * Sets up the accelerators for the 
     * hotkeys about to be practiced
     */
    public void hotkeyStart()
    {
    	for (HotKey hotkey : possibleKeys) {
	        hint.getScene().getAccelerators().put(
	        	KeyCombination.keyCombination(hotkey.getCombo()),
	        	  new Runnable() {
					@Override public void run() {					   
					   guess(hotkey);
					}
		  	});
    	}

    	guess(null);
    }

    /**
     * Checks to see if a hotkey matches guessME
     * and updates the GUI elements accordingly
     * @param hk The guessed hotkey
     */
	public void guess(HotKey hk)
	{
		if(guessME==null || hk==null || guessME.getCombo().equals(hk.getCombo()))
		{
			int cmdOrKey = RANDOM.nextInt(2);
			if( (cmdOrKey==0 || possibleCmds==null || possibleCmds.size()<=0) && possibleKeys!=null && possibleKeys.size()>0)
			{
				guessME = possibleKeys.get(RANDOM.nextInt(possibleKeys.size()));
				hint.setText(guessME.getDesc());
			}
			else if( (cmdOrKey==1 || possibleKeys==null || possibleKeys.size()<=0) && possibleCmds!=null && possibleCmds.size()>0)
			{
				guessME = possibleCmds.get(RANDOM.nextInt(possibleCmds.size()));
				hint.setText(guessME.getDesc());
			}
			answer.setText("");

			streakCount ++;
			if(streakCount>streakRecord)
				streakRecord = streakCount;
		}
		else {streakCount = 0;}

		if(hk!=null)
		{
			statLeft.setText(hk.getDesc());
			statRight.setText(hk.getCombo());
		}
		streak.setText(""+streakCount);
	}

	/**
	 * Search for a specific cmd hotkey 
	 * @param  cmd Command of the hotkey in question
	 * @return     The hotkey in question
	 */
	private HotKey cmdLookup(String cmd)
	{
		HotKey result = null;
		if(possibleCmds!=null)
		{
			for (HotKey hk : possibleCmds) {
				if( hk.getCombo().equals(cmd) ){
					result = hk;
					break;
				}
			}
		}
		if( result==null )
			result = new HotKey(cmd,"Unknown");
		return result;
	}

	/* FXML Methods! */
	/**
	 * Shows the hint!
	 * @param event 
	 */
	@FXML
	public void giveAnswer(ActionEvent event)
	{
		if(guessME!=null){answer.setText(guessME.getCombo());}
	}

	/**
	 * Setups the cmds/hotkeys based
	 * on choosen option
	 * @param event 
	 */
	@FXML
	public void setup(ActionEvent event)
	{
		hint.getScene().getAccelerators().clear();
		level = slider.valueProperty().getValue();
		Model.Json json = chooser.getValue();
		System.out.println(json);
   		possibleKeys = Model.getKeys(json,level);
   		possibleCmds = Model.getCmds(json,level);

   		streakCount = -1;
   		guessME = null;
   		hotkeyStart();
	}

	/**
	 * Checks the text in input against guessME
	 * @param event 
	 */
	@FXML
	public void cmdEntered(ActionEvent event)
	{
		String cmd = input.getText().trim();
		input.setText("");
		HotKey hk = cmdLookup(cmd);
		guess(hk);
	}

	@FXML
	public void lvlEntered(Event event)
	{
		setup(null);
	}

	@FXML
	public void typeChosen(ActionEvent event)
	{
		chooser.getItems().clear();
		chooser.getItems().addAll(Model.getJsons(typeChooser.getValue()));
	}
}