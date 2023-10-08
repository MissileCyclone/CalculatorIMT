package com.missilecyclone.calculatorimt

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Activity2_Weight : AppCompatActivity() {
    private lateinit var weightEditText: EditText // Создание переменной для EditText веса

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setEnterTransition(null) // Отключение эффекта перехода при открытии активности
        setContentView(R.layout.activity_activity2_weight) // Установка макета активности
        // Настройка обработчика нажатия на кнопку "Назад"
        val actionBar = supportActionBar // Получение ActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true) // Установка кнопки "Назад"

        weightEditText = findViewById(R.id.editTextNumberDecimal2) // Инициализация переменной EditText

        val height = intent.getDoubleExtra("height", 0.0) // Получение данных о росте из предыдущей активности

        val nextButton: ImageButton = findViewById(R.id.imageButton2) // Получение кнопки "Далее"
        nextButton.setOnClickListener {
            val weightText = weightEditText.text.toString() // Получение текста из EditText
            if (weightText.isEmpty()) { // Если строка веса пустая
                Toast.makeText(this, "Введите вес", Toast.LENGTH_SHORT).show() // Вывод сообщения
                return@setOnClickListener // Выход из функции
            }
            val weight = weightText.toDoubleOrNull() // Попытка преобразовать строку в число
            if (weight == null) { // Если преобразование не удалось
                Toast.makeText(this, "Введите корректное число в поле веса", Toast.LENGTH_SHORT).show() // Вывод сообщения
                return@setOnClickListener // Выход из функции
            }
            if (weight < 30 || weight > 200) { // Если вес вне диапазона
                Toast.makeText(this, "Введите число веса в диапазоне от 30 до 200", Toast.LENGTH_SHORT).show() // Вывод сообщения
                return@setOnClickListener // Выход из функции
            }
            val intent = Intent(this, Activity3_Sex::class.java) // Создание намерения для перехода на следующую активность
            intent.putExtra("height", height) // Добавление данных о росте в намерение
            intent.putExtra("weight", weight) // Добавление данных о весе в намерение
            startActivity(intent) // Запуск следующей активности
        }

    }

    override fun startActivity(intent: Intent?) { // Переопределение метода startActivity для установки эффекта перехода при открытии активности
        super.startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left) // Установка эффекта перехода
    }

    override fun finish() { // Переопределение метода finish для установки эффекта перехода при закрытии активности
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right) // Установка эффекта перехода
    }
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