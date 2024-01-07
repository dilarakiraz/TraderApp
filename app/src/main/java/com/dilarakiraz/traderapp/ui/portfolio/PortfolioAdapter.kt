package com.dilarakiraz.traderapp.ui.portfolio

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dilarakiraz.traderapp.R
import com.dilarakiraz.traderapp.data.model.response.PortfolioItem

/**
 * Created on 6.01.2024
 * @author Dilara Kiraz
 */

class PortfolioAdapter(private var portfolioItems: List<PortfolioItem>) :
    RecyclerView.Adapter<PortfolioAdapter.PortfolioViewHolder>() {

    fun updateData(newData: List<PortfolioItem>) {
        portfolioItems = newData
        notifyDataSetChanged()
    }

    inner class PortfolioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val symbolTextView: TextView = itemView.findViewById(R.id.symbolTextView)
        val qtyT2TextView: TextView = itemView.findViewById(R.id.qtyT2TextView)
        val lastPxTextView: TextView = itemView.findViewById(R.id.lastPxTextView)
        val totalAmountItemTextView: TextView = itemView.findViewById(R.id.totalAmountItemTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PortfolioViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_portfolio, parent, false)

        return PortfolioViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PortfolioViewHolder, position: Int) {
        val currentItem = portfolioItems[position]

        holder.symbolTextView.text = "Symbol: ${currentItem.symbol}"
        holder.qtyT2TextView.text = "Qty_T2: ${currentItem.quantity}"
        holder.lastPxTextView.text = "LastPx: ${currentItem.lastPrice}"

        val totalPrice = currentItem.quantity * currentItem.lastPrice
        holder.totalAmountItemTextView.text = "Total Price: $totalPrice"
    }

    override fun getItemCount() = portfolioItems.size
}