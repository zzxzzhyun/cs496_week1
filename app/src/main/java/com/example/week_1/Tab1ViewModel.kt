package com.example.week_1

import android.Manifest
import android.app.Application
import android.os.Build
import android.provider.ContactsContract
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel


class Tab1ViewModel(
    application : Application
) : AndroidViewModel(application)  {

    val context = getApplication<Application>().applicationContext
    var list : MutableList<ListViewItem> = ArrayList()
    val permissions = arrayOf(
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.WRITE_CONTACTS)

    @RequiresApi(Build.VERSION_CODES.M)
    fun getPhoneNumbers(sort:String, searchName:String?) : MutableList<ListViewItem> {

        val phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val projections = arrayOf(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER)

        var wheneClause:String? = null
        var whereValues:Array<String>? = null

        var id: String=""
        var name = mutableListOf<String>()
        var number= mutableListOf<String>()
        var nickname = mutableListOf<String>()
        var email = mutableListOf<String>()
        var food = mutableListOf<String>()
        val optionSort = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " $sort"

        val cursorOrNull = context?.contentResolver?.query(phoneUri,projections,wheneClause,whereValues,optionSort)
        if (cursorOrNull != null) {
            val cursor = cursorOrNull
            val nameColumn = cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val numberColumn = cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER)
            while (cursor.moveToNext()) {
                name.add(cursor.getString(nameColumn))
                number.add(featPhoneNumber(cursor.getString(numberColumn)))
            }
            cursor.close()
        }

        val emailUri = ContactsContract.CommonDataKinds.Email.CONTENT_URI
        val emailprojections = arrayOf(ContactsContract.CommonDataKinds.Email.DATA)
        val emailcursorOrNull = context?.contentResolver?.query(emailUri,emailprojections,wheneClause,whereValues,optionSort)
        if (emailcursorOrNull != null) {
            val cursor = emailcursorOrNull
            val emailColumn = cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Email.DATA)
            while (cursor.moveToNext()) {
                email.add(cursor.getString(emailColumn))
            }
            cursor.close()
        }


        val otherUri = ContactsContract.Data.CONTENT_URI
        val otherproj = arrayOf(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME)

        val othercursorOrNull = context?.contentResolver?.query(otherUri,otherproj,null ,whereValues,optionSort)
        if (othercursorOrNull != null) {
            val cursor = othercursorOrNull
            val otherColumn = cursor.getColumnIndexOrThrow(ContactsContract.Data.DATA1)
            while (cursor.moveToNext()) {
                nickname.add(cursor.getString(otherColumn))
            }
            cursor.close()
        }


        for (i in name.indices){
            val phoneModel = ListViewItem(name[i], nickname[i*5+3],nickname[i*5+4],email[i], number[i])
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