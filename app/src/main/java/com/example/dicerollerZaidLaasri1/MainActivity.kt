package com.example.dicerollerZaidLaasri1

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import nl.dionsegijn.konfetti.xml.KonfettiView
import java.util.concurrent.TimeUnit


/**
 * This activity allows the user to roll a dice and view the result
 * on the screen.
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val targetInput: EditText = findViewById(R.id.targetInput)
        val resultTextView: TextView = findViewById(R.id.resultTextView)

        // Lancer automatiquement les dés dès qu'un nombre est saisi
        targetInput.addTextChangedListener {
            val targetNumber = it.toString().toIntOrNull()
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
            resultTextView.text = "Félicitations ! Vous avez gagné 🎉"
            animateDice(dice1TextView, dice2TextView) // Animer les dés
            showConfetti()
        } else {
            resultTextView.text = "Dommage ! Somme des dés : $sum"
        }
    }

    private fun showConfetti() {
        val konfettiView: KonfettiView = findViewById(R.id.konfettiView)
        konfettiView.start(
            Party(
                speed = 0f,
                maxSpeed = 30f,
                damping = 0.9f,
                spread = 360,
                colors = listOf(0xFFE91E63.toInt(), 0xFF2196F3.toInt(), 0xFFFFC107.toInt()),
                emitter = Emitter(duration = 3, TimeUnit.SECONDS).max(500),
                position = Position.Relative(0.5, 0.5) // Centre de l'écran
            )
        )
    }

    private fun animateDice(dice1View: View, dice2View: View) {
        // Animation pour faire bouger les dés de droite à gauche
        val dice1Animator = ObjectAnimator.ofFloat(dice1View, "translationX", -50f, 50f)
        dice1Animator.duration = 500
        dice1Animator.repeatCount = 5
        dice1Animator.repeatMode = ObjectAnimator.REVERSE

        val dice2Animator = ObjectAnimator.ofFloat(dice2View, "translationX", 50f, -50f)
        dice2Animator.duration = 500
        dice2Animator.repeatCount = 5
        dice2Animator.repeatMode = ObjectAnimator.REVERSE

        // Démarrer les animations
        dice1Animator.start()
        dice2Animator.start()
    }
}

// Classe Dice pour générer des valeurs aléatoires
class Dice(private val numSides: Int) {
    fun roll(): Int {
        return (1..numSides).random()
    }
}

