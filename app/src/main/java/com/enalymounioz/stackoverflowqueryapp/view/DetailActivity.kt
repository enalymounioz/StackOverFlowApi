package com.enalymounioz.stackoverflowqueryapp.view

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Build.VERSION
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import com.enalymounioz.stackoverflowqueryapp.R
import com.enalymounioz.stackoverflowqueryapp.model.Question
import com.enalymounioz.stackoverflowqueryapp.model.convertTitle
import com.enalymounioz.stackoverflowqueryapp.model.getDate
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    companion object {
        const val PARAM_QUESTION = "param_question"

        fun getIntent(context: Context, question: Question)=
            Intent(context, DetailActivity::class.java).putExtra(PARAM_QUESTION, question)
    }

    var question: Question? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

                    question = intent.extras?.getParcelable(PARAM_QUESTION)

        if (question == null) {
            finish()
        }

        populateUI()
    }
    private fun populateUI() {
        question_score.text = question!!.score
        question_date.text = getDate(question!!.date)
        question_title.text = convertTitle(question!!.title)
    }
}