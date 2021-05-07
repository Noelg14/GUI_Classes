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

		guip.setSize(400,500);//400 width and 500 height  
		guip.setLayout(null);//using no layout managers  
		guip.setVisible(true);//making the frame visible  
        // login.pack(); minises tab 
        lname.setBounds(150,130, 150,20); 
		email.setBounds(150,160, 150,20);  
        fname.setBounds(150,100,150,20);
        pass.setBounds(150,190,150,20);

		b.setBounds(75,250,100,40);//x axis, y axis, width, height  
		reset.setBounds(200,250,100, 40);//x axis, y axis, width, height  

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
		guip.add(pass);guip.add(t1);guip.add(t4);
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

	}
    
    private static void saveP(person p,person a){//person a == logged in user, person p created user. 
		try{
			//Class.forName("com.mysql.cj.jdbc.Driver");  
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
		catch(SQLException e){
			System.out.println(e);
		}
		
    }

	static void createP(String fn,String ln,String em,String pass,person a){ //person a == logged in user

		person p = new person();

		p.id=(System.currentTimeMillis());
		p.dob =new Date(System.currentTimeMillis());
		p.fname=fn;
		p.lname=ln;
		p.email=em;
		p.pw=pass;

		saveP(p,a);

	}

	static person getP(long id2){
		
		person p = new person();
		try{
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/schema","noel","noel");
			Statement stmt=con.createStatement();  

			ResultSet rs=stmt.executeQuery("Select * from testperson where ID="+id2+" limit 1;");
			if(rs.next()){
				//System.out.println(p.id);
				p.id=id2;
				p.fname=rs.getString(2);
				p.lname= rs.getString(3);
			return p;
			}
			else{
				System.out.println("Uh Oh!");
			}
		}
		catch(Exception e){}
		return p;
	}

}
