
package org.alvaroalburez.controller;

import eu.schudt.javafx.controls.calendar.DatePicker;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
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
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;
import org.alvaroalburez.bean.Administracion;
import org.alvaroalburez.bean.CuentaPorPagar;
import org.alvaroalburez.bean.Proveedor;
import org.alvaroalburez.db.Conexion;
import org.alvaroalburez.system.Principal;

public class CuentaPorPagarController implements Initializable{
    private Principal escenarioPrincipal;
    private enum operaciones {NUEVO, ELIMINAR, GUARDAR, ACTUALIZAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<CuentaPorPagar> listaCuentaPorPagar;
    private ObservableList<Administracion> listaAdministracion;
    private ObservableList<Proveedor> listaProveedor;
    private DatePicker fechaLimite;
    
    
    
    
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReportes;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReportes;
    @FXML private GridPane grpFechaLimite;
    @FXML private TextField txtCodigoCuentaPorPagar;
    @FXML private TextField txtNumeroFactura;
    @FXML private TextField txtEstadoPago;
    @FXML private TextField txtValorNetoPago;
    @FXML private ComboBox cmbCodigoAdministracion;
    @FXML private ComboBox cmbCodigoProveedor;
    @FXML private TableView tblCuentaPorPagar;
    @FXML private TableColumn colCodigoCuentaPorPagar;
    @FXML private TableColumn colNumeroFactura;
    @FXML private TableColumn colFechaLimitePago;
    @FXML private TableColumn colEstadoPago;
    @FXML private TableColumn colValorNetoPago;
    @FXML private TableColumn colCodigoAdministracion;
    @FXML private TableColumn colCodigoProveedor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        fechaLimite = new DatePicker(Locale.ENGLISH);
        
        fechaLimite.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        
        fechaLimite.getCalendarView().todayButtonTextProperty().set("Today");
        
        fechaLimite.getCalendarView().setShowWeeks(true);
        
        grpFechaLimite.add(fechaLimite, 5, 0);
        
    }
    
    
    
    public void cargarDatos(){
        tblCuentaPorPagar.setItems(getCuentaPorPagar());
        colCodigoCuentaPorPagar.setCellValueFactory(new PropertyValueFactory<CuentaPorPagar, Integer>("codigoCuentaPorPagar"));
        colNumeroFactura.setCellValueFactory(new PropertyValueFactory<CuentaPorPagar, String>("numeroFactura"));
        colFechaLimitePago.setCellValueFactory(new PropertyValueFactory<CuentaPorPagar, String>("fechaLimitePago"));
        colEstadoPago.setCellValueFactory(new PropertyValueFactory<CuentaPorPagar, String>("estadoPago"));
        colValorNetoPago.setCellValueFactory(new PropertyValueFactory<CuentaPorPagar, Double>("valorNetoPago"));
        colCodigoAdministracion.setCellValueFactory(new PropertyValueFactory<CuentaPorPagar, Integer>("codigoAdministracion"));
        cmbCodigoAdministracion.setItems(getAdministracion());
        colCodigoProveedor.setCellValueFactory(new PropertyValueFactory<CuentaPorPagar, Integer>("codigoProveedor"));
        cmbCodigoProveedor.setItems(getProveedor());
    }
    
    
    
