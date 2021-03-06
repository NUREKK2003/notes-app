package com.hexagonstudio.notesapp

import android.app.Service
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.hexagonstudio.notesapp.databinding.CardViewBinding
import java.security.Provider

private lateinit var binding: CardViewBinding



class CardViewAdapter(val context: Context, val db:SQLiteDatabase, var notes: ArrayList<Note>): RecyclerView.Adapter<MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = CardViewBinding.inflate(LayoutInflater.from(parent.context),parent,false) /// słuzy do podpięcia widoku kart
        return MyViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //elementy pojedynczej notatki--------
        val cardView_note = holder.binding.noteCardView
        val title = holder.binding.titleCard
        val message = holder.binding.messangeCard
        val context: Context = holder.binding.root.context
        //Wczytywanie treści----------------------------

        title.setText(notes[holder.adapterPosition].title)
        message.setText(notes[holder.adapterPosition].message)


        //Wczytywanie treści----------------------
        /*
        val cursor = db.query(TableInfo.TABLE_NAME,null,
            BaseColumns._ID+"=?", arrayOf(holder.adapterPosition.plus(1).toString()),
            null,null,null)

        if(cursor.moveToFirst()){
            if(!(cursor.getString(1).isNullOrEmpty() && cursor.getString(2).isNullOrEmpty())){
                title.setText(cursor.getString(1))
                message.setText(cursor.getString(2))
            }
        }*/
        //--------------------------------------------

        cardView_note.setOnClickListener{
            val intent_edit = Intent(context,DetailsActivity::class.java)

            val title_edit = notes[holder.adapterPosition].title
            val message_edit = notes[holder.adapterPosition].message
            val id_edit = notes[holder.adapterPosition].id.toString()

            intent_edit.putExtra("title",title_edit)
            intent_edit.putExtra("message",message_edit)
            intent_edit.putExtra("ID",id_edit)

            context.startActivity(intent_edit)

        }

        cardView_note.setOnLongClickListener(object : View.OnLongClickListener{
            override fun onLongClick(v: View?): Boolean {
                //usuwanie-------------------
                db.delete(TableInfo.TABLE_NAME,
                    BaseColumns._ID+"=?",
                    arrayOf(notes[holder.adapterPosition].id.toString()))

                notes.removeAt(holder.adapterPosition)
                notifyItemRemoved(holder.adapterPosition)

                //-----kopiowanie--------------
                 /*
                val copy_info = Toast.makeText(context,"Skopiowano",Toast.LENGTH_SHORT)
                val cm = context.getSystemService(Service.CLIPBOARD_SERVICE) as ClipboardManager
                val clipdata = ClipData.newPlainText("CopyText",
                "Title: "+ title.text+"\n"+"Message: "+message.text)
                cm.setPrimaryClip(clipdata)
                copy_info.show()*/
                //---------------------------
                return true
            }

        })

    }

    override fun getItemCount(): Int {
        val cursor = db.query(TableInfo.TABLE_NAME,null,
                null, null,
                null,null,null)
        val liczbaWierszy = cursor.count
        cursor.close()
        return liczbaWierszy
    }

}
class MyViewHolder(val binding: CardViewBinding): RecyclerView.ViewHolder(binding.root){

}
















