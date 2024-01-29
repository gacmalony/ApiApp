package com.example.apiapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val text = findViewById<TextView>(R.id.textView)


        val retrofitService = RetrofitInstance
            .getRetrofitInstance()
            .create(AlbumService::class.java)


        val rliveData:LiveData<Response<Albums>> =
            liveData {
               // val response = retrofitService.getAlbums()
                val response = retrofitService.getSpecificAlbums(2)
                emit(response)
            }

        rliveData.observe(this, Observer {
            val albumsList = it.body()?.listIterator()

            if(albumsList != null){
                while (albumsList.hasNext()){
                    val albumItem = albumsList.next()
                    Log.i("TAGK", albumItem.title)

                    val resultobject = "Album Titles: ${albumItem.title}\n"

                    text.append(resultobject)

                }
            }
        })

    }
}