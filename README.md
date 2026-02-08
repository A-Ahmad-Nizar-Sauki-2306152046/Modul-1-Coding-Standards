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