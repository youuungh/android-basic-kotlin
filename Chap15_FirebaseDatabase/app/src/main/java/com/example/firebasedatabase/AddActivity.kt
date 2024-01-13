package com.example.firebasedatabase

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.firebasedatabase.databinding.ActivityAddBinding
import com.example.firebasedatabase.util.dateToString
import com.google.firebase.storage.StorageReference
import java.io.File
import java.util.Date

class AddActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddBinding
    lateinit var filePath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    val requestLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode === android.app.Activity.RESULT_OK) {
            Glide
                .with(applicationContext)
                .load(it.data?.data)
                .apply(RequestOptions().override(250, 200))
                .centerCrop()
                .into(binding.addImageView)

            val cursor = contentResolver.query(it.data?.data as Uri, arrayOf<String>(MediaStore.Images.Media.DATA), null, null, null);
            cursor?.moveToFirst().let { filePath = cursor?.getString(0) as String }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId === R.id.menu_add_gallery) {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
            requestLauncher.launch(intent)
        } else if (item.itemId === R.id.menu_add_save) {
            if (binding.addImageView.drawable !== null && binding.addEditView.text.isNotEmpty()) {
                //store 에 먼저 data 저장후 document id 값으로 업로드 파일 이름 지정
                saveStore()
            } else {
                Toast.makeText(this, "데이터가 모두 입력되지 않았습니다.", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveStore() {
        // add
        val data = mapOf("email" to MyApplication.email, "content" to binding.addEditView.text.toString(), "date" to dateToString(Date()))
        MyApplication.db.collection("news")
            .add(data)
            .addOnSuccessListener {
                // storage에 data 저장 후 id값으로 이미지 업로드
                uploadImage(it.id)
            }
            .addOnFailureListener {
                Log.w("saveStore()", "data save error", it)
            }
    }

    private fun uploadImage(docId: String){
        // add
        val storage = MyApplication.storage
        // storage를 참조하는 StorageReference 생성
        val storageRef: StorageReference = storage.reference
        // 실제 업로드하는 파일을 참조하는 StorageReference 생성
        val imgRef: StorageReference = storageRef.child("images/${docId}.jpg")
        // 파일 업로드
        val file = Uri.fromFile(File(filePath))
        imgRef.putFile(file)
            .addOnSuccessListener {
                Toast.makeText(this, "데이터가 저장되었습니다.", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Log.d("uploadImage()", "failure............."+it)
            }
    }
}