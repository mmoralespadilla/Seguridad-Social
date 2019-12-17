package ftpCliente;

import java.awt.Component;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class BorderTableCellRenderer extends JLabel implements TableCellRenderer {
	public BorderTableCellRenderer() {
		super();
		this.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		setForeground(table.getForeground());
		setFont(table.getFont());
		setValue(value);
		return this;
	}

	public void updateUI() {
		super.updateUI();
		setForeground(null);
	}

	public void validate() {
	}

	public void revalidate() {
	}

	public void repaint(long tm, int x, int y, int width, int height) {
	}

	public void repaint(Rectangle r) {
	}

	public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {
	}

	protected void setValue(Object value) {
		setText((value == null) ? "" : value.toString());
	}

	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
// Strings get interned...
		if (propertyName == "text") {
			super.firePropertyChange(propertyName, oldValue, newValue);
		}
	}
}
