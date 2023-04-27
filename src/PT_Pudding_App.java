import java.sql.*;
import javax.swing.*;
import java.awt.event.*;

public class PT_Pudding_App extends JFrame implements ActionListener {

    // Mendefinisikan komponen GUI
    JLabel lblKodeMenu, lblNamaMenu, lblHargaMenu, lblStokMenu;
    JTextField txtKodeMenu, txtNamaMenu, txtHargaMenu, txtStokMenu;
    JButton btnInsert, btnView, btnUpdate, btnDelete, btnReset;

    // Mendefinisikan variabel koneksi database
    Connection con;
    Statement stmt;
    ResultSet rs;

    public PT_Pudding_App() {
        // Membuat koneksi database
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/pt_pudding", "root", "");
            stmt = con.createStatement();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }

        // Mengatur tampilan GUI
        setTitle("PT Pudding App");
        setSize(400, 300);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        lblKodeMenu = new JLabel("Kode Menu");
        lblNamaMenu = new JLabel("Nama Menu");
        lblHargaMenu = new JLabel("Harga Menu");
        lblStokMenu = new JLabel("Stok Menu");

        txtKodeMenu = new JTextField();
        txtNamaMenu = new JTextField();
        txtHargaMenu = new JTextField();
        txtStokMenu = new JTextField();

        btnInsert = new JButton("Insert");
        btnView = new JButton("View");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnReset = new JButton("Reset");

        lblKodeMenu.setBounds(20, 20, 100, 20);
        lblNamaMenu.setBounds(20, 50, 100, 20);
        lblHargaMenu.setBounds(20, 80, 100, 20);
        lblStokMenu.setBounds(20, 110, 100, 20);

        txtKodeMenu.setBounds(130, 20, 100, 20);
        txtNamaMenu.setBounds(130, 50, 100, 20);
        txtHargaMenu.setBounds(130, 80, 100, 20);
        txtStokMenu.setBounds(130, 110, 100, 20);

        btnInsert.setBounds(250, 20, 100, 20);
        btnView.setBounds(250, 50, 100, 20);
        btnUpdate.setBounds(250, 80, 100, 20);
        btnDelete.setBounds(250, 110, 100, 20);
        btnReset.setBounds(20, 140, 330, 20);

        btnInsert.addActionListener(this);
        btnView.addActionListener(this);
        btnUpdate.addActionListener(this);
        btnDelete.addActionListener(this);
        btnReset.addActionListener(this);

        add(lblKodeMenu);
        add(lblNamaMenu);
        add(lblHargaMenu);
        add(lblStokMenu);
        add(txtKodeMenu);
        add(txtNamaMenu);
        add(txtHargaMenu);
        add(txtStokMenu);
        add(btnInsert);
        add(btnView);
        add(btnUpdate);
        add(btnDelete);
        add(btnReset);
        setVisible(true);
    }

    // Implementasi ActionListener
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnInsert) {
            try {
                // Mengambil nilai dari inputan
                String kodeMenu = txtKodeMenu.getText();
                String namaMenu = txtNamaMenu.getText();
                int hargaMenu = Integer.parseInt(txtHargaMenu.getText());
                int stokMenu = Integer.parseInt(txtStokMenu.getText());

                // Menambah data ke dalam database
                String sql = "INSERT INTO menu VALUES('" + kodeMenu + "', '" + namaMenu + "', " + hargaMenu + ", " + stokMenu + ")";
                stmt.executeUpdate(sql);
                JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan!");
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        } else if(e.getSource() == btnView) {
            try {
                // Mengambil data dari database
                String sql = "SELECT * FROM menu";
                rs = stmt.executeQuery(sql);

                // Menampilkan data ke dalam tabel
                String[][] data = new String[100][4];
                String[] kolom = {"Kode Menu", "Nama Menu", "Harga Menu", "Stok Menu"};
                int row = 0;
                while(rs.next()) {
                    data[row][0] = rs.getString("kode_menu");
                    data[row][1] = rs.getString("nama_menu");
                    data[row][2] = rs.getString("harga_menu");
                    data[row][3] = rs.getString("stok_menu");
                    row++;
                }
                JTable table = new JTable(data, kolom);
                JOptionPane.showMessageDialog(null, new JScrollPane(table));
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        } else if(e.getSource() == btnUpdate) {
            try {
                // Mengambil nilai dari inputan
                String kodeMenu = txtKodeMenu.getText();
                int hargaMenu = Integer.parseInt(txtHargaMenu.getText());
                int stokMenu = Integer.parseInt(txtStokMenu.getText());

                // Mengubah data pada database
                String sql = "UPDATE menu SET harga_menu=" + hargaMenu + ", stok_menu=" + stokMenu + " WHERE kode_menu='" + kodeMenu + "'";
                stmt.executeUpdate(sql);
                JOptionPane.showMessageDialog(null, "Data berhasil diubah!");
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        } else if(e.getSource() == btnDelete) {
            try {
                // Mengambil nilai dari inputan
                String kodeMenu = txtKodeMenu.getText();

                // Menghapus data dari database
                String sql = "DELETE FROM menu WHERE kode_menu='" + kodeMenu + "'";
                stmt.executeUpdate(sql);
                JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        } else if(e.getSource() == btnReset) {
            // Mengosongkan inputan
            txtKodeMenu.setText("");
            txtNamaMenu.setText("");
            txtHargaMenu.setText("");
            txtStokMenu.setText("");
        }
    }

    public static void main(String[] args) {
        new PT_Pudding_App();
    }
}

