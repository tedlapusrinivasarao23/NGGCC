package com.contact.mgmt.service;

import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.contact.mgmt.bos.AddContact;
import com.contact.mgmt.bos.AddContactEmails;
import com.contact.mgmt.bos.AddContactPhoneNumbers;
import com.contact.mgmt.bos.AddMultipleContacts;
import com.contact.mgmt.bos.ContactGroupTrans;
import com.contact.mgmt.bos.DeleteMultipleContacts;
import com.contact.mgmt.bos.LoginUserDetails;
import com.contact.mgmt.bos.LogoutContactDetails;
import com.contact.mgmt.bos.ShareContact;
import com.contact.mgmt.bos.SignUpContact;
import com.contact.mgmt.bos.SignUpTransactionDetails;
import com.contact.mgmt.dao.ContactManagementDao;
import com.contact.mgmt.dao.GroupManagementDao;
import com.contact.mgmt.dummy.ContactComparator;
import com.contact.mgmt.dummy.ContactManagementQueries;
import com.contact.mgmt.dummy.ForgotPassword;
import com.contact.mgmt.dummy.LoginResponse;
import com.contact.mgmt.dummy.LogoutResponse;
import com.contact.mgmt.dummy.MoveContactToGroup;
import com.contact.mgmt.dummy.ResultResponse;
import com.contact.mgmt.dummy.SignUpContactResponse;
import com.contact.mgmt.dummy.UpdatePassword;
import com.contact.mgmt.forms.LoginContact;
import com.contact.mgmt.forms.LogoutContact;
import com.contact.mgmt.mappers.SendEmail;
import com.contact.mgmt.mappers.SendSms;

@Service("contactManagementService")
public class ContactManagementService {

	@Autowired
	private ContactManagementDao contactManagementDao;

	@Autowired
	private GroupManagementDao groupManagementDao;

	@Autowired
	private GroupManagementService groupManagementService;

	@Autowired
	private SendEmail sendEmail;

	@Autowired
	private SendSms sendSms;

	@Transactional
	public ResultResponse signUpContact(SignUpContact signUpContact) {

		ResultResponse resultResponse = new ResultResponse();
		String uuid = UUID.randomUUID().toString();
		String[] newPasswords = uuid.split("-");
		String newpassword = newPasswords[0];

		try {
			SignUpContact signUpContactList = contactManagementDao.getSignUpContactByEmailId(signUpContact);
			if (signUpContactList.getEmailId().equals(signUpContact.getEmailId())) {
				resultResponse.setResult("Given Email_Id Already Exists");
				return resultResponse;
			}
		} catch (Exception e) {

			try {
				SignUpContact signUpContactList = contactManagementDao.getSignUpContactByPhoneNumber(signUpContact);
				if (signUpContactList.getPhoneNumber().equals(signUpContact.getPhoneNumber())) {
					resultResponse.setResult("Given Phone Number Already Exists");
					return resultResponse;

				}
			} catch (Exception exe) {
				signUpContact.setPassword(newpassword);
				signUpContact.setSignupDateAndTime(getCurrentSystemDateAndTime());
				contactManagementDao.methodForSave(signUpContact);
			}
		}
		sendEmail.sendEmailForgotPassword(signUpContact.getEmailId(), newpassword,
				" Welcome To NearGeoContacts.com Please use the below credentials to login for the first time and reset the password after login. Username: "
						+ signUpContact.getEmailId() + " Password: ");
		sendSms.sendSMS(signUpContact.getPhoneNumber(), newpassword,
				" Welcome To NearGeoContacts.com Please use the below credentials to login for the first time and reset the password after login. Username: "
						+ signUpContact.getEmailId() + " Password: ");
		resultResponse.setResult("Sign Up done Successfully password Sent to ur registered Email and Phone Number");
		return resultResponse;
	}

