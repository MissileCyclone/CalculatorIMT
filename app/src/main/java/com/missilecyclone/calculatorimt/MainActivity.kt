package com.missilecyclone.calculatorimt

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Объявление переменной для редактирования поля роста и переменной для проверки, была ли нажата кнопка "Назад"
    private lateinit var heightEditText: EditText
    private var isBackPressed = false

    // Переопределение функции onCreate(), которая вызывается при создании экрана
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Анимация перехода между экранами
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        // Установка макета для MainActivity
        setContentView(R.layout.activity_main)

        // Настройка обработчика нажатия на кнопку "Назад"
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        // Нахождение поля для редактирования роста и кнопки "Далее" и установка обработчика нажатия на кнопку "Далее"
        heightEditText = findViewById(R.id.EditText_Main)
        val nextButton: ImageButton = findViewById(R.id.ImageButton_Main)
        nextButton.setOnClickListener {
            val heightText = heightEditText.text.toString()

            // Проверка, что поле роста не пустое
            if (heightText.isEmpty()) {
                Toast.makeText(this, "Введите рост", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Проверка, что введено корректное число в поле роста
            val height = heightText.toDoubleOrNull()
            if (height == null) {
                Toast.makeText(this, "Введите корректное число в поле роста", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (height < 100 || height > 300) {
                Toast.makeText(this, "Рост должен быть от 100 до 300", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Передача значения роста во вторую активность
            val intent = Intent(this, Activity2_Weight::class.java)
            intent.putExtra("height", height)
            startActivity(intent)
        }

    }

    // Переопределение функции startActivity(), которая вызывается при запуске новой активности
    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    // Переопределение функции finish(), которая вызывается при закрытии активности
    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    // Переопределение функции onOptionsItemSelected(), которая вызывается при нажатии на пункт меню в ActionBar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // Вызов метода onBackPressed() при нажатии на кнопку "Назад"
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}