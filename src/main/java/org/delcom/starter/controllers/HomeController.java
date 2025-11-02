package org.delcom.starter.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.*;
import java.util.Base64;
import java.nio.charset.StandardCharsets;

@Controller
public class HomeController {

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "Eyoyoo Immanuel Lumbantobing, selamat datang di pengembangan aplikasi dengan Spring Boot!";
    }

    @GetMapping("/sayHello")
    @ResponseBody
    public String sayHello(@RequestParam String nama) {
        return "Hello, " + nama + "!";
    }

    @GetMapping("/informasiNim")
    @ResponseBody
    public String informasiNim(@RequestParam String nim) {
        StringBuilder result = new StringBuilder();
        
        String prodi = "";
        String akt = "";
        String urutan = "";

        // Prefix bisa 3 atau 4 digit, cek dulu
        if (nim.startsWith("114") || nim.startsWith("113") || nim.startsWith("133")) {
            prodi = nim.substring(0, 3);
            akt = nim.substring(3, 5);
            urutan = nim.substring(5);
        } else {
            prodi = nim.substring(0, 3);
            akt = nim.substring(3, 5);
            urutan = nim.substring(5);
        }

        result.append("Inforamsi NIM ").append(nim).append(": \n");

        switch (prodi) {
            case "11S":
                result.append(">> Program Studi: Sarjana Informatika\n");
                break;
            case "12S":
                result.append(">> Program Studi: Sarjana Sistem Informasi\n");
                break;
            case "14S":
                result.append(">> Program Studi: Sarjana Teknik Elektro\n");
                break;
            case "21S":
                result.append(">> Program Studi: Sarjana Manajemen Rekayasa\n");
                break;
            case "22S":
                result.append(">> Program Studi: Sarjana Teknik Metalurgi\n");
                break;
            case "31S":
                result.append(">> Program Studi: Sarjana Teknik Bioproses\n");
                break;
            case "114":
                result.append(">> Program Studi: Diploma 4 Teknologi Rekasaya Perangkat Lunak\n");
                break;
            case "113":
                result.append(">> Program Studi: Diploma 3 Teknologi Informasi\n");
                break;
            case "133":
                result.append(">> Program Studi: Diploma 3 Teknologi Komputer\n");
                break;
            default:
                result.append(">> Program Studi: Tidak diketahui\n");
        }

        result.append(">> Angkatan: 20").append(akt).append("\n");
        result.append(">> Urutan: ").append(Integer.parseInt(urutan)).append("\n");

        return result.toString();
    }

    @GetMapping("/perolehanNilai")
    @ResponseBody
    public String perolehanNilai(@RequestParam String strBase64) {
        StringBuilder result = new StringBuilder();
        
        // Decode Base64 input
        String decodedInput = new String(Base64.getDecoder().decode(strBase64), StandardCharsets.UTF_8);
        Scanner scanner = new Scanner(decodedInput);
        scanner.useLocale(Locale.US);

        // Baca bobot komponen (PA, T, K, P, UTS, UAS)
        double bobotPA  = scanner.nextDouble();
        double bobotTugas   = scanner.nextDouble();
        double bobotKuis   = scanner.nextDouble();
        double bobotProyek   = scanner.nextDouble();
        double bobotUTS = scanner.nextDouble();
        double bobotUAS = scanner.nextDouble();
        scanner.nextLine(); // buang newline

        // Akumulator skor dan maksimal per komponen
        int skorPA = 0, maxPA = 0;
        int skorTugas = 0, maxTugas = 0;
        int skorKuis = 0, maxKuis = 0;
        int skorProyek = 0, maxProyek = 0;
        int skorUTS = 0, maxUTS = 0;
        int skorUAS = 0, maxUAS = 0;

        // Baca item sampai "---"
        while (true) {
            String line = scanner.nextLine().trim();
            if (line.equals("---")) break;
            String[] parts = line.split("\\|");
            String simbol = parts[0];
            int nilaiMax = Integer.parseInt(parts[1]);
            int nilaiSkor = Integer.parseInt(parts[2]);

            switch (simbol) {
                case "PA":  maxPA  += nilaiMax; skorPA  += nilaiSkor; break;
                case "T":   maxTugas   += nilaiMax; skorTugas   += nilaiSkor; break;
                case "K":   maxKuis   += nilaiMax; skorKuis   += nilaiSkor; break;
                case "P":   maxProyek   += nilaiMax; skorProyek   += nilaiSkor; break;
                case "UTS": maxUTS += nilaiMax; skorUTS += nilaiSkor; break;
                case "UAS": maxUAS += nilaiMax; skorUAS += nilaiSkor; break;
            }
        }

        // Hitung persentase (dibulatkan ke bawah) dan kontribusi = (persen/100)*bobot
        int persenPA  = maxPA  > 0 ? (int)Math.floor(skorPA  * 100.0 / maxPA ) : 0;
        int persenTugas   = maxTugas   > 0 ? (int)Math.floor(skorTugas   * 100.0 / maxTugas  ) : 0;
        int persenKuis   = maxKuis   > 0 ? (int)Math.floor(skorKuis   * 100.0 / maxKuis  ) : 0;
        int persenProyek   = maxProyek   > 0 ? (int)Math.floor(skorProyek   * 100.0 / maxProyek  ) : 0;
        int persenUTS = maxUTS > 0 ? (int)Math.floor(skorUTS * 100.0 / maxUTS) : 0;
        int persenUAS = maxUAS > 0 ? (int)Math.floor(skorUAS * 100.0 / maxUAS) : 0;

        double kontribusiPA  = (persenPA  / 100.0) * bobotPA;
        double kontribusiTugas   = (persenTugas   / 100.0) * bobotTugas;
        double kontribusiKuis   = (persenKuis   / 100.0) * bobotKuis;
        double kontribusiProyek   = (persenProyek   / 100.0) * bobotProyek;
        double kontribusiUTS = (persenUTS / 100.0) * bobotUTS;
        double kontribusiUAS = (persenUAS / 100.0) * bobotUAS;

        double nilaiAkhir = kontribusiPA + kontribusiTugas + kontribusiKuis +
                            kontribusiProyek + kontribusiUTS + kontribusiUAS;

        // Konversi grade
        String grade;
        if (nilaiAkhir >= 79.5) grade = "A";
        else if (nilaiAkhir >= 72) grade = "AB";
        else if (nilaiAkhir >= 64.5) grade = "B";
        else if (nilaiAkhir >= 57) grade = "BC";
        else if (nilaiAkhir >= 49.5) grade = "C";
        else if (nilaiAkhir >= 34) grade = "D";
        else grade = "E";

        // Output
        result.append("Perolehan Nilai:\n");
        result.append(String.format(">> Partisipatif: %d/100 (%.2f/%.0f)%n", persenPA,  kontribusiPA,  bobotPA));
        result.append(String.format(">> Tugas: %d/100 (%.2f/%.0f)%n",        persenTugas,   kontribusiTugas,   bobotTugas));
        result.append(String.format(">> Kuis: %d/100 (%.2f/%.0f)%n",         persenKuis,   kontribusiKuis,   bobotKuis));
        result.append(String.format(">> Proyek: %d/100 (%.2f/%.0f)%n",       persenProyek,   kontribusiProyek,   bobotProyek));
        result.append(String.format(">> UTS: %d/100 (%.2f/%.0f)%n",          persenUTS, kontribusiUTS, bobotUTS));
        result.append(String.format(">> UAS: %d/100 (%.2f/%.0f)%n",          persenUAS, kontribusiUAS, bobotUAS));

        result.append("\n");
        result.append(String.format(">> Nilai Akhir: %.2f%n", nilaiAkhir));
        result.append(String.format(">> Grade: %s%n", grade));

        scanner.close();
        return result.toString();
    }

    @GetMapping("/perbedaanL")
    @ResponseBody
    public String perbedaanL(@RequestParam String strBase64) {
        StringBuilder result = new StringBuilder();
        
        // Decode Base64 input
        String decodedInput = new String(Base64.getDecoder().decode(strBase64), StandardCharsets.UTF_8);
        Scanner input = new Scanner(decodedInput);

        int ukuranMatriks = input.nextInt();
        int[][] matriks = new int[ukuranMatriks][ukuranMatriks];
        for (int i = 0; i < ukuranMatriks; i++) {
            for (int j = 0; j < ukuranMatriks; j++) {
                matriks[i][j] = input.nextInt();
            }
        }

        // Kasus khusus ukuranMatriks = 1
        if (ukuranMatriks == 1) {
            int nilaiTengah = matriks[0][0];
            result.append("Nilai L: Tidak Ada\n");
            result.append("Nilai Kebalikan L: Tidak Ada\n");
            result.append("Nilai Tengah: ").append(nilaiTengah).append("\n");
            result.append("Perbedaan: Tidak Ada\n");
            result.append("Dominan: ").append(nilaiTengah).append("\n");
            input.close();
            return result.toString();
        }

        // Kasus khusus ukuranMatriks = 2
        if (ukuranMatriks == 2) {
            int total = 0;
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    total += matriks[i][j];
                }
            }
            result.append("Nilai L: Tidak Ada\n");
            result.append("Nilai Kebalikan L: Tidak Ada\n");
            result.append("Nilai Tengah: ").append(total).append("\n");
            result.append("Perbedaan: Tidak Ada\n");
            result.append("Dominan: ").append(total).append("\n");
            input.close();
            return result.toString();
        }

        // N >= 3
        // Hitung Nilai L: kolom pertama + baris terakhir (tanpa pojok kanan bawah)
        int jumlahL = 0;
        for (int i = 0; i < ukuranMatriks; i++) jumlahL += matriks[i][0];
        for (int j = 1; j <= ukuranMatriks - 2; j++) jumlahL += matriks[ukuranMatriks - 1][j];

        // Hitung Nilai Kebalikan L: kolom terakhir + baris pertama (tanpa pojok kiri atas)
        int jumlahKebalikanL = 0;
        for (int i = 0; i < ukuranMatriks; i++) jumlahKebalikanL += matriks[i][ukuranMatriks - 1];
        for (int j = 1; j <= ukuranMatriks - 2; j++) jumlahKebalikanL += matriks[0][j];

        // Hitung Nilai Tengah
        int nilaiTengah;
        if (ukuranMatriks % 2 == 1) {
            nilaiTengah = matriks[ukuranMatriks / 2][ukuranMatriks / 2];
        } else {
            int mid1 = ukuranMatriks / 2 - 1;
            int mid2 = ukuranMatriks / 2;
            nilaiTengah = matriks[mid1][mid1] + matriks[mid1][mid2] + matriks[mid2][mid1] + matriks[mid2][mid2];
        }

        int selisih = Math.abs(jumlahL - jumlahKebalikanL);
        int nilaiDominan = (selisih == 0) ? nilaiTengah : Math.max(jumlahL, jumlahKebalikanL);

        result.append("Nilai L: ").append(jumlahL).append("\n");
        result.append("Nilai Kebalikan L: ").append(jumlahKebalikanL).append("\n");
        result.append("Nilai Tengah: ").append(nilaiTengah).append("\n");
        result.append("Perbedaan: ").append(selisih).append("\n");
        result.append("Dominan: ").append(nilaiDominan).append("\n");

        input.close();
        return result.toString();
    }

    @GetMapping("/palingTer")
    @ResponseBody
    public String palingTer(@RequestParam String strBase64) {
        StringBuilder result = new StringBuilder();
        
        // Decode Base64 input
        String decodedInput = new String(Base64.getDecoder().decode(strBase64), StandardCharsets.UTF_8);
        Scanner input = new Scanner(decodedInput);

        // Struktur data yang dipakai
        HashMap<Integer, Integer> frekuensiNilai = new HashMap<>();
        HashMap<Integer, Integer> frekuensiSementara = new HashMap<>();
        HashMap<Integer, Integer> totalPerNilai = new HashMap<>();
        HashMap<Integer, Integer> hitungTerbanyak = new HashMap<>();
        ArrayList<Integer> daftarInputNilai = new ArrayList<>();

        // Input data
        while (true) {
            String data = input.nextLine();
            if (data.equals("---")) break;

            int nilai = Integer.parseInt(data);
            int jumlahMuncul = frekuensiNilai.getOrDefault(nilai, 0) + 1;
            frekuensiNilai.put(nilai, jumlahMuncul);
            daftarInputNilai.add(nilai);
        }

        // Konversi ke array
        int[] arrayNilai = daftarInputNilai.stream().mapToInt(Integer::intValue).toArray();

        // Cari nilai tertinggi & terendah
        int maxNilai = arrayNilai[0];
        int minNilai = arrayNilai[0];
        for (int nilai : arrayNilai) {
            if (nilai > maxNilai) maxNilai = nilai;
            if (nilai < minNilai) minNilai = nilai;
        }

        // Cari jumlah total terendah
        int nilaiDenganJumlahTerendah = arrayNilai[0];
        int totalTerendah = nilaiDenganJumlahTerendah;
        for (int nilai : arrayNilai) {
            int jumlahBaru = totalPerNilai.getOrDefault(nilai, 0) + nilai;
            totalPerNilai.put(nilai, jumlahBaru);

            if (jumlahBaru < totalTerendah || nilai == nilaiDenganJumlahTerendah) {
                nilaiDenganJumlahTerendah = nilai;
                totalTerendah = jumlahBaru;
            }
        }

        // Cari nilai paling sering muncul
        int frekuensiMaksimum = 0;
        int nilaiPalingSering = 0;

        for (int nilai : arrayNilai) {
            int frekuensiSekarang = frekuensiNilai.get(nilai);
            if (frekuensiSekarang > frekuensiMaksimum) {
                frekuensiMaksimum = frekuensiSekarang;
            }
        }

        for (int nilai : arrayNilai) {
            int jumlahMuncul = hitungTerbanyak.getOrDefault(nilai, 0) + 1;
            hitungTerbanyak.put(nilai, jumlahMuncul);

            if (jumlahMuncul == frekuensiMaksimum) {
                nilaiPalingSering = nilai;
                break;
            }
        }

        // Cari nilai paling jarang muncul
        int nilaiPalingJarang = arrayNilai[0];
        int frekuensiMinimum = 1;
        frekuensiSementara.put(nilaiPalingJarang, 1);

        for (int i = 1; i < arrayNilai.length; i++) {
            int nilai = arrayNilai[i];
            int jumlahMuncul = frekuensiSementara.getOrDefault(nilai, 0) + 1;
            frekuensiSementara.put(nilai, jumlahMuncul);

            if (nilai == nilaiPalingJarang) {
                for (int j = i + 1; j < arrayNilai.length; j++) {
                    int kandidat = arrayNilai[j];
                    if (!frekuensiSementara.containsKey(kandidat)) {
                        frekuensiSementara.put(kandidat, 1);
                        nilaiPalingJarang = kandidat;
                        frekuensiMinimum = 1;
                        i = j;
                        break;
                    }
                }
            }
        }

        // Cari jumlah total tertinggi
        int totalTertinggi = arrayNilai[0];
        int nilaiDenganTotalTertinggi = 0;
        int frekuensiNilaiDenganTotalTertinggi = 0;

        for (var entry : frekuensiNilai.entrySet()) {
            int angka = entry.getKey();
            int frek = entry.getValue();
            int total = angka * frek;

            if (total > totalTertinggi || 
               (total == totalTertinggi && angka > nilaiDenganTotalTertinggi)) {
                totalTertinggi = total;
                nilaiDenganTotalTertinggi = angka;
                frekuensiNilaiDenganTotalTertinggi = frek;
            }
        }

        // Output hasil
        result.append("Tertinggi: ").append(maxNilai).append("\n");
        result.append("Terendah: ").append(minNilai).append("\n");
        result.append("Terbanyak: ").append(nilaiPalingSering).append(" (").append(frekuensiMaksimum).append("x)\n");
        result.append("Tersedikit: ").append(nilaiPalingJarang).append(" (").append(frekuensiMinimum).append("x)\n");
        result.append("Jumlah Tertinggi: ").append(nilaiDenganTotalTertinggi).append(" * ") 
               .append(frekuensiNilaiDenganTotalTertinggi).append(" = ").append(totalTertinggi).append("\n");
        result.append("Jumlah Terendah: ").append(nilaiDenganJumlahTerendah).append(" * ") 
               .append(frekuensiNilai.get(nilaiDenganJumlahTerendah)).append(" = ").append(totalTerendah).append("\n");

        input.close();
        return result.toString();
    }
}