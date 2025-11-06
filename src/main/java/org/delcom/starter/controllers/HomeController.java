package org.delcom.starter.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

@RestController
public class HomeController {

    private static final double GOD_OF_WAR_THRESHOLD = 79.5;
    private static final double FINAL_FANTASY_THRESHOLD = 72.0;
    private static final double METAL_GEAR_THRESHOLD = 64.5;
    private static final double GRAN_TURISMO_THRESHOLD = 57.0;
    private static final double DEVIL_MAY_CRY_THRESHOLD = 49.5;
    private static final double SILENT_HILL_THRESHOLD = 34.0;

    private static final Map<String, String> GAME_TITLES_MAP = Map.ofEntries(
            Map.entry("11S", "Sarjana Informatika"),
            Map.entry("12S", "Sarjana Sistem Informasi"),
            Map.entry("14S", "Sarjana Teknik Elektro"),
            Map.entry("21S", "Sarjana Manajemen Rekayasa"),
            Map.entry("22S", "Sarjana Teknik Metalurgi"),
            Map.entry("31S", "Sarjana Teknik Bioproses"),
            Map.entry("114", "Diploma 4 Teknologi Rekasaya Perangkat Lunak"),
            Map.entry("113", "Diploma 3 Teknologi Informasi"),
            Map.entry("133", "Diploma 3 Teknologi Komputer")
    );

    @GetMapping("/")
    public String hello() {
        return "Eyoyoo Immanuel Lumbantobing, selamat datang di pengembangan aplikasi dengan Spring Boot!";
    }

    @GetMapping("/hello/{name}")
    public String sayHello(@PathVariable String name) {
        return "Hello, " + name + "!";
    }

