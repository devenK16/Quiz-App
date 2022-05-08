package com.example.quizappudemy

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_quiz_questions.*

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentPosition: Int = 1
    private var mQuestionList: ArrayList<Questions>? = null
    private var mSelectedOptionPosition: Int = 0
    private var mCorrectAnswers: Int = 0
    private var mUserName:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        mUserName = intent.getStringExtra(Constant.USER_NAME)
        mQuestionList = Constant.getQuestions()

        setQuestion()

        tv_optino_one.setOnClickListener(this)
        tv_optino_two.setOnClickListener(this)
        tv_optino_three.setOnClickListener(this)
        tv_optino_four.setOnClickListener(this)

        btn_submit.setOnClickListener(this)
    }

    private fun setQuestion() {

        val question = mQuestionList!!.get(mCurrentPosition - 1)

        defaultOptionView()

        if (mCurrentPosition == mQuestionList!!.size) {
            btn_submit.text = "FINISH"
        } else {
            btn_submit.text = "SUBMIT"
        }

        progressBar.progress = mCurrentPosition
        tv_progress.text = "$mCurrentPosition" + "/" + progressBar.max

        tv_question.text = question!!.question
        tv_image.setImageResource(question.image)
        tv_optino_one.text = question.QuestionOne
        tv_optino_two.text = question.QuestionTwo
        tv_optino_three.text = question.QuestionThree
        tv_optino_four.text = question.QuestionFour
    }

    private fun defaultOptionView() {
        val options = ArrayList<TextView>()
        options.add(0, tv_optino_one)
        options.add(1, tv_optino_two)
        options.add(2, tv_optino_three)
        options.add(3, tv_optino_four)

        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_optoin_border_bg
            )
        }
    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.tv_optino_one -> {
                selectedOptionView(tv_optino_one, 1)
            }
            R.id.tv_optino_two -> {
                selectedOptionView(tv_optino_two, 2)
            }
            R.id.tv_optino_three -> {
                selectedOptionView(tv_optino_three, 3)
            }
            R.id.tv_optino_four -> {
                selectedOptionView(tv_optino_four, 4)
            }
            R.id.btn_submit -> {
                if (mSelectedOptionPosition == 0) {
                    mCurrentPosition++

                    when {
                        mCurrentPosition <= mQuestionList!!.size -> {
                            setQuestion()
                        }
                        else -> {
                            val intent = Intent(this,ResultActivity::class.java)
                            intent.putExtra(Constant.USER_NAME,mUserName)
                            intent.putExtra(Constant.CORRECT_ANSWERS,mCorrectAnswers)
                            intent.putExtra(Constant.TOTAL_QUESTIONS,mQuestionList!!.size)
                            startActivity(intent)
                            finish()
                        }
                    }

                } else {
                    val question = mQuestionList?.get(mCurrentPosition - 1)
                    if (question!!.correctAnswer != mSelectedOptionPosition) {
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                    } else {
                        mCorrectAnswers++
                    }
                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)


                    if (mCurrentPosition == mQuestionList!!.size) {
                        btn_submit.text = "FINISH"
                    } else {
                        btn_submit.text = "GO TO NEXT QUESTION"
                    }
                    mSelectedOptionPosition = 0
                }

            }
        }

    }

    private fun selectedOptionView(tv: TextView, selectedOptionNumber: Int) {
        defaultOptionView()
        mSelectedOptionPosition = selectedOptionNumber
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this,
            R.drawable.selected_optoin_border_bg
        )
    }

    private fun answerView(answer: Int, drawableView: Int) {
        when (answer) {
            1 -> {
                tv_optino_one.background = ContextCompat.getDrawable(
                    this, drawableView
                )
            }
            2 -> {
                tv_optino_two.background = ContextCompat.getDrawable(
                    this, drawableView
                )
            }
            3 -> {
                tv_optino_three.background = ContextCompat.getDrawable(
                    this, drawableView
                )
            }
            4 -> {
                tv_optino_four.background = ContextCompat.getDrawable(
                    this, drawableView
                )
            }
        }
    }

}