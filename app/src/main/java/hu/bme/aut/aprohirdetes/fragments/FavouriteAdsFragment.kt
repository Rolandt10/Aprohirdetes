package hu.bme.aut.aprohirdetes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hu.bme.aut.aprohirdetes.R
import hu.bme.aut.aprohirdetes.databinding.FragmentFavAdsBinding

class FavouriteAdsFragment : Fragment(R.layout.fragment_fav_ads) {

    private lateinit var binding: FragmentFavAdsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavAdsBinding.inflate(inflater, container, false)

        return binding.root
    }
}