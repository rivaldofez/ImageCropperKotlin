package com.ykbintang.imagecropper

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.ykbintang.imagecropper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val launchImageCrop =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            if (result.resultCode == RESULT_OK) {
                val selectedImg: Uri = result.data?.data as Uri
//                uriToFile(selectedImg, this@CreateActivity).also { getFile = it }
//
//                createBinding.ivStory.setImageURI(selectedImg)

                val intent = Intent(this@MainActivity, CropperActivity::class.java)
//                intent.putExtra("DATA", "haiiii")
                intent.putExtra("DATA", result.data?.data.toString())
//                Log.d("Teston", result.data?.data as Uri)
                startActivityForResult(intent, 101)
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivPickImage.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            val chooser = Intent. createChooser(intent, "Choose Picture")
            launchImageCrop.launch(chooser)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == -1 && requestCode==101) {
            val result = data?.getStringExtra("RESULT")
            var resultUrl: Uri? = null
            if (result != null) {
                resultUrl = Uri.parse(result)
            }

            binding.ivPickImage.setImageURI(resultUrl)
        }
    }
}