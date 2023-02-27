/**
 * Programador: Alvaro Ramiro Alburez Rivera
 * Codigo Tecnico: IN5AV
 * Carne: 2020013
 * Fecha de Creacion: 5/05/2021
 * Modificacion: 12/05/2021
 * @author Ramiro Alburez
 */
package org.alvaroalburez.system;

import java.io.IOException;
import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import javafx.stage.Stage;
import org.alvaroalburez.controller.MenuPrincipalController;
import org.alvaroalburez.controller.ProgramadorController;
import org.alvaroalburez.controller.AdmnistracionController;
import org.alvaroalburez.controller.CargoController;
import org.alvaroalburez.controller.DepartamentosController;
import org.alvaroalburez.controller.LocalesController;
import org.alvaroalburez.controller.ProveedorController;
import org.alvaroalburez.controller.TipoClienteController;
import org.alvaroalburez.controller.ClienteController;
import org.alvaroalburez.controller.CuentaPorCobrarController;
import org.alvaroalburez.controller.CuentaPorPagarController;
import org.alvaroalburez.controller.EmpleadoController;
import org.alvaroalburez.controller.HorarioController;
import org.alvaroalburez.controller.LoginController;
import org.alvaroalburez.controller.UsuarioController;

/**
 *
 * @author Ramiro Alburez
 */
public class Principal extends Application {
    private final String PAQUETE_VISTA ="/org/alvaroalburez/view/";
    private Stage escenarioPrincipal;
    private Scene escena;
    @Override
    public void start(Stage escenarioPrincipal) throws IOException {
       this.escenarioPrincipal = escenarioPrincipal;
       this.escenarioPrincipal.setTitle("Kinal Mall 2021");
       //Parent root = FXMLLoader.load(getClass().getResource("/org/alvaroalburez/view/MenuPrincipalView.fxml"));
       //Scene escena = new Scene(root);
       //escenarioPrincipal.setScene(escena);
       ventanaLogin();
       escenarioPrincipal.show();
    }

    public void menuPrincipal(){
        try{
            MenuPrincipalController menuPrincipal = (MenuPrincipalController)cambiarEscena("MenuPrincipalView.fxml", 538, 400);
            menuPrincipal.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
      
    }
    }
    public void ventanaProgramador(){
        try{
            ProgramadorController programador = (ProgramadorController)cambiarEscena("ProgramadorView.fxml", 600, 400);
            programador.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaAdmnistracion(){
        
        try{
            AdmnistracionController admin = (AdmnistracionController)cambiarEscena("AdministracionView.fxml",718,404);
            admin.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void ventanaTipoCliente(){
        
        try{
            TipoClienteController tipoCliente = (TipoClienteController)cambiarEscena("TipoClienteView.fxml",830,404);
            tipoCliente.setEscenarioPrincipal(this);
            
        }catch (Exception e){
            e.printStackTrace();
        }        
        
    }
    
    public void ventanaLocales(){
        
        try{
            LocalesController local = (LocalesController)cambiarEscena("LocalesView.fxml",992,404);
            local.setEscenarioPrincipal (this);
        }catch (Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void ventanaDepartamentos(){
        
        try{
            
            DepartamentosController depa = (DepartamentosController)cambiarEscena("DepartamentosView.fxml",830,404);
            depa.setEscenarioPrincipal (this);
        }catch (Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void ventanaProveedores(){
        try{
            ProveedorController proveedor = (ProveedorController)cambiarEscena("ProveedoresView.fxml",1121,404);
            proveedor.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void ventanaCliente(){
        try{
            ClienteController Cliente = (ClienteController)cambiarEscena("ClientesView.fxml",1121,404);
            Cliente.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void ventanaCuentaPorPagar(){
        try{
            CuentaPorPagarController cuentaPagar = (CuentaPorPagarController)cambiarEscena("CuentaPorPagarView.fxml",1144,404);
            cuentaPagar.setEscenarioPrincipal(this);
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
   public void ventanaHorario(){
        
        try{
            HorarioController Horario = (HorarioController)cambiarEscena("HorarioView.fxml",1121,404);
            Horario.setEscenarioPrincipal(this);
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
    }
    
   public void ventanaLogin(){
       
       try{
           LoginController loginController = (LoginController)cambiarEscena("LoginView.fxml",600,400);
                   loginController.setEscenarioPrincipal(this);
       }catch(Exception e){
           e.printStackTrace();
       }
       
   }
   
   public void ventanaUsuario(){
       
       try{
           UsuarioController usuarioController = (UsuarioController)cambiarEscena("UsuarioView.fxml",718,404);
           usuarioController.setEscenarioPrincipal(this);
           
           
       }catch(Exception e){
           e.printStackTrace();
       }
       
       
       
   }
   
   public void ventanaCargo(){
       
       try{
           CargoController cargoController = (CargoController)cambiarEscena("CargoView.fxml",718,404);
           cargoController.setEscenarioPrincipal(this);
           
       }catch (Exception e){
           e.printStackTrace();
       }
       
   }
   
   public void ventanaEmpleado(){
       
       try{
           EmpleadoController empleadoController = (EmpleadoController)cambiarEscena("EmpleadosView.fxml",1324,404);
           empleadoController.setEscenarioPrincipal (this);
           
           
           
       }catch (Exception e){
           e.printStackTrace();
       }
       
       
       
       
   }
   
    public void ventanaCuentaPorCobrar(){
       
       try{
           CuentaPorCobrarController cuentaPorCobrarController = (CuentaPorCobrarController)cambiarEscena("CuentaPorCobrarView.fxml",1239,404);
           cuentaPorCobrarController.setEscenarioPrincipal(this);
           
       }catch (Exception e){
           e.printStackTrace();
       }
       
   }
   
   
   
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    



public Initializable cambiarEscena(String fxml,int ancho,int alto) throws IOException{

Initializable resultado = null;
FXMLLoader cargadorFXML = new FXMLLoader();
InputStream archivo = Principal.class.getResourceAsStream(PAQUETE_VISTA+fxml);
cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
cargadorFXML.setLocation(Principal.class.getResource(PAQUETE_VISTA+fxml));
escena = new Scene((AnchorPane)cargadorFXML.load(archivo),ancho,alto);
escenarioPrincipal.setScene(escena);
escenarioPrincipal.sizeToScene();
resultado = (Initializable)cargadorFXML.getController();
return resultado;
}




}