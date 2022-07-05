package com.example.week_1

import android.app.Application
import android.content.ContentResolver
import android.os.Build
import android.provider.ContactsContract
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel


class Tab1ViewModel(
    application : Application
) : AndroidViewModel(application)  {

    val context = getApplication<Application>().applicationContext
    var list : MutableList<ListViewItem> = ArrayList()

    @RequiresApi(Build.VERSION_CODES.M)
    fun getPhoneNumbers(sort:String, searchName:String?) : MutableList<ListViewItem> {
        var wheneClause:String? = null
        var whereValues:Array<String>? = null

        var ids = mutableListOf<String>()
        var name = ""
        var number = ""
        var nickname = ""
        var email = ""
        var food = ""
        val optionSort = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " $sort"

        val cr = context?.contentResolver
        val cur = cr?.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, optionSort)
        val phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val emailUri = ContactsContract.CommonDataKinds.Email.CONTENT_URI

        if (cur != null) {
            if (cur.getCount() > 0) {
                val idColumn = cur.getColumnIndexOrThrow(ContactsContract.Data._ID)
                while (cur.moveToNext()) {
                    ids.add(cur.getString(idColumn))
                }
            }
            cur.close()
        }

        for (id in ids){
            val nameCursor = cr?.query(phoneUri,arrayOf(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME),ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " like ?",arrayOf(id),optionSort)
            if (nameCursor != null){
                val cursor = nameCursor
                val nameColumn =
                    cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                while (cursor.moveToNext()) {
                    name = (cursor.getString(nameColumn))
                }
                cursor.close()
            } else{
                name = ""
            }

            val phoneCursor = cr?.query(phoneUri,arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER),ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " like ?",arrayOf(id),optionSort)
            if (phoneCursor != null){
                val cursor = phoneCursor
                val numberColumn =
                    cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER)
                while (cursor.moveToNext()) {
                    number = (featPhoneNumber(cursor.getString(numberColumn)))
                }
                cursor.close()
            } else {
                number = ""
            }

            val emailCursor = cr?.query(emailUri, arrayOf(ContactsContract.CommonDataKinds.Email.DATA),ContactsContract.CommonDataKinds.Email.CONTACT_ID +" like ?", arrayOf(id)
                ,null )
            if (emailCursor != null){
                val cursor = emailCursor
                val emailcolumn =
                    cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Email.DATA)
                while (cursor.moveToNext()) {
                    email = (cursor.getString(emailcolumn))
                }
                cursor.close()
            } else {
                email = ""
            }

            val noteCursor = cr?.query(ContactsContract.Data.CONTENT_URI, arrayOf(ContactsContract.CommonDataKinds.Note.NOTE),ContactsContract.Data.CONTACT_ID +" like ?", arrayOf(id)
                ,null )
            if (noteCursor != null){
                val cursor = noteCursor
                val foodcolumn =
                    cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Note.NOTE)
                while (cursor.moveToNext()) {
                    food = (cursor.getString(foodcolumn))
                }
                cursor.close()
            }

            val nickCursor = cr?.query(ContactsContract.Data.CONTENT_URI, arrayOf(ContactsContract.CommonDataKinds.Nickname.NAME),ContactsContract.Data.CONTACT_ID +" like ?", arrayOf(id)
                ,null )
            if (nickCursor != null){
                val cursor = nickCursor
                val nickcolumn =
                    cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Nickname.NAME)
                while (cursor.moveToNext()) {
                    nickname = (cursor.getString(nickcolumn))
                }
                cursor.close()
            } else {
                nickname = ""
            }

            val phoneModel = ListViewItem(name, "nickname","food",email, number)
            list.add(phoneModel)

        }
        return list
    }


    fun featPhoneNumber(number : String) : String {
        var phone : String = number
        if (number.length == 11) {
            phone = number.substring(0,3) + "-" + number.substring(3,7) + "-" + number.substring(7,11)
        }
        else if (number.length == 10) {
            phone = number.substring(0,3) + "-" + number.substring(3,6) + "-" + number.substring(6,10)
        }
        return phone
    }


}