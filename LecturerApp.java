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
import java.awt.event.ActionEvent;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.awt.Color;

public class LecturerApp {

    private JFrame frame;
    private JTextField idFld;
    private JPasswordField passwdFld;
    private JTable resultTable;
    private DefaultTableModel tableModel;
    private String lecturerId;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LecturerApp window = new LecturerApp();
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
    public LecturerApp() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame("LECTURER APP");
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel_1 = new JLabel("Lecturer ID: ");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNewLabel_1.setBounds(59, 86, 85, 13);
        frame.getContentPane().add(lblNewLabel_1);

        JLabel lblNewLabel_1_1 = new JLabel("Password:");
        lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNewLabel_1_1.setBounds(69, 114, 64, 13);
        frame.getContentPane().add(lblNewLabel_1_1);

        idFld = new JTextField();
        idFld.setBounds(136, 85, 131, 19);
        frame.getContentPane().add(idFld);
        idFld.setColumns(10);

        passwdFld = new JPasswordField();
        passwdFld.setEchoChar('*');
        passwdFld.setColumns(10);
        passwdFld.setBounds(136, 113, 131, 19);
        frame.getContentPane().add(passwdFld);

        JButton btnLogin = new JButton("Check");
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
        btnLogin.setBounds(69, 167, 85, 21);
        frame.getContentPane().add(btnLogin);

        JButton btnCancel = new JButton("Exit");
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                frame.dispose();
            }
        });
        btnCancel.setBounds(182, 167, 85, 21);
        frame.getContentPane().add(btnCancel);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Student ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Email");
        tableModel.addColumn("Course ID");
        tableModel.addColumn("Score");

        resultTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.setBounds(50, 258, 700, 197);
        frame.getContentPane().add(scrollPane);
        
        JLabel lblNewLabel_2 = new JLabel("Student Informations");
        lblNewLabel_2.setFont(new Font("Trebuchet MS", Font.PLAIN, 18));
        lblNewLabel_2.setBounds(300, 226, 174, 13);
        frame.getContentPane().add(lblNewLabel_2);
        
        JPanel panel = new JPanel();
        panel.setBackground(new Color(100, 149, 237));
        panel.setBounds(0, 0, 786, 64);
        frame.getContentPane().add(panel);
        panel.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("MyDAD Grade App");
        lblNewLabel.setBounds(287, 19, 211, 25);
        panel.add(lblNewLabel);
        lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
    }

    private void handleLogin() {
        Runnable run = new Runnable() {
            public void run() {
                String lecturerId = idFld.getText();
                @SuppressWarnings("deprecation")
				String password = passwdFld.getText();

                if (lecturerId.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter both Lecturer ID and password!");
                    return;
                }

                try {
                    String urlString = "http://localhost/dadProject/LecturerWebService.php?action=login&lecturer_id=" + URLEncoder.encode(lecturerId, StandardCharsets.UTF_8.toString()) + "&password=" + URLEncoder.encode(password, StandardCharsets.UTF_8.toString());
                    JSONObject responseJson = makeHttpRequest(urlString);

                    if (responseJson != null && responseJson.getBoolean("success")) {
                        LecturerApp.this.lecturerId = responseJson.getJSONObject("lecturer").getString("lecturer_id");
                        JOptionPane.showMessageDialog(frame, "Login successful!");
		                passwdFld.setText("");
                        fetchStudentsData();
                        
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

    private void fetchStudentsData() {
        Runnable run = new Runnable() {
            public void run() {
                try {
                    String urlString = "http://localhost/dadProject/LecturerWebService.php?action=getStudentsData&lecturerId=" + URLEncoder.encode(lecturerId, StandardCharsets.UTF_8.toString());
                    JSONArray responseJson = makeHttpRequestArray(urlString);

                    if (responseJson != null) {
                        tableModel.setRowCount(0);
                        for (int i = 0; i < responseJson.length(); i++) {
                            JSONObject obj = responseJson.getJSONObject(i);
                            tableModel.addRow(new Object[]{
                                obj.getString("student_id"),
                                obj.getString("name"),
                                obj.getString("email"),
                                obj.getString("course_id"),
                                obj.getString("score")
                            });
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
