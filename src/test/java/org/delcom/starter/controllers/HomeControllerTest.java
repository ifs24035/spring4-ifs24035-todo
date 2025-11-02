package org.delcom.starter.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HomeControllerUnitTest {

    private final HomeController controller = new HomeController();

    // ===== TEST METHOD DASAR =====
    @Test
    @DisplayName("hello() mengembalikan pesan selamat datang")
    void hello_ShouldReturnWelcomeMessage() {
        String result = controller.hello();
        assertEquals("Eyoyoo Immanuel Lumbantobing, selamat datang di pengembangan aplikasi dengan Spring Boot!", result);
    }

    @Test
    @DisplayName("sayHello() mengembalikan pesan sapaan")
    void sayHello_ShouldReturnPersonalizedGreeting() {
        String result = controller.sayHello("Immanuel Lumbantobing");
        assertEquals("Hello, Immanuel Lumbantobing!", result);
    }

    // ===== TEST INFORMASI NIM =====
    @Test
    @DisplayName("informasiNim() dengan prefix 114")
    void informasiNim_With114Prefix() {
        String result = controller.informasiNim("11421001");
        assertTrue(result.contains("Diploma 4 Teknologi Rekasaya Perangkat Lunak"));
    }

    @Test
    @DisplayName("informasiNim() dengan prefix 113")
    void informasiNim_With113Prefix() {
        String result = controller.informasiNim("11321001");
        assertTrue(result.contains("Diploma 3 Teknologi Informasi"));
    }

    @Test
    @DisplayName("informasiNim() dengan prefix 133")
    void informasiNim_With133Prefix() {
        String result = controller.informasiNim("13321001");
        assertTrue(result.contains("Diploma 3 Teknologi Komputer"));
    }

    @Test
    @DisplayName("informasiNim() dengan program studi sarjana")
    void informasiNim_WithFITEFaculty() {
        assertTrue(controller.informasiNim("11S21001").contains("Sarjana Informatika"));
        assertTrue(controller.informasiNim("12S21001").contains("Sarjana Sistem Informasi"));
        assertTrue(controller.informasiNim("14S21001").contains("Sarjana Teknik Elektro"));
    }

    @Test
    @DisplayName("informasiNim() dengan program studi sarjana (teknik)")
    void informasiNim_WithSarjanaTeknikPrograms() {
        assertTrue(controller.informasiNim("21S21001").contains("Sarjana Manajemen Rekayasa"));
        assertTrue(controller.informasiNim("22S21001").contains("Sarjana Teknik Metalurgi"));
        assertTrue(controller.informasiNim("31S21001").contains("Sarjana Teknik Bioproses"));
    }

    @Test
    @DisplayName("informasiNim() dengan program studi tidak diketahui")
    void informasiNim_WithUnknownProgram() {
        String result = controller.informasiNim("99921001");
        assertTrue(result.contains("Tidak diketahui"));
    }

    // ===== TEST PEROLEHAN NILAI =====
    @Test
    @DisplayName("perolehanNilai() dengan data valid")
    void perolehanNilai_WithValidData() {
        String strBase64 = "MA0KMzUNCjENCjE2DQoyMg0KMjYNClR8OTB8MjENClVBU3w5Mnw4Mg0KVUFTfDYzfDE1DQpUfDEwfDUNClVBU3w4OXw3NA0KVHw5NXwzNQ0KUEF8NzV8NDUNClBBfDkwfDc3DQpQQXw4NnwxNA0KVVRTfDIxfDANCkt8NTB8NDQNCi0tLQ==";
        String result = controller.perolehanNilai(strBase64);
        assertTrue(result.contains("Nilai Akhir"));
    }

    @Test
    @DisplayName("perolehanNilai() dengan data sederhana")
    void perolehanNilai_WithSimpleData() {
        // Data yang sangat sederhana
        String strBase64 = "MA0KMA0KMA0KMA0KMA0KMA0KLS0t";
        String result = controller.perolehanNilai(strBase64);
        assertNotNull(result);
    }

    @Test
    @DisplayName("perolehanNilai() dengan Proyek sebagai komponen utama")
    void perolehanNilai_WithProjectAsMainComponent() {
        String strBase64 = "MA0KMA0KMA0KNjANCjANCjANCiB8MTAwfDg1DQotLS0=";
        String result = controller.perolehanNilai(strBase64);
        assertTrue(result.contains("Proyek"));
    }

    @Test
    @DisplayName("perolehanNilai() dengan single komponen Proyek")
    void perolehanNilai_WithSingleProject() {
        // Format paling sederhana untuk cover case "P"
        String strBase64 = "MA0KMA0KMA0KMA0KMA0KMA0KUHwxMDB8ODANCi0tLQ==";
        String result = controller.perolehanNilai(strBase64);
        assertTrue(result.contains("Proyek"));
    }

    @Test
    @DisplayName("perolehanNilai() dengan grade coverage")
    void perolehanNilai_WithGradeCoverage() {
        // Test hanya memastikan method tidak error untuk berbagai input
        String[] testData = {
            "MA0KMA0KMA0KMA0KMA0KODUNClBBfDEwMHw4NQ0KLS0t", // A
            "MA0KMA0KMA0KMA0KMA0KNzUNClBBfDEwMHw3NQ0KLS0t", // AB
            "MA0KMA0KMA0KMA0KMA0KNjUNClBBfDEwMHw2NQ0KLS0t", // B
            "MA0KMA0KMA0KMA0KMA0KNTUNClBBfDEwMHw1NQ0KLS0t", // BC
            "MA0KMA0KMA0KMA0KMA0KNTANClBBfDEwMHw1MA0KLS0t", // C
            "MA0KMA0KMA0KMA0KMA0KMzUNClBBfDEwMHwzNQ0KLS0t", // D
            "MA0KMA0KMA0KMA0KMA0KMjUNClBBfDEwMHwyNQ0KLS0t"  // E
        };
        
        for (String data : testData) {
            String result = controller.perolehanNilai(data);
            assertNotNull(result);
            assertTrue(result.contains("Grade:"));
        }
    }

    @Test
    @DisplayName("perolehanNilai() dengan data benar untuk grade A")
    void perolehanNilai_WithCorrectDataGradeA() {
        String strBase64 = "MTANCjEwDQoxMA0KMTANCjMwDQozMA0KUEF8MTAwfDk1DQpUfDEwMHw5MA0KS3wxMDB8ODUNCiB8MTAwfDkwDQpVVFN8MTAwfDg1DQpVQVN8MTAwfDkwDQotLS0=";     // Decoded:
        String result = controller.perolehanNilai(strBase64);
        System.out.println("Grade A Test: " + result);
        assertTrue(result.contains("Grade: A"));
    }

    @Test
    @DisplayName("perolehanNilai() multiple tests untuk cover AB dan BC")
    void perolehanNilai_MultipleTestsForABandBC() {
        // Test berbagai data yang akan melewati AB dan BC
        String[] testData = {
            // Data untuk AB range (72-79.4)
            "MTANCjEwDQoxMA0KMTANCjMwDQozMA0KUEF8MTAwfDc4DQpUfDEwMHw3NQ0KS3wxMDB8NzQNCiB8MTAwfDc0DQpVVFN8MTAwfDc2DQpVQVN8MTAwfDc4DQotLS0=",
            
            // Data untuk BC range (57-64.4)  
            "MTANCjEwDQoxMA0KMTANCjMwDQozMA0KUEF8MTAwfDYyDQpUfDEwMHw2MA0KS3wxMDB8NTgNCiB8MTAwfDU4DQpVVFN8MTAwfDYwDQpVQVN8MTAwfDYyDQotLS0=",
            
            // Data untuk exact boundary AB (72.0)
            "MTANCjEwDQoxMA0KMTANCjMwDQozMA0KUEF8MTAwfDcyDQpUfDEwMHw3Mg0KS3wxMDB8NzINCiB8MTAwfDcyDQpVVFN8MTAwfDcyDQpVQVN8MTAwfDcyDQotLS0=",
            
            // Data untuk exact boundary BC (57.0)
            "MTANCjEwDQoxMA0KMTANCjMwDQozMA0KUEF8MTAwfDU3DQpUfDEwMHw1Nw0KS3wxMDB8NTcNCiB8MTAwfDU3DQpVVFN8MTAwfDU3DQpVQVN8MTAwfDU3DQotLS0="
        };
        
        for (int i = 0; i < testData.length; i++) {
            String result = controller.perolehanNilai(testData[i]);
            System.out.println("Test " + i + " (AB/BC): " + result);
            assertNotNull(result);
            assertTrue(result.contains("Grade:"));
        }
    }


    // ===== TEST PERBEDAAN L =====
    @Test
    @DisplayName("perbedaanL() dengan matriks 1x1")
    void perbedaanL_With1x1Matrix() {
        String encoded = "MQ0KMQ==";
        String result = controller.perbedaanL(encoded);
        assertTrue(result.contains("Tidak Ada"));
    }

    @Test
    @DisplayName("perbedaanL() dengan matriks 2x2")
    void perbedaanL_With2x2Matrix() {
        String encoded = "Mg0KMSAyDQozIDQ=";
        String result = controller.perbedaanL(encoded);
        assertTrue(result.contains("Tidak Ada"));
    }

    @Test
    @DisplayName("perbedaanL() dengan matriks 3x3")
    void perbedaanL_With3x3Matrix() {
        String encoded = "Mw0KMSAyIDMNCjQgNSA2DQo3IDggOQ==";
        String result = controller.perbedaanL(encoded);
        assertTrue(result.contains("Nilai L"));
    }

    @Test
    @DisplayName("perbedaanL() dengan matriks 4x4 - nilai tengah 4 elemen")
    void perbedaanL_With4x4Matrix_ShouldCalculateFourMiddleElements() {
        String encoded = "NA0KMSAyIDMgNA0KNSA2IDcgOA0KOSAxMCAxMSAxMg0KMTMgMTQgMTUgMTY=";
        String result = controller.perbedaanL(encoded);
        assertTrue(result.contains("Nilai Tengah: 34"));
    }

    @Test
    @DisplayName("perbedaanL() dengan matriks 4x4 nilai spesifik")
    void perbedaanL_With4x4SpecificValues() {
        String encoded = "NA0KMSAxIDEgMQ0KMSAyIDIgMQ0KMSAyIDIgMQ0KMSAxIDEgMQ==";
        String result = controller.perbedaanL(encoded);
        assertTrue(result.contains("Nilai Tengah: 8"));
    }

    // ===== TEST PALING TER =====
    @Test
    @DisplayName("palingTer() dengan data normal")
    void palingTer_WithNormalData() {
        String encoded = "MQ0KMg0KMg0KMw0KMw0KMw0KLS0t";
        String result = controller.palingTer(encoded);
        assertTrue(result.contains("Tertinggi"));
    }

    @Test
    @DisplayName("palingTer() dengan data sederhana")
    void palingTer_WithSimpleData() {
        String encoded = "MTANCjIwDQozMA0KLS0t";
        String result = controller.palingTer(encoded);
        assertNotNull(result);
    }

    @Test
    @DisplayName("palingTer() dengan satu nilai")
    void palingTer_WithSingleValue() {
        String encoded = "NQ0KLS0t";
        String result = controller.palingTer(encoded);
        assertNotNull(result);
    }

    @Test
    @DisplayName("palingTer() dengan nilai lebih kecil di tengah array")
    void palingTer_WithSmallerValueInMiddle() {
        // Array: 50, 30, 40, 20, 60
        // Saat proses nilai 20, kondisi (nilai < minNilai) akan true
        String encoded = "NTANCjMwDQo0MA0KMjANCjYwDQotLS0=";
        String result = controller.palingTer(encoded);
        assertTrue(result.contains("Terendah: 20"));
    }

    @Test
    @DisplayName("palingTer() dengan mixed - ada yang lebih kecil dan sama")
    void palingTer_WithMixedSmallerAndEqual() {
        String encoded = "MTANCjUNCjUNCjMNCjgNCi0tLQ==";
        String result = controller.palingTer(encoded);
        assertTrue(result.contains("Terendah: 3"));
    }

    @Test
    @DisplayName("palingTer() dengan banyak nilai")
    void palingTer_WithManyValues() {
        // For-loop dieksekusi multiple times
        String encoded = "MTANCjIwDQozMA0KNDANCjUwDQo2MA0KNzANCjgwDQo5MA0KLS0t";
        String result = controller.palingTer(encoded);
        assertTrue(result.contains("Tertinggi: 90"));
        assertTrue(result.contains("Terendah: 10"));
    }

    @Test
    @DisplayName("palingTer() minimal test untuk coverage")
    void palingTer_MinimalTestForCoverage() {
        String[] simpleData = {
            "MTANCi0tLQ==",         // 1 value
            "MTANCjIwDQotLS0=",     // 2 values
            "MTANCjEwDQoyMA0KLS0t", // 3 values (10,10,20)
            "NQ0KMTANCjE1DQotLS0"   // 3 values (5,10,15)
        };
        
        for (String data : simpleData) {
            String result = controller.palingTer(data);
            assertNotNull(result);
            assertTrue(result.contains("Tertinggi:"));
            assertTrue(result.contains("Terendah:"));
        }
    }

    @Test
    @DisplayName("palingTer() fokus pada kondisi containsKey")
    void palingTer_FocusOnContainsKeyCondition() {
        // Data minimal yang memenuhi kondisi
        // 5,5,10 -> saat proses 5 kedua, akan cari kandidat 10
        String encoded = "NQ0KNQ0KMTANCi0tLQ==";
        String result = controller.palingTer(encoded);
        assertTrue(result.contains("Tersedikit:"));
    }

    @Test
    @DisplayName("palingTer() dengan tiga nilai sama di awal")
    void palingTer_WithThreeSameValuesAtStart() {
        // 3,3,3,6,9 -> memaksa inner loop mencari berkali-kali
        String encoded = "Mw0KMw0KMw0KNg0KOQ0KLS0t";
        String result = controller.palingTer(encoded);
        assertTrue(result.contains("Tersedikit:"));
    }

    @Test
    @DisplayName("palingTer() dengan data kompleks untuk cover semua branch")
    void palingTer_WithComplexDataForFullCoverage() {
        String encoded = "MQ0KMQ0KMjANCjMwDQozMA0KNDANCjQwDQo0MA0KNTANCjUwDQo1MA0KNjANCjYwDQo2MA0KNzANCjcxDQo3Mg0KLS0t";
        String result = controller.palingTer(encoded);
        assertNotNull(result);
    }
}