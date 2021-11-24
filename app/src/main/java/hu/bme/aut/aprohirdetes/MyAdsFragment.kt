package hu.bme.aut.aprohirdetes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hu.bme.aut.aprohirdetes.databinding.FragmentMyAdsBinding

class MyAdsFragment : Fragment(R.layout.fragment_my_ads) {

    private lateinit var binding : FragmentMyAdsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMyAdsBinding.inflate(inflater, container, false)

        binding.newAdButton.setOnClickListener {
            (activity as MainActivity?)?.openNewAd()
        }

        return binding.root
    }
}