	@Transactional
	public LoginResponse loginContact(LoginContact loginContact) {

		LoginResponse loginResponse = new LoginResponse();

		try {
			SignUpContact signUpContactList = contactManagementDao
					.getSignUpContactByEmailIdAndPhoneNumber(loginContact);
			
			List<LoginUserDetails> loginUserDetailsList = contactManagementDao.getCheckForFirstLogin(loginContact.getPhoneNumber(),loginContact.getEmailId());
			
			if (signUpContactList.getPassword().equals(loginContact.getPassword())) {

				LoginUserDetails loginUserDetails = new LoginUserDetails();

				loginUserDetails.setUserEmailId(loginContact.getEmailId());
				loginUserDetails.setUserPhoneNumber(loginContact.getPhoneNumber());
				loginUserDetails.setLastLoginDeviceID(loginContact.getLoginDeviceID());
				loginUserDetails.setLastLoginDeviceOS(loginContact.getLoginDeviceOS());
				loginUserDetails.setLastLoginLocation(loginContact.getLoginLocation());
				loginUserDetails.setLastLoginTime(getCurrentSystemDateAndTime());
				loginUserDetails.setLastLoginDeviceOSVersion(loginContact.getLoginDeviceOSVersion());
				loginUserDetails.setLastLoginDeviceModel(loginContact.getLoginDeviceModel());
				contactManagementDao.methodForSave(loginUserDetails);
				
				if(loginUserDetailsList.size()>0) {
					loginResponse.setFirstLoginMessage("Not First Login");
				}else {
					loginResponse.setFirstLoginMessage("First Login");
				}
				
				loginResponse.setUserName(signUpContactList.getUserName());
				loginResponse.setEmailId(signUpContactList.getEmailId());
				loginResponse.setPhoneNumber(signUpContactList.getPhoneNumber());
				loginResponse.setProfileImage(signUpContactList.getImage());
				loginResponse.setAddress(signUpContactList.getAddress());
				loginResponse.setPaid(signUpContactList.isPaid());
				loginResponse.setLoginMessage("Login Successful");

				return loginResponse;
			}

		} catch (Exception e) {
			loginResponse.setLoginMessage("In valid " + loginContact.getEmailId() + "" + loginContact.getPhoneNumber());
			return loginResponse;

		}
		loginResponse.setLoginMessage("Invalid Password");

		return loginResponse;
	}

	@Transactional
	public LogoutResponse logoutContact(LogoutContact logoutContact) {

		LogoutResponse logoutResponse = new LogoutResponse();

		LogoutContactDetails logoutContactDetails = new LogoutContactDetails();
		logoutContactDetails.setLogoutDeviceIDHistory(logoutContact.getLogoutDeviceID());
		logoutContactDetails.setLogoutDeviceOSHistory(logoutContact.getLogoutDeviceOS());
		logoutContactDetails.setLogoutLocationHistory(logoutContact.getLogoutLocation());
		logoutContactDetails.setLogoutTimeAndDateHistory(getCurrentSystemDateAndTime());
		logoutContactDetails.setUserEmailId(logoutContact.getUserEmailId());
		logoutContactDetails.setUserPhoneNumber(logoutContact.getUserPhoneNumber());
		logoutContactDetails.setLogoutDeviceModel(logoutContact.getLogoutDeviceModel());
		logoutContactDetails.setLogoutDeviceOSVersion(logoutContact.getLogoutDeviceOSVersion());
		contactManagementDao.methodForSave(logoutContactDetails);

		logoutResponse.setLogoutResult("Successfully LoggedOut");

		return logoutResponse;

	}

	@Transactional
	public ResultResponse updatePassword(UpdatePassword updatePassword) {

		ResultResponse resultResponse = new ResultResponse();

		try {
			SignUpContact signUpContactList = contactManagementDao
					.getSignUpContactByEmailIdAndPhoneNumberForUpdatePassword(updatePassword);
			if (signUpContactList.getPassword().equals(updatePassword.getOldPassword())) {
				signUpContactList.setPassword(updatePassword.getNewPassword());
				contactManagementDao.methodForUpdate(signUpContactList);
				resultResponse.setResult("Password Updated Successfully");
				return resultResponse;
			}
		} catch (Exception e) {
			resultResponse.setResult(
					"No Contact with Given :" + updatePassword.getEmailId() + updatePassword.getPhoneNumber());
			return resultResponse;
		}
		resultResponse.setResult("Invalid Old Password");
		return resultResponse;

	}

