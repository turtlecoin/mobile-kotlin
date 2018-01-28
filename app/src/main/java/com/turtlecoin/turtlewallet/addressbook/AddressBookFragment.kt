package com.turtlecoin.turtlewallet.addressbook


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.turtlecoin.turtlewallet.R
import com.turtlecoin.turtlewallet.ReceiveAddressActivity
import com.turtlecoin.turtlewallet.db.Contact
import com.turtlecoin.turtlewallet.model.ContactItem
import kotlinx.android.synthetic.main.fragment_address_book.*
import java.io.Serializable


class AddressBookFragment : Fragment() {
    private lateinit var viewModel: AddressBookViewModel
    private lateinit var adapter: ContactListAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_address_book, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = ContactListAdapter(this@AddressBookFragment.context, mutableListOf())
        contact_list!!.adapter = adapter
        contact_list!!.setOnItemClickListener({ _, _, position, _ ->
            val contactItem = contact_list!!.getItemAtPosition(position) as ContactItem
            // TODO send contactItem to ViewContactActivity
            val intent = Intent(activity, ReceiveAddressActivity::class.java)
            intent.putExtra("contact", contactItem as Serializable)
            intent.putExtra("editable", true)
            activity.startActivity(intent)
        })
        viewModel = ViewModelProviders.of(this).get(AddressBookViewModel::class.java)
        viewModel.watchContacts().observe(this, Observer { updateUi(it) })
    }

    private fun updateUi(contacts: List<Contact>?) {
        if (contacts == null || contacts.isEmpty()){
            adapter.contactList = emptyList()
        }else {
            val items = contacts.map { ContactItem(it.cid, it.alias, it.address) }
            adapter.contactList = items
        }
        adapter.notifyDataSetChanged()
        hideNoContactsText()
    }

    private fun hideNoContactsText() {
        if (contact_list.count == 0) {
            no_contacts_container.visibility = View.VISIBLE
        } else {
            no_contacts_container.visibility = View.GONE
        }
    }
}
