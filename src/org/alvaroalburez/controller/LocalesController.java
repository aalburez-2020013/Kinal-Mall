
package org.alvaroalburez.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.alvaroalburez.bean.Locales;
import org.alvaroalburez.db.Conexion;
import org.alvaroalburez.system.Principal;


public class LocalesController implements Initializable {
     private Principal escenarioPrincipal;
     private enum operaciones{NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO};
     private operaciones tipoDeOperacion = operaciones.NINGUNO;
     private ObservableList<Locales> listaLocales;
     
     @FXML private Button btnNuevo;
     @FXML private Button btnEliminar;
     @FXML private Button btnEditar;
     @FXML private Button btnReportes;
     @FXML private TextField txtCodigoLocales;
     @FXML private TextField txtSaldoFavor;
     @FXML private TextField txtSaldoContra;
     @FXML private TextField txtMesesPendientes;
     @FXML private TextField txtDisponibilidad;
     @FXML private TextField txtValorLocal;
     @FXML private TextField txtValorAdministracion;
     @FXML private TableView tblLocales;
     @FXML private TableColumn colCodigoLocales;
     @FXML private TableColumn colSaldoFavor;
     @FXML private TableColumn colSaldoContra;
     @FXML private TableColumn colMesesPendientes;
     @FXML private TableColumn colDisponibilidad;
     @FXML private TableColumn colValorLocal;
     @FXML private TableColumn colValorAdministracion;
     @FXML private ImageView imgNuevo;
     @FXML private ImageView imgEliminar;
     @FXML private ImageView imgEditar;
     @FXML private ImageView imgReportes;
     
     @Override
     public void initialize(URL location, ResourceBundle resources) {
       
        cargarDatos(); 
         
    }
     
     
     public void cargarDatos(){
         tblLocales.setItems(getLocales());
         colCodigoLocales.setCellValueFactory(new PropertyValueFactory<Locales,Integer>("CodigoLocales"));
         colSaldoFavor.setCellValueFactory(new PropertyValueFactory<Locales,Double>("SaldoFavor"));
         colSaldoContra.setCellValueFactory(new PropertyValueFactory<Locales,Double>("SaldoContra"));
         colMesesPendientes.setCellValueFactory(new PropertyValueFactory<Locales,Integer>("MesesPendientes"));
         colDisponibilidad.setCellValueFactory(new PropertyValueFactory<Locales,Boolean>("Disponibilidad"));
         colValorLocal.setCellValueFactory(new PropertyValueFactory<Locales,Double>("ValorLocal"));
         colValorAdministracion.setCellValueFactory(new PropertyValueFactory<Locales,Double>("ValorAdministracion"));
     }
     
     
     public ObservableList<Locales>getLocales(){
         ArrayList<Locales> lista = new ArrayList<Locales>();
         
                 
           try{
               PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{call sp_ListarLocales()}");
               ResultSet resultado = procedimiento.executeQuery();
               while(resultado.next()){
                   lista.add(new Locales(resultado.getInt("CodigoLocales"),
                           resultado.getDouble("SaldoFavor"),
                           resultado.getDouble("SaldoContra"),
                           resultado.getInt("MesesPendientes"),
                           resultado.getBoolean("Disponibilidad"),
                           resultado.getDouble("ValorLocal"),
                           resultado.getDouble("ValorAdministracion")));
               }
           }catch(Exception e){
               e.printStackTrace();
           }      
                 return listaLocales = FXCollections.observableArrayList(lista);
     }
     
     
     public void seleccionarElemento(){
    if(tblLocales.getSelectionModel().getSelectedItem()!=null){
         txtCodigoLocales.setText(String.valueOf(((Locales)tblLocales.getSelectionModel().getSelectedItem()).getCodigoLocales()));
           txtSaldoFavor.setText(String.valueOf(((Locales)tblLocales.getSelectionModel().getSelectedItem()).getSaldoFavor()));
        txtSaldoContra.setText(String.valueOf(((Locales)tblLocales.getSelectionModel().getSelectedItem()).getSaldoContra()));
        txtMesesPendientes.setText(String.valueOf((((Locales)tblLocales.getSelectionModel().getSelectedItem()).getMesesPendientes())));
        txtDisponibilidad.setText(String.valueOf(((Locales)tblLocales.getSelectionModel().getSelectedItem()).getDisponibilidad()));
        txtValorLocal.setText(String.valueOf(((Locales)tblLocales.getSelectionModel().getSelectedItem()).getValorLocal()));
        txtValorAdministracion.setText(String.valueOf(((Locales)tblLocales.getSelectionModel().getSelectedItem()).getValorAdministracion()));
         
    }
 
       }    
     
      public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:
                activarControles();
                btnNuevo.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReportes.setDisable(true);
                imgNuevo.setImage(new Image("/org/alvaroalburez/images/Guardar.png"));
                imgEliminar.setImage(new Image("/org/alvaroalburez/images/Cancelar.png"));
                tipoDeOperacion = LocalesController.operaciones.GUARDAR;
                break;
                