	@SuppressWarnings("unchecked")
	@Transactional
	public ResultResponse addContact(AddContact contact) {

		AddContact addCnt = null;
		ResultResponse resultResponse = new ResultResponse();
		ResultResponse resultResponse1 = new ResultResponse();
		List<AddContact> addContactDbLists = null;

		int temp = 0;

		Collection<AddContactPhoneNumbers> addContactPhoneNumbersLists = contact.getAddContactPhoneNumbers();
		Collection<AddContactEmails> addContactEmailsLists = contact.getAddContactEmails();

		List<String> phoneNumbers = new ArrayList<String>();
		Set<String> phoneNumbersSets = new HashSet<String>();
		List<String> emailIds = new ArrayList<String>();
		Set<String> emailIdsSets = new HashSet<String>();

		try {

			addContactDbLists = contactManagementDao.addContactByOwnerNumber(contact.getOwnerNumber());
			Collection<AddContactPhoneNumbers> addContactPhoneNumbersDbLists = null;
			Collection<AddContactEmails> addContactEmailsDbLists = null;

			for (AddContactPhoneNumbers addContactPhoneNumbersList : addContactPhoneNumbersLists) {
				phoneNumbers.add(addContactPhoneNumbersList.getPhoneNumber());
				phoneNumbersSets.add(addContactPhoneNumbersList.getPhoneNumber());

				for (AddContact addContactDbList : addContactDbLists) {

					addContactPhoneNumbersDbLists = addContactDbList.getAddContactPhoneNumbers();

					for (AddContactPhoneNumbers addContactPhoneNumbersDbList : addContactPhoneNumbersDbLists) {

						if (addContactPhoneNumbersDbList.getPhoneNumber()
								.equals(addContactPhoneNumbersList.getPhoneNumber())) {
							temp = 1;
						}
					}
				}
			}

			for (AddContactEmails addContactEmailsList : addContactEmailsLists) {

				emailIds.add(addContactEmailsList.getEmailId());
				emailIdsSets.add(addContactEmailsList.getEmailId());

				for (AddContact addContactDbList : addContactDbLists) {

					addContactEmailsDbLists = addContactDbList.getAddContactEmails();

					for (AddContactEmails addContactEmailsDbList : addContactEmailsDbLists) {

						if (addContactEmailsDbList.getEmailId().equals(addContactEmailsList.getEmailId())) {
							temp = 2;
						}
					}
				}
			}

			if (phoneNumbers.size() > phoneNumbersSets.size()) {
				temp = 3;
			}

			if (emailIds.size() > emailIdsSets.size()) {
				temp = 4;
			}

			if (temp == 1) {
				resultResponse.setResult("Given Phone Number Already Exists");
				return resultResponse;
			} else if (temp == 2) {
				resultResponse.setResult("Given Email Id Already Exists");
				return resultResponse;
			} else if (temp == 3) {

				resultResponse.setResult("Both the Mobile numbers should not be unique");
				return resultResponse;
			} else if (temp == 4) {
				resultResponse.setResult("Both the Email Id's should not be unique");
				return resultResponse;
			} else {

				if (contact.getFile() != null) {

					byte[] imageData = new byte[(int) contact.getFile().length()];

					try {
						FileInputStream fileInputStream = new FileInputStream(contact.getFile());
						fileInputStream.read(imageData);
						fileInputStream.close();
					} catch (Exception e5) {
						e5.printStackTrace();
					}

					contact.setImage(imageData);
					contactManagementDao.methodForSave(contact);
					groupManagementService.addContactToGroup(contact.getOwnerNumber(), contact.getGroupName(),
							contact.getId());

					resultResponse.setResult("Contact Saved Successfully");
					return resultResponse;
				} else {
					contactManagementDao.methodForSave(contact);
					groupManagementService.addContactToGroup(contact.getOwnerNumber(), contact.getGroupName(),
							contact.getId());

					resultResponse.setContact(getContactById(contact.getId()));
					resultResponse.setResult("Contact Saved Successfully");
					return resultResponse;
				}
			}
		} catch (Exception e) {

			if (contact.getFile() != null) {

				byte[] imageData = new byte[(int) contact.getFile().length()];

				try {
					FileInputStream fileInputStream = new FileInputStream(contact.getFile());
					fileInputStream.read(imageData);
					fileInputStream.close();
				} catch (Exception e5) {
					e5.printStackTrace();
				}

				contact.setImage(imageData);
				contactManagementDao.methodForSave(contact);
				groupManagementService.addContactToGroup(contact.getOwnerNumber(), contact.getGroupName(),
						contact.getId());
				resultResponse.setResult("Contact Saved Successfully");
				return resultResponse;
			} else {
				contactManagementDao.methodForSave(contact);
				groupManagementService.addContactToGroup(contact.getOwnerNumber(), contact.getGroupName(),
						contact.getId());

				resultResponse.setResult("Contact Saved Successfully");
				return resultResponse;
			}
		}
	}

