package Modelo;
// Generated 19-oct-2017 9:10:41 by Hibernate Tools 4.3.1

import Code.NewHibernateUtil;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JOptionPane;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * Usuario generated by hbm2java
 */
public class Usuario implements java.io.Serializable {
    
    Solicitud solicitud;

    private Integer idUsuario;
    private String nick;
    private String email;
    private String password;
    private Date createTime;
    private String nombre;
    private String apellido;
    private String telefono;
    private String tipo;
    private Set solicituds = new HashSet(0);

    public Usuario() {
    }

    public Usuario(String nombre, String apellido, String nick, String password, String telefono, String email, String tipo) {
        this.nick = nick;
        this.email = email;
        this.password = password;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.tipo = tipo;
    }

    public Usuario(String nick, String password, String tipo) {
        this.nick = nick;
        this.password = password;
        this.tipo = tipo;
    }

    public Usuario(String nick, String email, String password, Date createTime, String nombre, String apellido, String telefono, String tipo, Set solicituds) {
        this.nick = nick;
        this.email = email;
        this.password = password;
        this.createTime = createTime;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.tipo = tipo;
        this.solicituds = solicituds;
    }

    public Integer getIdUsuario() {
        return this.idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNick() {
        return this.nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return this.apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Set getSolicituds() {
        return this.solicituds;
    }

    public void setSolicituds(Set solicituds) {
        this.solicituds = solicituds;
    }

    public void updateSolicitud(Solicitud solicitud) {
        SessionFactory factory = NewHibernateUtil.getSessionFactory();
        System.out.println(factory.isClosed());
        Session session;
        session = factory.openSession();
        Transaction tx = null;
        System.out.println("updateSolicitud");
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea modificar los datos de la solicitud N° " + solicitud.getIdSolicitud() + "?", "Advertencia", JOptionPane.WARNING_MESSAGE);
        if (dialogResult == 0) {
            try {
                tx = session.beginTransaction();
                session.update(solicitud);
                tx.commit();

            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw e;
            }
            session.close();
            JOptionPane.showMessageDialog(null, "Solicitud N°" + solicitud.getIdSolicitud() + " modificada exitosamente!", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }

}
