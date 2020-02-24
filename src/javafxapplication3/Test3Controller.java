/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;	
import java.nio.file.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.InputMethodEvent;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * FXML Controller class
 *
 * @author semmy guiose
 */
public class Test3Controller implements Initializable {

    @FXML
    private Text textpage;
    @FXML
    private Button chiffreImageButton;
    @FXML
    private Button chargerImageButton;
    @FXML
    private Button dechiffreImageButton;
    @FXML
    private ImageView imagetoConvert;
    @FXML
    private TextArea chiffreTextArea;
    private ImageView imagResult;
    @FXML
    private TextField keyTextField;
    @FXML
    private Button chiffreTextButton;
    @FXML
    private Button dechiffreTextButton;
    @FXML
    private TextArea dechiffreTextArea;
    @FXML
    private Button generatKeyButton;
    @FXML
    private Text labelIv;
    @FXML
    private TextField IVTextField;
    @FXML
    private Button changeKeymanuel_button;
    @FXML
    private Button changeIVvalueButton;
    @FXML
    private Button autogenKEY_button;
    @FXML
    private Button setkey_bouton;
    @FXML
    private Label namefileLabel;
    @FXML
    private Button saveKeyButton;
        @FXML
    private Button setIVkey_button;

    private  byte[] tkey;
    private  SecretKeySpec secretKey;
    private  Cipher  cipher;
    private  byte[] IV;
    private  SecretKey key;
    private Image image  ;
    private File file ;
   
    @FXML
     public  void choosePicture( ActionEvent event ) throws IOException, Exception
     {
     Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
     FileChooser fileChooser = new FileChooser(); 
     fileChooser.setTitle("choisir image de à convertir ");
     fileChooser.setInitialDirectory(new File(System.getProperty("user.home") ));
     file= fileChooser.showOpenDialog(stage);
     if (file !=null )
       { 
           String  extension = "inconnu";
           if(Files.probeContentType(file.toPath())!= null)
                extension = Files.probeContentType(file.toPath());
           if ( (extension.equals("image/jpeg"))|| (extension.equals("image/gif")) || (extension.equals("image/bmp")) || (extension.equals("image/png")))
            {
                image= new Image(file.toURI().toString());
                imagetoConvert.setImage(image);
                imagetoConvert.setVisible(true);
                namefileLabel.setVisible(false);
            }else 
            {
                 namefileLabel.setVisible(true);
                 imagetoConvert.setVisible(false);
                 namefileLabel.setText(file.getName());
            }
       }else 
           throw new Exception( "le fichier n\'existe pas ");    
     }
     
    @FXML
    public void cypherImage( ActionEvent event) throws IOException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, Exception
    {Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Path fichier = Paths.get(file.getAbsolutePath());
         byte[] tocode= Files.readAllBytes(fichier);
          IvParameterSpec ivSpec = new IvParameterSpec(IV);
     cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
      byte[] imagecipher =cipher.doFinal(tocode);
     //todo ajouter filechooser pour selectionner ou on va enredistrer le fichier
     FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Image");
          //  System.out.println(pic.getId());
            File tFile = fileChooser.showSaveDialog(stage);
        
        // tFile.createNewFile();
        try (OutputStream os = new FileOutputStream(tFile)) {
            os.write(imagecipher);
        }catch(Exception e)
        {
            System.out.println(" something wrong ");
        } 
   /* if (tFile !=null )
       {String  extension = "inconnu";
           if(Files.probeContentType(file.toPath())!= null)
                extension = Files.probeContentType(file.toPath());
           if ( (extension.equals("image/jpeg"))|| (extension.equals("image/gif")) || (extension.equals("image/bmp")) || (extension.equals("image/png")))
            {
              //  Image imageconvert =  new Image(tFile.toURI().toString());
         //imagResult.setImage(imageconvert);
                image= new Image(file.toURI().toString());
                imagetoConvert.setImage(image);
                imagetoConvert.setVisible(true);
                namefileLabel.setVisible(false);
            }
         
       }else 
           throw new Exception( " le   fichier  n\'existe pas "); */
    } 
    
