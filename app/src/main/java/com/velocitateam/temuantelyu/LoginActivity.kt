package com.velocitateam.temuantelyu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.velocitateam.temuantelyu.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {    lateinit var binding : ActivityLoginBinding
    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.resgiterButtonLogin.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.loginButtonLogin.setOnClickListener{
            val email = binding.emailEditLogin.text.toString()
            val password = binding.passwordEditLogin.text.toString()

            //Validasi Email Kosong
            if(email.isEmpty()){
                binding.emailEditLogin.error = "Email Harus Diisi"
                binding.emailEditLogin.requestFocus()
                return@setOnClickListener
            }

            //Validasi Email Tidak Sesuai
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.emailEditLogin.error = "Email Tidak Valid"
                binding.emailEditLogin.requestFocus()
                return@setOnClickListener
            }

            //Validasi Password Kosong
            if(password.isEmpty()){
                binding.passwordEditLogin.error = "Password Harus Diisi"
                binding.passwordEditLogin.requestFocus()
                return@setOnClickListener
            }

            LoginFirebase(email, password)
        }
    }

    private fun LoginFirebase(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                if(it.isSuccessful){
                    Toast.makeText(this, "Selamat datang $email", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}