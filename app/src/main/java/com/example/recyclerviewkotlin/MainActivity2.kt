package com.example.recyclerviewkotlin


import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

class MainActivity2 : AppCompatActivity() {

    private lateinit var highlightViewPager: ViewPager2
    private lateinit var newsRecyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        highlightViewPager = findViewById(R.id.highlightViewPager)
        newsRecyclerView = findViewById(R.id.newsRecyclerView)

        val homeButton: ImageButton = findViewById(R.id.homeButton)
        val searchButton: ImageButton = findViewById(R.id.searchButton)
        val savedDataButton: ImageButton = findViewById(R.id.saveButton)

        homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }

        searchButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        savedDataButton.setOnClickListener {
            val intent = Intent(this, SavedDataActivity::class.java)
            startActivity(intent)
        }

        viewPagerAdapter = ViewPagerAdapter(getHighlightImages())
        highlightViewPager.adapter = viewPagerAdapter

        newsRecyclerView.layoutManager = LinearLayoutManager(this)
        newsRecyclerView.setHasFixedSize(true)
        newsAdapter = NewsAdapter(getNewsList())
        newsRecyclerView.adapter = newsAdapter
    }

    private fun getHighlightImages(): List<Int> {
        return listOf(
            R.drawable.highlight_image1,
            R.drawable.highlight_image2,
            R.drawable.highlight_image3
        )
    }

    private fun getNewsList(): List<News> {
        return listOf(
            News("Ahli Kasih Masukan soal Penanganan Sampah, Ini Caranya\n", "Jakarta - Timbulan sampah kronis di berbagai provinsi dan kabupaten/kota, selain mengganggu estetika dan higienitas karena bertebaran di pinggir sawah, sungai, danau, pesisir, laut, jalan raya, hingga hutan. Sampah juga memicu bencana seperti longsoran sampah, pencemaran leachate, pencemaran udara, bau busuk, hingga ledakaan gas metan.\n" +
                    "Ahli pengelolaan kualitas udara yang juga beraktivitas di KOMNAS HAM Dr Esrom Hamonangan, mengatakan mengurangi timbulan sampah dapat dilakukan dengan berbagai cara, seperti merancang dan merencanakan proses industrialisasi produk dengan material yang berpotensi menjadi sampah.\n" + "Selain itu mengembangkan pola konsumsi secara menyeluruh, global dan holistik dalam lingkup makro kemudian diturunkan menjadi berbagai kegiatan teknis pada tingkat mikro.", R.drawable.highlight_image1),
            News("Bersih-bersih Sungai Citarum, Pemprov Jabar Sampai Terjunkan 400 Personel\n", "Jakarta - Pemerintah Provinsi Jawa Barat (Pemprov Jabar) menerjunkan sejumlah alat berat guna mempercepat proses pembersihan dan pengangkatan sampah di aliran Sungai Citarum, tepatnya di bawah Jembatan Babakan Sapan penghubung Kecamatan Batujajar dan Cihampelas, Kabupaten Bandung Barat.\n" +
                    "Adapun alat berat yang diturunkan pada kegiatan bersih-bersih serta penataan lingkungan di Sungai Citarum Dansektor 9 pada Jumat (14/5), di antaranya excavator long arm, excavator amphibi, dan excavator standart. Alat berat tersebut berasal dari Dansektor 9, Dansektor 8, dan IP Saguling POMU.", R.drawable.highlight_image3),
            News("Ketika Ford Ranger Dijadikan 'Tong Sampah' Warga\n", "Jakarta - Ford Ranger kehilangan 'harga diri' di Filipina. Sebab, mobil double-cabin tersebut dijadikan tempat sampah warga setelah kedapatan parkir sembarangan!\n" +
                    "Dilansir dari laman Carscoops, Selasa (18/6), Ford Ranger di Filipina itu menjadi sasaran kemarahan warga setelah ditemukan parkir liar di zona pembuangan sampah. Kantong-kantong sampah ditumpuk di bagian belakang pikap berukuran sedang, menghalangi kendaraan dari semua sisi.", R.drawable.highlight_image3),
        )
    }
}
