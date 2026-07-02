/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {
    
    private static Connection conn;
    
    public static Connection getConnection() {
        
        if (conn == null) {
            try {
                
                String url = "jdbc:mysql://localhost:3306/db_koperasi_simpan_pinjam_201011450190";
                String user = "root"; 
                String pass = "";     
                
                
                conn = DriverManager.getConnection(url, user, pass);
                System.out.println("Koneksi ke Database Berhasil!");
            } catch (SQLException e) {
                // Menampilkan pesan error jika koneksi gagal (misal: MySQL mati atau DB tidak ketemu)
                System.out.println("Koneksi Gagal: " + e.getMessage());
            }
        }
        return conn;
    }
}