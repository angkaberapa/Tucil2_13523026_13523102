# Tugas Kecil 2 Strategi Algoritma
## Daftar Isi
1. [Tentang Projek](#tentang-projek)
2. [Requirements](#requirement)
3. [Cara Menjalankan dan Kompilasi](#cara-menjalankan-dan-kompilasi)
4. [Kelompok](#kelompok)


## Tentang Projek
Tugas Kecil 2 Strategi Algoritma menilai mengenai implementasi algoritma Divide and Conquer pada kompresi gambar. Kompresi gambar dilakukan dengan memanfaatkan struktur data QuadTree. Quadtree adalah struktur data hierarkis yang digunakan untuk membagi ruang atau data menjadi bagian yang lebih kecil. Program adalah berbasis CLI dengan implementasi dengan bahasa Java, paradigma prosedural. Hasil dari kompresi gambar disimpan pada file output pilihan pengguna.

## Requirement
1. Java Development Kit (JDK) 17 atau lebih
2. Maven (https://maven.apache.org/download.cgi)

## Cara Menjalankan dan Kompilasi
1. Clone repository ini dengan menjalankan perintah di bawah ini
   ```sh
   git clone https://github.com/BerthaSoliany/IF2150-2024-K02-G10-Tunaz.git

2. Membuka folder hasil clone di IDE.

3. Membuka file pom.xml dan menyesuaikan versi JDK yang digunakan pada bagian build (line 52 dan 53)
4. Menyesuaikan pula versi maven yang digunakan
5. Menjalankan perintah berikut untuk memulai program
  ```
  mvn clean compile exec:java
  ```
6. Memasukkan nama file yang ingin dikompresi
7. Memilih metode error yang diinginkan dan memberikan nilai threshold, ukuran blok minimum, dan target persentase kompresi.
8. Masukkan nama atau path gambar hasil kompresl
9. Menunggu hasil dari kompresi gambar. Bila kompresi telah selesai dilakukan, hasil output akan berada di path yang diberikan. Hasil ini juga bersamaan dengan file .GIF yang merupakan visualisasi pembentukan Quadtree dalam kompresi gambar
> [!Note]
> Pembagian dilakukan dari sudut kiri atas terlebih dahulu sehingga GIF juga akan memulai dari sana. Dikarenakan algoritma yang digunakan, sangat disarankan untuk menggunakan ukuran blok minimum yang besar untuk melihat hasil GIF yang jelas. Selain itu, GIF dibuat dari banyak frame. Kondisi frame yang disimpan untuk GIF adalah ketika node merupakan kelipatan 50. Jika ingin melihat lebih detail, dapat mengubah kode pada file QuadTreeNode.java baris 34 di dalam prosedur divide dan mengubah 100 yang ada pada `ImageData.totalNodes % 100 == 0`


## Kelompok
| Nama | NIM |
|-----|------|
| Bertha Soliany Frandi | 13523026 |
| Michael Alexander Angkawijaya | 13523102 |

