package euresty.fxml;

import euresty.FillTool;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.Random;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

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
    ImageView pincelBtn;
    @FXML
    ImageView calligraphicBrush1Btn;
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
    MenuItem newBtn;
    @FXML
    MenuItem openBtn;
    @FXML
    ImageView textBtn;
    @FXML
    ImageView openV;
    @FXML
    ImageView dzpBtn1;
    @FXML
    ImageView dzpBtn2;
    @FXML
    ImageView dzpBtn3;
    //Bandera indica si se realizaron cambios antes de salir, verificar en dónde se ubica cada vez que se procesa
    @FXML
    ImageView btnRectangulo, Circulo, Elipse, Triangulo, Screen;
    int bandera = 0;

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
                if (event.getSource() == colorPicker) {
                    g.setFill(colorPicker.getValue());
                }

                if (event.getSource() == comboFont) {
                    g.setFont(new Font((String) comboFont.getSelectionModel().getSelectedItem(), (int) comboSize.getSelectionModel().getSelectedItem()));
                }

                if (event.getSource() == comboSize) {
                    g.setFont(new Font((String) comboFont.getSelectionModel().getSelectedItem(), (int) comboSize.getSelectionModel().getSelectedItem()));
                }

                if (event.getSource() == saveBtn) {
                    onSave();
                }
                if (event.getSource() == newBtn) {
                    onNew();
                }
                if (event.getSource() == openBtn) {
                    onOpen();
                }

                if (event.getSource() == exitBtn) {
                    onExit();
                }
            }
        };

        EventHandler<MouseEvent> listenerImageButtons = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //Aquí se llaman las funciones de las imagenes que sirven como botones
                if (event.getSource() == pincelBtn) {
                    onPincel();
                }
                
                if (event.getSource() == calligraphicBrush1Btn) {
                    onCalligraphicBrush1();
                }
                
                if (event.getSource() == airbrushBtn) {
                    onAirbrush();
                }

                if (event.getSource() == calligraphicBrush2Btn) {
                    onCalligraphicBrush2();
                }

                if (event.getSource() == pencilBtn) {
                    onPencil();
                }

                if (event.getSource() == textBtn) {
                    onText();
                }

                if (event.getSource() == fillBtn) {
                    try {
                        onFill();
                    } catch (IOException ex) {
                        Logger.getLogger(PaintFXController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                if (event.getSource() == eraseBtn) {
                    onErase();
                }
                
                // dzp y abc
                if(event.getSource() == dzpBtn1)
                {
                    onPlumon();
                }
                // dzp y abc
                if(event.getSource() == dzpBtn2)
                {
                    onPincelz();
                }
                // dzp y abc
                if(event.getSource() == dzpBtn3)
                {
                    onPincel2();
                }
                  if(event.getSource() == btnRectangulo)
                {
                    onRectangulo();
                }
               if(event.getSource() == Circulo)
                {
                    onCirculo();
                }
               if(event.getSource() == Elipse)
                {
                    onElipse();
                }
               if(event.getSource() == Triangulo)
                {
                    onTriangulo();
                }
               
               if(event.getSource() == Screen)
                {
                    onDialogo();
                }
            }
        };

        //Aquí se agregan los botones y menus al listener de botones y menus
        colorPicker.setOnAction(listenerButtons);
        comboFont.setOnAction(listenerButtons);
        comboSize.setOnAction(listenerButtons);
        exitBtn.setOnAction(listenerButtons);
        saveBtn.setOnAction(listenerButtons);
        newBtn.setOnAction(listenerButtons);
        openBtn.setOnAction(listenerButtons);

        //Aquí se agregan las imagenes que sirven como botones al listener de imagenes que sirven como botones
        pincelBtn.setOnMouseClicked(listenerImageButtons);
        calligraphicBrush1Btn.setOnMouseClicked(listenerImageButtons);
        airbrushBtn.setOnMouseClicked(listenerImageButtons);
        calligraphicBrush2Btn.setOnMouseClicked(listenerImageButtons);
        eraseBtn.setOnMouseClicked(listenerImageButtons);
        fillBtn.setOnMouseClicked(listenerImageButtons);
        pencilBtn.setOnMouseClicked(listenerImageButtons);
        textBtn.setOnMouseClicked(listenerImageButtons);
        dzpBtn1.setOnMouseClicked(listenerImageButtons); // dzp y abc
        dzpBtn2.setOnMouseClicked(listenerImageButtons); // dzp y abc
        dzpBtn3.setOnMouseClicked(listenerImageButtons); // dzp y abc
        btnRectangulo.setOnMouseClicked(listenerImageButtons);
        Circulo.setOnMouseClicked(listenerImageButtons);
        Elipse.setOnMouseClicked(listenerImageButtons);
        Triangulo.setOnMouseClicked(listenerImageButtons);
        Screen.setOnMouseClicked(listenerImageButtons);
    }

    //Las funciones que cambian el comportamiento del canvas deben llevar esta función al principio
    public void selectNewAction(String accion) {
        activeAction = accion;
        activeActionL.setText(accion);
        g.setFill(colorPicker.getValue());

        //Si se agrega un nuevo evento en el canvas se tiene que agregar aquí
        canvas.setOnMouseDragged(null);
        canvas.setOnMouseClicked(null);
    }

    //Aquí se agregan las funciones de cada funcionalidad del proyecto
    public void onPincel() {
        selectNewAction("Pincel");
        canvas.setOnMouseClicked(e -> {
            double x = e.getX();
            double y = e.getY();
            g.strokeLine(x - 1, y, x + 1, y);
            g.strokeLine(x - 1, y - 1, x + 1, y - 1);
            bandera = 1;
        });
        canvas.setOnMouseDragged(e -> {
            double x = e.getX();
            double y = e.getY();
            g.strokeLine(x - 1, y, x + 1, y);
            g.strokeLine(x - 1, y - 1, x + 1, y - 1);
            bandera = 1;
        });
    }
    
    public void onCalligraphicBrush1() {
        selectNewAction("Pincel caligráfico 1");
        canvas.setOnMouseClicked(e -> {
            double x = e.getX();
            double y = e.getY();
            g.strokeLine(x - 2, y - 2, x + 2, y + 2);
            bandera = 1;
        });
        canvas.setOnMouseDragged(e -> {
            double x = e.getX();
            double y = e.getY();
            g.strokeLine(x - 2, y - 2, x + 2, y + 2);
            bandera = 1;
        });
    }
    
    public void onAirbrush() {
        selectNewAction("Aerógrafo");
        canvas.setOnMouseClicked(e -> {
            Random rand = new Random();
            int i, j, x = (int) (e.getX()), y = (int) (e.getY());
            for (i = y - 4; i <= y + 4; i++) {
                for (j = x - 4; j <= x + 4; j++) {
                    if (Math.sqrt((x - j) * (x - j) + (y - i) * (y - i)) < 4.5 && rand.nextBoolean()) {
                        g.fillRect(j, i, 1, 1);
                    }
                }
            }
            bandera = 1;
        });
        canvas.setOnMouseDragged(e -> {
            Random rand = new Random();
            int i, j, x = (int) (e.getX()), y = (int) (e.getY());
            for (i = y - 4; i <= y + 4; i++) {
                for (j = x - 4; j <= x + 4; j++) {
                    if (Math.sqrt((x - j) * (x - j) + (y - i) * (y - i)) < 4.5 && rand.nextBoolean()) {
                        g.fillRect(j, i, 1, 1);
                    }
                }
            }
            bandera = 1;
        });
    }

    public void onCalligraphicBrush2() {
        selectNewAction("Pincel caligráfico 2");
        canvas.setOnMouseClicked(e -> {
            double x = e.getX();
            double y = e.getY();
            g.strokeLine(x - 2, y + 2, x + 2, y - 2);
            bandera = 1;
        });
        canvas.setOnMouseDragged(e -> {
            double x = e.getX();
            double y = e.getY();
            g.strokeLine(x - 2, y + 2, x + 2, y - 2);
            bandera = 1;
        });
    }

    public void onErase() {
        selectNewAction("Borrador");
        g.setFill(javafx.scene.paint.Color.WHITE);
        canvas.setOnMouseDragged(e -> {
            double size = 12;
            double x = e.getX() - size / 2;
            double y = e.getY() - size / 2;
            g.fillRect(x, y, size, size);
            bandera = 1;
        });
    }

    public void onExit() {
        if (bandera == 1) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Salir");

            alert.setHeaderText("¿Deseas salir sin guardar?");

            ButtonType buttonTypeOne = new ButtonType("Sí");
            ButtonType buttonTypeTwo = new ButtonType("No");
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeOne) {
                Platform.exit();
            } else if (result.get() == buttonTypeTwo) {
                onSave();
                Platform.exit();
            } else {
                alert.close();
            }
        } else {
            Platform.exit();
        }
    }

    public void onFill() throws IOException {
        selectNewAction("Rellenar");
        Image snapshot = canvas.snapshot(null, null);
        ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", new File("temp.png"));
        BufferedImage image;
        image = (BufferedImage) ImageIO.read(new File("temp.png"));
        canvas.setOnMouseClicked(e -> {
            javafx.scene.paint.Color color = colorPicker.getValue();
            java.awt.Color awtColor = new java.awt.Color((float) color.getRed(), (float) color.getGreen(), (float) color.getBlue(), (float) color.getOpacity());

            FillTool f = new FillTool((int) e.getX(), (int) e.getY(), (int) canvas.getWidth(), (int) canvas.getHeight(), awtColor, image);
            f.flood((int) e.getX(), (int) e.getY());
            Image card = SwingFXUtils.toFXImage(f.getImgFilled(), null);
            g.drawImage(card, 0, 0);
            bandera = 1;
        });
    }

    public void onPencil() {
        selectNewAction("Lápiz");
        canvas.setOnMouseDragged(e -> {
            double size = 12;
            double x = e.getX() - size / 2;
            double y = e.getY() - size / 2;
            g.setFill(colorPicker.getValue());
            g.fillRect(x, y, size, size);
            bandera = 1;
        });
    }
    
    // dzp y abc
    public void onPlumon() {
        selectNewAction("Plumon");
        canvas.setOnMouseDragged(e -> {
            double size = 22;
            double x = e.getX() - size / 2;
            double y = e.getY() - size / 2;
            
            int i, j;
            
            for(i=0;i<22;i+=3)
                for(j=0;j<22;j+=2)
                    if(Math.pow(-11+i, 2)+Math.pow(-11+j, 2) <= 121)
                        g.fillRect(x+i, y+j, 1, 1);
            
            g.setFill(colorPicker.getValue());
            bandera = 1;
        });
    }
    
    // dzp y abc
    public void onPincelz() {
        selectNewAction("Pincel");
        canvas.setOnMouseDragged(e -> {
            double size = 8;
            double x = e.getX() - size / 2;
            double y = e.getY() - size / 2;
            g.setFill(colorPicker.getValue());
            g.fillOval(x, y, size, size);
            bandera = 1;
        });
    }
    
    //dzp y abc
    public void onPincel2() {
        selectNewAction("Pincel gráfico");
        canvas.setOnMouseClicked(e -> {
            double x = e.getX();
            double y = e.getY();
            g.strokeArc(x, y, 30, 30, 45, 90, ArcType.CHORD);
            bandera = 1;
        });
        canvas.setOnMouseDragged(e -> {
            double x = e.getX();
            double y = e.getY();
            g.strokeArc(x, y, 30, 30, 45, 90, ArcType.CHORD);
            bandera = 1;
        });
    }
    
     public void onRectangulo(){
        selectNewAction("Cuadrado");
        canvas.setOnMouseClicked(e->{
             double size = 100;
            double x = e.getX() - size / 2;
            double y = e.getY() - size / 2;
             g.setFill(colorPicker.getValue());
             //g.fillRect(x, y, size, size);
             g.strokeRect(x, y, size, size);
             bandera=1;
        });
    }
        
        public void onCirculo(){
            selectNewAction("Circulo");
            canvas.setOnMouseClicked(e->{
                 double size = 70;
                double x = e.getX() - size / 2;
                double y = e.getY() - size / 2;
                 g.setFill(colorPicker.getValue());
                 g.strokeOval(x, y, size, size);
                 bandera=1;
            });
        }
        
        public void onElipse(){
            selectNewAction("Elipse");
            canvas.setOnMouseClicked(e->{
                 double size = 70;
                 double size2=100;
                double x = e.getX() - size / 2;
                double y = e.getY() - size2 / 2;
                 g.setFill(colorPicker.getValue());
                 g.strokeOval(x, y, size, size2);
                 bandera=1;
            });
        }
        
       public void onTriangulo(){
            selectNewAction("Triangulo");
            canvas.setOnMouseClicked(e->{
                 double size = 1;
                 double size2=100;
                double x = e.getX() - size / 2;
                double y = e.getY() - size / 2;
                g.beginPath();
                g.moveTo(x+25, y);
                g.lineTo(x+50, y+25);
                g.lineTo(x+50, y-25);
                g.closePath();
                g.stroke();
                g.setFill(colorPicker.getValue());
                bandera=1;
            });
        }
       
       public void onDialogo(){
            selectNewAction("screen");
            canvas.setOnMouseClicked(e->{
                
                g.beginPath();
                g.moveTo(75,25);
                g.quadraticCurveTo(25,25,25,62.5);
                g.quadraticCurveTo(25,100,50,100);
                g.quadraticCurveTo(50,120,30,125);
                g.quadraticCurveTo(60,120,65,100);
                g.quadraticCurveTo(125,100,125,62.5);
                g.quadraticCurveTo(125,25,75,25);
                g.stroke();
                bandera=1;
            });
       }

    public void onSave() {
        try {
            bandera = 0;
            Image snapshot = canvas.snapshot(null, null);
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", new File("paint.png"));
        } catch (Exception e) {
            System.out.println("Failed to save image: " + e);
        }
    }

    public void onNew() {
        if (bandera == 1) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Salir");

            alert.setHeaderText("¿Deseas guardar los cambios?");

            ButtonType buttonTypeOne = new ButtonType("Sí");
            ButtonType buttonTypeTwo = new ButtonType("No");
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeOne) {
                onSave();
                g.setFill(Color.WHITE);
                g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                g.setFill(colorPicker.getValue());
            } else if (result.get() == buttonTypeTwo) {
                
                g.setFill(Color.WHITE);
                g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                g.setFill(colorPicker.getValue());

            } else {
                alert.close();

            }
        }

    }

    public void onOpen() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Buscar Imagen");

        // Agregar filtros para facilitar la busqueda
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );

        // Obtener la imagen seleccionada
        File imgFile = fileChooser.showOpenDialog(null);

        // Mostar la imagen
        if (imgFile != null) {
            Image image = new Image("file:" + imgFile.getAbsolutePath());
            // canvas.setImage(image);
            g.drawImage(image, bandera, bandera);

        }

    }

    public void onText() {
        selectNewAction("Texto");
        canvas.setOnMouseClicked(e -> {
            TextInputDialog dialog = new TextInputDialog("Texto");
            dialog.setTitle("Ingresa texto");
            dialog.setHeaderText("ENTRADA");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                double x = e.getX();
                double y = e.getY();
                g.fillText(result.get(), x, y);
                bandera = 1;
            }
        });
    }

    //Aquí pones funciones auxiliares si se necesitan
    public void fillCombos() {
        for (int i = 12; i < 60; i++) {
            comboSize.getItems().add(i);
        }
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        comboFont.getItems().addAll(fonts);
        comboFont.getSelectionModel().selectFirst();
        comboSize.getSelectionModel().selectFirst();
    }
}
