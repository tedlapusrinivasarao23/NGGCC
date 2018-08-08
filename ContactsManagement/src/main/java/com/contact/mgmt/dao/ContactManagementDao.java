package com.contact.mgmt.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.contact.mgmt.bos.AddContact;
import com.contact.mgmt.bos.ContactGroupTrans;
import com.contact.mgmt.bos.LoginUserDetails;
import com.contact.mgmt.bos.ShareContact;
import com.contact.mgmt.bos.SignUpContact;
import com.contact.mgmt.bos.SignUpTransactionDetails;
import com.contact.mgmt.dummy.ContactManagementQueries;
import com.contact.mgmt.dummy.ForgotPassword;
import com.contact.mgmt.dummy.UpdatePassword;
import com.contact.mgmt.forms.LoginContact;

@Repository("contactManagementDao")
public class ContactManagementDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;

	public SignUpContact getSignUpContactByEmailId(SignUpContact signUpContact) {

		SignUpContact signUpContactList = (SignUpContact) hibernateTemplate
				.find(ContactManagementQueries.SIGNUP_QUERY_BY_EMAILID, new Object[] { signUpContact.getEmailId() })
				.get(0);
		hibernateTemplate.flush();
		hibernateTemplate.clear();

		return signUpContactList;
	}

	public SignUpContact getSignUpContactByPhoneNumber(SignUpContact signUpContact) {

		SignUpContact signUpContactList = (SignUpContact) hibernateTemplate
				.find(ContactManagementQueries.SIGNUP_QUERY_BY_PHONE_NUMBER,
						new Object[] { signUpContact.getPhoneNumber() })
				.get(0);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return signUpContactList;
	}

	public List<LoginUserDetails> getCheckForFirstLogin(String phoneNumber, String emailId) {

		List<LoginUserDetails> loginUserDetailsList = (List<LoginUserDetails>) hibernateTemplate.find(
				ContactManagementQueries.LOGIN_QUERY_BY_EMAILID_OR_PHONENUMBER, new Object[] { emailId, phoneNumber  });
		
		
			hibernateTemplate.flush();
			hibernateTemplate.clear();
			return loginUserDetailsList;
		

	}

	public SignUpContact getSubscriptionDaysLeftAndDateEnd(String ownerNumber, String ownerEmail) {

		SignUpContact signUpContactList = (SignUpContact) hibernateTemplate
				.find(ContactManagementQueries.SIGNUP_QUERY_BY_EMAILID_AND_PHONENUMBER,
						new Object[] { ownerEmail, ownerNumber })
				.get(0);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return signUpContactList;
	}

	public SignUpContact getSignUpContactByEmailIdAndPhoneNumber(LoginContact loginContact) {

		SignUpContact signUpContactList = (SignUpContact) hibernateTemplate
				.find(ContactManagementQueries.SIGNUP_QUERY_BY_EMAILID_OR_PHONENUMBER,
						new Object[] { loginContact.getEmailId(), loginContact.getPhoneNumber() })
				.get(0);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return signUpContactList;
	}

	public SignUpContact getSignUpContactByEmailIdAndPhoneNumber(ForgotPassword forgotPassword) {

		SignUpContact signUpContactList = (SignUpContact) hibernateTemplate
				.find(ContactManagementQueries.SIGNUP_QUERY_BY_EMAILID_OR_PHONENUMBER,
						new Object[] { forgotPassword.getOwnerEmailId(), forgotPassword.getPhoneNumber() })
				.get(0);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return signUpContactList;
	}

	public SignUpContact getSignUpContactByEmailIdAndPhoneNumberForUpdatePassword(UpdatePassword updatePassword) {

		SignUpContact signUpContactList = (SignUpContact) hibernateTemplate
				.find(ContactManagementQueries.SIGNUP_QUERY_BY_EMAILID_OR_PHONENUMBER,
						new Object[] { updatePassword.getEmailId(), updatePassword.getPhoneNumber() })
				.get(0);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return signUpContactList;
	}

	/*
	 * @SuppressWarnings("unchecked") public List<AddContact> addContact(AddContact
	 * contact) {
	 * 
	 * List<AddContact> addContactDbLists = (List<AddContact>) hibernateTemplate
	 * .find(ContactManagementQueries.ADDCONTACT_BY_OWNERNUMBER, new Object[] {
	 * contact.getOwnerNumber() }); hibernateTemplate.flush();
	 * hibernateTemplate.clear(); return addContactDbLists;
	 * 
	 * }
	 */

	@SuppressWarnings("unchecked")
	public List<AddContact> addContactByOwnerNumber(String ownerNumber) {

		List<AddContact> addContactDbLists = (List<AddContact>) hibernateTemplate
				.find(ContactManagementQueries.ADDCONTACT_BY_OWNERNUMBER, new Object[] { ownerNumber });
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return addContactDbLists;

	}

	public ContactGroupTrans getGroupTransByOwnerNumberAndContactId(String ownerNumber, int id) {

		ContactGroupTrans contactGroupTrans = (ContactGroupTrans) hibernateTemplate
				.find(ContactManagementQueries.GET_CONTACT_GROUPTRANS_BY_OWNERNUMBER_AND_ID,
						new Object[] { ownerNumber, id })
				.get(0);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return contactGroupTrans;
	}

	public List<ShareContact> getShareContactForDelete(String ownerNumber, int id) {
		@SuppressWarnings("unchecked")
		List<ShareContact> shareContacts = (List<ShareContact>) hibernateTemplate.find(
				ContactManagementQueries.GET_SHARECONTACT_BY_OWNER_AND_ACTIVETRUE, new Object[] { ownerNumber, id });
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return shareContacts;
	}

	public SignUpContact getSignUpContactByEmailIdAndPhoneNumberForTransactionUpdate(
			SignUpTransactionDetails signUpTransactionDetails) {

		SignUpContact signUpContactList = (SignUpContact) hibernateTemplate.find(
				ContactManagementQueries.SIGNUP_QUERY_BY_EMAILID_OR_PHONENUMBER,
				new Object[] { signUpTransactionDetails.getOwnerEmail(), signUpTransactionDetails.getOwnerNumber() })
				.get(0);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return signUpContactList;
	}
	
	

	
	
	public void methodForSave(Object object) {
		hibernateTemplate.save(object);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
	}

	public void methodForUpdate(Object object) {
		hibernateTemplate.update(object);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
	}

	public void methodForSaveORUpdate(Object object) {
		hibernateTemplate.saveOrUpdate(object);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
	}

	@SuppressWarnings("unchecked")
	public Object getFromDb(Class object, int id) {
		Object addContact = hibernateTemplate.get(object, id);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return addContact;
	}

	public void methodForDelete(Object object) {
		hibernateTemplate.delete(object);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
	}
	
	public void methodForDeleteAll(List<AddContact> list) {
		hibernateTemplate.deleteAll(list);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
	}


	
}
