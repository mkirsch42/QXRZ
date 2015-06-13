package org.amityregion5.qxrz.server.ui;

import java.net.InetAddress;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import org.amityregion5.qxrz.common.net.NetworkNode;
import org.amityregion5.qxrz.server.Game;
import org.amityregion5.qxrz.server.net.ServerNetworkManager;

public class MainGui extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTable table;

	private ServerNetworkManager manager;

	private final String removeName = "(Remove)";

	private DefaultTableModel dm;

	public MainGui(ServerNetworkManager _manager, Game g) throws Exception
	{
		super("QXRZ");
		manager = _manager;

		setSize(400, 400);
		setResizable(false);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(null);

		JLabel iplbl = new JLabel("Host Name and IP: "
				+ InetAddress.getLocalHost().toString());
		JLabel namelbl = new JLabel("Server Name: " + manager.getServerName());

		namelbl.setLocation(10, 10);
		namelbl.setSize(getWidth(), 15);

		iplbl.setLocation(10, 30);
		iplbl.setSize(getWidth(), 15);

		mainPanel.add(iplbl);
		mainPanel.add(namelbl);

		dm = new DefaultTableModel(new Object[] { "Name", "IP", removeName }, 0);

		dm.addTableModelListener(new TableModelListener()
		{

			@Override
			public void tableChanged(TableModelEvent e)
			{
				if (e.getType() == TableModelEvent.UPDATE)
				{
					dm.removeRow(e.getFirstRow());
					manager.removeClient(e.getFirstRow());
				}

			}
		});
		table = new JTable(dm)
		{

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column)
			{
				return column == table.getColumn(removeName).getModelIndex();
			}

		};
		table.setFocusable(false);
		table.setRowSelectionAllowed(false);

		table.getColumn(removeName).setCellRenderer(new ButtonRenderer());
		table.getColumn(removeName).setCellEditor(
				new ButtonListener(new JCheckBox()));

		JScrollPane scroll = new JScrollPane(table);
		scroll.setLocation(10, 60);
		scroll.setSize(getWidth() - 25, getHeight() - 100);

		add(scroll);

		add(mainPanel);

		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	public void addClient(NetworkNode c)
	{
		dm.addRow(new String[] { c.getName(), c.getSocketAddress().getHostName(),
				"X" });
	}

}
