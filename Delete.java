import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

class DeleteFrame extends JFrame
{
    private Container c ;
    private JLabel labRno;
    private JTextField txtRno;
    private JButton btnSave, btnBack;

    DeleteFrame()
    {
        c = getContentPane();
        c.setLayout(null);
        c.setBackground(Color.lightGray);
         Font f = new Font("Arial",Font.BOLD, 30);
        
        labRno = new JLabel("enter rno");
        txtRno = new JTextField(20);
        btnSave = new JButton("Save");
        btnBack = new JButton("Back");
      
        labRno.setFont(f);
        txtRno.setFont(f);
        btnSave.setFont(f);
        btnBack.setFont(f);

        labRno.setBounds(50, 50, 200, 50);
        txtRno.setBounds(250, 50, 200, 50);
        btnSave.setBounds(170, 200, 200, 50);
        btnBack.setBounds(170, 300, 200, 50);

        c.add(labRno);
        c.add(txtRno);
        c.add(btnSave); 
        c.add(btnBack);
       
btnSave.addActionListener(new ActionListener() {
    @Override
            public void actionPerformed(ActionEvent ae) {
             try {
              String rollNoStr = txtRno.getText().trim();
              if (rollNoStr.isEmpty()) {
              throw new IllegalArgumentException("Roll number should not be empty.");
                 }

              int rollNo = Integer.parseInt(rollNoStr);

             if (rollNo <= 0) {
              throw new IllegalArgumentException("Roll number must be greater than zero.");
                    }
    try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms21june23", "root", "abc456")) {
              String sql = "DELETE FROM student WHERE rno=?";
              try (PreparedStatement pst = con.prepareStatement(sql)) {
               pst.setInt(1, rollNo);
                            int rowsAffected = pst.executeUpdate();
     if (rowsAffected > 0)
      {
        JOptionPane.showMessageDialog(c, "Record deleted successfully");
      } else 
      {
         JOptionPane.showMessageDialog(c, "No record found with the given roll number");
       }
       }
       } catch (SQLException e) {
         JOptionPane.showMessageDialog(c, "Database Error: " + e.getMessage());
        }
        } catch (NumberFormatException e) {
       JOptionPane.showMessageDialog(c, "roll no should be integer");
        } catch (IllegalArgumentException e) {
        JOptionPane.showMessageDialog(c, "Error: " + e.getMessage());
        }
        }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
            Home h = new Home();
            dispose();
            }
        });

        setTitle("Delete Frame");
        setSize(650, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
}
}