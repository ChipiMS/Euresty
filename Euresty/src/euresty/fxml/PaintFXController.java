package euresty.fxml;

import euresty.FillTool;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
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
    @FXML
    ImageView fillBtn;
    @FXML
    ComboBox comboFont;
    @FXML
    ComboBox comboSize;
    @FXML
    Button fontBtn;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     fillCombos();
        g = canvas.getGraphicsContext2D();
        g.setFill(javafx.scene.paint.Color.WHITE);
        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
       
        
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
                      selectNewAction("Texto");
                   canvas.setOnMouseClicked(e->{
                       onText();
                   });
                }
                if(event.getSource()== fillBtn){
                   selectNewAction("Rellenar");
                        canvas.setOnMouseClicked(e->{
                            try {
                                onFill();
                            } catch (IOException ex) {
                                Logger.getLogger(PaintFXController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                       
                    
                }
                if(event.getSource()==fontBtn)
                {
                    g.setFont(new Font((String)comboFont.getSelectionModel().getSelectedItem(), (int)comboSize.getSelectionModel().getSelectedItem()));
                }
            }
        };
        
        //Aquí se agregan los botones y menus al listener de botones y menus
        saveBtn.setOnAction(listenerButtons);
        
        //Aquí se agregan las imagenes que sirven como botones al listener de imagenes que sirven como botones
        pencilBtn.setOnMouseClicked(listenerImageButtons);
        textBtn.setOnMouseClicked(listenerImageButtons);
        fillBtn.setOnMouseClicked(listenerImageButtons);
        fontBtn.setOnMouseClicked(listenerImageButtons);
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
        g.setFill(colorPicker.getValue());
      
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
    public void onFill() throws IOException{
        g.setFill(colorPicker.getValue());
          Image snapshot = canvas.snapshot(null, null);
   ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", new File("temp.png"));
        BufferedImage image;
        image = (BufferedImage)ImageIO.read(new File("temp.png"));
        
        canvas.setOnMouseClicked(e->{
            javafx.scene.paint.Color color=colorPicker.getValue();
          java.awt.Color awtColor = new java.awt.Color((float) color.getRed(),
                                                       (float) color.getGreen(),
                                                       (float) color.getBlue(),
                                                       (float) color.getOpacity());

            FillTool f=new FillTool((int)e.getX(),(int)e.getY(),(int)canvas.getWidth(),(int)canvas.getHeight(),awtColor,image);
            f.flood((int)e.getX(), (int)e.getY());
            Image card=SwingFXUtils.toFXImage(f.getImgFilled(), null);
            g.drawImage(card, 0, 0);
            
            
        });
    }
    
    public void fillCombos()
    {
          for (int i = 12; i < 60; i++) {
           comboSize.getItems().add(i);
        }
     String[] fonts=GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
     comboFont.getItems().addAll(fonts);
     comboFont.getSelectionModel().selectFirst();
     comboSize.getSelectionModel().selectFirst();
    }
}
