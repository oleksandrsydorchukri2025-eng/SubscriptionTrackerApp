package com.example.subscriptiontracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.subscriptiontracker.api.LoginRequest
import com.example.subscriptiontracker.api.RetrofitClient
import com.example.subscriptiontracker.databinding.FragmentLoginBinding
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    // Створюємо змінну для нашого TokenManager
    private lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        tokenManager = TokenManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // АВТОЛОГІН: Перевіряємо, чи є вже збережений токен при відкритті екрану
        viewLifecycleOwner.lifecycleScope.launch {
            val token = tokenManager.getToken.firstOrNull()
            if (!token.isNullOrEmpty()) {
                // Якщо токен є, одразу йдемо на Dashboard
                findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
            }
        }

        // Клік по кнопці входу
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Будь ласка, заповніть всі поля", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewLifecycleOwner.lifecycleScope.launch {
                try {
                    val request = LoginRequest(email, password)
                    val response = RetrofitClient.apiService.loginUser(request)

                    if (response.isSuccessful) {
                        // 1. Отримуємо токен із відповіді сервера
                        val token = response.body()?.token

                        // 2. Якщо сервер повернув токен, зберігаємо його
                        if (!token.isNullOrEmpty()) {
                            tokenManager.saveToken(token)
                        }

                        // 3. Переходимо на дашборд
                        findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
                    } else {
                        Toast.makeText(requireContext(), "Неправильний email або пароль", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Помилка підключення: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.tvGoToRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}