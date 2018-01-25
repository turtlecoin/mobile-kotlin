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



class AddressBookFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater!!.inflate(R.layout.fragment_address_book, container, false)
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideNoContactsText()
    }

    private fun hideNoContactsText() {
        if (contact_list.childCount == 0) {
            no_contacts_container.visibility = View.VISIBLE
        }
    }

}
