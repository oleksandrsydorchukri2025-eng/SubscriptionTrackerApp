package com.example.subscriptiontracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.subscriptiontracker.api.RetrofitClient
import com.example.subscriptiontracker.api.Subscription
import com.example.subscriptiontracker.databinding.FragmentDashboardBinding
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        tokenManager = TokenManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvSubscriptions.layoutManager = LinearLayoutManager(requireContext())

        loadSubscriptions()

        binding.fabAddSubscription.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_addSubscriptionFragment)
        }

        // Логіка для кнопки виходу (Logout)
        // Додай кнопку з id btnLogout у свій XML, якщо хочеш її використовувати
        binding.btnLogout?.setOnClickListener {
            logoutUser()
        }
    }

    private fun loadSubscriptions() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val token = tokenManager.getToken.firstOrNull()
                if (token.isNullOrEmpty()) {
                    Toast.makeText(requireContext(), "Помилка авторизації", Toast.LENGTH_SHORT).show()
                    return@launch
                }

                val tokenHeader = "Bearer $token"
                val response = RetrofitClient.apiService.getSubscriptions(tokenHeader)

                if (response.isSuccessful) {
                    val subscriptions = response.body() ?: emptyList()

                    // Оновлюємо список
                    val adapter = SubscriptionAdapter(subscriptions)
                    binding.rvSubscriptions.adapter = adapter

                    // РАХУЄМО ЗАГАЛЬНУ СУМУ
                    calculateTotal(subscriptions)

                } else {
                    Toast.makeText(requireContext(), "Помилка завантаження: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Немає зв'язку з сервером", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun calculateTotal(subs: List<Subscription>) {
        var totalUah = 0.0

        for (sub in subs) {
            when (sub.currency.uppercase()) {
                "USD", "$" -> totalUah += sub.price * 43.0
                "EUR", "€" -> totalUah += sub.price * 45.0
                "UAH", "ГРН" -> totalUah += sub.price
                else -> totalUah += sub.price
            }
        }

        val formattedTotal = String.format("%.2f", totalUah)
        binding.tvTotalAmount.text = "Всього до сплати: $formattedTotal грн"
    }

    private fun logoutUser() {
        viewLifecycleOwner.lifecycleScope.launch {
            tokenManager.deleteToken() // Очищаємо токен у DataStore
            Toast.makeText(requireContext(), "Ви вийшли з аккаунту", Toast.LENGTH_SHORT).show()
            // Повертаємось на екран логіну та очищаємо стек переходів
            findNavController().navigate(R.id.action_dashboardFragment_to_loginFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}