package com.example.subscriptiontracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.subscriptiontracker.api.RetrofitClient
import com.example.subscriptiontracker.databinding.FragmentSubscriptionDetailsBinding
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class SubscriptionDetailsFragment : Fragment() {

    private var _binding: FragmentSubscriptionDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSubscriptionDetailsBinding.inflate(inflater, container, false)
        tokenManager = TokenManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Дістаємо всі дані з Bundle
        val subId = arguments?.getInt("sub_id") ?: -1
        val name = arguments?.getString("sub_name") ?: "Невідомо"
        val price = arguments?.getDouble("sub_price") ?: 0.0
        val currency = arguments?.getString("sub_currency") ?: ""

        // Відображаємо дані
        binding.tvDetailName.text = name
        binding.tvDetailPrice.text = "$price $currency"

        // Кнопка Назад
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        // Кнопка Видалити
        binding.btnDelete.setOnClickListener {
            if (subId != -1) {
                performDelete(subId)
            } else {
                Toast.makeText(requireContext(), "Помилка: ID не знайдено", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun performDelete(id: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val token = tokenManager.getToken.firstOrNull()
                if (token.isNullOrEmpty()) return@launch

                val response = RetrofitClient.apiService.deleteSubscription("Bearer $token", id)

                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Підписку видалено!", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack() // Повертаємось на головний екран
                } else {
                    Toast.makeText(requireContext(), "Не вдалося видалити: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Помилка мережі", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}