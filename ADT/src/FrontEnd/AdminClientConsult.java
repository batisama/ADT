/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FrontEnd;

import Modelo.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataSource;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.UIManager;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableRowSorter;
import org.apache.commons.dbcp.*;
import org.apache.commons.pool.*;

/**
 *
 * @author Pepe
 */
public class AdminClientConsult extends javax.swing.JFrame {

    private TableRowSorter trsFiltro;
    String filter;
    String parameter;
    Admin admin;
    String config[] = {"jdbc:mysql://undercode.com.ar:3306/proyectoADT", "jvidelaolmos", "MoTorp21co"};
    String titulos[] = {"id-cliente", "Fecha de ingreso", "Nombre", "Apellido", "Telefono", "E-mail", "Region", "Estado", "Vendedor", "Origen"};
    String fila[] = new String[10];
    Statement stmt = null;
    BasicDataSource bds = new BasicDataSource();
    int id;
    AdminClientConsult cc;
    String[] dates;

    public AdminClientConsult(BasicDataSource bds, Admin ad) {
        this.admin = ad;
        initComponents();
        this.setTitle("ADT Alpha");
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        this.bds = bds;
        cc = this;
        startTable();
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    System.out.println(table.getValueAt(table.getSelectedRow(), 0).toString());
                    id = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
                    CambioSolicitud cs = new CambioSolicitud(id, cc, admin);
                    cs.setVisible(true);
                }
            }
        });

    }

    public AdminClientConsult(BasicDataSource bds, Admin ad, String desde, String hasta) {
        this.admin = ad;
        initComponents();
        this.setTitle("ADT Alpha");
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        this.bds = bds;
        cc = this;
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    System.out.println(table.getValueAt(table.getSelectedRow(), 0).toString());
                    id = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
                    CambioSolicitud cs = new CambioSolicitud(id, cc, admin);
                    cs.setVisible(true);
                }
            }
        });

        try {
            Connection con = bds.getConnection();
            if (con != null) {
                System.out.println("Se ha establecido una conexion a la base de datos" + "\n" + config[0]);
            }
            stmt = con.createStatement();
            System.out.println(desde);
            System.out.println(hasta);
            ResultSet rs = stmt.executeQuery("Select* from Solicitud where fechaIngreso between '"+desde+"' and '"+hasta+"' ORDER BY fechaIngreso DESC LIMIT 60000");
            String[] solStatus = {"Nuevo", "Reprogramada", "No contesta"};
            DefaultTableModel tableModel = new DefaultTableModel(null, titulos) {

                boolean[] canEdit = new boolean[]{
                    false, false, false, false, false, false, false, false, false, false
                };

                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit[columnIndex];
                }

            };

            RowsRenderer rr = new RowsRenderer(7);
            table.setDefaultRenderer(Object.class, rr);

            while (rs.next()) {

                fila[0] = rs.getString("idSolicitud");
                fila[1] = rs.getString("fechaIngreso");
                fila[2] = rs.getString("nombre");
                fila[3] = rs.getString("apellido");
                fila[4] = rs.getString("telefono");
                fila[5] = rs.getString("email");
                fila[6] = rs.getString("region");
                fila[7] = rs.getString("estado");
                fila[8] = rs.getString("Usuario_idUsuario");
                fila[9] = rs.getString("origen");

                tableModel.addRow(fila);
            }
            table.setModel(tableModel);

            TableColumn ci = table.getColumn("id-cliente");
            ci.setMaxWidth(35);
            TableColumn cf = table.getColumn("Fecha de ingreso");
            cf.setMaxWidth(200);
            TableColumn cn = table.getColumn("Nombre");
            cn.setMaxWidth(150);
            TableColumn ca = table.getColumn("Apellido");
            ca.setMaxWidth(210);
            TableColumn ct = table.getColumn("Telefono");
            ct.setMaxWidth(210);
            TableColumn cem = table.getColumn("E-mail");
            cem.setMaxWidth(210);
            TableColumn cr = table.getColumn("Region");
            cr.setMaxWidth(210);
            TableColumn cst = table.getColumn("Estado");
            cst.setMaxWidth(120);
            TableColumn csl = table.getColumn("Vendedor");
            csl.setMaxWidth(120);
            TableColumn cor = table.getColumn("Origen");
            cor.setMaxWidth(120);
            rs.close();
            stmt.close();
            con.close();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "Error al extraer los datos de la tabla");
        }

    }

    public void startTable() {

        try {
            Connection con = bds.getConnection();
            if (con != null) {
                System.out.println("Se ha establecido una conexion a la base de datos" + "\n" + config[0]);
            }
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT* FROM Solicitud ORDER BY fechaIngreso DESC LIMIT 60000");
            String[] solStatus = {"Nuevo", "Reprogramada", "No contesta"};
            DefaultTableModel tableModel = new DefaultTableModel(null, titulos) {

                boolean[] canEdit = new boolean[]{
                    false, false, false, false, false, false, false, false, false, false
                };

                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit[columnIndex];
                }

            };

            RowsRenderer rr = new RowsRenderer(7);
            table.setDefaultRenderer(Object.class, rr);

            while (rs.next()) {

                fila[0] = rs.getString("idSolicitud");
                fila[1] = rs.getString("fechaIngreso");
                fila[2] = rs.getString("nombre");
                fila[3] = rs.getString("apellido");
                fila[4] = rs.getString("telefono");
                fila[5] = rs.getString("email");
                fila[6] = rs.getString("region");
                fila[7] = rs.getString("estado");
                fila[8] = rs.getString("Usuario_idUsuario");
                fila[9] = rs.getString("origen");

                tableModel.addRow(fila);
            }
            table.setModel(tableModel);

            TableColumn ci = table.getColumn("id-cliente");
            ci.setMaxWidth(35);
            TableColumn cf = table.getColumn("Fecha de ingreso");
            cf.setMaxWidth(200);
            TableColumn cn = table.getColumn("Nombre");
            cn.setMaxWidth(150);
            TableColumn ca = table.getColumn("Apellido");
            ca.setMaxWidth(210);
            TableColumn ct = table.getColumn("Telefono");
            ct.setMaxWidth(210);
            TableColumn cem = table.getColumn("E-mail");
            cem.setMaxWidth(210);
            TableColumn cr = table.getColumn("Region");
            cr.setMaxWidth(210);
            TableColumn cst = table.getColumn("Estado");
            cst.setMaxWidth(120);
            TableColumn csl = table.getColumn("Vendedor");
            csl.setMaxWidth(120);
            TableColumn cor = table.getColumn("Origen");
            cor.setMaxWidth(120);
            rs.close();
            stmt.close();
            con.close();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "Error al extraer los datos de la tabla");
        }

    }

    public void searchTableBy(String filter, String parameter) {

        try {
            Connection con = bds.getConnection();
            if (con != null) {
                System.out.println("Se ha establecido una conexion a la base de datos" + "\n" + config[0]);
            }
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT* FROM Solicitud where " + filter + "= '" + parameter + "' ORDER BY fechaIngreso DESC LIMIT 500 ");
            String[] solStatus = {"Nuevo", "Reprogramada", "No contesta"};
            DefaultTableModel tableModel = new DefaultTableModel(null, titulos) {

                boolean[] canEdit = new boolean[]{
                    false, false, false, false, false, false, false, false, false, false
                };

                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit[columnIndex];
                }

            };

            RowsRenderer rr = new RowsRenderer(7);
            table.setDefaultRenderer(Object.class, rr);

            while (rs.next()) {
                System.out.println(rs.getString("idSolicitud"));
                fila[0] = rs.getString("idSolicitud");
                fila[1] = rs.getString("fechaIngreso");
                fila[2] = rs.getString("nombre");
                fila[3] = rs.getString("apellido");
                fila[4] = rs.getString("telefono");
                fila[5] = rs.getString("email");
                fila[6] = rs.getString("region");
                fila[7] = rs.getString("estado");
                fila[8] = rs.getString("Usuario_idUsuario");
                fila[9] = rs.getString("origen");

                tableModel.addRow(fila);
            }
            table.setModel(tableModel);

            TableColumn ci = table.getColumn("id-cliente");
            ci.setMaxWidth(35);
            TableColumn cf = table.getColumn("Fecha de ingreso");
            cf.setMaxWidth(200);
            TableColumn cn = table.getColumn("Nombre");
            cn.setMaxWidth(150);
            TableColumn ca = table.getColumn("Apellido");
            ca.setMaxWidth(210);
            TableColumn ct = table.getColumn("Telefono");
            ct.setMaxWidth(210);
            TableColumn cem = table.getColumn("E-mail");
            cem.setMaxWidth(210);
            TableColumn cr = table.getColumn("Region");
            cr.setMaxWidth(210);
            TableColumn cst = table.getColumn("Estado");
            cst.setMaxWidth(120);
            TableColumn csl = table.getColumn("Vendedor");
            csl.setMaxWidth(120);
            TableColumn cor = table.getColumn("Origen");
            cor.setMaxWidth(120);
            rs.close();
            stmt.close();
            con.close();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "Error al extraer los datos de la tabla");
        }

    }

    public void filtro() {
        int columnaABuscar = 1;
        switch (cb_filtro.getSelectedItem().toString()) {
            case "Nombre":
                columnaABuscar = 2;
                break;
            case "Apellido":
                columnaABuscar = 3;
                break;
            case "Telefono":
                columnaABuscar = 4;
                break;
            case "E-mail":
                columnaABuscar = 5;
                break;
            case "Region":
                columnaABuscar = 6;
                break;
            case "Estado":
                columnaABuscar = 7;
                break;
            case "Vendedor":
                columnaABuscar = 8;
                break;
            case "Origen":
                columnaABuscar = 9;
                break;
        }
        trsFiltro.setRowFilter(RowFilter.regexFilter(txt_parametroFiltro.getText(), columnaABuscar));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        cb_filtro = new javax.swing.JComboBox<>();
        txt_parametroFiltro = new javax.swing.JTextField();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9", "Title 10"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(table);

        jLabel2.setText("Filtrar por");

        cb_filtro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Fecha", "Nombre", "Apellido", "Telefono", "E-mail", "Region", "Estado", "Vendedor", "Origen" }));
        cb_filtro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_filtroActionPerformed(evt);
            }
        });

        txt_parametroFiltro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_parametroFiltroKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 262, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cb_filtro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_parametroFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(50, 50, 50))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(258, 258, 258))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_parametroFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cb_filtro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
                .addGap(69, 69, 69))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_parametroFiltroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_parametroFiltroKeyTyped
        // TODO add your handling code here:
        txt_parametroFiltro.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (txt_parametroFiltro.getText());
                txt_parametroFiltro.setText(cadena);
                repaint();
                filtro();
            }
        });
        trsFiltro = new TableRowSorter(table.getModel());
        table.setRowSorter(trsFiltro);

    }//GEN-LAST:event_txt_parametroFiltroKeyTyped

    private void cb_filtroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_filtroActionPerformed


    }//GEN-LAST:event_cb_filtroActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cb_filtro;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table;
    private javax.swing.JTextField txt_parametroFiltro;
    // End of variables declaration//GEN-END:variables

}
