/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Code.NewHibernateUtil;
import java.util.Date;
import java.util.Set;
import javax.swing.JOptionPane;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import Code.*;
import org.hibernate.Query;

/**
 *
 * @author Pepe
 */
public class Admin extends Usuario {

    SessionFactory main;
    Usuario user = new Usuario();

    public Admin() {

    }

    public Admin(String nick, String email, String password, Date createTime, String nombre, String apellido, String telefono, String tipo, Set solicituds) {
        super(nick, email, password, createTime, nombre, apellido, telefono, tipo, solicituds);
    }

    public void add(Usuario user) {
        SessionFactory factory = NewHibernateUtil.getSessionFactory();
        System.out.println(factory.isClosed());
        Session session;
        session = factory.openSession();
        Transaction tx = null;
        System.out.println("save");
        try {
            tx = session.beginTransaction();
            session.save(user);
            tx.commit();

        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
        session.close();
        JOptionPane.showMessageDialog(null, "Usuario " + user.getNick() + " ingresado exitosamente!", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    public Usuario search(String parameter) {

        SessionFactory factory = NewHibernateUtil.getSessionFactory();
        Session session;
        session = factory.openSession();
//        Query, con parametro nick particular igual a->formUser
        Query query = session.createQuery("FROM Usuario u WHERE u.nick=:nick");
        query.setParameter("nick", parameter);
//        Extracción de objeto usuario desde la DB (se puede extraer lista con query.list();
        Usuario user = (Usuario) query.uniqueResult();
        session.close();
        return user;
    }

    public void updateUser(Usuario user) {
        SessionFactory factory = NewHibernateUtil.getSessionFactory();
        System.out.println(factory.isClosed());
        Session session;
        session = factory.openSession();
        Transaction tx = null;
        System.out.println("update");
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea modificar los datos del usuario " + user.getNick() + "?", "Advertencia", JOptionPane.WARNING_MESSAGE);
        if (dialogResult == 0) {
            try {
                tx = session.beginTransaction();
                session.update(user);
                tx.commit();

            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw e;
            }
            session.close();
            JOptionPane.showMessageDialog(null, "Usuario " + user.getNick() + " modificado exitosamente!", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void drop(Usuario user) {

        SessionFactory factory = NewHibernateUtil.getSessionFactory();
        System.out.println(factory.isClosed());
        Session session;
        session = factory.openSession();
        Transaction tx = null;
        System.out.println("drop");
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea eliminar el usuario " + user.getNick() + "?", "Advertencia", JOptionPane.WARNING_MESSAGE);
        if (dialogResult == 0) {
            try {
                tx = session.beginTransaction();
                session.delete(user);
                tx.commit();

            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                throw e;
            }
            session.close();
            JOptionPane.showMessageDialog(null, "Usuario " + user.getNick() + " eliminado exitosamente!", "Info", JOptionPane.INFORMATION_MESSAGE);
        }

    }

}
