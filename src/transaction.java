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
	long createdBy;

	public static void guiT(long c) {
		JFrame login=new JFrame("Create Transaction:");
		final JTextField gross=new JTextField();   //gross field
		final JTextField net=new JTextField();   //net field
		final JTextField ref=new JTextField();   //ref field
		final JButton b=new JButton("Create");//creating instance of JButton  
		final JButton reset=new JButton("Reset");

		login.setSize(400,500);//400 width and 500 height  
		login.setLayout(null);//using no layout managers  
		login.setVisible(true);//making the frame visible  

        net.setBounds(150,180, 150,20); 
		ref.setBounds(150,210, 150,20);  
        gross.setBounds(150,150,150,20);

		b.setBounds(75,250,100,40);//x axis, y axis, width, height  
		reset.setBounds(200,250,100, 40);//x axis, y axis, width, height  
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


		login.add(b);login.add(reset);login.add(gross);login.add(net);login.add(ref);login.add(t2);
		login.add(t1);login.add(t4);
		login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 

		b.addActionListener(new ActionListener(){ // waits for button click takes U&PW passes it into Connection
			public void actionPerformed(ActionEvent e)
			{  
				double g =Double.parseDouble(gross.getText());
				double n =Double.parseDouble(net.getText());
				String r = ref.getText();
				
				createT(g, n, r, c);
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

    static void createT(double g,double n,String r,long i){

        transaction t= new transaction();
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
	
}
