@file:Suppress("DEPRECATION")

package com.example.tp3

import android.annotation.SuppressLint
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient

import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var nom: EditText
    private lateinit var prenom: EditText
    private lateinit var email: EditText
    private lateinit var validerButton: Button
    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar
    private lateinit var progressDialog: ProgressDialog

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nom = findViewById(R.id.nom)
        prenom = findViewById(R.id.prenom)
        email = findViewById(R.id.email)
        validerButton = findViewById(R.id.button)
        webView = findViewById(R.id.webView)
        progressBar = findViewById(R.id.progressBar)
        validerButton.setOnClickListener {
            valider()
            val snack = Snackbar.make(it,"connexion echouéé ",Snackbar.LENGTH_LONG)
            snack.show()

        }
    }

    private fun valider() {
        if (nom.text.isEmpty() || prenom.text.isEmpty() || email.text.isEmpty()) {
            val ad = AlertDialog.Builder(this)
            ad.setMessage("Les champs ne doivent pas être vides")
            ad.setTitle("Error")
            ad.setIcon(android.R.drawable.btn_dialog)
            ad.setPositiveButton("OK") { dialogInterface, _ -> dialogInterface.dismiss() }
            val dialog = ad.create()
            dialog.show()
        } else {
            progressDialog = ProgressDialog(this@MainActivity)
            progressDialog.setTitle("Kotlin Progress Dialog")
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
            progressDialog.setMessage("Downloading music, please wait")
            progressDialog.isIndeterminate = true
            progressDialog.progress = 0
            progressDialog.show()

            // Simuler une tâche en arrière-plan avec un Handler
            val handler = Handler()
            handler.postDelayed(
                {
                    // Faire disparaître la ProgressDialog après la tâche (simulée avec un délai)
                    // Ne pas fermer la ProgressDialog ici
                    // Charger l'URL dans le WebView après la validation des champs
                    webView.webViewClient = MyWebViewClient()
                    webView.settings.javaScriptEnabled = true
                    webView.settings.setSupportZoom(true)
                    webView.loadUrl("https://www.google.com/")
                },
                5000
            )  // Délai de 5000 millisecondes (simulation, remplacez-le par votre tâche réelle)
        }
    }

    inner class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            if (url != null) {
                view?.loadUrl(url)
            }
            return false
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            // Faire disparaître la ProgressBar après le chargement de la page
            progressBar.visibility = View.GONE

            // Fermer la ProgressDialog ici, après le chargement de la page
            progressDialog.dismiss()
        }
    }
}
