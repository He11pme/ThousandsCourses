package io.github.he11pme.thousandscourses

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import androidx.core.view.updatePadding
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import io.github.he11pme.thousandscourses.databinding.ActivityMainBinding
import io.github.he11pme.thousandscourses.utils.extensions.doOnApplyWindowInsets

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val appBarManager = AppBarManager()
    private lateinit var binding: ActivityMainBinding
    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.contentContainer) as NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)

        setInsets()
        setSupportActionBar(binding.toolbar)
        setUpNavigation()

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

    private fun setUpNavigation() {
        setUpAppBar()
        setupBottomNavigation()
        observeDestinationChange()
    }

    private fun setUpAppBar() {
        val topLevelDestinationIds = binding.bottomNavigationView.menu.children.map { it.itemId }
        val appBarConfig = AppBarConfiguration(topLevelDestinationIds.toSet())
        binding.toolbar.setupWithNavController(navController, appBarConfig)
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigationView.setupWithNavController(navController)
    }

    private fun observeDestinationChange() {
        navController.addOnDestinationChangedListener { _, dest, _ ->
            binding.appBarState = when (dest.id) {
                R.id.loginFragment -> appBarManager.emptyBar
                R.id.homeFragment -> appBarManager.searchBar
                else -> appBarManager.defaultBar
            }
        }
    }
}