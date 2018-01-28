package com.turtlecoin.turtlewallet.addressbook

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.turtlecoin.turtlewallet.db.Contact
import com.turtlecoin.turtlewallet.db.ContactDatabase


class AddressBookViewModel : ViewModel(){
    private var contactLiveData: LiveData<List<Contact>>? = null

    fun watchContacts(): LiveData<List<Contact>> {
        if (contactLiveData == null) {
            contactLiveData = ContactDatabase.observeContacts()
        }
        return contactLiveData as LiveData<List<Contact>>
    }
}
