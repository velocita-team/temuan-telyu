package com.velocitateam.temuantelyu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.velocitateam.temuantelyu.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    lateinit var binding : ActivityRegisterBinding
    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.registerButtonRegister.setOnClickListener{
            val fullname = binding.fullnameEditRegister.text.toString()
            val email = binding.emailEditRegister.text.toString()
            val password = binding.passwordEditRegister.text.toString()

            //Validasi Fullname Kosong
            if(fullname.isEmpty()){
                binding.fullnameEditRegister.error = "Nama Lengkap Harus Diisi"
                binding.fullnameEditRegister.requestFocus()
                return@setOnClickListener
            }

            //Validasi Email Kosong
            if(email.isEmpty()){
                binding.emailEditRegister.error = "Email Harus Diisi"
                binding.emailEditRegister.requestFocus()
                return@setOnClickListener
            }

            //Validasi Email Tidak Sesuai
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.emailEditRegister.error = "Email Tidak Valid"
                binding.emailEditRegister.requestFocus()
                return@setOnClickListener
            }

            //Validasi Password Kosong
            if(password.isEmpty()){
                binding.passwordEditRegister.error = "Password Harus Diisi"
                binding.passwordEditRegister.requestFocus()
                return@setOnClickListener
            }

            //Validasi Panjang Password
            if(password.length < 6){
                binding.passwordEditRegister.error = "Password Minimal 6 Karakter"
                binding.passwordEditRegister.requestFocus()
                return@setOnClickListener
            }

            RegisterFirebase(fullname, email, password)
        }
    }

    private fun RegisterFirebase(fullname: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){
                if(it.isSuccessful){
                    Toast.makeText(this, "Register Berhasil", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}