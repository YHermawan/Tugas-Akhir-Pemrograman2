/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class Pinjaman {
    // Atribut yang merepresentasikan kolom di tabel tbl_pinjaman
    private String no;
    private String nama;
    private double jumlah;
    private double angsuran;

    // Constructor Kosong (Default)
    public Pinjaman() {}

    // Constructor dengan Parameter untuk inisialisasi cepat
    public Pinjaman(String no, String nama, double jumlah, double angsuran) {
        this.no = no;
        this.nama = nama;
        this.jumlah = jumlah;
        this.angsuran = angsuran;
    }

    // Getter dan Setter (Enkapsulasi Data)
    public String getNo() { return no; }
    public void setNo(String no) { this.no = no; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public double getJumlah() { return jumlah; }
    public void setJumlah(double jumlah) { this.jumlah = jumlah; }

    public double getAngsuran() { return angsuran; }
    public void setAngsuran(double angsuran) { this.angsuran = angsuran; }
}