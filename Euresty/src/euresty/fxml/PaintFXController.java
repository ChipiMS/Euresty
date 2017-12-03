package euresty.fxml;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javax.imageio.ImageIO;

public class PaintFXController implements Initializable {
    String activeAction;
    GraphicsContext g;
    
    @FXML
    Label activeActionL;
    @FXML
    Canvas canvas;
    @FXML
    ColorPicker colorPicker;
    
    //Aquí se declaran los botones e imagenes
    @FXML
    ImageView pencilBtn;
    @FXML
    MenuItem saveBtn;
    @FXML
    ImageView textBtn;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        g = canvas.getGraphicsContext2D();
        
        EventHandler<ActionEvent> listenerButtons = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                g.setFill(colorPicker.getValue());
                
                //Aquí se llaman las funciones de los botones y menus
                if(event.getSource() == saveBtn){
                    onSave();
                }
            }
        };

        EventHandler<MouseEvent> listenerImageButtons = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                g.setFill(colorPicker.getValue());
                
                //Aquí se llaman las funciones de las imagenes que sirven como botones
                if(event.getSource() == pencilBtn){
                    onPencil();
                }
                if(event.getSource() == textBtn){
                    onText();
                }
            }
        };
        
        //Aquí se agregan los botones y menus al listener de botones y menus
        saveBtn.setOnAction(listenerButtons);
        
        //Aquí se agregan las imagenes que sirven como botones al listener de imagenes que sirven como botones
        pencilBtn.setOnMouseClicked(listenerImageButtons);
        textBtn.setOnMouseClicked(listenerImageButtons);
    }
    
    //Las funciones que cambian el comportamiento del canvas deben llevar esta función al principio
    public void selectNewAction(String accion){
        activeAction = accion;
        activeActionL.setText(accion);
        
        //Si se agrega un nuevo evento en el canvas se tiene que agregar aquí
        canvas.setOnMouseDragged(null);
        canvas.setOnMouseClicked(null);
    }
    
    //Aquí se agregan las funciones de cada funcionalidad del proyecto
    public void onPencil(){
        selectNewAction("Lapiz");
        System.out.println("No programado");
    }
    public void onSave() {
        try {
            Image snapshot = canvas.snapshot(null, null);
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", new File("paint.png"));
        } catch (Exception e) {
            System.out.println("Failed to save image: " + e);
        }
    }
    public void onText(){
        selectNewAction("Texto");
        canvas.setOnMouseClicked(e->{
            TextInputDialog dialog = new TextInputDialog("Texto");
            dialog.setTitle("Ingresa texto");
            dialog.setHeaderText("ENTRADA");
            Optional<String> result = dialog.showAndWait();
            if(result.isPresent()){
                double x = e.getX();
                double y = e.getY();
                g.fillText(result.get(), x, y);
            }
        });
    }
}
