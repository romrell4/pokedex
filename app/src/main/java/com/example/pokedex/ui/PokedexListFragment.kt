package com.example.pokedex.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.pokedex.databinding.FragmentPokedexListBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PokedexListFragment : Fragment() {
    companion object {
        fun newInstance(): PokedexListFragment {
            return PokedexListFragment()
        }
    }

    private val viewModel: PokedexListViewModel by viewModels {
        defaultViewModelProviderFactory
    }

    private lateinit var binding: FragmentPokedexListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokedexListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.viewStateFlow.collect {
                binding.textHome.text = it.list?.get(0)?.name
            }
        }
    }
}