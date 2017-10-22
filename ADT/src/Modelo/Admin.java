/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.Date;
import java.util.Set;


/**
 *
 * @author Pepe
 */
public class Admin extends Usuario{

    public Admin(String nick, String email, String password, Date createTime, String nombre, String apellido, String telefono, String tipo, Set solicituds) {
        super(nick, email, password, createTime, nombre, apellido, telefono, tipo, solicituds);
    }
  public String nombreCompleto(){
      String name= this.getNombre()+" "+this.getApellido();
        return name;
    
  }
}
