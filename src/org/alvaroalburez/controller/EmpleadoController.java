
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
import org.alvaroalburez.bean.Cargo;
import org.alvaroalburez.bean.Departamentos;
import org.alvaroalburez.bean.Empleado;
import org.alvaroalburez.bean.Horario;
import org.alvaroalburez.db.Conexion;
import org.alvaroalburez.system.Principal;

public class EmpleadoController implements Initializable{
    private Principal escenarioPrincipal;
    private enum operaciones {NUEVO, ELIMINAR, GUARDAR, ACTUALIZAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<Empleado> listaEmpleado;
    private ObservableList<Departamentos> listaDepartamento;
    private ObservableList<Cargo> listaCargo;
    private ObservableList<Horario> listaHorario;
    private ObservableList<Administracion> listaAdministracion;
    private DatePicker fechaDeContratacion;
    
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReportes;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReportes;
    @FXML private GridPane grpFechaContratacion;
    @FXML private TextField txtCodigoEmpleado;
    @FXML private TextField txtNombresEmpleado;
    @FXML private TextField txtApellidosEmpleado;
    @FXML private TextField txtCorreoElectronico;
    @FXML private TextField txtTelefonoEmpleado;
    @FXML private TextField txtSueldo;
    @FXML private ComboBox cmbCodigoDepartamento;
    @FXML private ComboBox cmbCodigoCargo;
    @FXML private ComboBox cmbCodigoHorario;
    @FXML private ComboBox cmbCodigoAdministracion;
    @FXML private TableView tblEmpleado;
    @FXML private TableColumn colCodigoEmpleado;
    @FXML private TableColumn colNombresEmpleado;
    @FXML private TableColumn colApellidosEmpleado;
    @FXML private TableColumn colCorreoElectronico;
    @FXML private TableColumn colTelefonoEmpleado;
    @FXML private TableColumn colFechaContratacion;
    @FXML private TableColumn colSueldo;
    @FXML private TableColumn colCodigoDepartamento;
    @FXML private TableColumn colCodigoCargo;
    @FXML private TableColumn colCodigoHorario;
    @FXML private TableColumn colCodigoAdministracion;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        
        fechaDeContratacion = new DatePicker(Locale.ENGLISH);
        fechaDeContratacion.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        fechaDeContratacion.getCalendarView().todayButtonTextProperty().set("Today");
        fechaDeContratacion.getCalendarView().setShowWeeks(true);
        grpFechaContratacion.add(fechaDeContratacion, 5, 1);
        
    }
    
    
    
