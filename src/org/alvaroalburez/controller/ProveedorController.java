
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.alvaroalburez.bean.Administracion;
import org.alvaroalburez.bean.Proveedor;
import org.alvaroalburez.db.Conexion;
import org.alvaroalburez.system.Principal;


public class ProveedorController implements Initializable{
    private enum operaciones {NUEVO, GUARDAR, Eliminar,ACTUALIZAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private Principal escenarioPrincipal;
    private ObservableList<Proveedor> listaProveedor;
    private ObservableList<Administracion> listaAdministracion;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReportes;
    @FXML private TextField txtcodigoProveedor;
    @FXML private TextField txtNITProveedor;
    @FXML private TextField txtServicioPrestado;
    @FXML private TextField txtTelefonoProveedor;
    @FXML private TextField txtDireccionProveedor;
    @FXML private TextField txtSaldoFavor;
    @FXML private TextField txtSaldoContra;
    @FXML private ComboBox cmbCodigoAdministracion;
    @FXML private TableView tblProveedores;
    @FXML private TableColumn colCodigoProveedor;
    @FXML private TableColumn colNITProveedor;
    @FXML private TableColumn colServicioPrestado;
    @FXML private TableColumn colTelefonoProveedor;
    @FXML private TableColumn colSaldoFavor;
    @FXML private TableColumn colSaldoContra;
    @FXML private TableColumn colDireccionProveedor;
    @FXML private TableColumn colCodigoAdministracion;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReportes;
   
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }

    public void cargarDatos(){
        tblProveedores.setItems(getProveedor());
        colCodigoProveedor.setCellValueFactory(new PropertyValueFactory<Proveedor,Integer>("codigoProveedor"));
        colNITProveedor.setCellValueFactory(new PropertyValueFactory<Proveedor,String>("NITProveedor"));
        colServicioPrestado.setCellValueFactory(new PropertyValueFactory<Proveedor,String>("ServicioPrestado"));
        colTelefonoProveedor.setCellValueFactory(new PropertyValueFactory<Proveedor,String>("TelefonoProveedor"));
        colDireccionProveedor.setCellValueFactory(new PropertyValueFactory<Proveedor,String>("DireccionProveedor"));
        colSaldoFavor.setCellValueFactory(new PropertyValueFactory<Proveedor,Double>("SaldoFavor"));
        colSaldoContra.setCellValueFactory(new PropertyValueFactory<Proveedor,Double>("SaldoContra"));
        colCodigoAdministracion.setCellValueFactory(new PropertyValueFactory<Administracion,Integer>("CodigoAdministracion"));
    }
    
