package ru.rsue.android.droidquest

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class DeceitActivity : AppCompatActivity() {
    private lateinit var mAnswerTextView: TextView
    private lateinit var mShowAnswer: Button
    companion object {
        val EXTRA_ANSWER_IS_TRUE = "ru.rsue.android.droidquest.answer_is_true"
        val EXTRA_ANSWER_SHOWN = "ru.rsue.android.droidquest.answer_shown"
        const val KEY_ANSWER_SHOWN = "answer_shown"
        fun newIntent(packageContext: Context?, answerIsTrue: Boolean):
                Intent? {
            val intent = Intent(packageContext,
                DeceitActivity::class.java)
            return intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
        }
        fun wasAnswerShown(result: Intent) =
            result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deceit)
        val answerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)

        mAnswerTextView = findViewById(R.id.answer_text_view);
        mShowAnswer = findViewById(R.id.show_answer_button);

        mShowAnswer.setOnClickListener {
            showAnswer(answerIsTrue)
        }

        savedInstanceState?.let { savedState ->
            val isAnswerShown = savedState.getBoolean(KEY_ANSWER_SHOWN, false)
            if (isAnswerShown) {
                showAnswer(answerIsTrue)
            }
        }
    }

    private fun showAnswer(answerIsTrue: Boolean) {
        val answerText = if (answerIsTrue) R.string.true_button else R.string.false_button
        mAnswerTextView.setText(answerText)

        setResult(Activity.RESULT_OK, Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, true)
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_ANSWER_SHOWN, mAnswerTextView.visibility == View.VISIBLE)
    }

}