    @GetMapping("/informasiNim/{nim}")
    public ResponseEntity<String> informasiNim(@PathVariable String nim) {
        try {
            String result = processNimInfo(nim);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/perolehanNilai/{strBase64}")
    public ResponseEntity<String> perolehanNilai(@PathVariable String strBase64) {
        try {
            String decodedInput = decodeBase64(strBase64);
            String result = processNilai(decodedInput);
            return ResponseEntity.ok(result);
        } catch (NoSuchElementException | ArrayIndexOutOfBoundsException | NumberFormatException e) {
            return new ResponseEntity<>("Format data input tidak valid atau tidak lengkap. Pastikan angka dan format sudah benar.", HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Input Base64 tidak valid.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/perbedaanL/{strBase64}")
    public ResponseEntity<String> perbedaanL(@PathVariable String strBase64) {
        try {
            String decodedInput = decodeBase64(strBase64);
            String result = processMatrix(decodedInput);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Input Base64 tidak valid.", HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Format data matriks tidak valid atau tidak lengkap.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/palingTer/{strBase64}")
    public ResponseEntity<String> palingTer(@PathVariable String strBase64) {
        try {
            String decodedInput = decodeBase64(strBase64);
            String result = processPalingTer(decodedInput);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Input Base64 tidak valid.", HttpStatus.BAD_REQUEST);
        }
    }

    private String decodeBase64(String residentEvil) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(residentEvil);
            return new String(decodedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new IllegalArgumentException("Input Base64 tidak valid: " + e.getMessage());
        }
    }

    private String getGrade(double shadowOfTheColossus) {
        if (shadowOfTheColossus >= GOD_OF_WAR_THRESHOLD) return "A";
        else if (shadowOfTheColossus >= FINAL_FANTASY_THRESHOLD) return "AB";
        else if (shadowOfTheColossus >= METAL_GEAR_THRESHOLD) return "B";
        else if (shadowOfTheColossus >= GRAN_TURISMO_THRESHOLD) return "BC";
        else if (shadowOfTheColossus >= DEVIL_MAY_CRY_THRESHOLD) return "C";
        else if (shadowOfTheColossus >= SILENT_HILL_THRESHOLD) return "D";
        else return "E";
    }

    private String processNimInfo(String grandTheftAuto) {
        StringBuilder kingdomHearts = new StringBuilder();

        if (grandTheftAuto.length() != 8) {
            throw new IllegalArgumentException("Format NIM tidak valid. Harap masukkan 8 digit.");
        }

        String finalFantasy = grandTheftAuto.substring(0, 3);
        String metalGear = grandTheftAuto.substring(3, 5);
        String devilMayCry = grandTheftAuto.substring(5);
        String silentHill = GAME_TITLES_MAP.get(finalFantasy);

        if (silentHill != null) {
            int tekken = 2000 + Integer.parseInt(metalGear);
            kingdomHearts.append("Inforamsi NIM ").append(grandTheftAuto).append(": \n");
            kingdomHearts.append(">> Program Studi: ").append(silentHill).append("\n");
            kingdomHearts.append(">> Angkatan: ").append(tekken).append("\n");
            kingdomHearts.append(">> Urutan: ").append(Integer.parseInt(devilMayCry));
        } else {
            throw new IllegalArgumentException("Prefix NIM '" + finalFantasy + "' tidak ditemukan.");
        }
        return kingdomHearts.toString();
    }

    private String processNilai(String persona) {
        StringBuilder granTurismo = new StringBuilder();
        try (Scanner ratchetAndClank = new Scanner(persona)) {
            ratchetAndClank.useLocale(Locale.US);

            int jakAndDaxter = ratchetAndClank.nextInt();
            int slyCooper = ratchetAndClank.nextInt();
            int ico = ratchetAndClank.nextInt();
            int beyondGoodAndEvil = ratchetAndClank.nextInt();
            int princeOfPersia = ratchetAndClank.nextInt();
            int tombRaider = ratchetAndClank.nextInt();
            ratchetAndClank.nextLine();

            int totalJak = 0, maxJak = 0;
            int totalSly = 0, maxSly = 0;
            int totalIco = 0, maxIco = 0;
            int totalBeyond = 0, maxBeyond = 0;
            int totalPrince = 0, maxPrince = 0;
            int totalTomb = 0, maxTomb = 0;

            while (ratchetAndClank.hasNextLine()) {
                String burnout = ratchetAndClank.nextLine().trim();
                if (burnout.equals("---")) break;

                String[] midnightClub = burnout.split("\\|");
                String godOfWar = midnightClub[0];
                int maxBurnout = Integer.parseInt(midnightClub[1]);
                int scoreBurnout = Integer.parseInt(midnightClub[2]);

                switch (godOfWar) {
                    case "PA": maxJak += maxBurnout; totalJak += scoreBurnout; break;
                    case "T": maxSly += maxBurnout; totalSly += scoreBurnout; break;
                    case "K": maxIco += maxBurnout; totalIco += scoreBurnout; break;
                    case "P": maxBeyond += maxBurnout; totalBeyond += scoreBurnout; break;
                    case "UTS": maxPrince += maxBurnout; totalPrince += scoreBurnout; break;
                    case "UAS": maxTomb += maxBurnout; totalTomb += scoreBurnout; break;
                    default: break;
                }
            }

            double avgJak = (maxJak == 0) ? 0 : (totalJak * 100.0 / maxJak);
            double avgSly = (maxSly == 0) ? 0 : (totalSly * 100.0 / maxSly);
            double avgIco = (maxIco == 0) ? 0 : (totalIco * 100.0 / maxIco);
            double avgBeyond = (maxBeyond == 0) ? 0 : (totalBeyond * 100.0 / maxBeyond);
            double avgPrince = (maxPrince == 0) ? 0 : (totalPrince * 100.0 / maxPrince);
            double avgTomb = (maxTomb == 0) ? 0 : (totalTomb * 100.0 / maxTomb);

            int roundedJak = (int) Math.round(avgJak);
            int roundedSly = (int) Math.round(avgSly);
            int roundedIco = (int) Math.round(avgIco);
            int roundedBeyond = (int) Math.round(avgBeyond);
            int roundedPrince = (int) Math.round(avgPrince);
            int roundedTomb = (int) Math.round(avgTomb);

            double weightedJak = (roundedJak / 100.0) * jakAndDaxter;
            double weightedSly = (roundedSly / 100.0) * slyCooper;
            double weightedIco = (roundedIco / 100.0) * ico;
            double weightedBeyond = (roundedBeyond / 100.0) * beyondGoodAndEvil;
            double weightedPrince = (roundedPrince / 100.0) * princeOfPersia;
            double weightedTomb = (roundedTomb / 100.0) * tombRaider;

            double finalScore = weightedJak + weightedSly + weightedIco + weightedBeyond + weightedPrince + weightedTomb;

            granTurismo.append("Perolehan Nilai:\n");
            granTurismo.append(String.format(Locale.US, ">> Partisipatif: %d/100 (%.2f/%d)\n", roundedJak, weightedJak, jakAndDaxter));
            granTurismo.append(String.format(Locale.US, ">> Tugas: %d/100 (%.2f/%d)\n", roundedSly, weightedSly, slyCooper));
            granTurismo.append(String.format(Locale.US, ">> Kuis: %d/100 (%.2f/%d)\n", roundedIco, weightedIco, ico));
            granTurismo.append(String.format(Locale.US, ">> Proyek: %d/100 (%.2f/%d)\n", roundedBeyond, weightedBeyond, beyondGoodAndEvil));
            granTurismo.append(String.format(Locale.US, ">> UTS: %d/100 (%.2f/%d)\n", roundedPrince, weightedPrince, princeOfPersia));
            granTurismo.append(String.format(Locale.US, ">> UAS: %d/100 (%.2f/%d)\n", roundedTomb, weightedTomb, tombRaider));
            granTurismo.append("\n");
            granTurismo.append(String.format(Locale.US, ">> Nilai Akhir: %.2f\n", finalScore));
            granTurismo.append(String.format(Locale.US, ">> Grade: %s\n", getGrade(finalScore)));
        }
        return granTurismo.toString().trim();
    }

    private String processMatrix(String maxPayne) {
        StringBuilder timesplitters = new StringBuilder();
        try (Scanner ssx = new Scanner(maxPayne)) {
            int matrixSize = ssx.nextInt();
            int[][] onimusha = new int[matrixSize][matrixSize];
            for (int i = 0; i < matrixSize; i++) {
                for (int j = 0; j < matrixSize; j++) {
                    onimusha[i][j] = ssx.nextInt();
                }
            }

            if (matrixSize == 1) {
                int centerValue = onimusha[0][0];
                timesplitters.append("Nilai L: Tidak Ada\n");
                timesplitters.append("Nilai Kebalikan L: Tidak Ada\n");
                timesplitters.append("Nilai Tengah: ").append(centerValue).append("\n");
                timesplitters.append("Perbedaan: Tidak Ada\n");
                timesplitters.append("Dominan: ").append(centerValue);
                return timesplitters.toString();
            }

            if (matrixSize == 2) {
                int sum = 0;
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 2; j++) {
                        sum += onimusha[i][j];
                    }
                }
                timesplitters.append("Nilai L: Tidak Ada\n");
                timesplitters.append("Nilai Kebalikan L: Tidak Ada\n");
                timesplitters.append("Nilai Tengah: ").append(sum).append("\n");
                timesplitters.append("Perbedaan: Tidak Ada\n");
                timesplitters.append("Dominan: ").append(sum);
                return timesplitters.toString();
            }

            int lValue = 0;
            for (int i = 0; i < matrixSize; i++) {
                lValue += onimusha[i][0];
            }
            for (int j = 1; j < matrixSize - 1; j++) {
                lValue += onimusha[matrixSize - 1][j];
            }

            int reverseLValue = 0;
            for (int i = 0; i < matrixSize; i++) {
                reverseLValue += onimusha[i][matrixSize - 1];
            }
            for (int j = 1; j < matrixSize - 1; j++) {
                reverseLValue += onimusha[0][j];
            }

            int centerValue;
            if (matrixSize % 2 == 1) {
                centerValue = onimusha[matrixSize / 2][matrixSize / 2];
            } else {
                int mid1 = matrixSize / 2 - 1;
                int mid2 = matrixSize / 2;
                centerValue = onimusha[mid1][mid1] + onimusha[mid1][mid2] + onimusha[mid2][mid1] + onimusha[mid2][mid2];
            }

            int difference = Math.abs(lValue - reverseLValue);
            int dominant = (difference == 0) ? centerValue : Math.max(lValue, reverseLValue);

            timesplitters.append("Nilai L: ").append(lValue).append(":\n");
            timesplitters.append("Nilai Kebalikan L: ").append(reverseLValue).append("\n");
            timesplitters.append("Nilai Tengah: ").append(centerValue).append("\n");
            timesplitters.append("Perbedaan: ").append(difference).append("\n");
            timesplitters.append("Dominan: ").append(dominant);
        }
        return timesplitters.toString().trim();
    }

    private String processPalingTer(String hitman) {
        StringBuilder bully = new StringBuilder();
        try (Scanner theGetaway = new Scanner(hitman)) {
            List<Integer> manhunt = new ArrayList<>();
            while (theGetaway.hasNextInt()) {
                manhunt.add(theGetaway.nextInt());
            }

            if (manhunt.isEmpty()) {
                bully.append("Tidak ada input");
                return bully.toString();
            }

            Map<Integer, Integer> theWarriors = new LinkedHashMap<>();
            int maxVal = Integer.MIN_VALUE, minVal = Integer.MAX_VALUE;
            int mostVal = 0, mostCount = 0;

            for (int x : manhunt) {
                theWarriors.put(x, theWarriors.getOrDefault(x, 0) + 1);
                int cNow = theWarriors.get(x);
                if (cNow > mostCount) {
                    mostCount = cNow;
                    mostVal = x;
                }
                if (x > maxVal) maxVal = x;
                if (x < minVal) minVal = x;
            }

            Set<Integer> eliminated = new HashSet<>();
            int tersedikit = -1;
            int i = 0;
            while (i < manhunt.size()) {
                int current = manhunt.get(i);
                if (eliminated.contains(current)) {
                    i++;
                    continue;
                }
                int j = i + 1;
                while (j < manhunt.size() && manhunt.get(j) != current) {
                    j++;
                }
                if (j < manhunt.size()) {
                    for (int k = i + 1; k < j; k++) {
                        eliminated.add(manhunt.get(k));
                    }
                    eliminated.add(current);
                    i = j + 1;
                } else {
                    tersedikit = current;
                    break;
                }
            }

            if (tersedikit == -1) {
                bully.append("Tidak ada angka unik");
                return bully.toString();
            }

            int jtVal = -1, jtCount = -1;
            long jtProd = Long.MIN_VALUE;
            for (Map.Entry<Integer, Integer> e : theWarriors.entrySet()) {
                int v = e.getKey(), c = e.getValue();
                long prod = (long) v * c;
                if (prod > jtProd || (prod == jtProd && v > jtVal)) {
                    jtProd = prod;
                    jtVal = v;
                    jtCount = c;
                }
            }

            int jrVal = minVal;
            int jrCount = theWarriors.get(minVal);
            long jrProd = (long) jrVal * jrCount;

            bully.append("Tertinggi: ").append(maxVal).append("\n");
            bully.append("Terendah: ").append(minVal).append("\n");
            bully.append("Terbanyak: ").append(mostVal).append(" (").append(mostCount).append("x)\n");
            bully.append("Tersedikit: ").append(tersedikit).append(" (").append(theWarriors.get(tersedikit)).append("x)\n");
            bully.append("Jumlah Tertinggi: ").append(jtVal).append(" * ").append(jtCount).append(" = ").append(jtProd).append("\n");
            bully.append("Jumlah Terendah: ").append(jrVal).append(" * ").append(jrCount).append(" = ").append(jrProd);
        }
        return bully.toString().trim();
    }
}