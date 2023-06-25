package com.aleph.labs.coderefine

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aleph.labs.coderefine.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        // Initialize views

        // Set click listener for the button
        binding.buttonAction.setOnClickListener {
            val codeSnippet = binding.editTextInput.text.toString()
            val analysisResult = analyzeKotlinCode(codeSnippet)
            displayAnalysisResult(analysisResult)
        }
    }

    private fun analyzeKotlinCode(code: String): List<String> {
        val lintErrors = mutableListOf<String>()

        // Perform your code analysis logic here
        // This is just a sample implementation, you can customize it based on your needs

        // Check for lines longer than 100 characters
        val lines = code.split("\n")
        for ((index, line) in lines.withIndex()) {
            if (line.length > 100) {
                val error = "Line ${index + 1}: Exceeded maximum line length (100 characters)"
                lintErrors.add(error)
            }
        }

        // Check for missing return statements in functions
        val functionPattern = Regex("""fun\s+\w+\(.*\):.*""")
        val matches = functionPattern.findAll(code)
        for (match in matches) {
            val functionDeclaration = match.value
            if (!functionDeclaration.contains("=") && !functionDeclaration.contains("{")) {
                val error = "Function '$functionDeclaration' is missing a return statement"
                lintErrors.add(error)
            }
        }

        return lintErrors
    }

    private fun displayAnalysisResult(analysisResult: List<String>) {
        val resultText = if (analysisResult.isNotEmpty()) {
            analysisResult.joinToString("\n")
        } else {
            "No lint errors found."
        }
        binding.textViewResult.text = resultText
    }

}