package com.hexagonstudio.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.hexagonstudio.notesapp.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
    fun onClickTakeNotes(v: View){
        val intent = Intent(applicationContext, DetailsActivity::class.java)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        val dbHelper = DataBaseHelper(applicationContext)
        val db = dbHelper.writableDatabase

        val cursor = db.query(TableInfo.TABLE_NAME,
            null,null,null,
            null,null,null)

        val notes = ArrayList<Note>()

        if(cursor.count>0){
            cursor.moveToFirst()
            while (!cursor.isAfterLast){
                val note = Note()
                note.id = cursor.getInt(0)
                note.title = cursor.getInt(1).toString()
                note.message = cursor.getInt(2).toString()
                notes.add(note)
                cursor.moveToNext()
            }
        }
        cursor.close()

        binding.recylerView.layoutManager = GridLayoutManager(applicationContext,2)
        binding.recylerView.adapter = CardViewAdapter(applicationContext, db, notes)
    }
}