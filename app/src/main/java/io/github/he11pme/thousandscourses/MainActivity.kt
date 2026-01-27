package io.github.he11pme.thousandscourses

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import dagger.hilt.android.AndroidEntryPoint
import io.github.he11pme.thousandscourses.databinding.ActivityMainBinding
import io.github.he11pme.thousandscourses.utils.extensions.doOnApplyWindowInsets

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)

        setInsets()

        setContentView(binding.root)
    }

    private fun setInsets() {
        binding.bottomAppBar.doOnApplyWindowInsets { v, _ ->
            v.updatePadding(bottom = 0)
        }

        binding.main.doOnApplyWindowInsets { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(systemBars.left, systemBars.top, systemBars.right)
        }
    }
}