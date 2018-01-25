package com.turtlecoin.turtlewallet

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.turtlecoin.turtlewallet.model.ContactItem
import kotlinx.android.synthetic.main.contact_item.view.*
import java.util.ArrayList

class ContactListAdapter(private val context: Context, private val contactList: ArrayList<ContactItem>) : BaseAdapter() {
    override fun getCount(): Int {
        return contactList.size
    }

    override fun getItem(position: Int): Any {
        return contactList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: View.inflate(context, R.layout.contact_item, null)
        val holder = view.tag as? ContactViewHolder ?: ContactViewHolder(view)

        if (convertView == null) {
            holder.name =  view.findViewById<TextView>(R.id.contact_name)
            holder.address = view.findViewById<TextView>(R.id.contact_address)
        }
        view.tag = holder

        holder.name.setText(contactList[position].name)
        holder.address.setText(contactList[position].address)

        return view
    }

    class ContactViewHolder(view: View){
        var name: TextView = view.contact_name
        var address: TextView = view.contact_address
    }
}