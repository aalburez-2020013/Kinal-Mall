
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
import org.alvaroalburez.bean.Cliente;
import org.alvaroalburez.bean.Locales;
import org.alvaroalburez.bean.TipoCliente;
import org.alvaroalburez.db.Conexion;
import org.alvaroalburez.system.Principal;

public class ClienteController implements Initializable{
    private Principal escenarioPrincipal;
    private enum operaciones{NUEVO, ELIMINAR, GUARDAR, CANCELAR, ACTUALIZAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<Cliente> listaCliente;
    private ObservableList<Locales> listaLocales;
    private ObservableList<Administracion> listaAdministracion;
    private ObservableList<TipoCliente> listaTipoCliente;
    
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReportes;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReportes;
    @FXML private TextField txtCodigoCliente;
    @FXML private TextField txtNombresCliente;
    @FXML private TextField txtApellidosCliente;
    @FXML private TextField txtTelefonoCliente;
    @FXML private TextField txtDireccionCliente;
    @FXML private TextField txtEmail;
    @FXML private ComboBox cmbCodigoLocales;
    @FXML private ComboBox cmbCodigoAdministracion;
    @FXML private ComboBox cmbCodigoTipoCliente;
    @FXML private TableView tblCliente;
    @FXML private TableColumn colCodigoCliente;
    @FXML private TableColumn colNombresCliente;
    @FXML private TableColumn colApellidosCliente;
    @FXML private TableColumn colTelefonoCliente;
    @FXML private TableColumn colDireccionCliente;
    @FXML private TableColumn colEmail;
    @FXML private TableColumn colCodigoLocales;
    @FXML private TableColumn colCodigoAdministracion;
    @FXML private TableColumn colCodigoTipoCliente;
    
    @Override
    public void initialize(URL location, ResourceBundle resources){
        cargarDatos();
    }
    
    public void cargarDatos(){
                tblCliente.setItems(getCliente());
                colCodigoCliente.setCellValueFactory(new PropertyValueFactory<Cliente, Integer>("codigoCliente"));
                colNombresCliente.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nombresCliente"));
                colApellidosCliente.setCellValueFactory(new PropertyValueFactory<Cliente, String>("apellidosCliente"));
                colTelefonoCliente.setCellValueFactory(new PropertyValueFactory<Cliente, String>("telefonoCliente"));
                colDireccionCliente.setCellValueFactory(new PropertyValueFactory<Cliente, String>("direccionCliente"));
                colEmail.setCellValueFactory(new PropertyValueFactory<Cliente, String>("email"));
                colCodigoLocales.setCellValueFactory(new PropertyValueFactory<Locales, Integer>("codigoLocales"));
                colCodigoAdministracion.setCellValueFactory(new PropertyValueFactory<Administracion, Integer>("codigoAdministracion"));
                colCodigoTipoCliente.setCellValueFactory(new PropertyValueFactory<TipoCliente, Integer>("codigoTipoCliente"));
                cmbCodigoLocales.setItems(getLocales());
                cmbCodigoAdministracion.setItems(getAdministracion());
                cmbCodigoTipoCliente.setItems(getTipoCliente());
    }
    
