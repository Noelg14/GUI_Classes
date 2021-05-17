import java.sql.*;
//import java.util.Scanner;
import javax.swing.*;
import javax.swing.JOptionPane;


import java.awt.event.*;

public class person {
	long id;
    String fname="";
    String lname="";
    Date dob;
    String email="";
	private String pw;

	public static void guiP(person a) { //person a == logged in user
		JFrame guip=new JFrame("Create Person:");
		final JTextField fname=new JTextField();   //Name field
		final JTextField lname=new JTextField();   //Lname field
		final JTextField email=new JTextField();   //email field
		final JPasswordField pass=new JPasswordField();   //pw field
		final JButton b=new JButton("Create");//creating instance of JButton  
		final JButton reset=new JButton("Reset");
		final JButton back = new JButton("Back");

		guip.setSize(400,500);//400 width and 500 height  
		guip.setLayout(null);//using no layout managers  
		guip.setVisible(true);//making the frame visible  
        // login.pack(); minises tab 
        lname.setBounds(150,130, 150,20); 
		email.setBounds(150,160, 150,20);  
        fname.setBounds(150,100,150,20);
        pass.setBounds(150,190,150,20);
		guip.setLocationRelativeTo(null);

		b.setBounds(75,250,100,40);//x axis, y axis, width, height  
		reset.setBounds(200,250,100, 40);//x axis, y axis, width, height  
		back.setBounds(140,300,100,40);

		JLabel t1,t2,t3,t4;
		t1=new JLabel();
		t2=new JLabel();
		t3=new JLabel();     
		t4= new JLabel();
		t1.setText("Last Name: ");
		t2.setText("Email: ");
		t3.setText("Password");
		t4.setText("First Name: ");

		t1.setBounds(50,130,150,20);
		t2.setBounds(50,160,150,20);
		t3.setBounds(50,190,150,20);
		t4.setBounds(50,100,150,20);


		guip.add(b);guip.add(reset);guip.add(fname);guip.add(lname);guip.add(email);guip.add(t2);guip.add(t3);
		guip.add(pass);guip.add(t1);guip.add(t4);guip.add(back);
		guip.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 

		b.addActionListener(new ActionListener(){ // waits for button click takes U&PW passes it into Connection
			public void actionPerformed(ActionEvent e)
			{  
				String f =fname.getText();
				String l = lname.getText();
				String em = email.getText();
				String pw = pass.getText();
				
				createP(f, l, em, pw,a);
				fname.setText("");
				lname.setText("");
				email.setText("");
				guip.dispose();
			}
		});
		reset.addActionListener( new ActionListener()
		{  
			public void actionPerformed(ActionEvent r)
			{  
				fname.setText("");
				lname.setText("");
				email.setText("");
				pass.setText("");
			}
		}); 
		back.addActionListener( new ActionListener()
		{  
			public void actionPerformed(ActionEvent r)
			{  
				guip.dispose();
				login.chooseType(a);
			}
		}); 

	}
  