    @FXML
     public void encypherImage( ActionEvent event) throws IOException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException
    {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
// retravailler sur cette partie pour choi ou  enregistrer le fichier 
        Path fichier;
        fichier = Paths.get(file.getAbsolutePath());
        byte[] tocode= Files.readAllBytes(fichier);
        IvParameterSpec ivSpec = new IvParameterSpec(IV);
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
        byte[] imagenocipher =cipher.doFinal(tocode);
        
        // ou enregistrer
          FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Image");
          //  System.out.println(pic.getId());
            File tFile = fileChooser.showSaveDialog(stage);
      
       // tFile.createNewFile();
        try (OutputStream os = new FileOutputStream(tFile)) {
            os.write(imagenocipher);   
        }catch(Exception e)
        {
            System.out.println(" something wrong ");
        } 
      
     if (tFile !=null )
       {
           String  extension = "inconnu";
           if(Files.probeContentType(tFile.toPath())!= null)
                extension = Files.probeContentType(tFile.toPath());
           if ( (extension.equals("image/jpeg"))|| (extension.equals("image/gif")) || (extension.equals("image/bmp")) || (extension.equals("image/png")))
            {
                image= new Image(tFile.toURI().toString());
                imagetoConvert.setImage(image);
                imagetoConvert.setVisible(true);
                namefileLabel.setVisible(false);
            }else 
            {
                 namefileLabel.setVisible(true);
                 imagetoConvert.setVisible(false);
                 namefileLabel.setText(tFile.getName());
            }
       }
    } 
    @FXML
    public void cypherText(ActionEvent event ) throws InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException
    {
     IvParameterSpec ivSpec = new IvParameterSpec(IV);
     cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
     byte[] messagecipher =cipher.doFinal(chiffreTextArea.getText().getBytes());
     dechiffreTextArea.setText(Base64.getEncoder().encodeToString(messagecipher)); 
    }
    @FXML
    public void encypherText(ActionEvent event ) throws InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException
    {
      IvParameterSpec ivSpec = new IvParameterSpec(IV);
      cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
      byte[] messageencipher = cipher.doFinal(Base64.getDecoder().decode(dechiffreTextArea.getText()));
      chiffreTextArea.setText(new String(messageencipher));
    }
    @FXML
     public void generateKey(ActionEvent event ) throws NoSuchAlgorithmException
     {
      KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        // Generate Key
        key = keyGenerator.generateKey();
        // Generating IV.
        IV = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(IV);
        keyTextField.setText( Base64.getEncoder().encodeToString(key.getEncoded()));
        // IVTextField.setText(new String(key.getEncoded(),StandardC));
        IVTextField.setText(  Base64.getEncoder().encodeToString(IV));
        // decode the base64 encoded string
        //byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        //if ( java.util.Arrays.equals(Base64.getDecoder().decode(keyTextField.getText()) , key.getEncoded()))
         //System.out.println("  egalité tableau de byte ");
    }
     /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            namefileLabel.setVisible(false);
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException ex) {
            Logger.getLogger(Test3Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    void Changevalue() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
    public void setChangevaluekey(ActionEvent event){
       
        secretKey= new SecretKeySpec(Base64.getDecoder().decode(keyTextField.getText()), "AES");
           key=secretKey;
    }

     
    @FXML
     public void generateKeyOnly(ActionEvent event) throws NoSuchAlgorithmException{
     
      KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        // Generate Key
        key = keyGenerator.generateKey();
            keyTextField.setText( Base64.getEncoder().encodeToString(key.getEncoded()));
     }
    @FXML
     public void ChangevalueIV(ActionEvent event){
         IV = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(IV);
        IVTextField.setText(Base64.getEncoder().encodeToString(IV));
    }
    public  void setKey(String myKey) 
    {
        MessageDigest sha = null;
        try {
            tkey = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            tkey = sha.digest(tkey);
            tkey = Arrays.copyOf(tkey, 16); 
           secretKey= new SecretKeySpec(tkey, "AES");
           key=secretKey;
            keyTextField.setText( Base64.getEncoder().encodeToString( secretKey.getEncoded()));
        } 
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } 
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void SetIVkey(ActionEvent event )
    {
     IV = Base64.getDecoder().decode(IVTextField.getText());
    }
    @FXML
    public void setKeyFromuUser(ActionEvent event ){
        TextInputDialog dialog = new TextInputDialog("Keyvalue");
        dialog.setTitle("saisie de clef ");
        dialog.setHeaderText("saisie de la valeur min 16 char");
        dialog.setContentText("entrez votre key ");
        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> System.out.println("valeur  key : " + name));
        result.ifPresent(name -> setKey(name));
    }

    @FXML
    private void SetIVkey(InputMethodEvent event) {
    }
}
