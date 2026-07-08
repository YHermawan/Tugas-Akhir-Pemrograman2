/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.Koneksi;
import model.Pinjaman;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PinjamanDAO {
    private Connection connection;

        public PinjamanDAO() {
        this.connection = Koneksi.getConnection();
    }

    // C - CREATE: Menambah data pinjaman baru
    public boolean insert(Pinjaman p) throws SQLException {
        String sql = "INSERT INTO tbl_pinjaman (no_pinjaman, nama_anggota, jumlah_pinjaman, angsuran_bulanan) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, p.getNo());
            ps.setString(2, p.getNama());
            ps.setDouble(3, p.getJumlah());
            ps.setDouble(4, p.getAngsuran());
            return ps.executeUpdate() > 0; // Mengembalikan true jika minimal ada 1 baris bertambah
        }
    }

    // R - READ: Mengambil seluruh data pinjaman untuk ditampilkan ke JTable
    public List<Pinjaman> getAll() throws SQLException {
        List<Pinjaman> list = new ArrayList<>();
        String sql = "SELECT * FROM tbl_pinjaman";
        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Pinjaman p = new Pinjaman(
                    rs.getString("no_pinjaman"), 
                    rs.getString("nama_anggota"), 
                    rs.getDouble("jumlah_pinjaman"), 
                    rs.getDouble("angsuran_bulanan")
                );
                list.add(p);
            }
        }
        return list;
    }

    // U - UPDATE: Mengubah data pinjaman berdasarkan Nomor Pinjaman (Primary Key)
    public boolean update(Pinjaman p) throws SQLException {
        String sql = "UPDATE tbl_pinjaman SET nama_anggota=?, jumlah_pinjaman=?, angsuran_bulanan=? WHERE no_pinjaman=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, p.getNama());
            ps.setDouble(2, p.getJumlah());
            ps.setDouble(3, p.getAngsuran());
            ps.setString(4, p.getNo());
            return ps.executeUpdate() > 0;
        }
    }

    // D - DELETE: Menghapus data pinjaman berdasarkan Nomor Pinjaman
    public boolean delete(String no) throws SQLException {
        String sql = "DELETE FROM tbl_pinjaman WHERE no_pinjaman=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, no);
            return ps.executeUpdate() > 0;
        }
    }

    // Fitur Pencarian Data (Berdasarkan nomor pinjaman atau nama anggota)
    public List<Pinjaman> search(String keyword) throws SQLException {
        List<Pinjaman> list = new ArrayList<>();
        String sql = "SELECT * FROM tbl_pinjaman WHERE no_pinjaman LIKE ? OR nama_anggota LIKE ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Pinjaman(
                        rs.getString("no_pinjaman"), 
                        rs.getString("nama_anggota"), 
                        rs.getDouble("jumlah_pinjaman"), 
                        rs.getDouble("angsuran_bulanan")
                    ));
                }
            }
        }
        return list;
    }
}
