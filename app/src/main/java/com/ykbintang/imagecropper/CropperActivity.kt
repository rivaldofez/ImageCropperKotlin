package com.ykbintang.imagecropper

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.yalantis.ucrop.UCrop
import java.io.File
import java.lang.StringBuilder
import java.nio.file.Files
import java.util.UUID

class CropperActivity : AppCompatActivity() {

    private var fileUrl: Uri? = null
    private var result: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cropper)

        readIntent()

        val options = UCrop.Options()

        val destUrl = StringBuilder(UUID.randomUUID().toString()).append(".jpg").toString()
        UCrop.of(fileUrl!!, Uri.fromFile(File(cacheDir, destUrl)))
            .withOptions(options)
            .withAspectRatio(0F, 0F)
            .useSourceImageAspectRatio()
            .withMaxResultSize(1000,1000)
            .start(this@CropperActivity)

    }

    private fun readIntent() {
        if(intent.extras != null){
            result = intent.getStringExtra("DATA")
            fileUrl = Uri.parse(result)
//            Log.d("Teston", result.toString())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            val resultUrl = data?.let { UCrop.getOutput(it) }
            val intent = Intent()
            intent.putExtra("RESULT", resultUrl.toString())
            setResult(-1, intent)
            finish()
        } else if(resultCode == UCrop.RESULT_ERROR) {
        }
    }


}