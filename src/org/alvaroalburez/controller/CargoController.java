
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
import org.alvaroalburez.bean.Cargo;
import org.alvaroalburez.db.Conexion;
import org.alvaroalburez.system.Principal;




public class CargoController implements Initializable{
    private Principal escenarioPrincipal;
    private enum operaciones{NUEVO, ELIMINAR, GUARDAR, CANCELAR, ACTUALIZAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<Cargo> listaCargo;
    
    
    
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReportes;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReportes;
    @FXML private TextField txtCodigoCargo;
    @FXML private TextField txtNombreCargo;
    @FXML private TableView tblCargo;
    @FXML private TableColumn colCodigoCargo;
    @FXML private TableColumn colNombreCargo;
    
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }
    
    public void cargarDatos(){
        tblCargo.setItems(getCargo());
        colCodigoCargo.setCellValueFactory(new PropertyValueFactory<Cargo, Integer>("codigoCargo"));
        colNombreCargo.setCellValueFactory(new PropertyValueFactory<Cargo, String>("nombreCargo"));
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
        txtCodigoCargo.setEditable(false);
        txtNombreCargo.setEditable(true);
    }
    
    public void desactivarControles(){
        txtCodigoCargo.setEditable(false);
        txtNombreCargo.setEditable(false);
    }
    
    public void limpiarControles(){
        txtCodigoCargo.clear();
        txtNombreCargo.clear();
    }
    
    public void seleccionarElemento(){
        if(tblCargo.getSelectionModel().getSelectedItem() != null){
            txtCodigoCargo.setText(String.valueOf(((Cargo)tblCargo.getSelectionModel().getSelectedItem()).getCodigoCargo()));
            txtNombreCargo.setText(String.valueOf(((Cargo)tblCargo.getSelectionModel().getSelectedItem()).getNombreCargo()));
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
                    imgEliminar.setImage(new Image("/org/alvaroalburez/images/Eliminar.png"));
                    tipoDeOperacion = operaciones.NINGUNO;
                    cargarDatos();
            break;
        }
    }
    
    public void guardar(){
                 Cargo registro = new Cargo();
        
                registro.setNombreCargo(txtNombreCargo.getText());
                 try{
            PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{Call sp_AgregarCargo(?)}");
            
                procedimiento.setString(1, registro.getNombreCargo());
            procedimiento.execute();
            listaCargo.add(registro);
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
                        if(tblCargo.getSelectionModel().getSelectedItem() != null){
                        int respuesta = JOptionPane.showConfirmDialog(null, "Quiere eliminar el registro?", "Eliminar Cargo", 
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if(respuesta == JOptionPane.YES_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{Call sp_EliminarCargo(?)}");
                            
                                procedimiento.setInt(1, ((Cargo)tblCargo.getSelectionModel().getSelectedItem()).getCodigoCargo());
                                procedimiento.execute();
                                listaCargo.remove(tblCargo.getSelectionModel().getSelectedIndex());
                                limpiarControles();
                        }
                            catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "debe seleccionar un elemento.");
                }
            }
        }
    
    
    
    
    public void editar(){
        switch(tipoDeOperacion){
                    case NINGUNO:
                if(tblCargo.getSelectionModel().getSelectedItem() != null){
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
                    JOptionPane.showMessageDialog(null, "debe seleccionar un elemento.");
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
            
            PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{Call sp_EditarCargo(?, ?)}");
            
            Cargo registro = (Cargo)tblCargo.getSelectionModel().getSelectedItem();
            
            registro.setNombreCargo(txtNombreCargo.getText());
            
            procedimiento.setInt(1, registro.getCodigoCargo());
            procedimiento.setString(2, registro.getNombreCargo());
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