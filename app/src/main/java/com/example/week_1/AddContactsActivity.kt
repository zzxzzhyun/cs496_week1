package com.example.week_1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.week_1.databinding.ActivityAddContactsBinding
import com.example.week_1.databinding.ActivityMainBinding
import com.example.week_1.databinding.FragmentMyBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.Manifest
import android.content.ContentProviderOperation
import android.content.ContentProviderResult
import android.content.pm.PackageManager
import android.provider.ContactsContract
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class AddContactsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddContactsBinding
    private val TAG = "CONTACT_ADD_TAG"
    private val WRITE_CONTACT_PERMISSION_CODE = 100
    private var contactPermissions = Array<String>(5){""}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        contactPermissions = arrayOf(Manifest.permission.WRITE_CONTACTS)

        binding.doneButton.setOnClickListener {
            if (isWriteContactPermissionEnabled()){
                saveContact()
            }
            else{
                requestWriteContactPermission()
            }
            val intent = Intent(this, MainActivity::class.java)
            this.startActivity(intent)
        }
    }

    private fun saveContact(){
        val name = binding.addName.text.toString()
        val nickname = binding.addNickname.text.toString()
        val phone = binding.addNumber.text.toString()
        val email = binding.addEmail.text.toString()
        val food = binding.addFood.text.toString()

        val intent = Intent(Intent.ACTION_INSERT)
        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE)
        intent.putExtra(ContactsContract.Intents.Insert.NAME, name)
        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email)
        intent.putExtra(ContactsContract.Intents.Insert.PHONE, food)

        if (intent.resolveActivity(packageManager)!=null){
            startActivity(intent)
        }else{
            Log.d(TAG, "saveContact: failed")
        }
/*
        var cpo = ArrayList<ContentProviderOperation>()
        // contact id
        var rawContactId: Int = cpo.size
        cpo.add(ContentProviderOperation.newInsert(
            ContactsContract.RawContacts.CONTENT_URI)
            .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
            .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
            .build())
        // add name
        cpo.add(ContentProviderOperation.newInsert(
            ContactsContract.RawContacts.CONTENT_URI)
            .withValueBackReference(ContactsContract.RawContacts.Data.RAW_CONTACT_ID, rawContactId)
            .withValue(ContactsContract.RawContacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
            .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name)
            .build())
        // add nickname
        cpo.add(ContentProviderOperation.newInsert(
            ContactsContract.RawContacts.CONTENT_URI)
            .withValueBackReference(ContactsContract.RawContacts.Data.RAW_CONTACT_ID, rawContactId)
            .withValue(ContactsContract.RawContacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE)
            .withValue(ContactsContract.CommonDataKinds.Nickname.NAME, nickname)
            .build())
        // add phone number (mobile)
        cpo.add(ContentProviderOperation.newInsert(
            ContactsContract.RawContacts.CONTENT_URI)
            .withValueBackReference(ContactsContract.RawContacts.Data.RAW_CONTACT_ID, rawContactId)
            .withValue(ContactsContract.RawContacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
            .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phone)
            .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
            .build())
        // add email
        cpo.add(ContentProviderOperation.newInsert(
            ContactsContract.RawContacts.CONTENT_URI)
            .withValueBackReference(ContactsContract.RawContacts.Data.RAW_CONTACT_ID, rawContactId)
            .withValue(ContactsContract.RawContacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
            .withValue(ContactsContract.CommonDataKinds.Email.DATA, email)
            .build())
        // add food
        cpo.add(ContentProviderOperation.newInsert(
            ContactsContract.RawContacts.CONTENT_URI)
            .withValueBackReference(ContactsContract.RawContacts.Data.RAW_CONTACT_ID, rawContactId)
            .withValue(ContactsContract.RawContacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE)
            .withValue(ContactsContract.CommonDataKinds.Note.NOTE, food)
            .build())

        //save contact

        this.contentResolver.applyBatch(ContactsContract.AUTHORITY, cpo)
        Log.d(TAG, "saveContact: Saved")

*/

    }

    private fun isWriteContactPermissionEnabled(): Boolean {
        val result: Boolean = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_CONTACTS) == (PackageManager.PERMISSION_GRANTED)
        return result
    }

    private fun requestWriteContactPermission(){
        ActivityCompat.requestPermissions(this, contactPermissions, WRITE_CONTACT_PERMISSION_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (grantResults.size > 0) {
            if (requestCode == WRITE_CONTACT_PERMISSION_CODE) {
                val haveWriteContactPermission: Boolean = grantResults[0] == PackageManager.PERMISSION_GRANTED
                if(haveWriteContactPermission) {
                    saveContact()
                }
            }
        }

    }


}