	public static void guiGetP(person a){
		JFrame getPerson=new JFrame("Search");
		final JTextField tf=new JTextField();   //Name field
		final JLabel s=new JLabel();
		final JButton b=new JButton("Search");//creating instance of JButton  
		final JButton reset=new JButton("Back");

		getPerson.setSize(400,500);//400 width and 500 height  
		getPerson.setLayout(null);//using no layout managers  
		getPerson.setVisible(true);//making the frame visible  
        tf.setBounds(150,130, 150,20); 
		b.setBounds(75,250,100,40);//x axis, y axis, width, height  
		reset.setBounds(200,250,100, 40);//x axis, y axis, width, height  
		s.setBounds(75,130,75,20);
		getPerson.setLocationRelativeTo(null);

		getPerson.add(tf);getPerson.add(b);getPerson.add(reset);getPerson.add(s);
		getPerson.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		s.setText("Search");

		b.addActionListener(new ActionListener(){ // waits for button click 
			public void actionPerformed(ActionEvent e)
			{  
				String search =tf.getText();
				search.trim();
				if(search.equals("")){
					JOptionPane.showMessageDialog(new JFrame(),"Please enter a username to search","Error",JOptionPane.ERROR_MESSAGE);
				}
				else{
					try{
						Class.forName("com.mysql.cj.jdbc.Driver"); 
						Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/schema","noel","noel");
						Statement stmt=con.createStatement();  
						ResultSet rs=stmt.executeQuery("Select id,first_name from testperson where first_name like '"+search+"%' order by 1;");

						while(rs.next()) {
							Long id=rs.getLong(1);
							person b = getP(id);
							showP(b);

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
				getPerson.dispose();
				login.chooseType(a);
			}
		});



	}

    private static void saveP(person p,person a){//person a == logged in user, person p created user. 
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/schema","noel","noel");
			try{
				String query = " insert into testperson (id, first_name, last_name, email, DOB,password)"+ " values (?, ?, ?, ?, ?,?)";// Ref
				PreparedStatement preparedStmt = con.prepareStatement(query);
				preparedStmt.setLong(1, p.id);
				preparedStmt.setString(2, p.fname);
				preparedStmt.setString (3,p.lname);
				preparedStmt.setString(4, p.email);
				preparedStmt.setDate(5, p.dob);
				preparedStmt.setString(6,p.pw);
			  
			  // execute the prepared statement
			  preparedStmt.execute();
			  con.close();
			  JOptionPane.showMessageDialog(new JFrame(),"Created new person, ID:"+p.id,"Saved",JOptionPane.INFORMATION_MESSAGE);
			  System.out.println("\nsaved");
			  login.chooseType(a);
			}
			catch(Exception e){
				System.out.println(e);
			}
        }
		catch(SQLException | ClassNotFoundException c){
			System.out.println(c);
		}
		
    }

	private static void createP(String fn,String ln,String em,String pass,person a){ //person a == logged in user

		person p = new person();

		p.id=(System.currentTimeMillis());
		p.dob =new Date(System.currentTimeMillis());
		p.fname=fn;
		p.lname=ln;
		p.email=em;
		p.pw=pass;

		saveP(p,a);

	}

	static person getP(long id2) throws SQLException,ClassNotFoundException{
		
		person p = new person();
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/schema","noel","noel");
			Statement stmt=con.createStatement();  
			ResultSet rs=stmt.executeQuery("Select * from testperson where ID="+id2+" limit 1;");
			if(rs.next()){
				//System.out.println(p.id);
				p.id=id2;
				p.fname=rs.getString(2);
				p.lname= rs.getString(3);
				p.email=rs.getString(4);
				p.dob=rs.getDate(5);
			return p;
			}
			else{
				System.out.println("Uh Oh!");
				return null;
			}

	}
	
	static void showP(person a){
		JFrame showP=new JFrame("Person");
		final JLabel fname=new JLabel();   //Name field
		final JLabel lname=new JLabel();   //Name field
		final JLabel email=new JLabel();   //Name field
		final JLabel id=new JLabel();   //Name field
		final JLabel dob=new JLabel();   //Name field
		
		final JButton reset=new JButton("Back");
		showP.setLocationRelativeTo(null);

		showP.setSize(400,500);//400 width and 500 height  
		showP.setLayout(null);//using no layout managers  
		showP.setVisible(true);//making the frame visible  
        fname.setBounds(50,130, 150,20); 
		lname.setBounds(50,160, 150,20); 
		email.setBounds(50,190, 200,20); 
		dob.setBounds(50,210,150,20); 
		id.setBounds(50,100,150,20); 

		reset.setBounds(150,260,100, 40);//x axis, y axis, width, height  

		showP.add(fname);showP.add(reset);showP.add(lname);showP.add(id);showP.add(email);showP.add(dob);
		showP.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		String d = a.dob.toString();

		fname.setText("First Name: "+a.fname);
		lname.setText("Last Name: "+a.lname);
		email.setText("Email :"+a.email);
		id.setText("ID: "+a.id);
		dob.setText("DOB: "+d);
		
		reset.addActionListener(new ActionListener(){ // waits for button click 
			public void actionPerformed(ActionEvent e)
			{  
				showP.dispose();

			}
		});
		
	}
	
}

