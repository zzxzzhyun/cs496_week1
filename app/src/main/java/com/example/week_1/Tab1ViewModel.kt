package com.example.week_1

import android.app.Application
import android.content.ContentUris
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.ContactsContract
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.InputStream

class Tab1ViewModel(
    application : Application
) : AndroidViewModel(application)  {

    private val context = getApplication<Application>().applicationContext
    var list : ArrayList<ListViewItem> = ArrayList()

    fun getPhoneNumbers(sort:String) : ArrayList<ListViewItem> {
        val phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI //전화번호 URI
        val projections = arrayOf(ContactsContract.CommonDataKinds.Phone.CONTACT_ID, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER)

        var wheneClause:String? = null
        var whereValues:Array<String>? = null

        val optionSort = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " $sort"

        val cursorOrNull = context?.contentResolver?.query(phoneUri,projections,wheneClause,whereValues,optionSort)
        if (cursorOrNull != null) {
            val cursor = cursorOrNull
            val idColumn = cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
            val nameColumn = cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val numberColumn = cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER)
            val nicknameColumn = cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Nickname.NAME)
            val emailColumn = cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Email.DATA)
            val foodColumn = cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Note.NOTE)


            while (cursor.moveToNext()) {
                val id = cursor.getString(idColumn)
                val name = cursor.getString(nameColumn)
                val number = featPhoneNumber(cursor.getString(numberColumn))
                val nickname = cursor.getString(nicknameColumn)
                val email = cursor.getString(emailColumn)
                val food = cursor.getString(foodColumn)

                val phoneModel = ListViewItem(name, nickname, food, email, number)
                list.add(phoneModel)
            }
            cursor.close()
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