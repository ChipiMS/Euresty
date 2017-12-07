package euresty.fxml;

import euresty.FillTool;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
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
    ImageView airbrushBtn;
    @FXML
    ImageView calligraphicBrush2Btn;
    @FXML
    ComboBox comboFont;
    @FXML
    ComboBox comboSize;
    @FXML
    ImageView eraseBtn;
    @FXML
    MenuItem exitBtn;
    @FXML
    ImageView fillBtn;
    @FXML
    ImageView pencilBtn;
    @FXML
    MenuItem saveBtn;
    @FXML
    ImageView textBtn;
    //Bandera indica si se realizaron cambios antes de salir, verificar en dónde se ubica cada vez que se procesa
    int bandera=0;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillCombos();
        g = canvas.getGraphicsContext2D();
        g.setFill(javafx.scene.paint.Color.WHITE);
        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
       
        
        EventHandler<ActionEvent> listenerButtons = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //Aquí se llaman las funciones de los botones y menus
                if(event.getSource() == colorPicker){
                    g.setFill(colorPicker.getValue());
                }
                
                if(event.getSource() == comboFont){
                    g.setFont(new Font((String)comboFont.getSelectionModel().getSelectedItem(), (int)comboSize.getSelectionModel().getSelectedItem()));
                }
                
                if(event.getSource() == comboSize){
                    g.setFont(new Font((String)comboFont.getSelectionModel().getSelectedItem(), (int)comboSize.getSelectionModel().getSelectedItem()));
                }
                
                if(event.getSource() == saveBtn){
                    onSave();
                }
                
                if(event.getSource() == exitBtn){
                    onExit();
                }
            }
        };

        EventHandler<MouseEvent> listenerImageButtons = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //Aquí se llaman las funciones de las imagenes que sirven como botones
                if(event.getSource() == airbrushBtn){
                    onAirbrush();
                }
                
                if(event.getSource() == calligraphicBrush2Btn){
                    onCalligraphicBrush2();
                }
                
                if(event.getSource() == pencilBtn){
                    onPencil();
                }
                
                if(event.getSource() == textBtn){
                    onText();
                }
                
                if(event.getSource()== fillBtn){
                    try {
                        onFill();
                    } catch (IOException ex) {
                        Logger.getLogger(PaintFXController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                if(event.getSource()==eraseBtn){
                    onErase();
                }
            }
        };
        
        //Aquí se agregan los botones y menus al listener de botones y menus
        colorPicker.setOnAction(listenerButtons);
        comboFont.setOnAction(listenerButtons);
        comboSize.setOnAction(listenerButtons);
        exitBtn.setOnAction(listenerButtons);
        saveBtn.setOnAction(listenerButtons);
        
        //Aquí se agregan las imagenes que sirven como botones al listener de imagenes que sirven como botones
        airbrushBtn.setOnMouseClicked(listenerImageButtons);
        calligraphicBrush2Btn.setOnMouseClicked(listenerImageButtons);
        eraseBtn.setOnMouseClicked(listenerImageButtons);
        fillBtn.setOnMouseClicked(listenerImageButtons);
        pencilBtn.setOnMouseClicked(listenerImageButtons);
        textBtn.setOnMouseClicked(listenerImageButtons);
    }
    
    //Las funciones que cambian el comportamiento del canvas deben llevar esta función al principio
    public void selectNewAction(String accion){
        activeAction = accion;
        activeActionL.setText(accion);
        g.setFill(colorPicker.getValue());
        
        //Si se agrega un nuevo evento en el canvas se tiene que agregar aquí
        canvas.setOnMouseDragged(null);
        canvas.setOnMouseClicked(null);
    }
    
    //Aquí se agregan las funciones de cada funcionalidad del proyecto
    public void onAirbrush(){
        selectNewAction("Aerógrafo");
        canvas.setOnMouseDragged(e -> {
            double size = 12;
            double x = e.getX() - size / 2;
            double y = e.getY() - size / 2;
            g.fillRect(x, y, size, size);
            bandera=1;
        });
    }
    
    public void onCalligraphicBrush2(){
        selectNewAction("Pincel caligráfico 2");
        canvas.setOnMouseDragged(e -> {
            double size = 12;
            double x = e.getX() - size / 2;
            double y = e.getY() - size / 2;
            g.fillRect(x, y, size, size);
            bandera=1;
        });
    }
    
    public void onErase(){
        selectNewAction("Borrador");
        g.setFill(javafx.scene.paint.Color.WHITE);
        canvas.setOnMouseDragged(e -> {
            double size = 12;
            double x = e.getX() - size / 2;
            double y = e.getY() - size / 2;
            g.fillRect(x, y, size, size);
            bandera=1;
        });
    }
    
    public void onExit(){
        if(bandera==1){
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Salir");

            alert.setHeaderText("¿Deseas salir sin guardar?");

            ButtonType buttonTypeOne = new ButtonType("Sí");
            ButtonType buttonTypeTwo = new ButtonType("No");
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeOne){
                Platform.exit();
            } 
            else if (result.get() == buttonTypeTwo) {
               onSave();
               Platform.exit();
            }
            else{
                alert.close();
            }
        }
        else {
            Platform.exit();
        }
    }
    
    public void onFill() throws IOException{
        selectNewAction("Rellenar");
        Image snapshot = canvas.snapshot(null, null);
        ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", new File("temp.png"));
        BufferedImage image;
        image = (BufferedImage)ImageIO.read(new File("temp.png"));
        canvas.setOnMouseClicked(e->{
            javafx.scene.paint.Color color=colorPicker.getValue();
            java.awt.Color awtColor = new java.awt.Color((float) color.getRed(), (float) color.getGreen(), (float) color.getBlue(), (float) color.getOpacity());

            FillTool f=new FillTool((int)e.getX(),(int)e.getY(),(int)canvas.getWidth(),(int)canvas.getHeight(),awtColor,image);
            f.flood((int)e.getX(), (int)e.getY());
            Image card=SwingFXUtils.toFXImage(f.getImgFilled(), null);
            g.drawImage(card, 0, 0);
            bandera=1;
        });
    }
    
    public void onPencil(){
        selectNewAction("Lápiz");
        canvas.setOnMouseDragged(e -> {
            double size = 12;
            double x = e.getX() - size / 2;
            double y = e.getY() - size / 2;
            g.setFill(colorPicker.getValue());
            g.fillRect(x, y, size, size);
            bandera=1;  
        });
    }
    
    public void onSave() {
        try {
            bandera=0;
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
                bandera=1;
            }
        });
    }
    
    //Aquí pones funciones auxiliares si se necesitan
    public void fillCombos(){
        for (int i = 12; i < 60; i++) {
           comboSize.getItems().add(i);
        }
        String[] fonts=GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        comboFont.getItems().addAll(fonts);
        comboFont.getSelectionModel().selectFirst();
        comboSize.getSelectionModel().selectFirst();
    }
}
