package controllers.menus;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static controllers.functions.WinModif.WinChange;
import static models.classes.playerdata.Player_Profile.DeleteSave;
import static models.classes.playerdata.Player_Profile.Load;

public class Saves_Selection_Controller implements Initializable {

    public ListView<String> listSave;
    public ObservableList<String> list_of_files;
    public ObservableList<String> emptylist = FXCollections.observableArrayList("Sauvegardes introuvables !");
    public Pane alert;
    public Button yes;
    public Button no;
    public Button choose;
    public Button delete;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        alert.setVisible(false);
        try {
            refreshList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(list_of_files.isEmpty()){
            listSave.setItems(emptylist);
            choose.setDisable(true);
            delete.setDisable(true);
        }
        else{
            choose.setOnMouseClicked(Event -> {
                try {
                    saveSelect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            delete.setOnMouseClicked(Event -> {
                try {
                    saveDelete();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void back() throws IOException {
        WinChange("menus/main_menu");
    }

    public void saveSelect() throws IOException {
        Load(listSave.getSelectionModel().getSelectedItem());
        WinChange("game/city");
    }

    public void saveDelete() throws IOException {
        if(listSave.getSelectionModel().getSelectedItem() != null) {
            alert.setVisible(true);
            yes.setOnMouseClicked(Event -> {
                try {
                    DeleteSave(listSave.getSelectionModel().getSelectedItem());
                    refreshList();
                    alert.setVisible(false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            no.setOnMouseClicked(Event -> {
                alert.setVisible(false);
            });
        }
    }

    public void refreshList() throws IOException {
        try {
            list_of_files = FXCollections.observableArrayList(
                    Files.walk(Path.of("./src/models/data"))
                            .filter(Files::isRegularFile)
                            .map(p -> p.getFileName().toString())
                            .filter(name -> name.endsWith(".json"))
                            .map(name -> name.replace("save_",""))
                            .map(name -> name.replace(".json",""))
                            .collect(Collectors.toList())
            );
            listSave.setItems(list_of_files);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
