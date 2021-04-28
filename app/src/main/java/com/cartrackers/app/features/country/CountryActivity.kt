package com.cartrackers.app.features.country

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.cartrackers.app.R
import com.cartrackers.app.databinding.ActivityCountryBinding
import com.cartrackers.app.features.main.CarTrackActivity
import org.koin.android.ext.android.inject

class CountryActivity: AppCompatActivity() {
    private lateinit var binding: ActivityCountryBinding
    private val countryAdapter: CountryAdapter by lazy { CountryAdapter { country -> onClickListener(country) } }
    private lateinit var resultLayout: LinearLayoutManager
    private val viewModel: ViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountryBinding.inflate(layoutInflater)
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right)
        setContentView(binding.root)

        initAdapter(binding.root)
        setupUi()
    }

    private fun setupUi() {
        binding.countryNextButton.setOnClickListener {
            launchActivity()
        }

        viewModel.searchState.observe(this) { list ->
            countryAdapter.submitList(list)
        }

        binding.country.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.countryList.visibility = View.VISIBLE
                binding.countryNextButton.isEnabled = false
            }
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString()
                if(s?.isNotEmpty() == true) {
                    viewModel.searchFlow.value = query
                }
            }
        })
    }

    private fun initAdapter(view: View) {
        resultLayout = LinearLayoutManager(view.context).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        binding.apply {
            binding.countryList.apply {
                layoutManager = resultLayout
                adapter = countryAdapter
                setHasFixedSize(true)
            }
        }
    }

    private fun launchActivity() {
        startActivity(Intent(this, CarTrackActivity::class.java))
    }

   private fun onClickListener(country: String) {
        binding.country.setText(country)
        binding.countryNextButton.isEnabled = true
        binding.countryList.visibility = View.GONE
    }
}