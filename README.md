<div align="center">

# 🛒 E-Shop (Advanced Programming)
**Tutorial & Exercise Reflections**

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/spring%20boot-%236DB33F.svg?style=for-the-badge&logo=springboot&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-%23005F0F.svg?style=for-the-badge&logo=Thymeleaf&logoColor=white)

Created by **Ahmad Nizar Sauki** | **2306152046**
*Fakultas Ilmu Komputer, Universitas Indonesia*

---
</div>

## 📚 Table of Contents
1. [Reflection 1: Clean Code & Secure Coding](#reflection-1)
2. [Reflection 2: Unit Testing & Functional Test Clean Code](#reflection-2)

---

### Reflection 1

#### 1. Clean Code Principles
Dalam pengerjaan tugas ini, saya telah menerapkan beberapa prinsip *Clean Code* untuk menjaga kualitas dan keterbacaan kode:

* **Meaningful Names (Penamaan yang Jelas):**
  Saya menghindari penggunaan variabel satu huruf. Semua variabel diberi nama yang deskriptif sesuai tujuannya.
  * **Repository vs Service Naming:** Saya menerapkan strategi penamaan yang berbeda sesuai konteks. Pada *Repository*, method diberi nama sederhana (seperti `create`, `delete`, `edit`) karena pemanggilannya sudah cukup jelas (misal: `productRepository.delete(productId)`). Namun, pada *Service*, saya menggunakan nama yang lebih spesifik (seperti `deleteProductById`). Hal ini dilakukan untuk menghindari ambiguitas di masa depan jika *ProductService* berkembang menjadi lebih kompleks, sehingga pemanggilan `service.deleteProductById(...)` lebih eksplisit maknanya dibandingkan hanya `service.delete(...)`.

* **Single Responsibility Principle:**
  Saya telah memisahkan tanggung jawab kode ke dalam package yang sesuai: `Controller` (mengatur request), `Service` (logika bisnis), dan `Repository` (akses data). Setiap class hanya memiliki satu alasan untuk berubah.

* **Functions:**
  Method yang saya buat dijaga agar tetap pendek dan hanya melakukan satu hal (*do one thing*). Contohnya method `delete` hanya fokus menghapus data tanpa melakukan validasi input yang berlebihan (validasi dilakukan di layer lain atau method terpisah).

#### 2. Secure Coding Practices
Saya menerapkan praktik keamanan dasar pada manajemen ID produk:

* **UUID vs Sequential Integer:** Saya menggunakan `UUID` (Universally Unique Identifier) yang digenerate secara random dan dikonversi menjadi String, alih-alih menggunakan integer berurut. Hal ini dilakukan untuk mencegah *Insecure Direct Object References (IDOR)* atau penebakan ID produk (enumeration attack).
* **Output Encoding/Limitation:** Pada halaman list produk, saya hanya menampilkan 8 karakter pertama dari UUID untuk alasan kerapian (*readability*) dan meminimalisir eksposur data ID mentah secara penuh ke pengguna, meskipun ID penuh tetap digunakan di URL untuk keperluan editing/deleting.

#### 3. Version Control & Workflow Management
Saya menyadari pentingnya *version control* yang rapi dalam pengembangan fitur:

* **Feature Branching:** Saya membuat branch terpisah untuk setiap fitur (`list-product`, `edit-product`, `delete-product`) dan baru melakukan *merge* ke `main` setelah fitur tersebut selesai dan stabil.
* **Atomic Commits:** Saya melakukan commit secara parsial dan berkala (misalnya, setelah selesai mengupdate Controller, saya langsung commit sebelum lanjut ke file lain). Hal ini membuat *git history* menjadi rapi dan memudahkan pelacakan perubahan jika terjadi *error*.

#### 4. Evaluasi & Perbaikan (Self-Reflection)
* **Kompleksitas Fitur:** Saya menyadari bahwa implementasi fitur *Delete* ternyata lebih sederhana dibandingkan fitur *Edit*. Fitur *Edit* memerlukan logika tambahan untuk mencari ID produk terlebih dahulu (*retrieval*) dan melakukan *mapping* data baru ke data lama, sedangkan *Delete* hanya memerlukan ID untuk menghapus data dari list.
* **Naming Clarity:** Selama proses *debugging*, saya tidak menemukan penamaan variabel yang membingungkan, yang menandakan bahwa prinsip *Meaningful Names* sudah cukup membantu saya dalam memelihara kode ini.

---

### Reflection 2

#### 1. Unit Testing
Setelah menulis unit test untuk fitur Edit dan Delete, saya merasa lebih percaya diri dalam memastikan keandalan kode saya. Namun, saya juga mempelajari bahwa **100% Code Coverage tidak menjamin kode bebas dari bug atau error**.

bagi saya code coverage hanya bisa mengukur persentase baris kode yang dieksekusi selama pengujian, tetapi tidak memverifikasi kebenaran logika di dalamnya, karena ada contoh seperti ini:
> **Contoh:** Pada fitur Edit, misalnya saya menulis kode untuk memperbarui data produk:
> `this.quantity = newProduct.getQuantity();`
> tetapi saya **lupa** menulis baris untuk memperbarui nama produk (`this.name = ...`).
>
> Jika unit test saya hanya mengecek apakah kuantitas berubah (tanpa mengecek apakah nama juga berubah), maka test akan *Passed* dan coverage code tersebut **100%** (karena baris update quantity dieksekusi). Padahal, secara logika bisnis, kode tersebut salah karena gagal memperbarui nama produk. Ini menunjukkan bahwa kualitas *assertion* dalam test jauh lebih penting daripada sekadar angka coverage.

#### 2. Clean Code in Functional Tests
Terkait tantangan pembuatan functional test baru untuk memverifikasi jumlah item dalam daftar produk:

* **Masalah (Code Duplication):**
  Jika saya membuat class functional test baru dan menyalin (*copy-paste*) semua kode setup dari `CreateProductFunctionalTest.java` (seperti konfigurasi `baseUrl`, `serverPort`, dan `@BeforeEach`), hal ini akan melanggar prinsip **DRY (Don't Repeat Yourself)**. Duplikasi kode akan menurunkan kualitas kode dan menyulitkan proses *maintenance*. Misalnya, jika konfigurasi port berubah, saya harus mengubahnya secara manual di setiap file test, lalu jika kedepannya membuat functional test dengan setup yang sama akan selalu memerlukan copy paste setup yang sama dan ini benar-benar tidak efisien.

* **Solusi (Inheritance):**
  menurut saya untuk mengatasi masalah kebersihan kode tersebut, saya menerapkan teknik **Inheritance** (Pewarisan).
  1. Saya membuat sebuah kelas dasar bernama `BaseFunctionalTest`. Kelas ini bertanggung jawab menangani semua konfigurasi setup umum, seperti inisialisasi `baseUrl`, port, dan driver Selenium.
  2. Class functional test lainnya (seperti `CreateProductFunctionalTest` atau test suite baru lainnya) cukup melakukan **extends** terhadap `BaseFunctionalTest`, begitu juga jika kedepannya ada yang memerlukan setup umum.

  Dengan ini, kode setup hanya perlu ditulis satu kali di `BaseFunctionalTest` dan dapat digunakan kembali oleh semua kelas turunan, menjadikan kode lebih bersih, terstruktur, dan mudah dipelihara.