            case GUARDAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReportes.setDisable(false);
                imgNuevo.setImage(new Image("/org/alvaroalburez/images/Nuevo.png"));
                imgEliminar.setImage(new Image("/org/alvaroalburez/images/Eliminar.png"));
                tipoDeOperacion = LocalesController.operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }
     
     public void guardar(){
       Locales  registro = new Locales();
       
       registro.setDisponibilidad(Boolean.parseBoolean(txtDisponibilidad.getText()));
       registro.setValorLocal(Double.parseDouble(txtValorLocal.getText()));
       registro.setValorAdministracion(Double.parseDouble(txtValorAdministracion.getText()));
       try{
           PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{call sp_AgregarLocales(?,?,?)}");
           
           procedimiento.setBoolean(1, registro.getDisponibilidad());
           procedimiento.setDouble(2, registro.getValorLocal());
           procedimiento.setDouble(3, registro.getValorAdministracion());
           procedimiento.execute();
           listaLocales.add(registro);
       }catch(Exception e){
           e.printStackTrace();
           
       }
   }
      
     
     public void eliminar(){
       switch(tipoDeOperacion){
           case GUARDAR:
               desactivarControles();
               limpiarControles();
               btnNuevo.setText("Nuevo");
               btnEliminar.setText("Eliminar");
               imgNuevo.setImage(new Image("/org/alvaroalburez/images/Nuevo.png"));
               imgEliminar.setImage(new Image("/org/alvaroalburez/images/Eliminar.png"));
               btnEditar.setDisable(false);
               btnReportes.setDisable(false);
               tipoDeOperacion = LocalesController.operaciones.NINGUNO;
               break;
               default:
                   if(tblLocales.getSelectionModel().getSelectedItem()!=null){
                   int respuesta = JOptionPane.showConfirmDialog(null, "Estas seguro de Eliminar el registro?", "Eliminar Locales", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                   if (respuesta == JOptionPane.YES_OPTION){
                       
                       try{
                           PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{call sp_EliminarLocales(?)}");
                                   procedimiento.setInt(1, ((Locales)tblLocales.getSelectionModel().getSelectedItem()).getCodigoLocales());
                                   procedimiento.execute();
                                   
                                   listaLocales.remove(tblLocales.getSelectionModel().getSelectedIndex());
                                   limpiarControles();
                       }catch(Exception e){
                           e.printStackTrace();
                       }
                       
                       
                   }
       
                   }else{
                       JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento.");
                   }
       }
          
   }
      
public void editar(){
       switch(tipoDeOperacion){
           case NINGUNO:
              
               if(tblLocales.getSelectionModel().getSelectedItem() != null){
                   btnEditar.setText("Actualizar");
                   btnReportes.setText("Cancelar");
                   btnNuevo.setDisable(true);
                   btnEliminar.setDisable(true);
                   imgEditar.setImage(new Image("/org/alvaroalburez/images/Actualizar.png"));
                   imgReportes.setImage(new Image("/org/alvaroalburez/images/Cancelar.png"));
                   activarControles();
                   tipoDeOperacion = LocalesController.operaciones.ACTUALIZAR;
               }else{
                   JOptionPane.showMessageDialog(null, "debe Seleccionar un Elemento");
               }
               
                 break;
           case ACTUALIZAR:
               actualizar();
               
              
               btnNuevo.setDisable(false);
               btnEliminar.setDisable(false);
               btnEditar.setText("Editar");
               btnReportes.setText("Reportes");
               imgEditar.setImage(new Image("/org/alvaroalburez/images/Editar.png"));
               imgReportes.setImage(new Image("/org/alvaroalburez/images/Reportes.png"));
               limpiarControles();
               desactivarControles();
               cargarDatos();
               tipoDeOperacion = LocalesController.operaciones.NINGUNO;
               break;
       }
   }

public void actualizar(){
            
            
       try{
           
           PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{call sp_EditarLocales(?,?,?,?)}");
            Locales registro=(Locales)tblLocales.getSelectionModel().getSelectedItem();
            registro.setDisponibilidad(Boolean.parseBoolean(txtDisponibilidad.getText()));
            registro.setValorLocal(Double.parseDouble(txtValorLocal.getText()));
            registro.setValorAdministracion(Double.parseDouble(txtValorAdministracion.getText()));
           
           procedimiento.setInt(1, registro.getCodigoLocales());
           procedimiento.setBoolean(2, registro.getDisponibilidad());
           procedimiento.setDouble(3, registro.getValorLocal());
           procedimiento.setDouble(4, registro.getValorAdministracion());
           procedimiento.execute();
           
       }catch(Exception e){
           e.printStackTrace();
       }
   }

public void reportes(){
       switch(tipoDeOperacion){
           case ACTUALIZAR:
                desactivarControles();
               limpiarControles();
               btnEditar.setText("Editar");
               btnReportes.setText("Reportes");
               imgEditar.setImage(new Image("/org/alvaroalburez/images/Editar.png"));
               imgReportes.setImage(new Image("/org/alvaroalburez/images/Reportes.png"));
               btnNuevo.setDisable(false);
               btnEliminar.setDisable(false);
               tipoDeOperacion = LocalesController.operaciones.NINGUNO;
               break;
       }
   }


public void desactivarControles(){
        
        txtCodigoLocales.setEditable(false);
        txtSaldoFavor.setEditable(false);
        txtSaldoContra.setEditable(false);
        txtMesesPendientes.setEditable(false);
        txtDisponibilidad.setEditable(false);
        txtValorLocal.setEditable(false);
        txtValorAdministracion.setEditable(false);
    }
    
    public void activarControles(){
        
        txtCodigoLocales.setEditable(false);
        txtSaldoFavor.setEditable(false);
        txtSaldoContra.setEditable(false);
        txtMesesPendientes.setEditable(false);
        txtDisponibilidad.setEditable(true);
        txtValorLocal.setEditable(true);
        txtValorAdministracion.setEditable(true);
    }
    
    public void limpiarControles(){
        
        txtCodigoLocales.clear();
        txtSaldoFavor.clear();
        txtSaldoContra.clear();
        txtMesesPendientes.clear();
        txtDisponibilidad.clear();
        txtValorLocal.clear();
        txtValorAdministracion.clear();
    }
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }
    








}




