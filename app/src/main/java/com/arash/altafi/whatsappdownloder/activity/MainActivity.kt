package com.arash.altafi.whatsappdownloder.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.arash.altafi.whatsappdownloder.R
import com.arash.altafi.whatsappdownloder.databinding.ActivityMainBinding
import com.arash.altafi.whatsappdownloder.utils.Constants
import com.arash.altafi.whatsappdownloder.utils.MyIntent
import com.arash.altafi.whatsappdownloder.utils.UtilsMethod
import com.arash.altafi.whatsappdownloder.dialog.AboutUsDialog
import com.arash.altafi.whatsappdownloder.dialog.CommentDialog
import com.arash.altafi.whatsappdownloder.dialog.ExitDialog
import com.arash.altafi.whatsappdownloder.utils.*
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setUpNavController()
        setUpBottomNavigation()
        toolBarButtonClick()
        setUpNavigationView()
    }

    private fun setUpNavController() {
        navController = findNavController(R.id.nav_host)
        navController.addOnDestinationChangedListener { _, destination, _ ->

            binding.apply {
                if (destination.id == R.id.show_media_fragment) {
                    toolbar.toGone()
                    bubbleNavigation.toGone()
                } else {
                    toolbar.toShow()
                    bubbleNavigation.toShow()
                }
            }
        }
    }

    //bottomNavigation
    private fun setUpBottomNavigation() {
        binding.bubbleNavigation.setNavigationChangeListener(BubbleNavigationChangeListener { _, position ->
            val bundle = Bundle()
            when (position) {
                0 -> navController.navigate(R.id.guide_fragment)
                1 -> {
                    if (UtilsMethod.isAndroid11orHigher())
                        bundle.putString(
                            Constants.DIRECTORY_KEY,
                            Constants.NEW_WHATSAPP_DIRECTORY
                        )
                    else
                        bundle.putString(Constants.DIRECTORY_KEY, Constants.WHATSAPP_DIRECTORY)

                    navController.navigate(R.id.whatsapp_status_fragment, bundle)
                }
                2 -> {
                    if (UtilsMethod.isAndroid11orHigher())
                        bundle.putString(
                            Constants.DIRECTORY_KEY,
                            Constants.NEW_WHATSAPP_BUSINESS_DIRECTORY
                        )
                    else
                        bundle.putString(
                            Constants.DIRECTORY_KEY,
                            Constants.WHATSAPP_BUSINESS_DIRECTORY
                        )
                    navController.navigate(R.id.wb_status_fragment, bundle)
                }
                3 -> {
                    bundle.putString(Constants.DIRECTORY_KEY, Constants.SAVED_DIRECTORY)
                    navController.navigate(R.id.saved_status_fragment, bundle)
                }
                else -> return@BubbleNavigationChangeListener
            }
        })
    }


    private fun toolBarButtonClick() {
        binding.apply {
            ivWhatsapp.setOnClickListener {
                MyIntent.openWhatsApp(this@MainActivity)
            }
            ivMenu.setOnClickListener {
                drawerLayout.openDrawer(GravityCompat.END)
            }
        }
    }

    private fun setUpNavigationView() {
        binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_item_guide -> {
                    navController.navigate(R.id.guide_fragment)
                    binding.bubbleNavigation.setCurrentActiveItem(0)
                }
                R.id.nav_item_share_app -> MyIntent.shareAppIntent(this@MainActivity)
                R.id.nav_item_other_app -> MyIntent.otherAppIntentBazaar(this@MainActivity)
                R.id.nav_item_comment -> CommentDialog(this@MainActivity).show()
                R.id.nav_item_about_us -> AboutUsDialog(this@MainActivity).show()
                R.id.nav_item_exit -> ExitDialog(this@MainActivity).show()
            }

            closeDrawer()
            true
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        binding.apply {
            if (drawerLayout.isDrawerOpen(GravityCompat.END))
                closeDrawer()
            else if (navController.currentDestination?.id == R.id.show_media_fragment) {
                navController.navigateUp()
            } else
                ExitDialog(this@MainActivity).show()
        }
    }

    private fun closeDrawer() {
        binding.drawerLayout.closeDrawer(GravityCompat.END)
    }

}