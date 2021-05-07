import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.event.*;
import java.sql.*;

public class login{
    Boolean success;

    public static void loginGUI(){

        JFrame login=new JFrame("Login to System:");
		final JTextField u=new JTextField();   //Name field
		final JPasswordField p=new JPasswordField();   //Lname field
		final JButton b=new JButton("Login");//creating instance of JButton  
		final JButton reset=new JButton("Reset");

		login.setSize(400,300);//400 width and 500 height  
		login.setLayout(null);//using no layout managers  
		login.setVisible(true);//making the frame visible  
        // login.pack(); minises tab 
        u.setBounds(150,80, 150,20); 
		p.setBounds(150,110,150,20);  
		b.setBounds(75,150,100,40);//x axis, y axis, width, height  
		reset.setBounds(200,150,100, 40);//x axis, y axis, width, height  
		JLabel t1,t2;
		t1=new JLabel();
		t2=new JLabel();
		t1.setText("Username/Email: ");
		t2.setText("Password: ");

		t1.setBounds(50,80,150,20);
		t2.setBounds(50,110,150,20);


		login.add(b);login.add(reset);login.add(u);login.add(p);login.add(t2);login.add(t1);
		login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        b.addActionListener(new ActionListener(){ // waits for button click takes U&PW passes it into Connection
			public void actionPerformed(ActionEvent e)
			{  
                String us = u.getText();
                String pa = p.getText();

                if(us.equals(null) || pa.equals(null)){
                    JOptionPane.showMessageDialog(new JFrame(),"Please enter a username & Password","Error",JOptionPane.ERROR_MESSAGE);
                }
                else{
					try {
						Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/schema","noel","noel");
						Statement stmt=con.createStatement();  
						ResultSet rs=stmt.executeQuery("Select * from testperson;"); 
						//ResultSetMetaData rsmd = rs.getMetaData();
						while(rs.next()){
							if(rs.getString(2).toLowerCase().equals(us) && rs.getString(2).toLowerCase().equals(pa)){ //uses First name (2) and First name (2)
								System.out.print("i passed");
								JOptionPane.showMessageDialog(new JFrame(),"Login Successful, User ID:"+rs.getInt(1),"Error",JOptionPane.INFORMATION_MESSAGE);
								transaction.guiT();
								login.dispose();
								//break;
								con.close();
							}
							else{
								System.out.print("i failed");
							}
						}
						JOptionPane.showMessageDialog(new JFrame(),"Login Failed. Try Again!","Error",JOptionPane.INFORMATION_MESSAGE);
					} 
					catch (Exception s) {
						//JOptionPane.showMessageDialog(new JFrame(),s,"Error",JOptionPane.ERROR_MESSAGE);
					}
                }
            }
        }); 
        reset.addActionListener(new ActionListener(){ // waits for button click takes U&PW passes it into Connection
			public void actionPerformed(ActionEvent e)
			{  
                u.setText("");
                p.setText("");
            }
        });

    }
}