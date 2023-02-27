
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
import org.alvaroalburez.bean.Horario;
import org.alvaroalburez.db.Conexion;
import org.alvaroalburez.system.Principal;

public class HorarioController implements Initializable{
    private Principal escenarioPrincipal;
    private enum operaciones{NUEVO, ELIMINAR, GUARDAR, CANCELAR, ACTUALIZAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<Horario> listaHorario;
    
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReportes;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReportes;
    @FXML private TextField txtCodigoHorario;
    @FXML private TextField txtHorarioEntrada;
    @FXML private TextField txtHorarioSalida;
    @FXML private TextField txtLunes;
    @FXML private TextField txtMartes;
    @FXML private TextField txtMiercoles;
    @FXML private TextField txtJueves;
    @FXML private TextField txtViernes;
    @FXML private TableView tblHorario;
    @FXML private TableColumn colCodigoHorario;
    @FXML private TableColumn colHorarioEntrada;
    @FXML private TableColumn colHorarioSalida;
    @FXML private TableColumn colLunes;
    @FXML private TableColumn colMartes;
    @FXML private TableColumn colMiercoles;
    @FXML private TableColumn colJueves;
    @FXML private TableColumn colViernes;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }
    
    
    
    public void cargarDatos(){
        tblHorario.setItems(getHorario());
        colCodigoHorario.setCellValueFactory(new PropertyValueFactory<Horario, Integer>("codigoHorario"));
        colHorarioEntrada.setCellValueFactory(new PropertyValueFactory<Horario, String>("horarioEntrada"));
        colHorarioSalida.setCellValueFactory(new PropertyValueFactory<Horario, String>("horarioSalida"));
        colLunes.setCellValueFactory(new PropertyValueFactory<Horario, Boolean>("lunes"));
        colMartes.setCellValueFactory(new PropertyValueFactory<Horario, Boolean>("martes"));
        colMiercoles.setCellValueFactory(new PropertyValueFactory<Horario, Boolean>("miercoles"));
        colJueves.setCellValueFactory(new PropertyValueFactory<Horario, Boolean>("jueves"));
        colViernes.setCellValueFactory(new PropertyValueFactory<Horario, Boolean>("viernes"));
    }
    
    
    
    
    public ObservableList<Horario> getHorario(){
        ArrayList<Horario> lista = new ArrayList<Horario>();
        try{
            PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{Call sp_ListarHorarios()}");
            ResultSet resultado = procedimiento.executeQuery();
            
            while(resultado.next()){
                lista.add(new Horario(resultado.getInt("codigoHorario"), resultado.getString("horarioEntrada"), 
                    resultado.getString("horarioSalida"), resultado.getBoolean("lunes"), resultado.getBoolean("martes"),
                        resultado.getBoolean("miercoles"), resultado.getBoolean("jueves"), resultado.getBoolean("viernes")));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return listaHorario = FXCollections.observableArrayList(lista);
    }
    
    
    
    
    
    
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }
    
    public Principal getEscenarioPrincipal(){
        return escenarioPrincipal;
    }
    
    public void setEscenarioPrincipal(Principal escenarioPrincipal){
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void activarControles(){
        txtCodigoHorario.setEditable(false);
        txtHorarioEntrada.setEditable(true);
        txtHorarioSalida.setEditable(true);
        txtLunes.setEditable(true);
        txtMartes.setEditable(true);
        txtMiercoles.setEditable(true);
        txtJueves.setEditable(true);
        txtViernes.setEditable(true);
    }
    
    
    
    public void desactivarControles(){
        txtCodigoHorario.setEditable(false);
        txtHorarioEntrada.setEditable(false);
        txtHorarioSalida.setEditable(false);
        txtLunes.setEditable(false);
        txtMartes.setEditable(false);
        txtMiercoles.setEditable(false);
        txtJueves.setEditable(false);
        txtViernes.setEditable(false);
    }
    
    
    
    public void limpiarControles(){
        txtCodigoHorario.clear();
        txtHorarioEntrada.clear();
        txtHorarioSalida.clear();
        txtLunes.clear();
        txtMartes.clear();
        txtMiercoles.clear();
        txtJueves.clear();
        txtViernes.clear();
    }
    
    
    
    
    public void seleccionarElemento(){
        if(tblHorario.getSelectionModel().getSelectedItem() != null){
            txtCodigoHorario.setText(String.valueOf(((Horario)tblHorario.getSelectionModel().getSelectedItem()).getCodigoHorario()));
            txtHorarioEntrada.setText(String.valueOf(((Horario)tblHorario.getSelectionModel().getSelectedItem()).getHorarioEntrada()));
            txtHorarioSalida.setText(String.valueOf(((Horario)tblHorario.getSelectionModel().getSelectedItem()).getHorarioEntrada()));
            txtLunes.setText(String.valueOf(((Horario)tblHorario.getSelectionModel().getSelectedItem()).getLunes()));
            txtMartes.setText(String.valueOf(((Horario)tblHorario.getSelectionModel().getSelectedItem()).getMartes()));
            txtMiercoles.setText(String.valueOf(((Horario)tblHorario.getSelectionModel().getSelectedItem()).getMiercoles()));
            txtJueves.setText(String.valueOf(((Horario)tblHorario.getSelectionModel().getSelectedItem()).getJueves()));
            txtViernes.setText(String.valueOf(((Horario)tblHorario.getSelectionModel().getSelectedItem()).getViernes()));
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
                imgEliminar.setImage((new Image("/org/alvaroalburez/images/Eliminar.png")));
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
            break;
        }
    }
    
    
    
    public void guardar(){
        Horario registro = new Horario();
        registro.setHorarioEntrada(txtHorarioEntrada.getText());
        registro.setHorarioSalida(txtHorarioSalida.getText());
        registro.setLunes(Boolean.parseBoolean(txtLunes.getText()));
        registro.setMartes(Boolean.parseBoolean(txtMartes.getText()));
        registro.setMiercoles(Boolean.parseBoolean(txtMiercoles.getText()));
        registro.setJueves(Boolean.parseBoolean(txtJueves.getText()));
        registro.setViernes(Boolean.parseBoolean(txtViernes.getText()));
        
        try{
            PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{Call sp_AgregarHorario(?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setString(1, registro.getHorarioEntrada());
            procedimiento.setString(2, registro.getHorarioSalida());
            procedimiento.setBoolean(3, registro.getLunes());
            procedimiento.setBoolean(4, registro.getMartes());
            procedimiento.setBoolean(5, registro.getMiercoles());
            procedimiento.setBoolean(6, registro.getJueves());
            procedimiento.setBoolean(7, registro.getViernes());
            procedimiento.execute();
            listaHorario.add(registro);
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
                if(tblHorario.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?", "Eliminar Horario",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{Call sp_EliminarCliente(?)}");
                            procedimiento.setInt(1, ((Horario)tblHorario.getSelectionModel().getSelectedItem()).getCodigoHorario());
                            procedimiento.execute();
                            listaHorario.remove(tblHorario.getSelectionModel().getSelectedIndex());
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
                if(tblHorario.getSelectionModel().getSelectedItem() != null){
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
            PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{Call sp_EditarHorario(?, ?, ?, ?, ?, ?, ?, ?)}");
            Horario registro = (Horario)tblHorario.getSelectionModel().getSelectedItem();
            registro.setHorarioEntrada(txtHorarioEntrada.getText());
            registro.setHorarioSalida(txtHorarioSalida.getText());
            registro.setLunes(Boolean.parseBoolean(txtLunes.getText()));
            registro.setMartes(Boolean.parseBoolean(txtMartes.getText()));
            registro.setMiercoles(Boolean.parseBoolean(txtMiercoles.getText()));
            registro.setJueves(Boolean.parseBoolean(txtJueves.getText()));
            registro.setViernes(Boolean.parseBoolean(txtViernes.getText()));
            procedimiento.setInt(1, registro.getCodigoHorario());
            procedimiento.setString(2, registro.getHorarioEntrada());
            procedimiento.setString(3, registro.getHorarioSalida());
            procedimiento.setBoolean(4, registro.getLunes());
            procedimiento.setBoolean(5, registro.getMartes());
            procedimiento.setBoolean(6, registro.getMiercoles());
            procedimiento.setBoolean(7, registro.getJueves());
            procedimiento.setBoolean(8, registro.getViernes());
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
}
