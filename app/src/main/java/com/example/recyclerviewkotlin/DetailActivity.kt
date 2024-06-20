package com.example.recyclerviewkotlin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayOutputStream

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val getData = intent.getParcelableExtra<DataClass>("android")
        if (getData != null) {
            val detailTitle: TextView = findViewById(R.id.detailTitle)
            val detailDesc: TextView = findViewById(R.id.detailDesc)
            val detailImage: ImageView = findViewById(R.id.detailImage)
            val savedDataButton: ImageButton = findViewById(R.id.savedDataButton)
            val shareButton: ImageButton = findViewById(R.id.shareButton)

            savedDataButton.setOnClickListener {
                saveData(getData)
                val intent = Intent(this, SavedDataActivity::class.java)
                startActivity(intent)
            }

            detailTitle.text = getData.dataTitle
            detailDesc.text = getData.dataDesc
            detailImage.setImageResource(getData.dataDetailImage)
            shareButton.setOnClickListener {
                shareData(getData)
            }
        }
    }

    private fun shareData(data: DataClass) {
        val drawable = ContextCompat.getDrawable(this, data.dataDetailImage)
        val bitmap = (drawable as BitmapDrawable).bitmap
        val uri = getImageUri(this, bitmap)

        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "${data.dataTitle}\n\n${data.dataDesc}")
            putExtra(Intent.EXTRA_STREAM, uri)
            type = "image/png,jpg"
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        startActivity(Intent.createChooser(shareIntent, "Share via"))
    }

    private fun getImageUri(context: Context, bitmap: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
        return Uri.parse(path)
    }





    private fun saveData(data: DataClass) {
        val sharedPreferences = getSharedPreferences("savedData", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val savedDataList = getSavedDataList(sharedPreferences)
        savedDataList.put(data.toJSONObject())

        editor.putString("savedList", savedDataList.toString())
        editor.apply()
    }

    private fun getSavedDataList(sharedPreferences: SharedPreferences): JSONArray {
        val savedDataJson = sharedPreferences.getString("savedList", null)
        return if (savedDataJson != null) {
            JSONArray(savedDataJson)
        } else {
            JSONArray()
        }
    }

    private fun DataClass.toJSONObject(): JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put("dataImage", dataImage)
        jsonObject.put("dataTitle", dataTitle)
        jsonObject.put("dataDesc", dataDesc)
        jsonObject.put("dataDetailImage", dataDetailImage)
        return jsonObject
    }
}
