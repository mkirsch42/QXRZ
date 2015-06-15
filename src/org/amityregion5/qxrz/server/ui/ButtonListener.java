package org.amityregion5.qxrz.server.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;

class ButtonListener extends DefaultCellEditor
{
	protected JButton button;

	private String label;

	private boolean isPushed;

	private int row;

	public ButtonListener(JCheckBox checkBox)
	{
		super(checkBox);
		button = new JButton();
		button.setOpaque(true);
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				fireEditingStopped();
			}
		});
	}

	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int r, int column)
	{
		if (isSelected)
		{
			button.setForeground(table.getSelectionForeground());
			button.setBackground(table.getSelectionBackground());
		} else
		{
			button.setForeground(table.getForeground());
			button.setBackground(table.getBackground());
		}
		label = (value == null) ? "" : value.toString();
		button.setText(label);
		isPushed = true;
		row = r;
		return button;
	}

	public Object getCellEditorValue()
	{
		isPushed = false;
		return new String(label);
	}

	public boolean stopCellEditing()
	{
		isPushed = false;
		return super.stopCellEditing();
	}

	protected void fireEditingStopped()
	{
		super.fireEditingStopped();
	}
}