package com.enalymounioz.stackoverflowqueryapp.view

import com.enalymounioz.stackoverflowqueryapp.model.Question

interface QuestionClickListener {

    fun onQuestionClicked(question: Question)
}