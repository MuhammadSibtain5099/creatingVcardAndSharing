package com.example.testapp

import android.content.Intent
import android.media.tv.TvContract.AUTHORITY
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.lang.Exception
import java.util.*
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import android.widget.Button
import android.widget.TextView
import androidx.core.content.FileProvider


class MainActivity : AppCompatActivity() {

    //lateinit var img_dail_phone: ImageView
    private val VCF_DIRECTORY: String? = "/vcf_demonuts"
    private var vcfFile: File? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val builder = VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

        val txtCompose= findViewById<TextView>(R.id.txtCompose)
        val btn = findViewById<Button>(R.id.btn)

        btn.setOnClickListener {
            try {
                generateVCard()

            } catch (e: IOException) {
                e.printStackTrace();
            }
        }

    }


    private fun generateVCard() {
        try {
            // File vcfFile = new File(this.getExternalFilesDir(null), "generated.vcf");
            val vdfdirectory = File(
                Environment.getExternalStorageDirectory().toString() + VCF_DIRECTORY
            );
            // have the object build the directory structure, if needed.
            if (!vdfdirectory.exists()) {
                vdfdirectory.mkdirs();
            }

            vcfFile =
                File(vdfdirectory, "android_" + Calendar.getInstance().getTimeInMillis() + ".vcf");

            val fw: FileWriter = FileWriter(vcfFile);
            fw.write("BEGIN:VCARD\r\n");
            fw.write("VERSION:3.0\r\n");
            // fw.write("N:" + p.getSurname() + ";" + p.getFirstName() + "\r\n");
            fw.write("FN:" + "Sibtain" + "\r\n");
            //  fw.write("ORG:" + p.getCompanyName() + "\r\n");
            //  fw.write("TITLE:" + p.getTitle() + "\r\n");
            fw.write("TEL;TYPE=WORK,VOICE:" + "+923155022905" + "\r\n");
            //   fw.write("TEL;TYPE=HOME,VOICE:" + p.getHomePhone() + "\r\n");
            //   fw.write("ADR;TYPE=WORK:;;" + p.getStreet() + ";" + p.getCity() + ";" + p.getState() + ";" + p.getPostcode() + ";" + p.getCountry() + "\r\n");
            fw.write("EMAIL;TYPE=PREF,INTERNET:" + "abc@gmail.com" + "\r\n");
            fw.write("END:VCARD\r\n");
            fw.close();

            val i = Intent() //this will import vcf in contact list

            i.action = Intent.ACTION_SEND
            i.setDataAndType(Uri.fromFile(vcfFile), "text/x-vcard")
            startActivityForResult(i,100)


//            val intent = Intent(Intent.ACTION_SEND, FileProvider.getUriForFile(this,"com.example.testapp.provider" , vcfFile!!))
//            //intent.setAction(Intent.ACTION_OPEN_DOCUMENT)
//            //intent.addCategory(Intent.CATEGORY_OPENABLE)
//            intent.setData( Uri.fromFile(vcfFile),)
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//            startActivityForResult(intent,100)

        }catch (e: IOException){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode==100){
            Toast.makeText(applicationContext,resultCode.toString(),Toast.LENGTH_LONG).show()
        }
    }

}