    public ObservableList<Cliente> getCliente(){
        ArrayList<Cliente> lista = new ArrayList<Cliente>();
        try{
                PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{Call sp_ListarClientes()}");
                ResultSet resultado = procedimiento.executeQuery();
            
            while(resultado.next()){
                lista.add(new Cliente(resultado.getInt("codigoCliente"), resultado.getString("nombresCliente"), 
                    resultado.getString("apellidosCliente"), resultado.getString("telefonoCliente"), resultado.getString("direccionCliente"),
                        resultado.getString("email"), resultado.getInt("codigoLocales"), resultado.getInt("codigoAdministracion"),
                            resultado.getInt("codigoTipoCliente")));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return listaCliente = FXCollections.observableArrayList(lista);
    }
    
    public ObservableList<Locales> getLocales(){
        ArrayList<Locales> lista = new ArrayList<Locales>();
        try{
            PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{Call sp_ListarLocales()}");
            ResultSet resultado = procedimiento.executeQuery();
            
            while(resultado.next()){
                lista.add(new Locales(resultado.getInt("codigoLocales"), resultado.getDouble("saldoFavor"), resultado.getDouble("saldoContra"),
                 resultado.getInt("mesesPendientes"), resultado.getBoolean("disponibilidad"), resultado.getDouble("valorLocal"),
                 resultado.getDouble("valorAdministracion")));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return listaLocales = FXCollections.observableArrayList(lista);
    }
    
    public Locales buscarLocales(int codigoLocales){
        Locales resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{Call sp_BuscarLocales(?)}");
            procedimiento.setInt(1, codigoLocales);
            ResultSet registro = procedimiento.executeQuery();
            
            while(registro.next()){
                resultado = new Locales(registro.getInt("codigoLocales"), registro.getDouble("saldoFavor"), registro.getDouble("saldoContra"),
                    registro.getInt("mesesPendientes"), registro.getBoolean("disponibilidad"), registro.getDouble("valorLocal"),
                        registro.getDouble("valorAdministracion"));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return resultado;
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
    
    public ObservableList<TipoCliente> getTipoCliente(){
        ArrayList<TipoCliente> lista = new ArrayList<TipoCliente>();
        try{
            PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall
                ("{Call sp_ListarTipoCliente()}");
            ResultSet resultado = procedimiento.executeQuery();
            
            while(resultado.next()){
                lista.add(new TipoCliente(resultado.getInt("codigoTipoCliente"),
                    resultado.getString("descripcion")));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return listaTipoCliente = FXCollections.observableArrayList(lista);
    }    
    
    public TipoCliente buscarTipoCliente(int codigoTipoCliente){
        TipoCliente resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{Call sp_BuscarTipoCliente(?)}");
            procedimiento.setInt(1, codigoTipoCliente);
            ResultSet registro = procedimiento.executeQuery();
            
            while(registro.next()){
                resultado = new TipoCliente(registro.getInt("codigoTipoCliente"), registro.getString("descripcion"));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return resultado;
    }

    
    
    public void activarControles(){
        txtCodigoCliente.setEditable(false);
        txtNombresCliente.setEditable(true);
        txtApellidosCliente.setEditable(true);
        txtTelefonoCliente.setEditable(true);
        txtDireccionCliente.setEditable(true);
        txtEmail.setEditable(true);
        cmbCodigoLocales.setEditable(false);
        cmbCodigoAdministracion.setEditable(false);
        cmbCodigoTipoCliente.setEditable(false);
    }
    
    public void desactivarControles(){
        txtCodigoCliente.setEditable(false);
        txtNombresCliente.setEditable(false);
        txtApellidosCliente.setEditable(false);
        txtTelefonoCliente.setEditable(false);
        txtDireccionCliente.setEditable(false);
        txtEmail.setEditable(false);
        cmbCodigoLocales.setEditable(true);
        cmbCodigoAdministracion.setEditable(true);
        cmbCodigoTipoCliente.setEditable(true);
    }
    
    public void limpiarControles(){
        txtCodigoCliente.clear();
        txtNombresCliente.clear();
        txtApellidosCliente.clear();
        txtTelefonoCliente.clear();
        txtDireccionCliente.clear();
        txtEmail.clear();
        cmbCodigoLocales.getSelectionModel().clearSelection();
        cmbCodigoAdministracion.getSelectionModel().clearSelection();
        cmbCodigoTipoCliente.getSelectionModel().clearSelection();
    }
    
    public void seleccionarElemento(){
    if(tblCliente.getSelectionModel().getSelectedItem() != null){
            txtCodigoCliente.setText(String.valueOf(((Cliente)tblCliente.getSelectionModel().getSelectedItem()).getCodigoTipoCliente()));
            txtNombresCliente.setText(String.valueOf(((Cliente)tblCliente.getSelectionModel().getSelectedItem()).getNombresCliente()));
            txtApellidosCliente.setText(String.valueOf(((Cliente)tblCliente.getSelectionModel().getSelectedItem()).getApellidosCliente()));
            txtTelefonoCliente.setText(String.valueOf(((Cliente)tblCliente.getSelectionModel().getSelectedItem()).getTelefonoCliente()));
            txtDireccionCliente.setText(String.valueOf(((Cliente)tblCliente.getSelectionModel().getSelectedItem()).getDireccionCliente()));
            txtEmail.setText(String.valueOf(((Cliente)tblCliente.getSelectionModel().getSelectedItem()).getEmail()));
            cmbCodigoLocales.getSelectionModel().select(buscarLocales(((Cliente)tblCliente.getSelectionModel().getSelectedItem()).getCodigoLocales()));
            cmbCodigoAdministracion.getSelectionModel().select(buscarAdministracion(((Cliente)tblCliente.getSelectionModel().getSelectedItem())
                .getCodigoAdministracion()));
            cmbCodigoTipoCliente.getSelectionModel().select(buscarTipoCliente(((Cliente)tblCliente.getSelectionModel().getSelectedItem())
                .getCodigoTipoCliente()));
        }
    }
    
    public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:
                activarControles();
                limpiarControles();
                btnNuevo.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReportes.setDisable(true);
                imgNuevo.setImage(new Image("/org/alvaroalburez/images/Guardar.png"));
                imgEliminar.setImage(new Image("/org/alvaroalburez/images/Cancelar.png"));
                tipoDeOperacion = operaciones.GUARDAR;
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
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
            break;
        }
    }
    
    public void guardar(){
        Cliente registro = new Cliente();
        registro.setNombresCliente(txtNombresCliente.getText());
        registro.setApellidosCliente(txtApellidosCliente.getText());
        registro.setTelefonoCliente(txtTelefonoCliente.getText());
        registro.setDireccionCliente(txtDireccionCliente.getText());
        registro.setEmail(txtEmail.getText());
        registro.setCodigoLocales(((Locales)cmbCodigoLocales.getSelectionModel().getSelectedItem()).getCodigoLocales());
        registro.setCodigoAdministracion(((Administracion)cmbCodigoAdministracion.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
        registro.setCodigoTipoCliente(((TipoCliente)cmbCodigoTipoCliente.getSelectionModel().getSelectedItem()).getCodigoTipoCliente());
        try{
            PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{Call sp_AgregarCliente(?, ?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setString(1, registro.getNombresCliente());
            procedimiento.setString(2, registro.getApellidosCliente());
            procedimiento.setString(3, registro.getTelefonoCliente());
            procedimiento.setString(4, registro.getDireccionCliente());
            procedimiento.setString(5, registro.getEmail());
            procedimiento.setInt(6, registro.getCodigoLocales());
            procedimiento.setInt(7, registro.getCodigoAdministracion());
            procedimiento.setInt(8, registro.getCodigoTipoCliente());
            procedimiento.execute();
            listaCliente.add(registro);
        }
        catch(Exception e){
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
                tipoDeOperacion = operaciones.NINGUNO;
            break;
            
            default:
                if(tblCliente.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?", "Eliminar Cliente",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{Call sp_EliminarCliente(?)}");
                            procedimiento.setInt(1, ((Cliente)tblCliente.getSelectionModel().getSelectedItem()).getCodigoCliente());
                            procedimiento.execute();
                            listaCliente.remove(tblCliente.getSelectionModel().getSelectedIndex());
                            limpiarControles();
                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento.");
                }
        }
    }
    
    public void editar(){
        switch(tipoDeOperacion){
            case NINGUNO:
                if(tblCliente.getSelectionModel().getSelectedItem() != null){
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    btnEditar.setText("Actualizar");
                    btnReportes.setText("Cancelar");
                    imgEditar.setImage(new Image("/org/alvaroalburez/images/Actualizar.png"));
                    imgReportes.setImage(new Image("/org/alvaroalburez/images/Cancelar.png"));
                    activarControles();
                    tipoDeOperacion = operaciones.ACTUALIZAR;
                }
                else{
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento.");
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
                tipoDeOperacion = operaciones.NINGUNO;
            break;
        }
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{Call sp_EditarCliente(?, ?, ?, ?, ?, ?)}");
            Cliente registro = (Cliente)tblCliente.getSelectionModel().getSelectedItem();
            registro.setNombresCliente(txtNombresCliente.getText());
            registro.setApellidosCliente(txtApellidosCliente.getText());
            registro.setTelefonoCliente(txtTelefonoCliente.getText());
            registro.setDireccionCliente(txtDireccionCliente.getText());
            registro.setEmail(txtEmail.getText());
            procedimiento.setInt(1, registro.getCodigoCliente());
            procedimiento.setString(2, registro.getNombresCliente());
            procedimiento.setString(3, registro.getApellidosCliente());
            procedimiento.setString(4, registro.getTelefonoCliente());
            procedimiento.setString(5, registro.getDireccionCliente());
            procedimiento.setString(6, registro.getEmail());
            procedimiento.execute();
        }
        catch(Exception e){
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
                tipoDeOperacion = operaciones.NINGUNO;
            break;
        }
        

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