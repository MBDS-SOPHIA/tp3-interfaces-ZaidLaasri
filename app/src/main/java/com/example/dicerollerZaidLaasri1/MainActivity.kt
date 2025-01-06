package com.example.dicerollerZaidLaasri1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener


/**
 * This activity allows the user to roll a dice and view the result
 * on the screen.
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val rollButton: Button = findViewById(R.id.button)
        val targetInput: EditText = findViewById(R.id.targetInput)
        val resultTextView: TextView = findViewById(R.id.resultTextView)

        // Activer le bouton lorsque le champ de texte est rempli
        targetInput.addTextChangedListener {
            rollButton.isEnabled = it.toString().isNotEmpty()
        }
        rollButton.setOnClickListener {
            val targetNumber = targetInput.text.toString().toIntOrNull()
            if (targetNumber != null) {
                rollDice(targetNumber)
            } else {
                resultTextView.text = "Veuillez entrer un nombre valide !"
            }
        }
    }

    private fun rollDice(targetNumber: Int) {
        val dice1 = Dice(6)
        val dice2 = Dice(6)

        val diceRoll1 = dice1.roll()
        val diceRoll2 = dice2.roll()
        val sum = diceRoll1 + diceRoll2

        val dice1TextView: TextView = findViewById(R.id.textView)
        val dice2TextView: TextView = findViewById(R.id.dice2TextView)
        val resultTextView: TextView = findViewById(R.id.resultTextView)

        dice1TextView.text = diceRoll1.toString()
        dice2TextView.text = diceRoll2.toString()

        if (sum == targetNumber) {
            resultTextView.text = "Félicitations ! Vous avez gagné  (Somme : $sum)"
        } else {
            resultTextView.text = "Dommage ! Somme des dés : $sum"
        }
    }
}

class Dice(private val numSides: Int) {
    fun roll(): Int {
        return (1..numSides).random()
    }
}
