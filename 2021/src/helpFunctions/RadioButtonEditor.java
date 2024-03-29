package helpFunctions;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class RadioButtonEditor extends DefaultCellEditor implements ItemListener {
	   private JRadioButton button;
	   public static int selectedRow = 0;
	   
	   public RadioButtonEditor(JCheckBox checkBox) {
	      super(checkBox);
	   }
	   
	   public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		  selectedRow = row;
	      if (value==null) return null;
	      button = (JRadioButton)value;
	      button.addItemListener(this);
	      return (Component)value;
	   }
	   
	   public Object getCellEditorValue() {
	      button.removeItemListener(this);
	      return button;
	   }
	   
	   public void itemStateChanged(ItemEvent e) {
	      super.fireEditingStopped();
	   }
}
