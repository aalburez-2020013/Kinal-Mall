
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
import org.alvaroalburez.bean.Departamentos;
import org.alvaroalburez.db.Conexion;
import org.alvaroalburez.system.Principal;


public class DepartamentosController implements Initializable {
    
    private Principal escenarioPrincipal;
    private enum operaciones{NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<Departamentos> listaDepartamentos;
    
    
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReportes;
    @FXML private TextField txtcodigoDepartamento;
    @FXML private TextField txtnombreDepartamento;
    @FXML private TableView tblDepartamento;
    @FXML private TableColumn colcodigoDepartamento;
    @FXML private TableColumn colnombreDepartamento;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReportes;
    
    
    @Override
     public void initialize(URL location, ResourceBundle resources) {
       
       cargarDatos(); 
         
    }
    
    
    public void cargarDatos(){
       tblDepartamento.setItems(getDepartamentos());
       colcodigoDepartamento.setCellValueFactory(new PropertyValueFactory<Departamentos,Integer>("codigoDepartamento"));
       colnombreDepartamento.setCellValueFactory(new PropertyValueFactory<Departamentos,String>("nombreDepartamento"));
        
    }
    
    public ObservableList<Departamentos>getDepartamentos(){
        ArrayList<Departamentos> lista = new ArrayList<Departamentos>();
        
        
        try{
            PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{Call sp_ListarDepartamento()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Departamentos (resultado.getInt("codigoDepartamento"),
                        resultado.getString("nombreDepartamento")));
                
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaDepartamentos = FXCollections.observableArrayList(lista);
        
    }
    
    
    public void seleccionarElemento(){
        
        if(tblDepartamento.getSelectionModel().getSelectedItem()!=null){
            txtcodigoDepartamento.setText(String.valueOf(((Departamentos)tblDepartamento.getSelectionModel().getSelectedItem()).getCodigoDepartamento()));
            txtnombreDepartamento.setText(((Departamentos)tblDepartamento.getSelectionModel().getSelectedItem()).getNombreDepartamento());
            
            
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
                tipoDeOperacion = DepartamentosController.operaciones.GUARDAR;
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
                tipoDeOperacion = DepartamentosController.operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }
    
    public void guardar(){
        Departamentos registro = new Departamentos();
        registro.setNombreDepartamento(txtnombreDepartamento.getText());
        
        
        try{
            PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{call sp_AgregarDepartamento(?)}");
           procedimiento.setString(1, registro.getNombreDepartamento());
           procedimiento.execute();
           listaDepartamentos.add(registro);
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
               tipoDeOperacion = DepartamentosController.operaciones.NINGUNO;
               break;
               
               default:
                   if(tblDepartamento.getSelectionModel().getSelectedItem()!=null){
                   int respuesta = JOptionPane.showConfirmDialog(null, "Estas seguro de Eliminar el registro?", "Eliminar Departamentos", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                   if (respuesta == JOptionPane.YES_OPTION){
                       
               try{
                           PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{call sp_EliminarDepartamento(?)}");
                                   procedimiento.setInt(1, ((Departamentos)tblDepartamento.getSelectionModel().getSelectedItem()).getCodigoDepartamento());
                                   procedimiento.execute();
               
                                   listaDepartamentos.remove(tblDepartamento.getSelectionModel().getSelectedIndex());
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
              
               if(tblDepartamento.getSelectionModel().getSelectedItem() != null){
                   btnEditar.setText("Actualizar");
                   btnReportes.setText("Cancelar");
                   btnNuevo.setDisable(true);
                   btnEliminar.setDisable(true);
                   imgEditar.setImage(new Image("/org/alvaroalburez/images/Actualizar.png"));
                   imgReportes.setImage(new Image("/org/alvaroalburez/images/Cancelar.png"));
                   activarControles();
                   tipoDeOperacion = DepartamentosController.operaciones.ACTUALIZAR;
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
               tipoDeOperacion = DepartamentosController.operaciones.NINGUNO;
               break;
        
    }
    
    }
    
    
           public void actualizar(){
       try{
           PreparedStatement procedimiento = Conexion.getIntance().getConexion().prepareCall("{call sp_EditarDepartamento(?,?)}");
           Departamentos registro = (Departamentos)tblDepartamento.getSelectionModel().getSelectedItem();
           registro.setNombreDepartamento(txtnombreDepartamento.getText());
           procedimiento.setInt(1, registro.getCodigoDepartamento());
           procedimiento.setString(2, registro.getNombreDepartamento());
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
               tipoDeOperacion = DepartamentosController.operaciones.NINGUNO;
               break;
       }
   }
           
           
           public void desactivarControles(){
        
        txtcodigoDepartamento.setEditable(false);
        txtnombreDepartamento.setEditable(false);
        
    }
    
    public void activarControles(){
        
        txtcodigoDepartamento.setEditable(false);
        txtnombreDepartamento.setEditable(true);
        
    }
    
    public void limpiarControles(){
        
        txtcodigoDepartamento.clear();
        txtnombreDepartamento.clear();
        
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
           
           
           
           

    
    
        

    
    
    
    

