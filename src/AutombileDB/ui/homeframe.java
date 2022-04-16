package AutombileDB.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import automobileDB.connection.getConnection;
import net.proteanit.sql.DbUtils;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.border.TitledBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class homeframe extends JFrame {

	private JPanel contentPane;
	private JTextField brandField;
	private JTextField modelField;
	private JTextField colorField;
	private JTextField licenceField;
	private JTable table;
	int idKeep;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					homeframe frame = new homeframe();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public homeframe() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1050, 608);
		contentPane = new JPanel();
		contentPane.setForeground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Automobile DataBase");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel.setBounds(365, 28, 286, 52);
		contentPane.add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Registration", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(49, 106, 385, 341);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Brand");
		lblNewLabel_1.setBounds(29, 49, 46, 14);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Model");
		lblNewLabel_2.setBounds(29, 90, 46, 14);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Color");
		lblNewLabel_3.setBounds(29, 130, 46, 14);
		panel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Licence Plate");
		lblNewLabel_4.setBounds(29, 169, 91, 14);
		panel.add(lblNewLabel_4);
		
		brandField = new JTextField();
		brandField.setBounds(159, 46, 169, 20);
		panel.add(brandField);
		brandField.setColumns(10);
		
		modelField = new JTextField();
		modelField.setBounds(159, 87, 169, 20);
		panel.add(modelField);
		modelField.setColumns(10);
		
		colorField = new JTextField();
		colorField.setBounds(159, 127, 169, 20);
		panel.add(colorField);
		colorField.setColumns(10);
		
		licenceField = new JTextField();
		licenceField.setBounds(159, 166, 169, 20);
		panel.add(licenceField);
		licenceField.setColumns(10);
		
		JButton addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addActionListener(e);
				updateActionListener(e);
			}
		});
		addButton.setBounds(31, 292, 89, 23);
		panel.add(addButton);
		
		JButton editButton = new JButton("Edit");
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editActionListener(e);
				updateActionListener(e);
			}
		});
		editButton.setBounds(156, 292, 89, 23);
		panel.add(editButton);
		
		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteActionListener(e);
				updateActionListener(e);
			}
		});
		deleteButton.setBounds(274, 292, 89, 23);
		panel.add(deleteButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(535, 106, 467, 405);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selectActionListener(e);
			}
		});
		scrollPane.setViewportView(table);
		
		JButton updateButton = new JButton("Update Table");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateActionListener(e);
			}
		});
		updateButton.setBounds(709, 522, 122, 23);
		contentPane.add(updateButton);
		
		Statement stmt;
		try {
			stmt = getConnection.createConnection().createStatement();
			ResultSet rs = stmt.executeQuery("select * FROM auto");
		    table.setModel(DbUtils.resultSetToTableModel(rs));
		    rs = stmt.getResultSet();
		    stmt.close(); 
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	private void addActionListener(ActionEvent e)
	{
		String brand =brandField.getText();
		String model =modelField.getText();
		String color =colorField.getText();
		String licenceP =licenceField.getText();
		if(!(brand.equals("")||model.equals("")||color.equals("")||licenceP.equals("")))
		{
			try {
				PreparedStatement ps = getConnection.createConnection().prepareStatement("INSERT INTO auto(`id`,`brand`,`model`,`color`,`licence_plate`) VALUES ((SELECT ifnull( max(id)+1, 1) from (select * from auto) as t),?,?,?,?)");
				ps.setString(1, brand);
				ps.setString(2, model);
				ps.setString(3, color);
				ps.setString(4, licenceP);
				ps.executeUpdate();
				ps.close();
				brandField.setText("");
				modelField.setText("");
				colorField.setText("");
				licenceField.setText("");
				brandField.requestFocus();
				JOptionPane.showMessageDialog(this,"Record added");
				
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
			else
			{
				JOptionPane.showMessageDialog(this,"Fill every field first");
			}
				

	}
	
	private void editActionListener(ActionEvent e)
	{
		String Query="UPDATE auto SET `brand`=?,`model`=?,`color`=?,`licence_plate`=? WHERE ID=?";
		String brand =brandField.getText();
		String model =modelField.getText();
		String color =colorField.getText();
		String licenceP =licenceField.getText();
		if((table.getSelectedRow())!=-1)
		{
			try {
				PreparedStatement ps3=getConnection.createConnection().prepareStatement(Query);
				ps3.setString(1, brand);
				ps3.setString(2, model);
				ps3.setString(3, color);
				ps3.setString(4, licenceP);
				ps3.setInt(5,idKeep);
				ps3.executeUpdate();
				ps3.close();
				brandField.setText("");
				modelField.setText("");
				colorField.setText("");
				licenceField.setText("");
				brandField.requestFocus();
				JOptionPane.showMessageDialog(this,"Record edited");
				
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else
		{
			JOptionPane.showMessageDialog(this,"Select a row first");
		}
		
		
	}
	
	private void selectActionListener(MouseEvent e)
	{
		String Query="SELECT `brand`,`model`,`color`,`licence_plate` FROM auto WHERE ID=?";
		String id = table.getValueAt(table.getSelectedRow(), 0).toString();
		try {
			PreparedStatement ps2=getConnection.createConnection().prepareStatement(Query);
			ps2.setInt(1, Integer.parseInt(id));
			ResultSet rs2 = ps2.executeQuery();
			rs2.next();
			
			String brand=rs2.getString("brand");
			String model=rs2.getString("model");
			String color=rs2.getString("color");
			String licenceP=rs2.getString("licence_plate");
			brandField.setText(brand);
			modelField.setText(model);
			colorField.setText(color);
			licenceField.setText(licenceP);
			idKeep=Integer.parseInt(id);
			
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
	}
	
	private void deleteActionListener(ActionEvent e)
	{
		if((table.getSelectedRow())!=-1)
		{
			try {
				String id = table.getValueAt(table.getSelectedRow(), 0).toString();
	            PreparedStatement ps1;
				try {
					ps1 = getConnection.createConnection().prepareStatement("delete from auto where ID = ?");
					ps1.setInt(1, Integer.parseInt(id));
		            ps1.executeUpdate();
		            ps1.close();
					brandField.setText("");
					modelField.setText("");
					colorField.setText("");
					licenceField.setText("");
					brandField.requestFocus();
					JOptionPane.showMessageDialog(this,"Record deleted");
					
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        } catch (SQLException e1) {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
	        }
		}
		else
		{
			JOptionPane.showMessageDialog(this,"Select a row first");
		}
	}
	
	private void updateActionListener(ActionEvent e)
	{
		Statement stmt;
		try {
			stmt = getConnection.createConnection().createStatement();
			ResultSet rs = stmt.executeQuery("select * FROM auto");
		    table.setModel(DbUtils.resultSetToTableModel(rs));
		    rs = stmt.getResultSet();
		    stmt.close(); 
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
       
	}
	
}
