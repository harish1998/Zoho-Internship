import java.util.ArrayList;
import java.io.File;
import java.awt.*;
import java.awt.event.*;
import java.awt.Dialog; 
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class App extends Frame {
	
	private Panel p;
	private Frame f;
	private Label l1 = new Label();
	Button addBtn, viewBtn, updateBtn, exitBtn;
	TextField id, name, dept, phone, newPhone;
	Label lid, lname, ldept, lphone, lnphone;
	
	public App() {
		
		f = new Frame("Employees Management"); 
		p = new Panel(new FlowLayout(FlowLayout.CENTER, 100, 300)); 
						
		addBtn = new Button("Add");
		addBtn.setPreferredSize(new Dimension(100, 100));
		addBtn.setFont(new Font("Sans Serif", Font.BOLD, 22));
		
		addBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
					
					Dialog d = new Dialog(App.this, "Add Employee");
					GridLayout g = new GridLayout(5, 2);
					g.setHgap(30);
					g.setVgap(50);
					d.setLayout(g);
					
					id = new TextField(10);
					lid = new Label("Employee ID");
					lid.setFont(new Font("Sans Serif", Font.BOLD, 14));
					lid.setBounds(70,90,90,60);
					id.setBounds(200,120,300,20);
					d.add(lid);
					d.add(id);
					
					name = new TextField(20);
					lname = new Label("Employee Name");
					lname.setFont(new Font("Sans Serif", Font.BOLD, 14));
					lname.setBounds(100,100,130,60);
					name.setBounds(200,120,300,20);
					d.add(lname);
					d.add(name);
					
					dept = new TextField(20);
					ldept = new Label("Employee Department");
					ldept.setFont(new Font("Sans Serif", Font.BOLD, 14));
					ldept.setBounds(100,100,130,60);
					dept.setBounds(200,120,300,20);
					d.add(ldept);
					d.add(dept);
					
					phone = new TextField(10);
					lphone = new Label("Employee Phone Number");
					lphone.setFont(new Font("Sans Serif", Font.BOLD, 14));
					lphone.setBounds(100,100,130,60);
					phone.setBounds(200,120,300,20);
					d.add(lphone);
					d.add(phone);
					
					d.pack();
					
					d.setLayout(new BorderLayout());
					Panel pl = new Panel();
					Button submit = new Button("Submit");
					submit.setFont(new Font("Sans Serif", Font.BOLD, 16));
					pl.add(submit);
					pl.setSize(400, 100);
					d.add(pl, BorderLayout.SOUTH);
					
					d.setVisible(true);
					d.setLocation(600, 400);
					d.setSize(900, 600);
					submit.addActionListener(new SubmitHandler());
				}
			}
		);
		
		p.add(addBtn);
		
		viewBtn = new Button("View");
		viewBtn.setPreferredSize(new Dimension(100, 100));
		viewBtn.setFont(new Font("Sans Serif", Font.BOLD, 22));
		viewBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
						
						Dialog d = new Dialog(App.this, "View Records");
						JTable table = new JTable();
						final Class[] columnClass = new Class[] {
							Long.class, String.class, String.class, Long.class
						};
						DefaultTableModel dtm = new DefaultTableModel(new String[] { "ID", "Name", "Department", "Phone Number"}, 0) {
							@Override
							public boolean isCellEditable(int row, int column)
							{
								return false;
							}
							@Override
							public Class<?> getColumnClass(int columnIndex)
							{
								return columnClass[columnIndex];
							}
						};
						table.setModel(dtm);
						ArrayList<String> fl = Main.getEmployeeRecordFileNamesFromBaseFolder();
						if(!fl.isEmpty()) {
							for(int i = 0; i < fl.size(); ++i) {
								 try {
									 ArrayList<Employee> a = Main.read(new File(fl.get(i)));
									  for (Employee emp : a) {
										  dtm.addRow(new Object[]{emp.getEmployeeID(), emp.getEmployeeName(), emp.getEmployeeDepartment(), emp.getEmployeePhoneNumber()});
									  }
								 }
								 catch(Exception e) {e.printStackTrace();}
							}
						}
						Panel pl = new Panel();
						Button ok = new Button("OK");
						pl.add(ok);
						ok.setFont(new Font("Sans Serif", Font.BOLD, 22));
						d.add(new JScrollPane(table));
						d.add(pl, BorderLayout.SOUTH);
						ok.addActionListener(new DefaultClose());
						d.pack();
						d.setLocation(300, 400);
						d.setSize(900, 600);
						d.setVisible(true);
						d.setLayout(new BorderLayout());
			}
		});
		
		p.add(viewBtn);
		
		updateBtn = new Button("Update");
		updateBtn.setPreferredSize(new Dimension(100, 100));
		updateBtn.setFont(new Font("Sans Serif", Font.BOLD, 22));
		updateBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				
						Dialog d = new Dialog(App.this, "Update Employee");				
						GridLayout g = new GridLayout(2, 2);
						g.setHgap(30);
						g.setVgap(50);
						d.setLayout(g);
						id = new TextField(10);
						lid = new Label("Employee ID");
						lid.setFont(new Font("Sans Serif", Font.BOLD, 14));
						lid.setBounds(70,90,90,60);
						id.setBounds(200,120,300,20);
						d.add(lid);
						d.add(id);
						newPhone = new TextField(20);
						lnphone = new Label("Employee New Phone Number");
						lnphone.setFont(new Font("Sans Serif", Font.BOLD, 14));
						lnphone.setBounds(100,100,130,60);
						newPhone.setBounds(200,120,300,20);
						d.add(lnphone);
						d.add(newPhone);
						d.pack();
						d.setLayout(new BorderLayout());
						Panel pl = new Panel();
						Button update = new Button("Update");
						pl.add(update);
						update.setFont(new Font("Sans Serif", Font.BOLD, 16));
						d.add(pl, BorderLayout.SOUTH);
						update.addActionListener(new UpdateHandler());
						d.setVisible(true);
						d.setLocation(600, 400);
						d.setSize(900, 600);
			}
		});
		
		p.add(updateBtn);
		
		exitBtn = new Button("Exit");
		exitBtn.setPreferredSize(new Dimension(100, 100));
		exitBtn.setFont(new Font("Sans Serif", Font.BOLD, 22));
		exitBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				f.setVisible(false);
				System.exit(0);
			}
		});
		
		p.add(exitBtn) ;
		
		f.setSize(900, 700);
		f.setLocation(500, 100);
		f.add(p);
		f.setVisible(true);
	}
	
	private class DefaultClose implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setVisible(false);
		}
	}
	
	private class SubmitHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Employee emp = new Employee(Long.parseLong(id.getText()), name.getText(), dept.getText(), Long.parseLong(phone.getText()));
			try {
				Main.add(emp);
			}
			catch(Exception ex) {ex.printStackTrace();return ;}
			setVisible(false);
		}
	}
	
	private class UpdateHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			boolean flag = true;
			try {
				flag = Main.update(Long.parseLong(id.getText()), Long.parseLong(newPhone.getText()));
				if (flag == false) {
						Dialog d = new Dialog(App.this, "Update Error");
						Label l = new Label("Sorry, Employee ID does not exists", Label.CENTER);
						l.setFont(new Font("Sans Serif", Font.BOLD, 16));
						l.setLocation(100, 100);
						l.setAlignment(Label.CENTER);
						l.setForeground(Color.RED);
						d.add(l);
						d.pack();
						Button b = new Button("OK");
						b.setPreferredSize(new Dimension(30, 30));
						b.setFont(new Font("Sans Serif", Font.BOLD, 16));
						d.setLayout(new BorderLayout());
						d.add(b,BorderLayout.SOUTH);
						d.setVisible(true);
						d.setLocation(700, 400);
						d.setSize(600, 400);
						b.addActionListener(new DefaultClose());
				}
				else 
					setVisible(false);
			}
			catch(Exception ex) {ex.printStackTrace();return ;}
		}
	}
	
    public static void main(String args[]) {
		new App();
		return ;
    }
}