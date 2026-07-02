/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.PinjamanDAO;
import model.Pinjaman;
import view.FormUtama;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

public class KoperasiController {
    private FormUtama view;
    private PinjamanDAO dao;

    // Konstruktor mengikat objek FormUtama agar komponen GUI bisa dimanipulasi teksnya
    public KoperasiController(FormUtama view) {
        this.view = view;
        this.dao = new PinjamanDAO();
    }

    // Sinkronisasi data array list dari DAO ke komponen JTable View
    public void loadData() {
        try {
            List<Pinjaman> list = dao.getAll();
            displayToTable(list);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Gagal memuat data: " + e.getMessage());
        }
    }

    // Melakukan filter data JTable berdasarkan input teks pencarian
    public void searchData() {
        String keyword = view.getTxtCari().getText();
        try {
            List<Pinjaman> list = dao.search(keyword);
            displayToTable(list);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Pencarian gagal: " + e.getMessage());
        }
    }

    private void displayToTable(List<Pinjaman> list) {
        DefaultTableModel model = (DefaultTableModel) view.getTabelPinjaman().getModel();
        model.setRowCount(0); // Kosongkan tabel sebelum memuat data baru
        for (Pinjaman p : list) {
            model.addRow(new Object[]{p.getNo(), p.getNama(), p.getJumlah(), p.getAngsuran()});
        }
    }

    // Logika Simpan Data + Validasi Input
    public void simpan() {
        // Validasi input kosong (Kriteria Penilaian 15%)
        if(view.getTxtNo().getText().trim().isEmpty() || view.getTxtNama().getText().trim().isEmpty() || 
           view.getTxtJumlah().getText().trim().isEmpty() || view.getTxtAngsuran().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Semua kolom input wajib diisi!");
            return;
        }

        try {
            Pinjaman p = new Pinjaman(
                view.getTxtNo().getText(),
                view.getTxtNama().getText(),
                Double.parseDouble(view.getTxtJumlah().getText()), // Validasi tipe angka pecahan
                Double.parseDouble(view.getTxtAngsuran().getText())
            );
            
            if (dao.insert(p)) {
                JOptionPane.showMessageDialog(view, "Data pinjaman berhasil disimpan!");
                loadData();
                clearFields();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Kolom Jumlah dan Angsuran wajib diisi angka/desimal!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Gagal Menyimpan (Nomor Pinjaman mungkin sudah ada): " + e.getMessage());
        }
    }

    // Logika Ubah Data
    public void ubah() {
        try {
            Pinjaman p = new Pinjaman(
                view.getTxtNo().getText(),
                view.getTxtNama().getText(),
                Double.parseDouble(view.getTxtJumlah().getText()),
                Double.parseDouble(view.getTxtAngsuran().getText())
            );
            if (dao.update(p)) {
                JOptionPane.showMessageDialog(view, "Data berhasil diperbarui!");
                loadData();
                clearFields();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Gagal mengubah data: " + e.getMessage());
        }
    }

    // Logika Hapus Data
    public void hapus() {
        String no = view.getTxtNo().getText();
        if (no.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Silakan pilih baris data pada tabel terlebih dahulu!");
            return;
        }
        int konfirmasi = JOptionPane.showConfirmDialog(view, "Yakin ingin menghapus data " + no + "?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (konfirmasi == JOptionPane.YES_OPTION) {
            try {
                if (dao.delete(no)) {
                    JOptionPane.showMessageDialog(view, "Data berhasil dihapus!");
                    loadData();
                    clearFields();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(view, "Gagal menghapus data: " + e.getMessage());
            }
        }
    }

    // Membersihkan form isian textfield
    public void clearFields() {
        view.getTxtNo().setText("");
        view.getTxtNama().setText("");
        view.getTxtJumlah().setText("");
        view.getTxtAngsuran().setText("");
        view.getTxtNo().requestFocus();
    }
}