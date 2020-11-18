package com.wmz.utils.utils;

/**
 * @author xiaobo.cui 2014年11月7日 上午11:24:25
 */

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Organization;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.CommonDataKinds.StructuredPostal;
import android.util.Log;

import com.wmz.utils.bean.Contact;

import java.util.ArrayList;

public class GetContactsInfo1 {
    /**
     * 取得mimetype类型
     * MIME types are:
     * CommonDataKinds.StructuredName StructuredName.CONTENT_ITEM_TYPE
     * CommonDataKinds.Phone Phone.CONTENT_ITEM_TYPE
     * CommonDataKinds.Email Email.CONTENT_ITEM_TYPE
     * CommonDataKinds.Photo Photo.CONTENT_ITEM_TYPE
     * CommonDataKinds.Organization Organization.CONTENT_ITEM_TYPE
     * CommonDataKinds.Im Im.CONTENT_ITEM_TYPE
     * CommonDataKinds.Nickname Nickname.CONTENT_ITEM_TYPE
     * CommonDataKinds.Note Note.CONTENT_ITEM_TYPE
     * CommonDataKinds.StructuredPostal StructuredPostal.CONTENT_ITEM_TYPE
     * CommonDataKinds.GroupMembership GroupMembership.CONTENT_ITEM_TYPE
     * CommonDataKinds.Website Website.CONTENT_ITEM_TYPE
     * CommonDataKinds.Event Event.CONTENT_ITEM_TYPE
     * CommonDataKinds.Relation Relation.CONTENT_ITEM_TYPE
     * CommonDataKinds.SipAddress SipAddress.CONTENT_ITEM_TYPE
     */
    public static ArrayList<Contact> getContactInfo(Context context) {
        // 获得通讯录信息 ，URI是ContactsContract.Contacts.CONTENT_URI
        ArrayList<Contact> contacts = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            Contact contact = new Contact();
            //姓名全名
            String display_name = cursor.getString(cursor.getColumnIndex(StructuredName.DISPLAY_NAME));
            contact.name = display_name;
            // 取出电话
            String phoneNumber = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
            contact.phone = phoneNumber;
            //公司
            String company = cursor.getString(cursor.getColumnIndex(Organization.COMPANY));
            String department = cursor.getString(cursor.getColumnIndex(Organization.DEPARTMENT));
            String position = cursor.getString(cursor.getColumnIndex(Organization.TITLE));
            String job_description = cursor.getString(cursor.getColumnIndex(Organization.JOB_DESCRIPTION));
            String office_location = cursor.getString(cursor.getColumnIndex(Organization.OFFICE_LOCATION));
            contact.company = company;
            //地址
            String address = cursor.getString(cursor.getColumnIndex(StructuredPostal.STREET));
            contact.address = address;
            contacts.add(contact);
        }
        cursor.close();
        Log.e("contacts", contacts.toString());
        return contacts;
    }
}
