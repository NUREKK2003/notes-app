package com.hexagonstudio.notesapp

import android.content.ContentValues
import android.os.Bundle
import android.provider.BaseColumns
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hexagonstudio.notesapp.databinding.ActivityDetailsBinding

private lateinit var binding: ActivityDetailsBinding


//private lateinit var bindingMenu: MenuDe
class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dbHelper = DataBaseHelper(applicationContext)
        val db = dbHelper.writableDatabase


        val saveInfoToast = Toast.makeText(applicationContext, "Zapisano", Toast.LENGTH_SHORT)

        if (intent.hasExtra("title")) binding.titleDetails.setText(intent.getStringExtra("title"))
        if (intent.hasExtra("message")) binding.messageDetails.setText(intent.getStringExtra("message"))


        //zapisuje notatki

        /*
        binding.saveBTDetails.setOnClickListener() {

            val title = binding.titleDetails.text.toString()
            val message = binding.messageDetails.text.toString()

            val value = ContentValues()

            value.put(TableInfo.TABLE_COLUMN_TITLE, title)
            value.put(TableInfo.TABLE_COLUMN_MESSAGE, message)

            if (intent.hasExtra("ID")) {
                db.update(TableInfo.TABLE_NAME, value, BaseColumns._ID + "=?",
                        arrayOf(intent.getStringExtra("ID")))
            } else {
                if (!title.isNullOrEmpty() || !message.isNullOrEmpty()) {

                    db.insertOrThrow(TableInfo.TABLE_NAME, null, value)
                    saveInfoToast.show()
                } else {
                    Toast.makeText(applicationContext, "Nie ma czego zapisać!!!", Toast.LENGTH_SHORT)
                }
            }


        }*/

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_details, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item?.itemId==R.id.save_button){

            val dbHelper = DataBaseHelper(applicationContext)
            val db = dbHelper.writableDatabase

            val title = binding.titleDetails.text.toString()
            val message = binding.messageDetails.text.toString()

            val value = ContentValues()

            value.put(TableInfo.TABLE_COLUMN_TITLE, title)
            value.put(TableInfo.TABLE_COLUMN_MESSAGE, message)

            if (intent.hasExtra("ID")) {
                db.update(TableInfo.TABLE_NAME, value, BaseColumns._ID + "=?",
                        arrayOf(intent.getStringExtra("ID")))
            } else {
                if (!title.isNullOrEmpty() || !message.isNullOrEmpty()) {
                    db.insertOrThrow(TableInfo.TABLE_NAME, null, value)
                } else {
                    Toast.makeText(applicationContext, "Nie ma czego zapisać!!!", Toast.LENGTH_SHORT)
                }
            }
            db.close()
            onBackPressed()



        }
        return super.onOptionsItemSelected(item)
    }
}