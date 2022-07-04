package com.example.week_1

import android.app.Activity
import android.content.ContentProviderOperation
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.week_1.databinding.ActivityAddContactsBinding


class AddContactsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contacts)

        var createButtonName = findViewById(R.id.add_name) as EditText
        var createButtonNumber = findViewById(R.id.add_number) as EditText
        var createButtonNickname = findViewById(R.id.add_nickname) as EditText
        var createButtonEmail = findViewById(R.id.add_email) as EditText
        var createButtonFood = findViewById(R.id.add_food) as EditText
        var createPhoneButton = findViewById(R.id.doneButton) as Button

        createPhoneButton.setOnClickListener {
            var name: String = createButtonName.text.toString()
            var phoneNumber: String = createButtonNumber.text.toString()
            var nickname: String = createButtonNickname.text.toString()
            var email: String = createButtonEmail.text.toString()
            var food: String = createButtonFood.text.toString()

            if (name == "" && phoneNumber == "" && nickname=="" && email=="" && food=="") {
                Toast.makeText(this, "항목을 입력해주세요", Toast.LENGTH_LONG).show()
                setResult(Activity.RESULT_CANCELED)
                finish()
                onResume()
            }
            else {
                var ops: ArrayList<ContentProviderOperation> = ArrayList()
                var op: ContentProviderOperation.Builder =
                    ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                ops.add(op.build())

                op = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(
                        ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE
                    )
                    .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name)
                ops.add(op.build())


                op = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(
                        ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
                    )
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNumber)
                    .withValue(ContactsContract.CommonDataKinds.Phone.LABEL, "LABEL?")
                    .withValue(
                        ContactsContract.CommonDataKinds.Phone.TYPE,
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE
                    )
                ops.add(op.build())


                op = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)// rawContact_NewID)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Email.DATA, email)
                ops.add(op.build())


                op = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)// rawContact_NewID)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Nickname.NAME, nickname)
                ops.add(op.build())

                op = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)// rawContact_NewID)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Note.NOTE, food)
                ops.add(op.build())


                this.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops)
                Toast.makeText(this, "저장되었습니다", Toast.LENGTH_LONG).show()
                setResult(Activity.RESULT_OK)
                finish()
                //onResume()
            }

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
