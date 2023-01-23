package com.enalymounioz.stackoverflowqueryapp.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.enalymounioz.stackoverflowqueryapp.R
import com.enalymounioz.stackoverflowqueryapp.model.Question
import com.enalymounioz.stackoverflowqueryapp.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail.list_error
import kotlinx.android.synthetic.main.activity_detail.loading_view
import kotlinx.android.synthetic.main.activity_main.*
import androidx.lifecycle.Observer
import com.enalymounioz.stackoverflowqueryapp.model.convertTitle
import com.enalymounioz.stackoverflowqueryapp.model.getDate


class DetailActivity : AppCompatActivity() {

    companion object {
        const val PARAM_QUESTION = "param_question"

        fun getIntent(context: Context, question: Question)=
            Intent(context, DetailActivity::class.java).putExtra(PARAM_QUESTION, question)
    }

    var question: Question? = null
    private val viewModel : DetailViewModel by viewModels()
    private val answersAdapter = AnswersAdapter(arrayListOf())
    private val lm = LinearLayoutManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        question = intent.extras?.getParcelable(PARAM_QUESTION)

        if (question == null) {
            finish()
        }

        populateUI()

        answers_list.apply {
            layoutManager = lm
            adapter = answersAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy>0){
                    val childCount = answersAdapter.itemCount
                    val lastPosition = lm.findLastCompletelyVisibleItemPosition()
                    if(childCount -1  == lastPosition && loading_view.visibility == View.GONE){
                        loading_view.visibility = View.VISIBLE
                        viewModel.getNextPage(question!!.questionId)
                    }
                }
            }
            })
        }

        observeViewModel()

        viewModel.getNextPage(question!!.questionId)
    }

    private fun observeViewModel() {
        viewModel.answersResponse.observe(this, Observer {items ->
                     items?.let {
                answers_list.visibility = View.VISIBLE
                answersAdapter.addAnswers(it)
            }
        })
        viewModel.error.observe(this, Observer {errMsg ->
            list_error.visibility = if(errMsg == null) View.GONE else View.VISIBLE
            list_error.text = errMsg
        })

        viewModel.loading.observe(this, Observer {isLoading ->
            isLoading?.let {
                loading_view.visibility = if(it) View.VISIBLE else View.GONE
                if(it){
                    list_error.visibility = View.GONE
                    questions_list.visibility = View.GONE
                }
            }
        })
    }

    private fun populateUI() {
        question_score.text = question!!.score
        question_date.text = getDate(question!!.date)
        question_title.text = convertTitle(question!!.title)
    }
}