    public ObservableList<CuentaPorPagar> getCuentaPorPagar(){
        ArrayList<CuentaPorPagar> lista = new ArrayList<CuentaPorPagar>();
        try{
            PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{Call sp_ListarCuentasPorPagar()}");
            ResultSet resultado = procedimiento.executeQuery();
            
            
            while(resultado.next()){
                lista.add(new CuentaPorPagar(resultado.getInt("codigoCuentaPorPagar"), resultado.getString("numeroFactura"), 
                    resultado.getDate("fechaLimitePago"), resultado.getString("estadoPago"), resultado.getDouble("valorNetoPago"),
                        resultado.getInt("codigoAdministracion"), resultado.getInt("codigoProveedor")));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return listaCuentaPorPagar = FXCollections.observableArrayList(lista);
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
    
    public ObservableList<Proveedor> getProveedor(){
        ArrayList<Proveedor> lista = new ArrayList<Proveedor>();
        try{
            PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{Call sp_ListarProveedores()}");
            ResultSet resultado =  procedimiento.executeQuery();
            
                 while (resultado.next()){
                lista.add(new Proveedor(resultado.getInt("codigoProveedor"), resultado.getString("NITProveedor"), 
                        
                    resultado.getString("servicioPrestado"), resultado.getString("telefonoProveedor"), resultado.getString("direccionProveedor"),
                        
                        resultado.getDouble("saldoFavor"), resultado.getDouble("saldoContra"), resultado.getInt("codigoAdministracion")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return listaProveedor = FXCollections.observableArrayList(lista);
    }
    
    public Proveedor buscarProveedor(int codigoProveedor){
        
        Proveedor resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{Call sp_BuscarProveedor(?)}");
            procedimiento.setInt(1, codigoProveedor);
            ResultSet registro = procedimiento.executeQuery();
            
            
                    while(registro.next()){
                    resultado = new Proveedor(registro.getInt("codigoProveedor"), registro.getString("NITProveedor"), 
                    registro.getString("servicioPrestado"), registro.getString("telefonoProveedor"), registro.getString("direccionProveedor"),
                        registro.getDouble("saldoFavor"), registro.getDouble("saldoContra"), registro.getInt("codigoAdministracion"));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return resultado;
    }
    
    
    
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }
    
    
    public Principal getEscenarioPrincipal(){
        return escenarioPrincipal;
    }
    
    public void setEscenarioPrincipal(Principal escenarioPrincipal){
        this.escenarioPrincipal=escenarioPrincipal;
    }
    
    public void activarControles(){
        txtCodigoCuentaPorPagar.setEditable(false);
        txtNumeroFactura.setEditable(true);
        fechaLimite.setDisable(false);
        txtEstadoPago.setEditable(true);
        txtValorNetoPago.setEditable(true);
        cmbCodigoAdministracion.setDisable(false);
        cmbCodigoProveedor.setDisable(false);
    }
    
    public void desactivarControles(){
        txtCodigoCuentaPorPagar.setEditable(false);
        txtNumeroFactura.setEditable(false);
        fechaLimite.setDisable(true);
        txtEstadoPago.setEditable(false);
        txtValorNetoPago.setEditable(false);
        cmbCodigoAdministracion.setDisable(true);
        cmbCodigoProveedor.setDisable(true);
    }
    
    public void limpiarControles(){
        txtCodigoCuentaPorPagar.clear();
        txtNumeroFactura.clear();
        fechaLimite.setPromptText("");
        txtEstadoPago.clear();
        txtValorNetoPago.clear();
        cmbCodigoAdministracion.setValue(null);
        cmbCodigoProveedor.setValue(null);
    }
    
    public void seleccionarElemento(){
        if(tblCuentaPorPagar.getSelectionModel().getSelectedItem() != null){
            txtCodigoCuentaPorPagar.setText(String.valueOf(((CuentaPorPagar)tblCuentaPorPagar.getSelectionModel().getSelectedItem())
                .getCodigoCuentaPorPagar()));
            txtNumeroFactura.setText(((CuentaPorPagar)tblCuentaPorPagar.getSelectionModel().getSelectedItem()).getNumeroFactura());
            
            fechaLimite.selectedDateProperty().set(((CuentaPorPagar)tblCuentaPorPagar.getSelectionModel().getSelectedItem()).getFechaLimitePago());
            txtEstadoPago.setText(((CuentaPorPagar)tblCuentaPorPagar.getSelectionModel().getSelectedItem()).getEstadoPago());
            txtValorNetoPago.setText(String.valueOf(((CuentaPorPagar)tblCuentaPorPagar.getSelectionModel().getSelectedItem()).getValorNetoPago()));
            cmbCodigoAdministracion.getSelectionModel().select(buscarAdministracion(((CuentaPorPagar)tblCuentaPorPagar.getSelectionModel()
                .getSelectedItem()).getCodigoAdministracion()));
            cmbCodigoProveedor.getSelectionModel().select(buscarProveedor(((CuentaPorPagar)tblCuentaPorPagar.getSelectionModel().getSelectedItem())
                .getCodigoProveedor()));
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
            CuentaPorPagar registro = new CuentaPorPagar();
            registro.setNumeroFactura(txtNumeroFactura.getText());
            registro.setFechaLimitePago(fechaLimite.getSelectedDate());
            registro.setEstadoPago(txtEstadoPago.getText());
            registro.setValorNetoPago(Double.parseDouble(txtValorNetoPago.getText()));
            registro.setCodigoAdministracion(((Administracion)cmbCodigoAdministracion.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
            registro.setCodigoProveedor(((Proveedor)cmbCodigoProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());
        
        try{
            PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{Call sp_AgregarCuentaPorPagar(?, ?, ?, ?, ?, ?)}");
            
            procedimiento.setString(1, registro.getNumeroFactura());
            procedimiento.setDate(2, new java.sql.Date(registro.getFechaLimitePago().getTime()));
            procedimiento.setString(3, registro.getEstadoPago());
            procedimiento.setDouble(4, registro.getValorNetoPago());
            procedimiento.setInt(5, registro.getCodigoAdministracion());
            procedimiento.setInt(6, registro.getCodigoProveedor());
            procedimiento.execute();
            listaCuentaPorPagar.add(registro);
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
                if(tblCuentaPorPagar.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "Quieres eliminar el registro?", "Eliminar Cuenta por Pagar",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{Call sp_EliminarCuentaPorPagar(?)}");
                            procedimiento.setInt(1, ((CuentaPorPagar)tblCuentaPorPagar.getSelectionModel().getSelectedItem())
                                .getCodigoCuentaPorPagar());
                            procedimiento.execute();
                            listaCuentaPorPagar.remove(tblCuentaPorPagar.getSelectionModel().getSelectedIndex());
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
                if(tblCuentaPorPagar.getSelectionModel().getSelectedItem() != null){
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
            
            
            PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{Call sp_EditarCuentaPorPagar(?, ?, ?, ?, ?)}");
            CuentaPorPagar registro = (CuentaPorPagar)tblCuentaPorPagar.getSelectionModel().getSelectedItem();
            
            
            registro.setNumeroFactura(txtNumeroFactura.getText());
            registro.setFechaLimitePago(fechaLimite.getSelectedDate());
            registro.setEstadoPago(txtEstadoPago.getText());
            registro.setValorNetoPago(Double.parseDouble(txtValorNetoPago.getText()));
            procedimiento.setInt(1, registro.getCodigoCuentaPorPagar());
            procedimiento.setString(2, registro.getNumeroFactura());
            procedimiento.setDate(3, new java.sql.Date(registro.getFechaLimitePago().getTime()));
            procedimiento.setString(4, registro.getEstadoPago());
            procedimiento.setDouble(5, registro.getValorNetoPago());
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
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                btnEditar.setText("Editar");
                btnReportes.setText("Reportes");
                imgEditar.setImage(new Image("/org/alvaroalburez/images/Editar.png"));
                imgReportes.setImage(new Image("/org/alvaroalburez/images/Reportes.png"));
                tipoDeOperacion = operaciones.NINGUNO;
            break;
        }
    }
}