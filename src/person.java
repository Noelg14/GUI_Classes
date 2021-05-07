import java.sql.*;
//import java.util.Scanner;
import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.event.*;

public class person {
	long id;
    String fname="";
    String lname="";
    Date dob = new Date(System.currentTimeMillis());
    String email="";

	public static void guiP(long id) {
		JFrame login=new JFrame("Create Person:");
		final JTextField fname=new JTextField();   //Name field
		final JTextField lname=new JTextField();   //Lname field
		final JTextField email=new JTextField();   //email field
		final JTextField dob=new JTextField();   //email field
		final JButton b=new JButton("Create");//creating instance of JButton  
		final JButton reset=new JButton("Reset");

		login.setSize(400,500);//400 width and 500 height  
		login.setLayout(null);//using no layout managers  
		login.setVisible(true);//making the frame visible  
        // login.pack(); minises tab 
        lname.setBounds(150,180, 150,20); 
		email.setBounds(150,210, 150,20);  
        fname.setBounds(150,150,150,20);
        //dob.setBounds(250,150,50,20);
		b.setBounds(75,250,100,40);//x axis, y axis, width, height  
		reset.setBounds(200,250,100, 40);//x axis, y axis, width, height  
		JLabel t1,t2,t3,t4;
		t1=new JLabel();
		t2=new JLabel();
		t3=new JLabel();     
		t4= new JLabel();
		t1.setText("Last Name: ");
		t2.setText("Email: ");
		t3.setText("");
		t4.setText("First Name: ");

		t1.setBounds(50,180,150,20);
		t2.setBounds(50,210,150,20);
		t3.setBounds(110,225,150,20);
		t4.setBounds(50,150,150,20);


		login.add(b);login.add(reset);login.add(fname);login.add(lname);login.add(email);login.add(t2);login.add(t3);
		login.add(dob);login.add(t1);login.add(t4);
		login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 

		b.addActionListener(new ActionListener(){ // waits for button click takes U&PW passes it into Connection
			public void actionPerformed(ActionEvent e)
			{  
				String f =fname.getText();
				String l = lname.getText();
				String em = email.getText();
				
				createP(f, l, em);
				fname.setText("");
				lname.setText("");
				email.setText("");
			}
		});
		reset.addActionListener( new ActionListener()
		{  
			public void actionPerformed(ActionEvent r)
			{  
				fname.setText("");
				lname.setText("");
				email.setText("");
			}
		}); 

	}
    

    private static void saveP(person p){
		try{
			//Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/schema","noel","noel");
			try{
				String query = " insert into testperson (id, first_name, last_name, email, DOB)"+ " values (?, ?, ?, ?, ?)";// Ref
				PreparedStatement preparedStmt = con.prepareStatement(query);
				preparedStmt.setLong(1, p.id);
				preparedStmt.setString(2, p.fname);
				preparedStmt.setString (3,p.lname);
				preparedStmt.setString(4, p.email);
				preparedStmt.setDate(5, p.dob);
			  
			  // execute the preparedstatement
			  preparedStmt.execute();
			  con.close();
			  JOptionPane.showMessageDialog(new JFrame(),"Created new person, ID:"+p.id,"Saved",JOptionPane.INFORMATION_MESSAGE);
			  System.out.println("\nsaved");
			  System.exit(0);
			}
			catch(Exception e){
				System.out.println(e);
			}
        }
		catch(SQLException e){
			System.out.println(e);
		}
		
    }

	static void createP(String fn,String ln,String em){

		//Scanner sc = new Scanner(System.in);
		person p = new person();
		/*System.out.print("Please enter a first name: ");
		p.fname=sc.nextLine();
		System.out.print("Please enter a last name: ");
		p.lname=sc.nextLine();
		System.out.print("Please enter an email: ");
		p.email=sc.nextLine(); */
		p.id=(System.currentTimeMillis());
		p.fname=fn;
		p.lname=ln;
		p.email=em;

		saveP(p);

	}

	static person getP(long id2){
		person p = new person();
		try{
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/schema","noel","noel");
			Statement stmt=con.createStatement();  

			ResultSet rs=stmt.executeQuery("Select * from testperson where ID="+id2);
			while(rs.next()){
				p.id=rs.getInt(1);
				p.fname=rs.getString(2);
				p.lname= rs.getString(3);
				System.out.println(p.lname+" "+p.fname+" "+p.id); //works 
			}
			return p;
		}
		catch(Exception e){}
		return p;
	}

}
