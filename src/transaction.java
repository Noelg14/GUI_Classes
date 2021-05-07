import java.sql.*;
//import java.util.Scanner;
import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.event.*;

public class transaction
{
    long id= (System.currentTimeMillis());
    double gross=0;
    double net=0;
	double tax=0;
	Date d = new Date(System.currentTimeMillis()); //will get this automatic in future
    String ref="";

	public static void guiT() {
		JFrame login=new JFrame("Create Person:");
		final JTextField gross=new JTextField();   //Name field
		final JTextField net=new JTextField();   //Lname field
		final JTextField ref=new JTextField();   //email field
		final JTextField dob=new JTextField();   //email field
		final JButton b=new JButton("Create");//creating instance of JButton  
		final JButton reset=new JButton("Reset");

		login.setSize(400,500);//400 width and 500 height  
		login.setLayout(null);//using no layout managers  
		login.setVisible(true);//making the frame visible  
        // login.pack(); minises tab 
        net.setBounds(150,180, 150,20); 
		ref.setBounds(150,210, 150,20);  
        gross.setBounds(150,150,150,20);
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


		login.add(b);login.add(reset);login.add(gross);login.add(net);login.add(ref);login.add(t2);login.add(t3);
		login.add(dob);login.add(t1);login.add(t4);
		login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 

		b.addActionListener(new ActionListener(){ // waits for button click takes U&PW passes it into Connection
			public void actionPerformed(ActionEvent e)
			{  
				double g =Double.parseDouble(gross.getText());
				double n =Double.parseDouble(net.getText());
				String r = ref.getText();
				
				createT(g, n, r);
				gross.setText("");
				net.setText("");
				ref.setText("");
				login.dispose();
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

	}
    private static void saveT(transaction t){
		try{
			//Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/schema","noel","noel");
			try{
				String query = " insert into transaction (ID, Date, Gross, Tax, Net,Ref)"+ " values (?, ?, ?, ?, ?, ?)";// Ref
				PreparedStatement preparedStmt = con.prepareStatement(query);
				preparedStmt.setLong(1, t.id);
				preparedStmt.setDate(2, t.d);
				preparedStmt.setDouble (3,t.gross);
				preparedStmt.setDouble(4, t.tax);
				preparedStmt.setDouble(5, t.net);
				preparedStmt.setString(6, t.ref);
			  
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

    static void createT(double g,double n,String r){
		//Scanner sc = new Scanner(System.in);
        transaction t= new transaction();
		t.gross=g;
		t.net=n;
		t.ref=r;
		t.tax=t.gross-t.net;
		
		//System.out.print("Please enter a reference no: ");
		//t.ref=sc.nextLine();
		//t.d= new Date(2021,05,06);
		System.out.println("ID:" +t.id +" Gross:"+t.gross+" Date:"+t.d+" Tax:"+t.tax+" Ref:"+t.ref);
		saveT(t);

		JOptionPane.showMessageDialog(new JFrame(),"Created new transaction, ID:"+t.id,"Saved",JOptionPane.INFORMATION_MESSAGE);
		person.guiP();
	}

}
