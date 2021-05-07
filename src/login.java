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

                if(us.equals("") || pa.equals("")){
                    JOptionPane.showMessageDialog(new JFrame(),"Please enter a username & Password","Error",JOptionPane.ERROR_MESSAGE);
                }
                else{
					try {
						Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/schema","noel","noel");
						Statement stmt=con.createStatement();  
						ResultSet rs=stmt.executeQuery("Select id,first_name,password from testperson order by 1;"); 
						//ResultSetMetaData rsmd = rs.getMetaData();
						//System.out.println(rsmd.getColumnCount()); // prints 3


						while(rs.next()){
							String checkuser=rs.getString(2);
							String checkpass=rs.getString(3);
							/*
								System.out.println(checkuser +" "+ checkpass);
								System.out.println(checkuser.equals(us) && checkpass.equals(pa));
							*/

							if(checkuser.equals(us) && checkpass.equals(pa))
							{ //uses First name (2) and password (3)
								
								long id=rs.getLong(1);//User ID
								//System.out.println(id);
								JOptionPane.showMessageDialog(new JFrame(),"Login Successful, User ID:"+id,"Error",JOptionPane.INFORMATION_MESSAGE);
								person p = person.getP(id); 
								chooseType(p);
								login.dispose();
								con.close();
								checkuser="";
								checkpass="";
								break;
							}
							else {
								//System.out.println("failed");
								checkuser="";
								checkpass="";
							}
						}
						//JOptionPane.showMessageDialog(new JFrame(),"Login Failed. Try Again!","Error",JOptionPane.INFORMATION_MESSAGE);
						con.close();
					} 
					catch (Exception s) {
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

	public static void chooseType(person a){
		JFrame choose=new JFrame("Logged in as "+a.fname+" "+a.lname); //
		final JButton createT=new JButton("Create Transaction");//creating instance of JButton  
		final JButton createP=new JButton("Create Person");
		final JButton getT=new JButton("Get Transaction");//creating instance of JButton  
		final JButton getP=new JButton("Get Person");

		choose.setSize(500,400);//width, height 
		choose.setLayout(null);//using no layout managers  
		choose.setVisible(true);//making the frame visible  
		createT.setBounds(70,50,150,50);//x axis, y axis, width, height  
		createP.setBounds(280,50,150, 50);//x axis, y axis, width, height 
		getT.setBounds(70,150,150,50);//x axis, y axis, width, height  
		getP.setBounds(280,150,150, 50);//x axis, y axis, width, height 

		choose.add(createT);choose.add(createP);choose.add(getT);choose.add(getP);
		choose.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		createP.addActionListener(new ActionListener(){ // waits for button click takes U&PW passes it into Connection
			public void actionPerformed(ActionEvent e)
			{  
				person.guiP(a);
				choose.dispose();
			}
		});
		createT.addActionListener(new ActionListener(){ // waits for button click takes U&PW passes it into Connection
			public void actionPerformed(ActionEvent e)
			{  
				transaction.guiT(a.id);
				choose.dispose();
			}
		});
		getT.addActionListener(new ActionListener(){ // waits for button click takes U&PW passes it into Connection
			public void actionPerformed(ActionEvent e)
			{  
				//TODO Add stuff here
			}
		});
		getP.addActionListener(new ActionListener(){ // waits for button click takes U&PW passes it into Connection
			public void actionPerformed(ActionEvent e)
			{  
				//TODO Add stuff here
			}
		});

	}

}