    public ObservableList<Proveedor> getProveedor(){
        
        ArrayList<Proveedor> Lista = new ArrayList<Proveedor>();
        try{
            PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{call sp_ListarProveedores()}");
            ResultSet resultado =  procedimiento.executeQuery();
            while (resultado.next()){
                Lista.add(new Proveedor(resultado.getInt("codigoProveedor"),
                            resultado.getString("NITProveedor"),
                            resultado.getString("ServicioPrestado"),
                            resultado.getString("telefonoProveedor"),
                            resultado.getString("DireccionProveedor"),
                            resultado.getDouble("SaldoFavor"),
                            resultado.getDouble("SaldoContra"),
                            resultado.getInt("CodigoAdministracion")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return listaProveedor = FXCollections.observableArrayList(Lista);
    }
    
    
    
    public ObservableList<Administracion> getAdministracion(){
        ArrayList<Administracion> lista = new ArrayList<Administracion>();
        try{
            PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{Call sp_ListarAdministraciones()}");
            ResultSet resultado = procedimiento.executeQuery();
            
            while(resultado.next()){
                lista.add(new Administracion(resultado.getInt("codigoAdministracion"), resultado.getString("direccion"), 
                    resultado.getString("telefono")));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return listaAdministracion = FXCollections.observableArrayList(lista);
    }

    public Administracion buscarAdministracion(int codigoAdministracion){
        Administracion resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{Call sp_BuscarAdministracion(?)}");
            procedimiento.setInt(1, codigoAdministracion);
            ResultSet registro = procedimiento.executeQuery();
            
            while(registro.next()){
                resultado = new Administracion(registro.getInt("codigoAdministracion"), registro.getString("direccion"), 
                    registro.getString("telefono"));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return resultado;
    }

    
    public void seleccionarElemento(){
        
        if(tblProveedores.getSelectionModel().getSelectedItem()!=null){
            txtcodigoProveedor.setText(String.valueOf(((Proveedor)tblProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
            txtNITProveedor.setText(((Proveedor)tblProveedores.getSelectionModel().getSelectedItem()).getNITProveedor());
            txtServicioPrestado.setText(((Proveedor)tblProveedores.getSelectionModel().getSelectedItem()).getServicioPrestado());
            txtTelefonoProveedor.setText(((Proveedor)tblProveedores.getSelectionModel().getSelectedItem()).getTelefonoProveedor());
            txtDireccionProveedor.setText(((Proveedor)tblProveedores.getSelectionModel().getSelectedItem()).getDireccionProveedor());
            txtSaldoFavor.setText(String.valueOf(((Proveedor)tblProveedores.getSelectionModel().getSelectedItem()).getSaldoFavor()));
            txtSaldoContra.setText(String.valueOf(((Proveedor)tblProveedores.getSelectionModel().getSelectedItem()).getSaldoContra()));
            cmbCodigoAdministracion.getSelectionModel().select(buscarAdministracion(((Proveedor)tblProveedores.getSelectionModel().getSelectedItem()).getCodigoAdministracion()));

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
                tipoDeOperacion = ProveedorController.operaciones.GUARDAR;
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
                tipoDeOperacion = ProveedorController.operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }
    
    
    
    public void guardar(){
       Proveedor  registro = new Proveedor();
       
       registro.setNITProveedor(txtNITProveedor.getText());
       registro.setServicioPrestado(txtServicioPrestado.getText());
       registro.setTelefonoProveedor(txtTelefonoProveedor.getText());
       registro.setDireccionProveedor(txtDireccionProveedor.getText());
       try{
           PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{call sp_AgregarProveedor(?,?,?,?)}");
           
           procedimiento.setString(1, registro.getNITProveedor());
           procedimiento.setString(2, registro.getServicioPrestado());
           procedimiento.setString(3, registro.getTelefonoProveedor());
           procedimiento.setString(4, registro.getDireccionProveedor());
           procedimiento.execute();
           listaProveedor.add(registro);
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
               tipoDeOperacion = ProveedorController.operaciones.NINGUNO;
               break;
               default:
                   if(tblProveedores.getSelectionModel().getSelectedItem()!=null){
                   int respuesta = JOptionPane.showConfirmDialog(null, "Estas seguro de Eliminar el registro?", "Eliminar Proveedor", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                   if (respuesta == JOptionPane.YES_OPTION){
                       
                       try{
                           PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{call sp_EliminarProveedor(?)}");
                                   procedimiento.setInt(1, ((Proveedor)tblProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor());
                                   procedimiento.execute();
                                   
                                   listaProveedor.remove(tblProveedores.getSelectionModel().getSelectedIndex());
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
              
               if(tblProveedores.getSelectionModel().getSelectedItem() != null){
                   btnEditar.setText("Actualizar");
                   btnReportes.setText("Cancelar");
                   btnNuevo.setDisable(true);
                   btnEliminar.setDisable(true);
                   imgEditar.setImage(new Image("/org/alvaroalburez/images/Actualizar.png"));
                   imgReportes.setImage(new Image("/org/alvaroalburez/images/Cancelar.png"));
                   activarControles();
                   tipoDeOperacion = ProveedorController.operaciones.ACTUALIZAR;
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
               tipoDeOperacion = ProveedorController.operaciones.NINGUNO;
               break;
       }
   }
    
    
    
    public void actualizar(){
            
            
       try{
           
           PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{call sp_EditarProveedor(?,?,?,?,?)}");
            Proveedor registro=(Proveedor)tblProveedores.getSelectionModel().getSelectedItem();
       registro.setNITProveedor(txtNITProveedor.getText());
       registro.setServicioPrestado(txtServicioPrestado.getText());
       registro.setTelefonoProveedor(txtTelefonoProveedor.getText());
       registro.setDireccionProveedor(txtDireccionProveedor.getText());
           
           procedimiento.setInt(1, registro.getCodigoProveedor());
           procedimiento.setString(2, registro.getNITProveedor());
           procedimiento.setString(3, registro.getServicioPrestado());
           procedimiento.setString(4, registro.getTelefonoProveedor());
           procedimiento.setString(5, registro.getDireccionProveedor());
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
               tipoDeOperacion = ProveedorController.operaciones.NINGUNO;
               break;
       }
   }
    
    public void desactivarControles(){
        
        txtcodigoProveedor.setEditable(false);
        txtNITProveedor.setEditable(false);
        txtServicioPrestado.setEditable(false);
        txtTelefonoProveedor.setEditable(false);
        txtDireccionProveedor.setEditable(false);
        txtSaldoFavor.setEditable(false);
        txtSaldoContra.setEditable(false);
    }
    
    public void activarControles(){
        
        txtcodigoProveedor.setEditable(false);
        txtNITProveedor.setEditable(true);
        txtServicioPrestado.setEditable(true);
        txtTelefonoProveedor.setEditable(true);
        txtDireccionProveedor.setEditable(true);
        txtSaldoFavor.setEditable(false);
        txtSaldoContra.setEditable(false);
    }
    
    public void limpiarControles(){
        
         txtcodigoProveedor.clear();
        txtNITProveedor.clear();
        txtServicioPrestado.clear();
        txtTelefonoProveedor.clear();
        txtDireccionProveedor.clear();
        txtSaldoFavor.clear();
        txtSaldoContra.clear();
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
