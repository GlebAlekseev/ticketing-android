package ru.alekseevjk.ticketing.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import ru.alekseevjk.ticketing.R
import ru.alekseevjk.ticketing.appComponent
import ru.alekseevjk.ticketing.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var mainFragmentFactory: MainFragmentFactory.AssistedFactory
    private lateinit var mNavHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        applicationContext.appComponent.inject(this)
        supportFragmentManager.fragmentFactory = mainFragmentFactory.create {
            navController.navigate(R.id.action_to_filtersFragment)
        }
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mNavHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        navController = mNavHostFragment.navController
    }
}