package uk.ac.aber.dcs.cs31620.faa.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import uk.ac.aber.dcs.cs31620.faa.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
    }
}