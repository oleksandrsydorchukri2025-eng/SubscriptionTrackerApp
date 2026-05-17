package com.example.subscriptiontracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.subscriptiontracker.api.Subscription // Додали імпорт моделі

class SubscriptionAdapter(private val subs: List<Subscription>) :
    RecyclerView.Adapter<SubscriptionAdapter.SubViewHolder>() {

    class SubViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.tvSubName)
        val price: TextView = view.findViewById(R.id.tvSubPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_subscription, parent, false)
        return SubViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubViewHolder, position: Int) {
        val sub = subs[position]
        holder.name.text = sub.name
        holder.price.text = "${sub.price} ${sub.currency}"

        holder.itemView.setOnClickListener { view ->
            val bundle = Bundle().apply {
                putInt("sub_id", sub.id) // Передаємо ID
                putString("sub_name", sub.name)
                putDouble("sub_price", sub.price)
                putString("sub_currency", sub.currency)
            }

            // Переконайся, що в nav_graph цей ID співпадає!
            view.findNavController().navigate(
                R.id.action_dashboardFragment_to_subscriptionDetailsFragment,
                bundle
            )
        }
    }

    override fun getItemCount() = subs.size
}