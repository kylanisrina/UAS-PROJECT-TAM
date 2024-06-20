package com.example.recyclerviewkotlin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONObject

class SavedDataActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var savedDataList: ArrayList<DataClass>
    private lateinit var myAdapter: AdapterClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_data)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        savedDataList = arrayListOf()
        loadData()

        myAdapter = AdapterClass(savedDataList)
        recyclerView.adapter = myAdapter

        myAdapter.onItemClick = { data ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("android", data)
            startActivity(intent)
        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                removeData(position)
                savedDataList.removeAt(position)
                myAdapter.notifyItemRemoved(position)
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun loadData() {
        val sharedPreferences = getSharedPreferences("savedData", Context.MODE_PRIVATE)
        val savedDataJson = sharedPreferences.getString("savedList", null)
        savedDataList = if (savedDataJson != null) {
            val jsonArray = JSONArray(savedDataJson)
            ArrayList<DataClass>().apply {
                for (i in 0 until jsonArray.length()) {
                    add(jsonArray.getJSONObject(i).toDataClass())
                }
            }
        } else {
            arrayListOf()
        }
    }

    private fun removeData(position: Int) {
        val sharedPreferences = getSharedPreferences("savedData", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val savedDataJson = sharedPreferences.getString("savedList", null)
        val jsonArray = if (savedDataJson != null) {
            JSONArray(savedDataJson)
        } else {
            JSONArray()
        }
        jsonArray.remove(position)
        editor.putString("savedList", jsonArray.toString())
        editor.apply()
    }

    private fun JSONObject.toDataClass(): DataClass {
        return DataClass(
            getInt("dataImage"),
            getString("dataTitle"),
            getString("dataDesc"),
            getInt("dataDetailImage")
        )
    }

    private fun JSONArray.remove(position: Int) {
        val newArray = JSONArray()
        for (i in 0 until length()) {
            if (i != position) {
                newArray.put(get(i))
            }
        }
        clear()
        for (i in 0 until newArray.length()) {
            put(newArray.get(i))
        }
    }

    private fun clear() {
        TODO("Not yet implemented")
    }
}
