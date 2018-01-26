package com.turtlecoin.turtlewallet


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.turtlecoin.turtlewallet.db.Contact
import com.turtlecoin.turtlewallet.db.DB

import kotlinx.android.synthetic.main.fragment_address_book.*
import com.turtlecoin.turtlewallet.model.ContactItem
import java.io.Serializable

class AddressBookFragment : Fragment() {

    var db: DB = DB()
    private var listView: ListView? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_address_book, container, false)

        db = DB(activity)

        val contacts:List<Contact> = db.getContacts()
        val items = ArrayList<ContactItem>()
        for(c in contacts) {
            items.add(ContactItem(c.cid, c.alias, c.address))
        }

        val adapter = ContactListAdapter(this.context, items)
        listView = view.findViewById<ListView>(R.id.contact_list) as ListView
        listView!!.adapter = adapter
        listView!!.setOnItemClickListener({ _, _, position, _ ->
            val contactItem = listView!!.getItemAtPosition(position) as ContactItem

            // TODO send contactItem to ViewContactActivity
            val intent = Intent(activity, ReceiveAddressActivity::class.java)
            intent.putExtra("contact", contactItem as Serializable)
            intent.putExtra("editable", true)
            activity.startActivity(intent)
        })

        return view
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideNoContactsText()
    }

    private fun hideNoContactsText() {
        if (contact_list.count == 0) {
            no_contacts_container.visibility = View.VISIBLE
        } else {
            no_contacts_container.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()

        loadList()
    }

    fun loadList() {
        val contacts:List<Contact> = db.getContacts()
        val items = ArrayList<ContactItem>()
        for(c in contacts) {
            items.add(ContactItem(c.cid, c.alias, c.address))
        }
        val adapter = ContactListAdapter(this.context, items)
        listView!!.adapter = adapter

        hideNoContactsText()
    }
}
