package com.contact.mgmt.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.contact.mgmt.bos.AddContact;
import com.contact.mgmt.bos.AddFavourite;
import com.contact.mgmt.bos.AddMultipleContacts;
import com.contact.mgmt.bos.ContactXref;
import com.contact.mgmt.bos.DeleteMultipleContacts;
import com.contact.mgmt.bos.GroupRef;
import com.contact.mgmt.bos.ShareContact;
import com.contact.mgmt.bos.SignUpContact;
import com.contact.mgmt.bos.SignUpTransactionDetails;
import com.contact.mgmt.dummy.ContactFilter;
import com.contact.mgmt.dummy.Distance;
import com.contact.mgmt.dummy.ForgotPassword;
import com.contact.mgmt.dummy.GroupCount;
import com.contact.mgmt.dummy.LoginResponse;
import com.contact.mgmt.dummy.LogoutResponse;
import com.contact.mgmt.dummy.MoveContactToGroup;
import com.contact.mgmt.dummy.ResultResponse;
import com.contact.mgmt.dummy.SignUpContactResponse;
import com.contact.mgmt.dummy.UpdatePassword;
import com.contact.mgmt.forms.LoginContact;
import com.contact.mgmt.forms.LogoutContact;
import com.contact.mgmt.service.ContactManagementService;
import com.contact.mgmt.service.GroupManagementService;
import com.contact.mgmt.sharedbos.SharedAddContact;

@RestController
@RequestMapping("/contacts")
public class ContactManagementController {

	@Autowired
	private ContactManagementService contactManagementService;

	@Autowired
	private GroupManagementService groupManagementService;

