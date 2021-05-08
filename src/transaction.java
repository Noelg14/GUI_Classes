import java.sql.*;
//import java.util.Scanner;
import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.Dimension;
import java.awt.event.*;
//import javax.swing.SwingUtilities;

public class transaction
{
    long id;
    double gross=0;
    double net=0;
	double tax=0;
	Date d; //will get this automatic in future
    String ref="";
	long createdBy;

	public static void guiT(person a) {
		JFrame l=new JFrame("Create Transaction:");
		final JTextField gross=new JTextField();   //gross field
		final JTextField net=new JTextField();   //net field
		final JTextField ref=new JTextField();   //ref field
		final JButton b=new JButton("Create");//creating instance of JButton  
		final JButton reset=new JButton("Reset");
		final JButton back = new JButton("Back");

		l.setMinimumSize(new Dimension(400,500));
		l.setLocationRelativeTo(null);
		l.setSize(400,500);//400 width and 500 height  
		l.setLayout(null);//using no layout managers  
		l.setVisible(true);//making the frame visible  


        net.setBounds(150,180, 150,20); 
		ref.setBounds(150,210, 150,20);  
        gross.setBounds(150,150,150,20);

		b.setBounds(75,250,100,40);//x axis, y axis, width, height  
		reset.setBounds(200,250,100, 40);//x axis, y axis, width, height  
		back.setBounds(140,300,100,40);
		JLabel t1,t2,t4;
		t1=new JLabel();
		t2=new JLabel();   
		t4= new JLabel();
		t1.setText("Net (€): ");
		t2.setText("Ref: ");
		t4.setText("Gross (€): ");

		t1.setBounds(50,180,150,20);
		t2.setBounds(50,210,150,20);
		t4.setBounds(50,150,150,20);


		l.add(b);l.add(reset);l.add(gross);l.add(net);l.add(ref);l.add(t2);
		l.add(t1);l.add(t4);l.add(back);
		l.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 

		b.addActionListener(new ActionListener(){ // waits for button click takes U&PW passes it into Connection
			public void actionPerformed(ActionEvent e)
			{  
				double g =Double.parseDouble(gross.getText());
				double n =Double.parseDouble(net.getText());
				String r = ref.getText();
				
				createT(g, n, r, a.id);
				gross.setText("");
				net.setText("");
				ref.setText("");
				//login.dispose();
			}
		});
		reset.addActionListener( new ActionListener()
		{  
			public void actionPerformed(ActionEvent r)
			{  
				gross.setText("");
				net.setText("");
				ref.setText("");
			}
		}); 
		back.addActionListener( new ActionListener()
		{  
			public void actionPerformed(ActionEvent r)
			{  
				l.dispose();
				login.chooseType(a);
			}
		}); 


	}
	
	public static void guiGetT(person c){
		JFrame getTrans=new JFrame("Search");
		final JTextField tf=new JTextField();   //Name field
		final JLabel s=new JLabel();
		final JButton b=new JButton("Search");//creating instance of JButton  
		final JButton reset=new JButton("Back");

		getTrans.setSize(400,500);//400 width and 500 height  
		getTrans.setLayout(null);//using no layout managers  
		getTrans.setVisible(true);//making the frame visible  
        tf.setBounds(150,130, 150,20); 
		b.setBounds(75,250,100,40);//x axis, y axis, width, height  
		reset.setBounds(200,250,100, 40);//x axis, y axis, width, height  
		s.setBounds(75,130,75,20);
		getTrans.setLocationRelativeTo(null);

		getTrans.add(tf);getTrans.add(b);getTrans.add(reset);getTrans.add(s);
		getTrans.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
		s.setText("Search");

		b.addActionListener(new ActionListener(){ // waits for button click 
			public void actionPerformed(ActionEvent e)
			{  
				String search =tf.getText();
				if(search.equals("")){
					JOptionPane.showMessageDialog(new JFrame(),"Please enter a reference to search","Error",JOptionPane.ERROR_MESSAGE);
				}
				else{
					try{
						Class.forName("com.mysql.cj.jdbc.Driver"); 
						Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/schema","noel","noel");
						Statement stmt=con.createStatement();  
						ResultSet rs=stmt.executeQuery("Select id,ref from transaction where ref like '"+search+"%' order by 1;");

						while(rs.next()) {
							Long id=rs.getLong(1);
							transaction t = getT(id);
							showT(t);
						}
					}
					catch(Exception s){
						System.out.print(s);
					}
				}
			}
		});

		reset.addActionListener(new ActionListener(){ // waits for button click 
			public void actionPerformed(ActionEvent e)
			{  
				getTrans.dispose();
				login.chooseType(c);
			}
		});



	}

