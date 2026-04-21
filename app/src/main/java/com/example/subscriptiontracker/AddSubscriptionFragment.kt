package com.example.subscriptiontracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.subscriptiontracker.databinding.FragmentAddSubscriptionBinding

class AddSubscriptionFragment : Fragment() {

    private var _binding: FragmentAddSubscriptionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddSubscriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSaveSubscription.setOnClickListener {
            val name = binding.etSubName.text.toString()
            val price = binding.etSubPrice.text.toString()
            val currency = binding.etSubCurrency.text.toString()

            if (name.isEmpty() || price.isEmpty() || currency.isEmpty()) {
                Toast.makeText(requireContext(), "Заповніть усі поля", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // ТУТ БУДЕ ВІДПРАВКА НА СЕРВЕР
            Toast.makeText(requireContext(), "Дані зібрані: $name, $price $currency", Toast.LENGTH_SHORT).show()

            // Тимчасово просто повертаємося назад після кліку
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}