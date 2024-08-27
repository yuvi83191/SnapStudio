package com.yuvraj.instagramclone

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.yuvraj.instagramclone.databinding.ActivitySignupBinding
import com.yuvraj.instagramclone.models.User
import com.yuvraj.instagramclone.utils.USER_NODE
import com.yuvraj.instagramclone.utils.USER_PROFILE_FOLDER
import com.yuvraj.instagramclone.utils.uploadImage

class SignupActivity : AppCompatActivity() {
    val binding by lazy {
   ActivitySignupBinding.inflate(layoutInflater)
    }
   lateinit var user:User
   private val launcher= registerForActivityResult(ActivityResultContracts.GetContent()){
       uri->
       uri?.let {
           uploadImage(uri, USER_PROFILE_FOLDER){
               if(it==null){

               }
               else{
                   user.image=it
                   binding.profileImage.setImageURI(uri)
               }
           }

       }
   }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val text= "<font color = #FF000000>Already have an Account</font> <font color= #1E88E5>Login</font>"
        binding.login.setText(Html.fromHtml(text))
        user=User()
        //binding.name.editText?.text.toString().equals("")
        binding.signUpBtn.setOnClickListener{
            if (binding.name.editText?.text.toString().equals("") or
                binding.email.editText?.text.toString().equals("") or
                binding.password.editText?.text.toString().equals("")
                ) {
                Toast.makeText(this@SignupActivity,"Please fill all the information",Toast.LENGTH_SHORT).show()
            } else{

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.email.editText?.text.toString(),
                    binding.password.editText?.text.toString()
                ).addOnCompleteListener {
                    result->

                    if (result.isSuccessful){
                        user.name=binding.name.editText?.text.toString()
                        user.password=binding.password.editText?.text.toString()
                        user.email=binding.email.editText?.text.toString()
                        Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).set(user).addOnSuccessListener {
                            startActivity(Intent(this@SignupActivity,HomeActivity::class.java))
                            finish()
                        }
                    }
                    else{
                        Toast.makeText(this@SignupActivity, result.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        binding.addImage.setOnClickListener {
            launcher.launch("image/*")
        }

        binding.login.setOnClickListener {
            startActivity(Intent(this@SignupActivity,LoginActivity::class.java))
            finish()
        }
    }
}