package com.example.wordle

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private var wordToGuess = ""
    private var guessCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        wordToGuess = FourLetterWordList.getRandomFourLetterWord()

        val etGuess = findViewById<EditText>(R.id.et_guess)
        val btnSubmit = findViewById<Button>(R.id.btn_submit)
        val tvTargetWord = findViewById<TextView>(R.id.target_word)
        tvTargetWord.text = wordToGuess

        val guessValues = listOf(
            findViewById<TextView>(R.id.guess1_value),
            findViewById<TextView>(R.id.guess2_value),
            findViewById<TextView>(R.id.guess3_value)
        )

        val checkValues = listOf(
            findViewById<TextView>(R.id.check1_value),
            findViewById<TextView>(R.id.check2_value),
            findViewById<TextView>(R.id.check3_value)
        )

        btnSubmit.setOnClickListener {
            val guess = etGuess.text.toString().uppercase()
            
            if (guess.length != 4) {
                Toast.makeText(this, "Please enter a 4-letter word", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val result = checkGuess(guess)
            
            guessValues[guessCount].text = guess
            checkValues[guessCount].text = result
            
            guessCount++
            etGuess.text.clear()

            if (guessCount == 3) {
                btnSubmit.isEnabled = false
                btnSubmit.isClickable = false
                etGuess.isEnabled = false
                tvTargetWord.visibility = View.VISIBLE
                Toast.makeText(this, "You've exceeded your number of guesses!", Toast.LENGTH_LONG).show()
            }
        }
    }

    /**
     * Parameters / Fields:
     *   wordToGuess : String - the target word the user is trying to guess
     *   guess : String - what the user entered as their guess
     *
     * Returns a String of 'O', '+', and 'X', where:
     *   'O' represents the right letter in the right place
     *   '+' represents the right letter in the wrong place
     *   'X' represents a letter not in the target word
     */
    private fun checkGuess(guess: String): String {
        var result = ""
        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                result += "O"
            } else if (guess[i] in wordToGuess) {
                result += "+"
            } else {
                result += "X"
            }
        }
        return result
    }
}