	@RequestMapping(value = "/signUpContact", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResultResponse signUpContact(@RequestBody SignUpContact signUpContact) {
		ResultResponse resultResponse = new ResultResponse();
		resultResponse = contactManagementService.signUpContact(signUpContact);
		return resultResponse;
	}

	@RequestMapping(value = "/loginContact", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody LoginResponse loginContact(@RequestBody LoginContact loginContact) {
		LoginResponse loginResponse = contactManagementService.loginContact(loginContact);
		return loginResponse;
	}
	
	@RequestMapping(value = "/logoutContact", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody LogoutResponse logoutContact(@RequestBody LogoutContact logoutContact) {
		LogoutResponse logoutResponse = contactManagementService.logoutContact(logoutContact);
		
		return logoutResponse;
	}

	@RequestMapping(value = "/updatePassword", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResultResponse updatePassword(@RequestBody UpdatePassword updatePassword) {
		ResultResponse resultResponse = new ResultResponse();
		resultResponse = contactManagementService.updatePassword(updatePassword);
		return resultResponse;
	}

	@RequestMapping(value = "/addContact", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResultResponse addContact(@RequestBody AddContact contact) throws Exception {
		ResultResponse resultResponse = new ResultResponse();
		resultResponse = contactManagementService.addContact(contact);
		return resultResponse;
	}

	@RequestMapping(value = "/contactlist/{ownerNumber}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<AddContact> getContactList(@PathVariable String ownerNumber) throws Exception {
		List<AddContact> groups = contactManagementService.getContactList(ownerNumber);
		return groups;

	}

	@RequestMapping(value = "/contactbyid/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody AddContact getContactById(@PathVariable int id) throws Exception {
		AddContact contact = contactManagementService.getContactById(id);
		return contact;

	}

	@RequestMapping(value = "/editContact", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResultResponse editContact(@RequestBody AddContact addContact) {
		ResultResponse resultResponse = new ResultResponse();
		resultResponse = contactManagementService.editContact(addContact);
		return resultResponse;
	}

	@RequestMapping(value = "/deleteContact/{ownerNumber}/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResultResponse deleteContact(@PathVariable String ownerNumber, @PathVariable int id) {
		ResultResponse resultResponse = new ResultResponse();
		resultResponse = contactManagementService.deleteContact(ownerNumber, id);
		return resultResponse;
	}

	@RequestMapping(value = "/addFavourite", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResultResponse addFavourite(@RequestBody AddFavourite favourite) {
		ResultResponse result = groupManagementService.addFavourite(favourite);
		return result;
	}

	@RequestMapping(value = "/getFavouriteList/{ownerNumber}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<AddContact> getFavouriteList(@PathVariable String ownerNumber) {
		List<AddContact> contactsListResult = groupManagementService.getFavouriteList(ownerNumber);
		return contactsListResult;

	}

	@RequestMapping(value = "/unFavourite/{ownerNumber}/{sharedFavourite}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResultResponse deleteContactFromFavourite(@PathVariable String ownerNumber,
			@PathVariable int sharedFavourite) {
		ResultResponse result = groupManagementService.deleteContactFromFavourite(ownerNumber, sharedFavourite);
		return result;

	}

	@RequestMapping(value = "/addGroup", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResultResponse addGroup(@RequestBody GroupRef contact) {
		ResultResponse result = groupManagementService.addGroup(contact);
		return result;
	}

	@RequestMapping(value = "/deleteGroup/{groupId}/{ownNumber}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResultResponse deleteGroup(@PathVariable int groupId, @PathVariable String ownNumber) {
		ResultResponse result = groupManagementService.deleteGroup(groupId, ownNumber);
		return result;
	}

	@RequestMapping(value = "/getGroupList/{ownerNumber}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Collection<GroupRef> getGroupList(@PathVariable String ownerNumber) {
		Collection<GroupRef> groupRefsList = groupManagementService.getGroupList(ownerNumber);
		return groupRefsList;
	}

	@RequestMapping(value = "/getGroupById/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody GroupRef getGroupById(@PathVariable int id) {
		GroupRef result = groupManagementService.getGroupById(id);
		return result;
	}

	@RequestMapping(value = "/getGroupByName/{ownerNumber}/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<GroupRef> getGroupByName(@PathVariable String ownerNumber, @PathVariable String name) {
		List<GroupRef> groupRefsList = groupManagementService.getGroupByName(ownerNumber, name);
		return groupRefsList;
	}

	@RequestMapping(value = "/editGroup", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResultResponse editGroup(@RequestBody GroupRef groupref) {
		ResultResponse result = groupManagementService.editGroup(groupref);
		return result;
	}

	@RequestMapping(value = "/contactSearch/{ownerId}/{search}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<AddContact> contactSearch(@PathVariable String ownerId, @PathVariable String search) {
		List<AddContact> addContactsSearchList = groupManagementService.contactSearch(ownerId, search);
		return addContactsSearchList;
	}

	@RequestMapping(value = "/addContactToGroup/{ownerNumber}/{groupName}/{contactId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResultResponse addContactToGroup(@PathVariable String ownerNumber,
			@PathVariable String groupName, @PathVariable int contactId) {
		ResultResponse result = groupManagementService.addContactToGroup(ownerNumber, groupName, contactId);
		return result;
	}

	@RequestMapping(value = "/addContactsToGroup", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResultResponse addContactsToGroup(@RequestBody ContactXref contact) {
		ResultResponse result = groupManagementService.addContactsToGroup(contact);
		return result;
	}

	@RequestMapping(value = "/getGroupMembersList/{ownerNumber}/{groupName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<AddContact> getGroupMembersList(@PathVariable String ownerNumber,
			@PathVariable String groupName) {
		List<AddContact> resultList = groupManagementService.getGroupMembersList(ownerNumber, groupName);
		return resultList;
	}

	@RequestMapping(value = "/deleteMemberInGroup/{contactId}/{groupId}/{ownerNumber}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResultResponse deleteMemberInGroup(@PathVariable int contactId, @PathVariable int groupId,
			@PathVariable String ownerNumber) {
		ResultResponse result = groupManagementService.deleteMemberInGroup(contactId, groupId, ownerNumber);
		return result;
	}

	@RequestMapping(value = "/sharedContact", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResultResponse sharedContact(@RequestBody ShareContact share) {
		ResultResponse result = groupManagementService.sharedContact(share);
		return result;
	}

	@RequestMapping(value = "/getSharedContacts/{borrowerOwnerNumber}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<SharedAddContact> getSharedContacts(@PathVariable String borrowerOwnerNumber) {
		List<SharedAddContact> addContactsListResult = groupManagementService.getSharedContacts(borrowerOwnerNumber);
		return addContactsListResult;
	}

	@RequestMapping(value = "/groupSearch/{groupId}/{ownerNumber}/{search}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<AddContact> groupSearch(@PathVariable int groupId, @PathVariable String ownerNumber,
			@PathVariable String search) {
		List<AddContact> addContactsListResult = groupManagementService.groupSearch(groupId, ownerNumber, search);
		return addContactsListResult;
	}

	@RequestMapping(value = "/searchSharedContact/{ownerId}/{search}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<AddContact> searchSharedContact(@PathVariable String ownerId,
			@PathVariable String search) {
		List<AddContact> addContactsResult = groupManagementService.searchSharedContact(ownerId, search);
		return addContactsResult;
	}

	@RequestMapping(value = "/groupFilterByCount/{ownerNumber}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<GroupCount> groupFilterByCount(@PathVariable String ownerNumber) {
		List<GroupCount> groupCountsResult = groupManagementService.groupFilterByCount(ownerNumber);
		return groupCountsResult;
	}

	@RequestMapping(value = "/getNearByContacts", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ContactFilter getNearByContacts(@RequestBody Distance d) {
		ContactFilter contactFilter = groupManagementService.getNearByContacts(d);
		return contactFilter;
	}

	@RequestMapping(value = "/searchNearByContacts/{search}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ContactFilter searchNearByContacts(@RequestBody Distance d, @PathVariable String search) {
		ContactFilter contactFilter = groupManagementService.searchNearByContacts(d, search);
		return contactFilter;
	}

	@RequestMapping(value = "/forgotPassword", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResultResponse forgotPassword(@RequestBody ForgotPassword forgotPassword) {
		ResultResponse resultResponse = contactManagementService.forgotPassword(forgotPassword);
		return resultResponse;
	}

	@RequestMapping(value = "/groupContactList/{ownerNumber}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<AddContact> groupContactList(@PathVariable String ownerNumber) {
		List<AddContact> addContactResult = groupManagementService.groupContactList(ownerNumber);
		return addContactResult;
	}

	@RequestMapping(value = "/moveContactToGroup", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResultResponse moveContactToGroup(@RequestBody MoveContactToGroup moveContactToGroup) {
		ResultResponse response = groupManagementService.moveContactToGroup(moveContactToGroup);
		return response;
	}

	@RequestMapping(value = "/shareDelete/{ownerNumber}/{contactId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResultResponse shareDelete(@PathVariable String ownerNumber, @PathVariable int contactId) {
		ResultResponse response = groupManagementService.shareDelete(ownerNumber, contactId);
		return response;
	}
	
	
	@RequestMapping(value = "/addMultipleContacts", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResultResponse addMultipleContacts(@RequestBody AddMultipleContacts addMultipleContacts){
		ResultResponse response=contactManagementService.addMultipleContacts(addMultipleContacts);
		return response;
	}
	
	@RequestMapping(value = "/deleteMultipleContacts", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResultResponse deleteMultipleContacts(@RequestBody DeleteMultipleContacts deleteMultipleContacts){
		ResultResponse response=	contactManagementService.deleteMultipleContacts(deleteMultipleContacts);
		return response;
	}

	@RequestMapping(value = "/profileUpdatation", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResultResponse profileUpdatation(@RequestBody SignUpContact signUpContact){
		ResultResponse resultResponse=contactManagementService.profileUpdatation(signUpContact);
		return resultResponse;
	}
	
	@RequestMapping(value = "/transactionDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResultResponse transactionDetails(@RequestBody SignUpTransactionDetails signUpTransactionDetails){
		ResultResponse response=contactManagementService.transactionDetails(signUpTransactionDetails);
		return response;
	}
	
	@RequestMapping(value = "/newSharedContact", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResultResponse newSharedContact(@RequestParam(value="ownerNumber",required=true) String ownerNumber,@RequestParam(value="sharedOwnerNumber",required=true) String sharedOwnerNumber,@RequestParam(value="sharedOwnerEmail",required=true) String sharedOwnerEmail, @RequestBody SharedAddContact sharedAddContact){
		ResultResponse response=new ResultResponse();
		System.out.println(">>>>>>>>"+ownerNumber);
		System.out.println("<<<<<<<<<"+sharedOwnerNumber);
		response=groupManagementService.newSharedContact(ownerNumber, sharedOwnerNumber, sharedOwnerEmail, sharedAddContact);
		return response;
	}
	

	@RequestMapping(value = "/getShareContactById/{ownerNumber}/{contactId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody SharedAddContact getShareContactById(@PathVariable String ownerNumber, @PathVariable int contactId) {
		SharedAddContact response = groupManagementService.getShareContactById(ownerNumber, contactId);
		return response;
	}
	
	@RequestMapping(value = "/getSubscriptionDaysLeftAndDateEnd", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody SignUpContactResponse getSubscriptionDaysLeftAndDateEnd(@RequestBody SignUpContact signUpContact) {
		SignUpContactResponse response = contactManagementService.getSubscriptionDaysLeftAndDateEnd(signUpContact.getPhoneNumber(), signUpContact.getEmailId());
		return response;
	}
	
	
	
	
	
}
