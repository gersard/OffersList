package cl.gersard.offerslist.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import cl.gersard.offerslist.R
import cl.gersard.offerslist.data.model.BasketData
import cl.gersard.offerslist.databinding.ActivityMainBinding
import cl.gersard.offerslist.ui.basket.BasketAdapter
import cl.gersard.offerslist.ui.basket.OffersBasketFragment
import cl.gersard.offerslist.ui.list.OffersListFragment
import cl.gersard.offerslist.util.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel = viewModels<OffersViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        showFragment(OffersListFragment(), false)

        setUpUI()
        setUpObservers()
    }

    private fun setUpObservers() {
        observe(viewModel.value.basketData, ::handleBasketData)
    }

    private fun setUpUI() {
        updateBasketData(0, 0)
    }

    //region Basket Data
    private fun handleBasketData(basketData: BasketData) {
        var quantity = 0
        var totalPrice = 0

        basketData.items.forEach {
            quantity += it.qty
            totalPrice += it.item.price * it.qty
        }

        updateBasketData(quantity, totalPrice)
    }

    private fun updateBasketData(quantity: Int, totalPrice: Int) {
        with(binding.contentMain) {
            // Basket data
            qty.text = getString(R.string.quantity_format, quantity)
            total.text = getString(R.string.total_format, totalPrice)
        }
    }
    //endregion

    //region Menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.action_basket) {
            showFragment(OffersBasketFragment(), true)
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
    //endregion

    private fun showFragment(fragment: Fragment, addToBackStack: Boolean) {
        val transaction = supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_content_main, fragment)

        if (addToBackStack) transaction.addToBackStack(null)

        transaction.commit()
    }

}