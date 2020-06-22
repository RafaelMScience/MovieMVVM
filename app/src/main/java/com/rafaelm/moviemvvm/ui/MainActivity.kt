package com.rafaelm.moviemvvm.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rafaelm.moviemvvm.R
import com.rafaelm.moviemvvm.ui.single_movie_details.SingleMovie
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn.setOnClickListener {
            val intent = Intent( this, SingleMovie::class.java)
            intent.putExtra("id", 554993)
            this.startActivity(intent)
        }
    }
}