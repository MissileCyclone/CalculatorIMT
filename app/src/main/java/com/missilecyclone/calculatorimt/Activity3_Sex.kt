package com.missilecyclone.calculatorimt

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.missilecyclone.calculatorimt.R

class Activity3_Sex : AppCompatActivity() {
    private lateinit var weight: String
    private lateinit var height: String
    private var gender: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Установка макета activity_activity3_sex.xml для данной активности
        setContentView(R.layout.activity_activity3_sex)
        // Получение ссылки на ActionBar и установка кнопки "Назад"
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        // Получаем данные из предыдущей активности
        height = intent.getDoubleExtra("height", 0.0).toString()
        weight = intent.getDoubleExtra("weight", 0.0).toString()

// Обработчик нажатия на кнопку "Мужской пол"
        val maleButton = findViewById<ImageButton>(R.id.imageButton)
        maleButton.setOnClickListener {
            gender = "male"
            Toast.makeText(this, "Мужской пол выбран", Toast.LENGTH_SHORT).show()
        }

// Обработчик нажатия на кнопку "Женский пол"
        val femaleButton = findViewById<ImageButton>(R.id.imageButton1)
        femaleButton.setOnClickListener {
            gender = "female"
            Toast.makeText(this, "Женский пол выбран", Toast.LENGTH_SHORT).show()
        }

// Обработчик нажатия на кнопку "Рассчитать"
        val calculateButton = findViewById<Button>(R.id.button)
        calculateButton.setOnClickListener {
            // Проверяем, выбран ли пол
            if (gender.isNullOrEmpty()) {
                Toast.makeText(this, "Выберите пол", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Показываем значения роста и веса
            Toast.makeText(this, "Рост: $height, Вес: $weight", Toast.LENGTH_SHORT).show()

            // Рассчитываем ИМТ
            val bmi = calculateBMI(height.toFloat(), weight.toFloat())

            // Перенаправляем на соответствующую активность в зависимости от результата ИМТ
            val intent = when (gender) {
                "male" -> when {
                    bmi < 18.5 -> Intent(this, Results_1::class.java)
                    bmi < 25 -> Intent(this, Results_2::class.java)
                    bmi < 30 -> Intent(this, Results_3::class.java)
                    else -> Intent(this, Results_8::class.java)
                }
                "female" -> when {
                    bmi < 16.5 -> Intent(this, Results_4::class.java)
                    bmi < 18.5 -> Intent(this, Results_5::class.java)
                    bmi < 25 -> Intent(this, Results_6::class.java)
                    else -> Intent(this, Results_7::class.java)
                }
                else -> Intent(this, MainActivity::class.java)
            }
            startActivity(intent) // Запускаем новую активность
        }
    }

    // Функция расчета ИМТ
    private fun calculateBMI(height: Float, weight: Float): Float {
        val heightInMeters = height / 100
        return weight / (heightInMeters * heightInMeters)
    }

// Функция обновления интерфейса в зависимости от выбора пола
    private fun updateGenderSelectionUI() {
        val maleButton = findViewById<ImageButton>(R.id.imageButton)
        val femaleButton = findViewById<ImageButton>(R.id.imageButton1)
// Обновление UI в зависимости от выбранного пола
    when (gender) {
        "male" -> {
            maleButton.isSelected = true // Выбор кнопки "Мужской пол"
            femaleButton.isSelected = false // Отмена выбора кнопки "Женский пол"
        }
        "female" -> {
            maleButton.isSelected = false // Отмена выбора кнопки "Мужской пол"
            femaleButton.isSelected = true // Выбор кнопки "Женский пол"
        }
        else -> {
            maleButton.isSelected = false // Отмена выбора кнопки "Мужской пол"
            femaleButton.isSelected = false // Отмена выбора кнопки "Женский пол"
        }
    }
}
    // Переопределение метода startActivity для добавления анимации перехода при запуске новой активности
    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left) // Анимация перехода при запуске новой активности
    }

    // Переопределение метода finish для добавления анимации перехода при закрытии активности
    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right) // Анимация перехода при закрытии активности
    }

    // Переопределение метода onOptionsItemSelected для обработки нажатия на элемент меню
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed() // Вызов метода onBackPressed() при нажатии на кнопку "Назад"
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}