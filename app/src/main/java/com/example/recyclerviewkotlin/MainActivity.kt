package com.example.recyclerviewkotlin

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var dataList: ArrayList<DataClass>
    lateinit var imageList:Array<Int>
    lateinit var titleList:Array<String>
    lateinit var descList: Array<String>
    lateinit var detailImageList: Array<Int>
    private lateinit var myAdapter: AdapterClass
    private lateinit var searchView: SearchView
    private lateinit var searchList: ArrayList<DataClass>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val homeButton: ImageButton = findViewById(R.id.homeButton)
        val searchButton: ImageButton = findViewById(R.id.searchButton)
        val savedDataButton: ImageButton = findViewById(R.id.savedDataButton)

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

        imageList = arrayOf(
            R.drawable.green,
            R.drawable.tree,
            R.drawable.happy,
            R.drawable.pantai,
            R.drawable.sungai,
            R.drawable.sehat,
            R.drawable.pantaisur,
            R.drawable.bumi,
            R.drawable.clean,
            R.drawable.pantaimang)

        titleList = arrayOf(
            "Taman Menteng\nKota jakarta\nTanggal 5 juni 2024\nNo 2390",
            "Malioboro\nKota Yogyakarta\nTanggal 25 juni 2024\nNo 3456",
            "Kawasan Dago Pakar\nKota Bandung\nTanggal 20 Februari 2024\nNo 2800",
            "Pantai Mutun\nKota Bandar Lampung\nTanggal 2 Juli 2024\nNo 3412",
            "Sungai Deli\nKota Medan\nTanggal 25 Juni 2024\nNo 2000",
            "Benteng Kuto Besak\nKota Palembang\nTanggal 10 Mei 2024\nNo 3409",
            "Pantai Kenjeran\nKota Surabaya\nTanggal 25 Agustus 2024\nNo 4800",
            "Taman Indonesia Kaya\nKota Semarang\nTanggal 22 April 2024\nNo 5680",
            "Pantai Losari\nKota Makassar\nTanggal 4 September 2024\nNo 1290",
            "Pantai Manggar\nKota Balikpapan\nTanggal 10 Mei 2024\nNo 9867")

        descList = arrayOf(
            getString(R.string.bersihjakarta),
            getString(R.string.bersihjogja),
            getString(R.string.bersihbandung),
            getString(R.string.bersihlampung),
            getString(R.string.bersihmedan),
            getString(R.string.bersihpalembang),
            getString(R.string.bersihsurabaya),
            getString(R.string.bersihsemarang),
            getString(R.string.bersihmakasar),
            getString(R.string.bersihbalik))

        detailImageList = arrayOf(
            R.drawable.green,
            R.drawable.tree,
            R.drawable.happy,
            R.drawable.pantai,
            R.drawable.sungai,
            R.drawable.sehat,
            R.drawable.pantaisur,
            R.drawable.bumi,
            R.drawable.clean,
            R.drawable.pantaimang)

        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.search)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        dataList = arrayListOf<DataClass>()
        searchList = arrayListOf<DataClass>()
        getData()

        val query = intent.getStringExtra("query")
        if (query != null) {
            searchView.setQuery(query, true)
        }

        searchView.clearFocus()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchList.clear()
                val searchText = newText!!.toLowerCase(Locale.getDefault())
                if (searchText.isNotEmpty()) {
                    dataList.forEach {
                        if (it.dataTitle.toLowerCase(Locale.getDefault()).contains(searchText)) {
                            searchList.add(it)
                        }
                    }
                    recyclerView.adapter!!.notifyDataSetChanged()
                } else {
                    searchList.clear()
                    searchList.addAll(dataList)
                    recyclerView.adapter!!.notifyDataSetChanged()
                }
                return false
            }
        })

        myAdapter = AdapterClass(searchList)
        recyclerView.adapter = myAdapter

        myAdapter.onItemClick = {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("android", it)
            startActivity(intent)
        }
    }

    private fun getData() {
        for (i in imageList.indices) {
            val dataClass = DataClass(imageList[i], titleList[i], descList[i], detailImageList[i])
            dataList.add(dataClass)
        }
        searchList.addAll(dataList)
        recyclerView.adapter = AdapterClass(searchList)
    }
}
