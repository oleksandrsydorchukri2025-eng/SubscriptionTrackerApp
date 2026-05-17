package com.example.subscriptiontracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.subscriptiontracker.api.RegisterRequest
import com.example.subscriptiontracker.api.RetrofitClient
import com.example.subscriptiontracker.databinding.FragmentRegisterBinding
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Клік по кнопці реєстрації
        binding.btnRegister.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Заповніть усі поля", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewLifecycleOwner.lifecycleScope.launch {
                try {
                    val request = RegisterRequest(name, email, password)
                    val response = RetrofitClient.apiService.registerUser(request)

                    if (response.isSuccessful) {
                        Toast.makeText(requireContext(), "Реєстрація успішна!", Toast.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                    } else {
                        Toast.makeText(requireContext(), "Помилка реєстрації", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Помилка мережі: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Клік по тексту "Вже є акаунт? Увійти"
        binding.tvGoToLogin.setOnClickListener {
            findNavController().popBackStack() // Повертає на екран логіну
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}