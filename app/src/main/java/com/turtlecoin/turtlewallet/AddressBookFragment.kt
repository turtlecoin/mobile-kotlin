package com.turtlecoin.turtlewallet


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ListView

import kotlinx.android.synthetic.main.fragment_address_book.*
import android.widget.TextView
import android.widget.ArrayAdapter
import com.turtlecoin.turtlewallet.model.ContactItem


class AddressBookFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_address_book, container, false)

        val items = ArrayList<ContactItem>()
        items.add(ContactItem("Contact #1", "TRTLaaaaaaaa"))
        items.add(ContactItem("Contact #2", "TRTLbbbbbbbb"))
        items.add(ContactItem("Contact #3", "TRTLcccccccc"))

        val adapter = ContactListAdapter(this.context, items)
        val listView = view.findViewById<ListView>(R.id.contact_list)
        listView.adapter = adapter

        return view
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideNoContactsText()


    }

    private fun hideNoContactsText() {
        if (contact_list.count == 0) {
            no_contacts_container.visibility = View.VISIBLE
        }
    }

}
