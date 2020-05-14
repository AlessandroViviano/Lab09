
package it.polito.tdp.borders;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;

public class FXMLController {

	private Model model;
	
	private ObservableList<Country> list = FXCollections.observableArrayList();
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtAnno"
    private TextField txtAnno; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
    
    @FXML
    private Button btnTrovaRaggiungibili;
    
    @FXML
    private ComboBox<Country> comboStati;
    
    @FXML
    private Button btnReset;

    @FXML
    void doCalcolaConfini(ActionEvent event) {
    	int anno = Integer.parseInt(txtAnno.getText());
    	model.creaGrafo(anno);
    	for(Country c: model.menuTendina()) {
    		list.add(c);
    	}
    	comboStati.setItems(list);
    	txtResult.appendText("Componenti connesse: " + model.componentiConnesse() + "\n" + model.elencoStati());
    }
    
    @FXML
    void doTrovaRaggiungibili(ActionEvent event) {
    	txtResult.appendText(model.statiConnessi(comboStati.getValue()));
    }
    

    @FXML
    void doReset(ActionEvent event) {
    	txtResult.clear();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnTrovaRaggiungibili != null : "fx:id=\"btnTrovaRaggiungibili\" was not injected: check your FXML file 'Scene.fxml'.";
        assert comboStati != null : "fx:id=\"comboStati\" was not injected: check your FXML file 'Scene.fxml'.";  
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'Scene.fxml'.";
        
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
