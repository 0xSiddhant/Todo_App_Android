package com.example.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(
            R.menu.main_menu,
            menu
        )

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //same as onclick listener
        //called when any menu item is tapped of the activity
        when(item.itemId) {
            R.id.history -> {
                startActivity(Intent(
                    this,
                    HistoryActivity::class.java
                ))
            }
        }

        return super.onOptionsItemSelected(item)
    }
}