    private static void saveT(transaction t){
		try{
			//Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/schema","noel","noel");
			try{
				String query = " insert into transaction (ID, Date, Gross, Tax, Net,Ref,created_By)"+ " values (?, ?, ?, ?, ?, ?,?)";// Ref
				PreparedStatement preparedStmt = con.prepareStatement(query);
				preparedStmt.setLong(1, t.id);
				preparedStmt.setDate(2, t.d);
				preparedStmt.setDouble (3,t.gross);
				preparedStmt.setDouble(4, t.tax);
				preparedStmt.setDouble(5, t.net);
				preparedStmt.setString(6, t.ref);
				preparedStmt.setLong(7, t.createdBy);
			  
			  // execute the preparedstatement
			  preparedStmt.execute();
			  con.close();
			  
			  System.out.println("\nsaved");
			}
			catch(Exception e){
				System.out.println(e);
			}
        }
		catch(SQLException e){
			System.out.println(e);
		}
		
    }

    private static void createT(double g,double n,String r,long i){

        transaction t= new transaction();
		t.id=(System.currentTimeMillis());
		t.d=new Date(System.currentTimeMillis());
		t.gross=g;
		t.net=n;
		t.ref=r;
		t.tax=t.gross-t.net;
		t.createdBy=i;

		saveT(t);

		JOptionPane.showMessageDialog(new JFrame(),"Created new transaction, ID:"+t.id,"Saved",JOptionPane.INFORMATION_MESSAGE);

	}

	static transaction getT(long id2){ //need to work on this
		transaction t = new transaction();
		try{
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/schema","noel","noel");
			Statement stmt=con.createStatement();  

			ResultSet rs=stmt.executeQuery("Select * from transaction where ID="+id2+" limit 1;");
			if(rs.next()){
				//System.out.println(p.id);
				t.id=id2;
				t.d=rs.getDate(2);
				t.gross=rs.getLong(3);
				t.net= rs.getLong(5);
				t.tax=rs.getLong(4);
				t.ref=rs.getString(6);
			return t;
			}
			else{
				System.out.println("Uh Oh!");
			}
		}
		catch(Exception e){}
		return t;
	}
	
	static void showT(transaction a){
		JFrame showP=new JFrame("Transaction");
		final JLabel gross=new JLabel();   //Name field
		final JLabel net=new JLabel();   //Name field
		final JLabel tax=new JLabel();   //Name field
		final JLabel date=new JLabel();   //Name field
		final JLabel ref=new JLabel();   //Name field
		
		final JButton reset=new JButton("Back");

		showP.setSize(400,500);//400 width and 500 height  
		showP.setLayout(null);//using no layout managers  
		showP.setVisible(true);//making the frame visible  
		showP.setLocationRelativeTo(null);

        gross.setBounds(50,130, 150,20); 
		net.setBounds(50,160, 150,20); 
		tax.setBounds(50,190, 200,20); 
		date.setBounds(50,210,150,20); 
		ref.setBounds(50,100,150,20); 
		

		reset.setBounds(150,260,100, 40);//x axis, y axis, width, height  

		showP.add(gross);showP.add(reset);showP.add(net);showP.add(tax);showP.add(date);showP.add(ref);
		showP.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
		String d = a.d.toString();

		ref.setText("Ref: "+a.ref);
		gross.setText("Gross: "+a.gross);
		net.setText("Net: "+a.net);
		tax.setText("Tax: "+a.tax);
		date.setText("Date: "+d);
		
		reset.addActionListener(new ActionListener(){ // waits for button click 
			public void actionPerformed(ActionEvent e)
			{  
				showP.dispose();

			}
		});
		
	}
}
