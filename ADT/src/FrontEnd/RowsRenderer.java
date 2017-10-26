package FrontEnd;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pepe
 */
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class RowsRenderer extends DefaultTableCellRenderer {

    private final int columna;
//    rowState es la columna patron , define con if el color

    public RowsRenderer(int rowState) {
        this.columna = rowState;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused, int row, int column) {

        super.getTableCellRendererComponent(table, value, selected, focused, row, column);

        switch (table.getValueAt(row, columna).toString()) {
            case "Nuevo":
                this.setForeground(Color.GREEN);
                break;
            case "Reprogramada":
                this.setForeground(Color.BLUE);
                break;
            case "No contesta":
                this.setForeground(Color.YELLOW);
                break;
            default:
                this.setForeground(Color.GRAY);
                break;
        }
        return this;
    }
}
