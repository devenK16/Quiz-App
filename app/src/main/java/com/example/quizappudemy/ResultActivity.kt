package com.example.quizappudemy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val username = intent.getStringExtra(Constant.USER_NAME)
        tv_name.text = username

        val totalquestions = intent.getIntExtra(Constant.TOTAL_QUESTIONS,0)
        val correctanswers = intent.getIntExtra(Constant.CORRECT_ANSWERS,0)

        tv_score.text = "Your score is $correctanswers out of $totalquestions"
        btn_finish.setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
}