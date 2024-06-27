package project_dad;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class StudentApp {

    private JFrame frame;
    private JTextField idFld;
    private JPasswordField passwordFld;
    private JTable resultTable;
    private DefaultTableModel tableModel;
    protected String student_id;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    StudentApp window = new StudentApp();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public StudentApp() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        
        frame = new JFrame("STUDENT APP");
        frame.setBounds(100, 100, 600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel idLbl = new JLabel("Student ID: ");
        idLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
        idLbl.setBounds(79, 85, 78, 13);
        frame.getContentPane().add(idLbl);

        JLabel pwdLbl = new JLabel("Password:");
        pwdLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
        pwdLbl.setBounds(89, 114, 64, 13);
        frame.getContentPane().add(pwdLbl);

        idFld = new JTextField();
        idFld.setBounds(167, 84, 131, 19);
        frame.getContentPane().add(idFld);
        idFld.setColumns(10);
        
        passwordFld = new JPasswordField();
        passwordFld.setEchoChar('*'); 
        passwordFld.setBounds(167, 113, 131, 20);
        frame.getContentPane().add(passwordFld);

        JButton btnLogin = new JButton("Check");
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
        btnLogin.setBounds(330, 125, 85, 21);
        frame.getContentPane().add(btnLogin);

        JButton btnCancel = new JButton("Exit");
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        btnCancel.setBounds(425, 125, 85, 21);
        frame.getContentPane().add(btnCancel);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Course ID");
        tableModel.addColumn("Course Name");
        tableModel.addColumn("Score");

        resultTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.setBounds(93, 184, 400, 150);
        frame.getContentPane().add(scrollPane);
        
        JLabel lblNewLabel_2 = new JLabel("Course Information");
        lblNewLabel_2.setFont(new Font("Trebuchet MS", Font.PLAIN, 17));
        lblNewLabel_2.setBounds(216, 161, 145, 13);
        frame.getContentPane().add(lblNewLabel_2);
        
        JPanel panel = new JPanel();
        panel.setBackground(new Color(100, 149, 237));
        panel.setBounds(0, 0, 586, 63);
        frame.getContentPane().add(panel);
        panel.setLayout(null);
        
        JLabel appName = new JLabel("MyDAD Grade App");
        appName.setBounds(188, 19, 209, 25);
        panel.add(appName);
        appName.setHorizontalAlignment(SwingConstants.CENTER);
        appName.setFont(new Font("Times New Roman", Font.BOLD, 24));
        
        
    }

    private void handleLogin() {
    	Runnable run = new Runnable() {
    		public void run() {
		        String id = idFld.getText();
		        @SuppressWarnings("deprecation")
				String password = passwordFld.getText();
		        
		        if (id.isEmpty() || password.isEmpty()) {
		            JOptionPane.showMessageDialog(frame, "Please enter both Student ID and password!");
		            return;
		        }
		
		        try {
		            String urlString = "http://localhost/dadProject/StudentWebService.php?action=login&studentId=" + URLEncoder.encode(id, StandardCharsets.UTF_8.toString()) + "&password=" + URLEncoder.encode(password, StandardCharsets.UTF_8.toString());
		            JSONObject responseJson = makeHttpRequest(urlString);
		
		            if (responseJson != null && responseJson.getBoolean("success")) {
		                StudentApp.this.student_id = responseJson.getJSONObject("student").getString("student_id");
		                JOptionPane.showMessageDialog(frame, "Login successful!");
		                passwordFld.setText("");
		                fetchScore();
		                
		            } else {
		                JOptionPane.showMessageDialog(frame, "Invalid login credentials!");
		            }
		        } catch (Exception ex) {
		            ex.printStackTrace();  
		            JOptionPane.showMessageDialog(frame, "Error connecting to server!");
		        }
    		}    
    	};
    	Thread thr1 = new Thread(run);
        thr1.start();
    }

	private JSONObject makeHttpRequest(String url) {
        JSONObject jObj = null;
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);

            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            String json = EntityUtils.toString(httpEntity);
            jObj = new JSONObject(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jObj;
    }
	
	private void fetchScore() {
        Runnable run = new Runnable() {
            public void run() {
                try {
                    String urlString = "http://localhost/dadProject/StudentWebService.php?action=getScoresByStudentId&studentId=" + URLEncoder.encode(student_id, StandardCharsets.UTF_8.toString());
                    JSONArray responseJson = makeHttpRequestArray(urlString);

                    if (responseJson != null && responseJson.length() > 0) {
                        tableModel.setRowCount(0); 
                        for (int i = 0; i < responseJson.length(); i++) {
                            JSONObject obj = responseJson.getJSONObject(i);
                            tableModel.addRow(new Object[]{obj.getString("course_id"), obj.getString("course_name"), obj.getString("score")});
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "No data found!");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error connecting to server!");
                }
            }
        };
        Thread thr1 = new Thread(run);
        thr1.start();
    }
	
	private JSONArray makeHttpRequestArray(String url) {
        JSONArray jArr = null;
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);

            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            String json = EntityUtils.toString(httpEntity);
            jArr = new JSONArray(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jArr;
    }
}

