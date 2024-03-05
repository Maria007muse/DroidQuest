package ru.rsue.android.droidquest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts


class QuestActivity : AppCompatActivity() {
    companion object{
        private val TAG = "QuestActivity"
        private val KEY_INDEX = "index"
        private const val IS_DECEITER = "is_deceiter"
    }

    private lateinit var mTrueButton: Button
    private lateinit var mFalseButton: Button
    private lateinit var mDeceitButton: Button
    private lateinit var mNextButton: ImageButton
    private lateinit var mBackButton: ImageButton
    private lateinit var mQuestionTextView: TextView
    private val mQuestionBank = listOf(
        Question(R.string.question_android, true),
        Question(R.string.question_linear, false),
        Question(R.string.question_service, false),
        Question(R.string.question_res, true),
        Question(R.string.question_manifest, true),

        Question(R.string.question_serviceone, false),
        Question(R.string.question_manifesttwo, true),
        Question(R.string.question_multitasking, false),
        Question(R.string.question_graphic, true),
        Question(R.string.question_saving, false)
    )
    private var mCurrentIndex = 0
    private var mIsDeceiter = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quest)
        Log.d(TAG, "onCreate(Bundle) вызван")
        val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                if (data != null) {
                    mIsDeceiter = DeceitActivity.wasAnswerShown(data)
                }
            }
        }

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mIsDeceiter = savedInstanceState.getBoolean(IS_DECEITER, false)

        }

        mQuestionTextView = findViewById(R.id.question_text_view)

        mQuestionTextView.setOnClickListener {
            mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.size
            updateQuestion()
        }

        mTrueButton = findViewById(R.id.true_button)
        mTrueButton.setOnClickListener {
            checkAnswer(true)
        }

        mFalseButton = findViewById(R.id.false_button)
        mFalseButton.setOnClickListener{
            checkAnswer(false)
        }

        mNextButton = findViewById(R.id.next_button)
        mNextButton.setOnClickListener {
            mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.size
            mIsDeceiter = false
            updateQuestion()
        }

        mBackButton = findViewById(R.id.back_button)
        mBackButton.setOnClickListener {
            mCurrentIndex = (mCurrentIndex - 1 + mQuestionBank.size) % mQuestionBank.size
            updateQuestion()
        }

        mDeceitButton = findViewById(R.id.deceit_button)
        mDeceitButton.setOnClickListener{
            val answerIsTrue = mQuestionBank[mCurrentIndex].answerTrue
            val intent = DeceitActivity.newIntent(this, answerIsTrue)
            startForResult.launch(intent)
        }
        updateQuestion()
    }

    private fun checkAnswer(userPressedTrue: Boolean) {
        val answerIsTrue = mQuestionBank[mCurrentIndex].answerTrue
        val messageResId = if (mIsDeceiter) R.string.judgment_toast
        else if (userPressedTrue == answerIsTrue) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i(TAG, "onSaveInstanceState")
        outState.putInt(KEY_INDEX, mCurrentIndex)
        outState.putBoolean(IS_DECEITER, mIsDeceiter)
    }

    private fun updateQuestion() {
        val question = mQuestionBank[mCurrentIndex].textResId
        mQuestionTextView.setText(question)

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() вызван")
    }
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() вызван")
    }
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() вызван")
    }
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() вызван")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() вызван")
    }
}