    public void cargarDatos(){
        
        tblEmpleado.setItems(getEmpleado());
        colCodigoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, Integer>("codigoEmpleado"));
        colNombresEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, String>("nombresEmpleado"));
        colApellidosEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, String>("apellidosEmpleado"));
        colCorreoElectronico.setCellValueFactory(new PropertyValueFactory<Empleado, String>("correoElectronico"));
        colTelefonoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, String>("telefonoEmpleado"));
        colFechaContratacion.setCellValueFactory(new PropertyValueFactory<Empleado, String>("fechaContratacion"));
        colSueldo.setCellValueFactory(new PropertyValueFactory<Empleado, Double>("sueldo"));
        colCodigoDepartamento.setCellValueFactory(new PropertyValueFactory<Empleado, Integer>("codigoDepartamento"));
        cmbCodigoDepartamento.setItems(getDepartamentos());
        colCodigoCargo.setCellValueFactory(new PropertyValueFactory<Empleado, Integer>("codigoCargo"));
        cmbCodigoCargo.setItems(getCargo());
        colCodigoHorario.setCellValueFactory(new PropertyValueFactory<Empleado, Integer>("codigoHorario"));
        cmbCodigoHorario.setItems(getHorario());
        colCodigoAdministracion.setCellValueFactory(new PropertyValueFactory<Empleado, Integer>("codigoAdministracion"));
        cmbCodigoAdministracion.setItems(getAdministracion());
        
    }
    
    
    
    public ObservableList<Empleado> getEmpleado(){
            ArrayList<Empleado> lista = new ArrayList<Empleado>();
            try{
            PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{Call sp_ListarEmpleados()}");
            ResultSet resultado = procedimiento.executeQuery();
            
            while(resultado.next()){
                lista.add(new Empleado(resultado.getInt("codigoEmpleado"), resultado.getString("nombresEmpleado"), 
                    resultado.getString("apellidosEmpleado"), resultado.getString("correoElectronico"), resultado.getString("telefonoEmpleado"),
                    resultado.getDate("fechaContratacion"), resultado.getDouble("sueldo"), resultado.getInt("codigoDepartamento"),
                    resultado.getInt("codigoCargo"), resultado.getInt("codigoHorario"), resultado.getInt("codigoAdministracion")));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return listaEmpleado = FXCollections.observableArrayList(lista);
    }
    
    
    
    
    public ObservableList<Departamentos> getDepartamentos(){
        ArrayList<Departamentos> lista = new ArrayList<Departamentos>();
        try{
         PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{Call sp_ListarDepartamentos}");
         ResultSet resultado = procedimiento.executeQuery();
            
         while(resultado.next()){
                lista.add(new Departamentos(resultado.getInt("codigoDepartamento"), resultado.getString("nombreDepartamento")));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return listaDepartamento = FXCollections.observableArrayList(lista);
    }
    
    
    
    
    public Departamentos buscarDepartamento(int codigoDepartamento){
        Departamentos resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{Call sp_BuscarDepartamento(?)}");
            
            
            
     procedimiento.setInt(1, codigoDepartamento);
     ResultSet registro = procedimiento.executeQuery();
            
            
            
            
        while(registro.next()){
                resultado = new Departamentos(registro.getInt("codigoDepartamento"), registro.getString("nombreDepartamento"));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return resultado;
    }
    
    
    
    
    
    public ObservableList<Cargo> getCargo(){
        ArrayList<Cargo> lista = new ArrayList<Cargo>();
        try{
            PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{Call sp_ListarCargos()}");
            ResultSet resultado = procedimiento.executeQuery();
            
            
            
            while(resultado.next()){
                lista.add(new Cargo(resultado.getInt("codigoCargo"), resultado.getString("nombreCargo")));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return listaCargo = FXCollections.observableArrayList(lista);
    }
    
    
    
    
    
    public Cargo buscarCargo(int codigoCargo){
        Cargo resultado = null;
        try{
                   PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{Call sp_BuscarCargo(?)}");
            
                    procedimiento.setInt(1, codigoCargo);
                    ResultSet registro = procedimiento.executeQuery();
            
                    while(registro.next()){
                    resultado = new Cargo(registro.getInt("codigoCargo"), registro.getString("nombreCargo"));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return resultado;
    }
    
    
    
    
    
    public ObservableList<Horario> getHorario(){
        ArrayList<Horario> lista = new ArrayList<Horario>();
        try{
            PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{Call sp_ListarHorarios()}");
            ResultSet resultado = procedimiento.executeQuery();
            
            while(resultado.next()){
         lista.add(new Horario(resultado.getInt("codigoHorario"), resultado.getString("horarioEntrada"), resultado.getString("horarioSalida"),
         resultado.getBoolean("lunes"), resultado.getBoolean("martes"), resultado.getBoolean("miercoles"), resultado.getBoolean("jueves"),
         resultado.getBoolean("viernes")));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return listaHorario = FXCollections.observableArrayList(lista);
    }
    
    
    
    
    
    public Horario buscarHorario(int codigoHorario){
        Horario resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{Call sp_BuscarHorario(?)}");
            
            procedimiento.setInt(1, codigoHorario);
            ResultSet registro = procedimiento.executeQuery();
            
            while(registro.next()){
                resultado = new Horario(registro.getInt("codigoHorario"), registro.getString("horarioEntrada"), registro.getString("horarioSalida"),
            registro.getBoolean("lunes"), registro.getBoolean("martes"), registro.getBoolean("miercoles"), registro.getBoolean("jueves"),
            registro.getBoolean("viernes"));
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
    
    
    
    
    
    
    
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }

    
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    
    
    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    
    
    
    public void activarControles(){
        txtCodigoEmpleado.setEditable(false);
        txtNombresEmpleado.setEditable(true);
        txtApellidosEmpleado.setEditable(true);
        txtCorreoElectronico.setEditable(true);
        txtTelefonoEmpleado.setEditable(true);
        fechaDeContratacion.setDisable(false);
        txtSueldo.setEditable(true);
        cmbCodigoDepartamento.setDisable(false);
        cmbCodigoCargo.setDisable(false);
        cmbCodigoHorario.setDisable(false);
        cmbCodigoAdministracion.setDisable(false);
    }
    
    
    
    
    public void desactivarControles(){
        txtCodigoEmpleado.setEditable(false);
        txtNombresEmpleado.setEditable(false);
        txtApellidosEmpleado.setEditable(false);
        txtCorreoElectronico.setEditable(false);
        fechaDeContratacion.setDisable(true);
        txtSueldo.setEditable(false);
        cmbCodigoDepartamento.setDisable(true);
        cmbCodigoCargo.setDisable(true);
        cmbCodigoHorario.setDisable(true);
        cmbCodigoAdministracion.setDisable(true);
    }
    
    
    
    
    public void limpiarControles(){
        txtCodigoEmpleado.clear();
        txtNombresEmpleado.clear();
        txtApellidosEmpleado.clear();
        txtCorreoElectronico.clear();
        txtTelefonoEmpleado.clear();
        fechaDeContratacion.setPromptText("");
        txtSueldo.clear();
        cmbCodigoDepartamento.setValue(null);
        cmbCodigoCargo.setValue(null);
        cmbCodigoHorario.setValue(null);
        cmbCodigoAdministracion.setValue(null);
    }
    
    
    
    
    public void seleccionarElemento(){
        
        
        if(tblEmpleado.getSelectionModel().getSelectedItem() != null){
            txtCodigoEmpleado.setText(String.valueOf(((Empleado)tblEmpleado.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));
            txtNombresEmpleado.setText(((Empleado)tblEmpleado.getSelectionModel().getSelectedItem()).getNombresEmpleado());
            txtApellidosEmpleado.setText(((Empleado)tblEmpleado.getSelectionModel().getSelectedItem()).getApellidosEmpleado());
            txtCorreoElectronico.setText(((Empleado)tblEmpleado.getSelectionModel().getSelectedItem()).getCorreoElectronico());
            txtTelefonoEmpleado.setText(((Empleado)tblEmpleado.getSelectionModel().getSelectedItem()).getTelefonoEmpleado());
            fechaDeContratacion.selectedDateProperty().set(((Empleado)tblEmpleado.getSelectionModel().getSelectedItem()).getFechaContratacion());
            txtSueldo.setText(String.valueOf(((Empleado)tblEmpleado.getSelectionModel().getSelectedItem()).getSueldo()));
            cmbCodigoDepartamento.getSelectionModel().select(buscarDepartamento(((Empleado)tblEmpleado.getSelectionModel().getSelectedItem())
                .getCodigoDepartamento()));
            cmbCodigoCargo.getSelectionModel().select(buscarCargo(((Empleado)tblEmpleado.getSelectionModel().getSelectedItem()).getCodigoCargo()));
            cmbCodigoHorario.getSelectionModel().select(buscarHorario(((Empleado)tblEmpleado.getSelectionModel().getSelectedItem())
                .getCodigoHorario()));
            cmbCodigoAdministracion.getSelectionModel().select(buscarAdministracion(((Empleado)tblEmpleado.getSelectionModel().getSelectedItem())
                .getCodigoAdministracion()));
            
            
            
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
        Empleado registro = new Empleado();
        
        registro.setNombresEmpleado(txtNombresEmpleado.getText());
        registro.setApellidosEmpleado(txtApellidosEmpleado.getText());
        registro.setCorreoElectronico(txtCorreoElectronico.getText());
        registro.setTelefonoEmpleado(txtTelefonoEmpleado.getText());
        registro.setFechaContratacion(fechaDeContratacion.getSelectedDate());
        registro.setSueldo(Double.parseDouble(txtSueldo.getText()));
        registro.setCodigoDepartamento(((Departamentos)cmbCodigoDepartamento.getSelectionModel().getSelectedItem()).getCodigoDepartamento());
        registro.setCodigoCargo(((Cargo)cmbCodigoCargo.getSelectionModel().getSelectedItem()).getCodigoCargo());
        registro.setCodigoHorario(((Horario)cmbCodigoHorario.getSelectionModel().getSelectedItem()).getCodigoHorario());
        registro.setCodigoAdministracion(((Administracion)cmbCodigoAdministracion.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
        
        try{
            
            
            PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{Call sp_AgregarEmpleado(?,?,?,?,?,?,?,?,?,?)}");
            
            procedimiento.setString(1, registro.getNombresEmpleado());
            procedimiento.setString(2, registro.getApellidosEmpleado());
            procedimiento.setString(3, registro.getCorreoElectronico());
            procedimiento.setString(4, registro.getTelefonoEmpleado());
            procedimiento.setDate(5, new java.sql.Date(registro.getFechaContratacion().getTime()));
            procedimiento.setDouble(6, registro.getSueldo());
            procedimiento.setInt(7, registro.getCodigoDepartamento());
            procedimiento.setInt(8, registro.getCodigoCargo());
            procedimiento.setInt(9, registro.getCodigoHorario());
            procedimiento.setInt(10, registro.getCodigoAdministracion());
            procedimiento.execute();
            listaEmpleado.add(registro);
            
            
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
                if(tblEmpleado.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, " Quieres eliminar el registro? ", "Eliminar Empleado",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    
                    if(respuesta == JOptionPane.YES_OPTION){
                        try{
                    PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{Call sp_EliminarEmpleado(?)}");
                            
                    procedimiento.setInt(1, ((Empleado)tblEmpleado.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
                    procedimiento.execute();
                    listaEmpleado.remove(tblEmpleado.getSelectionModel().getSelectedIndex());
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
                if(tblEmpleado.getSelectionModel().getSelectedItem() != null){
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
                    JOptionPane.showMessageDialog(null, " Debe seleccionar un elemento ");
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
                    PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{Call sp_EditarEmpleado(?, ?, ?, ?, ?, ?, ?)}");
                    Empleado registro = (Empleado)tblEmpleado.getSelectionModel().getSelectedItem();
            
                    registro.setNombresEmpleado(txtNombresEmpleado.getText());
                    registro.setApellidosEmpleado(txtApellidosEmpleado.getText());
                    registro.setCorreoElectronico(txtCorreoElectronico.getText());
                    registro.setTelefonoEmpleado(txtTelefonoEmpleado.getText());
                    registro.setFechaContratacion(fechaDeContratacion.getSelectedDate());
                    registro.setSueldo(Double.parseDouble(txtSueldo.getText()));
            
                procedimiento.setInt(1, registro.getCodigoEmpleado());
                procedimiento.setString(2, registro.getNombresEmpleado());
                procedimiento.setString(3, registro.getApellidosEmpleado());
                procedimiento.setString(4, registro.getCorreoElectronico());
                procedimiento.setString(5, registro.getTelefonoEmpleado());
                procedimiento.setDate(6, new java.sql.Date(registro.getFechaContratacion().getTime()));
                procedimiento.setDouble(7, registro.getSueldo());
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