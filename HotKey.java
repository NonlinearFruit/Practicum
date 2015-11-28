import java.util.Random;
import java.util.List;
import java.util.Collections;
import java.util.Arrays;

public class HotKey {

    private final String hotkey;
    private final String description;
    HotKey(String hotkey, String description) {
        this.hotkey = hotkey;
        this.description = description;
    }

    // public static HotKey getRandom()  {
    //   return hotkeyList.get(RANDOM.nextInt(SIZE));
    // }

    public String getCombo(){   return hotkey; }
    public String getDesc(){    return description; }
    public String toString(){   return hotkey+"\t"+description; }
}