	@Transactional
	public AddContact getContactById(int id) {
		AddContact contact = null;
		try {
			contact = (AddContact) contactManagementDao.getFromDb(AddContact.class, id);
		} catch (Exception e) {
			return null;
		}
		return contact;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<AddContact> getContactList(String ownerNumber) {
		List<AddContact> contctList = null;
		try {
			contctList = contactManagementDao.addContactByOwnerNumber(ownerNumber);

			Collections.sort(contctList, new ContactComparator());
		} catch (Exception e) {
			return null;
		}
		return contctList;
	}

	@Transactional
	public ResultResponse deleteContact(String ownerNumber, int id) {
		ResultResponse resultResponse = new ResultResponse();
		ContactGroupTrans contactGroupTrans = null;
		List<ShareContact> shareContactsList = null;
		try {
			//contactManagementDao.methodForDelete(contactManagementDao.getFromDb(AddContact.class, id));

			try {

				shareContactsList = contactManagementDao.getShareContactForDelete(ownerNumber, id);

				for (ShareContact shareContact : shareContactsList) {
					shareContact.setActive(false);
					contactManagementDao.methodForUpdate(shareContact);
				}

				contactGroupTrans = contactManagementDao.getGroupTransByOwnerNumberAndContactId(ownerNumber, id);
				contactGroupTrans.setActive(false);
				contactManagementDao.methodForUpdate(contactGroupTrans);

			} catch (Exception e) {
				try {
					contactGroupTrans = contactManagementDao.getGroupTransByOwnerNumberAndContactId(ownerNumber, id);
					contactGroupTrans.setActive(false);
					contactManagementDao.methodForUpdate(contactGroupTrans);
				} catch (Exception e1) {
					resultResponse.setResult("Contact Deleted Successfully");
					return resultResponse;
				}
			}

			resultResponse.setResult("Contact Deleted Successfully");
			return resultResponse;
		} catch (Exception e) {
			resultResponse.setResult("Contact Not with Given Id");
			return resultResponse;
		}
	}

	@Transactional
	public ResultResponse editContact(AddContact contact) {
		ResultResponse resultResponse = new ResultResponse();
		int temp = 0;

		AddContact addContact = null;
		List<AddContact> addContactDbLists = null;

		ContactGroupTrans contactGroupTrans = null;

		Collection<AddContactPhoneNumbers> addContactPhoneNumbersLists = contact.getAddContactPhoneNumbers();
		Collection<AddContactEmails> addContactEmailsLists = contact.getAddContactEmails();
		try {
			addContactDbLists = contactManagementDao.addContactByOwnerNumber(contact.getOwnerNumber());
			Collection<AddContactPhoneNumbers> addContactPhoneNumbersDbLists = null;
			Collection<AddContactEmails> addContactEmailsDbLists = null;

			List<String> phoneNumbers = new ArrayList<String>();
			Set<String> phoneNumbersSets = new HashSet<String>();
			List<String> emailIds = new ArrayList<String>();
			Set<String> emailIdsSets = new HashSet<String>();

			for (AddContact addContactDbList : addContactDbLists) {
				if (contact.getId() == addContactDbList.getId()) {
					addContact = addContactDbList;
				} else {
					addContactPhoneNumbersDbLists = addContactDbList.getAddContactPhoneNumbers();
					addContactEmailsDbLists = addContactDbList.getAddContactEmails();
				}
			}

			for (AddContactPhoneNumbers addContactPhoneNumbersList : addContactPhoneNumbersLists) {

				phoneNumbers.add(addContactPhoneNumbersList.getPhoneNumber());
				phoneNumbersSets.add(addContactPhoneNumbersList.getPhoneNumber());

				for (AddContactPhoneNumbers addContactPhoneNumbersDbList : addContactPhoneNumbersDbLists) {

					if (addContactPhoneNumbersDbList.getPhoneNumber()
							.equals(addContactPhoneNumbersList.getPhoneNumber())) {
						temp = 1;
					}
				}
			}

			for (AddContactEmails addContactEmailsList : addContactEmailsLists) {

				emailIds.add(addContactEmailsList.getEmailId());
				emailIdsSets.add(addContactEmailsList.getEmailId());

				for (AddContactEmails addContactEmailsDbList : addContactEmailsDbLists) {

					if (addContactEmailsDbList.getEmailId().equals(addContactEmailsList.getEmailId())) {
						temp = 2;
					}
				}
			}

			if (phoneNumbers.size() > phoneNumbersSets.size()) {
				temp = 3;
			}

			if (emailIds.size() > emailIdsSets.size()) {
				temp = 4;
			}

			if (temp == 1) {
				resultResponse.setResult("Given Phone Number Already Exists");
				return resultResponse;
			} else if (temp == 2) {
				resultResponse.setResult("Given Email Id Already Exists");
				return resultResponse;
			} else if (temp == 3) {

				resultResponse.setResult("Both the Mobile numbers should not be unique");
				return resultResponse;
			} else if (temp == 4) {
				resultResponse.setResult("Both the Email Id's should not be unique");
				return resultResponse;
			} else {

				if (contact.getFile() != null) {

					byte[] imageData = new byte[(int) contact.getFile().length()];

					try {
						FileInputStream fileInputStream = new FileInputStream(contact.getFile());
						fileInputStream.read(imageData);
						fileInputStream.close();
					} catch (Exception e5) {
						e5.printStackTrace();
					}

					contact.setImage(imageData);
					contactManagementDao.methodForUpdate(contact);

					try {
						contactGroupTrans = groupManagementDao.getContactGroupByContactIdAndOwnerisActiveTrue(contact);
						MoveContactToGroup moveContactToGroup = new MoveContactToGroup();
						moveContactToGroup.setContactId(contact.getId());
						moveContactToGroup.setFromGroup(contactGroupTrans.getGroupName());
						moveContactToGroup.setToGroup(contact.getGroupName());
						moveContactToGroup.setOwnerNumber(contact.getOwnerNumber());
						groupManagementService.moveContactToGroup(moveContactToGroup);
					} catch (Exception e4) {
						groupManagementService.addContactToGroup(contact.getOwnerNumber(), contact.getGroupName(),
								contact.getId());

					}

					resultResponse.setResult("Contact Updated Successfully");
					resultResponse.setContact(contact);
					return resultResponse;
				} else {
					contactManagementDao.methodForUpdate(contact);

					try {
						contactGroupTrans = groupManagementDao.getContactGroupByContactIdAndOwnerisActiveTrue(contact);
						MoveContactToGroup moveContactToGroup = new MoveContactToGroup();
						moveContactToGroup.setContactId(contact.getId());
						moveContactToGroup.setFromGroup(contactGroupTrans.getGroupName());
						moveContactToGroup.setToGroup(contact.getGroupName());
						moveContactToGroup.setOwnerNumber(contact.getOwnerNumber());
						groupManagementService.moveContactToGroup(moveContactToGroup);
					} catch (Exception e6) {
						groupManagementService.addContactToGroup(contact.getOwnerNumber(), contact.getGroupName(),
								contact.getId());

					}

					resultResponse.setResult("Contact Updated Successfully");
					resultResponse.setContact(contact);
					return resultResponse;
				}

			}
		} catch (Exception e) {
			resultResponse.setResult("please provide correct owner Number");
			return resultResponse;
		}

	}

	@Transactional
	public ResultResponse forgotPassword(ForgotPassword forgotPassword) {

		ResultResponse resultResponse = new ResultResponse();
		boolean result = false;
		boolean resultSms = false;
		String uuid = UUID.randomUUID().toString();
		String[] newPasswords = uuid.split("-");
		String newpassword = newPasswords[0];

		SignUpContact signUpContact = null;

		try {
			signUpContact = contactManagementDao.getSignUpContactByEmailIdAndPhoneNumber(forgotPassword);

			UpdatePassword updatePasswordEmail = new UpdatePassword();
			updatePasswordEmail.setEmailId(signUpContact.getEmailId());
			updatePasswordEmail.setOldPassword(signUpContact.getPassword());
			updatePasswordEmail.setPhoneNumber(signUpContact.getPhoneNumber());

			result = sendEmail.sendEmailForgotPassword(signUpContact.getEmailId(), newpassword,
					"Here is your new password : ");
			resultSms = sendSms.sendSMS(signUpContact.getPhoneNumber(), newpassword, "Here is your new password : ");

			if (result == true || resultSms == true) {
				updatePasswordEmail.setNewPassword(newpassword);
				this.updatePassword(updatePasswordEmail);
				resultResponse.setResult("New Password Sent Successfully to your registered EmailId and Mobile Number");
				return resultResponse;
			} else {
				resultResponse.setResult("Please try again there is problem with network");
				return resultResponse;
			}

		} catch (Exception e) {
			resultResponse.setResult("Provide Valid EmailId or Phone Number");
			return resultResponse;
		}

	}

	@Transactional
	public ResultResponse addMultipleContacts(AddMultipleContacts addMultipleContacts) {

		List<AddContact> addContactLists = addMultipleContacts.getAddContactLists();

		List<AddContact> contactLists = new ArrayList<AddContact>();
		
		List<String> unaddedContacts =new ArrayList<String>();

		ResultResponse response = new ResultResponse();

		for (AddContact addContacts : addContactLists) {
			ResultResponse response1 = addContact(addContacts);
			if (response1.getResult().equalsIgnoreCase("Contact Saved Successfully")) {
				contactLists.add(response1.getContact());
			}else {
				unaddedContacts.add(addContacts.getContact_id());
			}
		}
		response.setResult("Contacts Added Successfully");
		response.setContactsList(contactLists);
		response.setUnAddedContacts(unaddedContacts);
		return response;

	}

	@Transactional
	public synchronized ResultResponse deleteMultipleContacts(DeleteMultipleContacts deleteMultipleContacts) {
		ResultResponse response = new ResultResponse();
		String ownerNumber = deleteMultipleContacts.getOwnerNumber();
		List<Integer> contactIds = deleteMultipleContacts.getContactIds();
		
		List<AddContact> list= groupManagementDao.loadAll(contactIds);
		contactManagementDao.methodForDeleteAll(list);
		
		//List<ShareContact> shareContactsList=groupManagementDao.loadAllSharedContacts(ownerNumber,contactIds);
		
		for (Integer id : contactIds) {
			deleteContact(ownerNumber, id);
		}
		response.setResult("Contacts Deleted Successfully");
		return response;
	}

	@Transactional
	public ResultResponse profileUpdatation(SignUpContact signUpContact) {

		ResultResponse resultResponse = new ResultResponse();
		SignUpContact contactSignUp = new SignUpContact();

		SignUpContact contact = contactManagementDao.getSignUpContactByPhoneNumber(signUpContact);

		contactSignUp.setSignUpId(contact.getSignUpId());
		contactSignUp.setImage(signUpContact.getImage());
		contactSignUp.setUserName(signUpContact.getUserName());
		contactSignUp.setPassword(contact.getPassword());
		contactSignUp.setPaid(contact.isPaid());
		contactSignUp.setPhoneNumber(signUpContact.getPhoneNumber());
		contactSignUp.setEmailId(signUpContact.getEmailId());
		contactSignUp.setAddress(signUpContact.getAddress());

		contactManagementDao.methodForUpdate(contactSignUp);

		resultResponse.setResult("Profile Successfully Updated");
		resultResponse.setProfileImage(signUpContact.getImage());
		resultResponse.setProfileName(signUpContact.getUserName());
		return resultResponse;

	}
	
	@Transactional
	public SignUpContactResponse getSubscriptionDaysLeftAndDateEnd(String ownerNumber, String ownerEmail) {

		SignUpContactResponse signUpContactResponse = new SignUpContactResponse();
		
		SignUpContact signUpContact = contactManagementDao.getSubscriptionDaysLeftAndDateEnd(ownerNumber, ownerEmail);
		
		signUpContactResponse.setSubscriptionDaysLeft(signUpContact.getSubscriptionDaysLeft());
		signUpContactResponse.setSubscriptionEndDate(signUpContact.getSubscriptionEndDate());
		
		return signUpContactResponse;
	}
	

	@Transactional
	public ResultResponse transactionDetails(SignUpTransactionDetails signUpTransactionDetails) {

		ResultResponse resultResponse = new ResultResponse();
		signUpTransactionDetails.setTransactionDate(getCurrentSystemDateAndTime());

		SignUpContact signUpContact = contactManagementDao
				.getSignUpContactByEmailIdAndPhoneNumberForTransactionUpdate(signUpTransactionDetails);

		if (signUpTransactionDetails.getTransactionStatus().equalsIgnoreCase("SUCCESS")) {
			signUpContact.setPaid(true);

			contactManagementDao.methodForSave(signUpTransactionDetails);
			signUpContact.setSubscripedDate(getCurrentSystemDateAndTime());
			signUpContact.setSubscriptionEndDate(getOneYearLaterCurrentSystemDateAndTime());
			signUpContact.setSubscriptionDaysLeft((int) daysBetween());
			signUpContact.setAmount(signUpTransactionDetails.getTransactionAmount());
			
			contactManagementDao.methodForUpdate(signUpContact);
			sendEmail.sendEmailForgotPassword(signUpContact.getEmailId(), "",
					" Thank You for Upgrading to Pro Version. Your Transaction is Successful. Enjoy full Version and Free Backup on Server for one year ");
			sendSms.sendSMS(signUpContact.getPhoneNumber(), "",
					" Thank You for Upgrading to Pro Version. Your Transaction is Successful. Enjoy full Version and Free Backup on Server for one year ");

			resultResponse.setResult("SUCCESS AND PAYMENT UPDATED");
			return resultResponse;
		} else {
			contactManagementDao.methodForSave(signUpTransactionDetails);
			resultResponse.setResult("SUCCESS");
			return resultResponse;
		}
	}

	private String getCurrentSystemDateAndTime() {
		DateFormat df = new SimpleDateFormat("dd-MMM-yyyy hh.mm.ss aa");

		Date today = Calendar.getInstance().getTime();
		String reportDate = df.format(today);
		return reportDate;

	}

	private String getOneYearLaterCurrentSystemDateAndTime() {
		DateFormat df = new SimpleDateFormat("dd-MMM-yyyy hh.mm.ss aa");

		Calendar cal = Calendar.getInstance();
		Date today = cal.getTime();
		cal.add(Calendar.YEAR, 1); // to get previous year add -1
		Date nextYear = cal.getTime();

		String reportDate = df.format(nextYear);
		return reportDate;

	}

	private static long daysBetween() {

		Date today1 = Calendar.getInstance().getTime();

		Calendar ca2 = Calendar.getInstance();
		Date today = ca2.getTime();
		ca2.add(Calendar.YEAR, 1); // to get previous year add -1
		Date nextYear = ca2.getTime();

		long difference = (nextYear.getTime() - today1.getTime()) / 86400000;
		return Math.abs(difference);
	}
}
