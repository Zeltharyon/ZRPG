package controllers.menus;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import main.Run;

import java.io.IOException;

import static controllers.functions.WinModif.WinChange;
import static controllers.functions.WinModif.WinClose;

public class Main_Menu_Controller {
    
    public Button new_game_btn;
    public Button exit_btn;
    public Button load_game_btn;

    public void close() throws IOException {
        WinClose();
    }

    public void new_game() throws Exception{
        WinChange("menus/char_creation");
    }

    public void load_game() throws IOException {
        WinChange("menus/save_selection");
    }
    
}
