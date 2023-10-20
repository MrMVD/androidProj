package com.example.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.test.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater) //создание привязки
        setContentView(binding.root)
        val ft = supportFragmentManager //создание менеджера фрагментов
        val frag = BlankFragmentTest() //создание фрагмента
        ft.beginTransaction()
            .add(R.id.fragHolder, frag, "listFrag")//добавление фрагмента с тегом
            .commit() //подтвержение (да ладно?)
    }
}
