package com.contact.mgmt.service;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.contact.mgmt.bos.AddContact;
import com.contact.mgmt.bos.AddFavourite;
import com.contact.mgmt.bos.Address;
import com.contact.mgmt.bos.ContactGroupTrans;
import com.contact.mgmt.bos.ContactXref;
import com.contact.mgmt.bos.GroupRef;
import com.contact.mgmt.bos.ShareContact;
import com.contact.mgmt.bos.SignUpContact;
import com.contact.mgmt.dao.ContactManagementDao;
import com.contact.mgmt.dao.GroupManagementDao;
import com.contact.mgmt.dummy.ContactFilter;
import com.contact.mgmt.dummy.CountComparator;
import com.contact.mgmt.dummy.CountGroupListComparator;
import com.contact.mgmt.dummy.Distance;
import com.contact.mgmt.dummy.GroupCount;
import com.contact.mgmt.dummy.KmComparator;
import com.contact.mgmt.dummy.MoveContactToGroup;
import com.contact.mgmt.dummy.ResultResponse;
import com.contact.mgmt.mappers.SendEmail;
import com.contact.mgmt.mappers.SendSms;
import com.contact.mgmt.sharedbos.SharedAddContact;

@Service("groupManagementService")
public class GroupManagementService {

	@Autowired
	private GroupManagementDao groupManagementDao;
	
	/*@Autowired
	private ContactManagementDao contactManagementDao;
	*/
	@Autowired
	private SendEmail sendEmail;

	@Autowired
	private SendSms sendSms;

	@Transactional
	public ResultResponse addFavourite(AddFavourite favourite) {

		ResultResponse resultResponse = new ResultResponse();
		AddFavourite addFavourite = null;

		try {
			addFavourite = groupManagementDao.getAddFavbyOwnerSharedandisActiveFalse(favourite);
			addFavourite.setActive(true);
			groupManagementDao.methodForUpdate(addFavourite);
			resultResponse.setResult("Favourite Added Successfully");
			return resultResponse;
		} catch (Exception e) {
			try {
				addFavourite = groupManagementDao.getAddFavbyOwnerSharedandisActiveTrue(favourite);
				resultResponse.setResult("Favourite Already Exists");
				return resultResponse;
			} catch (Exception e1) {
				groupManagementDao.methodForSave(favourite);
				resultResponse.setResult("Favourite Added Successfully");
				return resultResponse;
			}
		}
	}

	@Transactional
	public List<AddContact> getFavouriteList(String ownerNumber) {

		List<AddContact> data = null;
		try {
			data = groupManagementDao.getFavouriteList(ownerNumber);
			return data;
		} catch (Exception e) {
			return null;
		}
	}

	@Transactional
	public ResultResponse deleteContactFromFavourite(String ownerNumber, int contactId) {

		ResultResponse resultResponse = new ResultResponse();
		AddFavourite addFavourite = null;

		try {
			addFavourite = groupManagementDao.getAddFavbyOwnerShared(ownerNumber, contactId);
			addFavourite.setActive(false);
			groupManagementDao.methodForUpdate(addFavourite);
			resultResponse.setResult("Deleted Successfully");
			return resultResponse;
		} catch (Exception e) {
			resultResponse.setResult("No Contact for OwnerNumber with in Favourites");
			return resultResponse;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public Collection<GroupRef> getGroupList(String ownerNumber) {

		List<GroupRef> groupRefsList = null;
		List<ContactGroupTrans> contactGroupTransLists = null;
		Set<String> groupNamesSets = new HashSet<String>();
		List<GroupRef> groupRefssets = new ArrayList<GroupRef>();
		Map<String, List<ContactGroupTrans>> gsl = new HashMap<String, List<ContactGroupTrans>>();

		try {
			groupRefsList = groupManagementDao.getGroupRefListByOwner(ownerNumber);
			for (GroupRef groupRef : groupRefsList) {
				groupNamesSets.add(groupRef.getGroupName());
			}
			try {
				for (String groupNamesSet : groupNamesSets) {
					contactGroupTransLists = groupManagementDao.getContactGroupsTransList(ownerNumber, groupNamesSet);
					gsl.put(groupNamesSet, contactGroupTransLists);
				}

				for (String groupName : gsl.keySet()) {
					for (GroupRef groupRef : groupRefsList) {
						if (groupRef.getGroupName().equals(groupName)) {
							GroupRef grf = new GroupRef();
							grf.setGroupId(groupRef.getGroupId());
							grf.setOwnerNumber(groupRef.getOwnerNumber());
							grf.setGroupName(groupRef.getGroupName());
							grf.setImage(groupRef.getImage());
							grf.setActive(groupRef.isActive());
							List<AddContact> groupContactsList = getGroupMembersList(groupRef.getOwnerNumber(),
									groupRef.getGroupName());
							grf.setGroupContactsList(groupContactsList);
							grf.setCount(gsl.get(groupName).size());
							groupRefssets.add(grf);
						}
					}
				}
				Collections.sort(groupRefssets, new CountGroupListComparator());
				return groupRefssets;
			} catch (Exception e1) {
				return groupRefsList;
			}
		} catch (Exception e) {
			return null;
		}
	}

	@Transactional
	public ResultResponse deleteGroup(int groupId, String ownNumber) {

		ResultResponse resultResponse = new ResultResponse();
		GroupRef groupRef = null;
		List<ContactGroupTrans> contactGroupTransLists = null;

		try {
			groupRef = groupManagementDao.getGroupRefGroupIdAndOwner(groupId, ownNumber);
			try {
				contactGroupTransLists = groupManagementDao.getContactGroupsTransListByIDandOwnerNumber(ownNumber,
						groupId);

				for (ContactGroupTrans contactGroupTrans : contactGroupTransLists) {
					contactGroupTrans.setActive(false);
					groupManagementDao.methodForUpdate(contactGroupTrans);
				}
				groupRef.setActive(false);
				groupManagementDao.methodForUpdate(groupRef);
				resultResponse.setResult("Group Deleted Successfully");
				return resultResponse;

			} catch (Exception e1) {

				groupRef.setActive(false);
				groupManagementDao.methodForUpdate(groupRef);
				resultResponse.setResult("Group Deleted Successfully");
				return resultResponse;
			}
		} catch (Exception e) {
			resultResponse.setResult("No GroupId for Owner");
			return resultResponse;
		}
	}

	@Transactional
	public ResultResponse addGroup(GroupRef contact) {

		ResultResponse resultResponse = new ResultResponse();
		GroupRef groupRef = null;

		try {
			groupRef = groupManagementDao.getGroupRefGroupIdAndOwnerAndisActiveFalse(contact);
			groupRef.setActive(true);

			if (contact.getFile() != null) {

				byte[] imageData = new byte[(int) contact.getFile().length()];

				try {
					FileInputStream fileInputStream = new FileInputStream(contact.getFile());
					fileInputStream.read(imageData);
					fileInputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}

				groupRef.setImage(imageData);
				groupManagementDao.methodForUpdate(groupRef);
				resultResponse.setResult("Group Added Successfully");
				resultResponse.setGroup(groupRef);
				return resultResponse;
			} else {
				groupManagementDao.methodForUpdate(groupRef);
				resultResponse.setResult("Group Added Successfully");
				resultResponse.setGroup(groupRef);
				return resultResponse;
			}
		} catch (Exception e) {
			try {
				groupRef = groupManagementDao.getGroupRefGroupIdAndOwnerAndisActiveTrue(contact);
				resultResponse.setResult("Group Name Already Exists for prticular Owner");
				return resultResponse;
			} catch (Exception e1) {

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

					groupManagementDao.methodForSave(contact);
					resultResponse.setResult("Group Added Successfully");
					resultResponse.setGroup(contact);
					return resultResponse;
				} else {
					groupManagementDao.methodForSave(contact);
					resultResponse.setResult("Group Added Successfully");
					resultResponse.setGroup(contact);
					return resultResponse;
				}
			}
		}
	}

	@Transactional
	public GroupRef getGroupById(int id) {

		GroupRef groupRef = null;

		try {
			groupRef = (GroupRef) groupManagementDao.getFromDb(GroupRef.class, id);
			return groupRef;
		} catch (Exception e) {
			return null;
		}
	}

	@Transactional
	public List<GroupRef> getGroupByName(String ownerNumber, String name) {

		try {
			List<GroupRef> data = groupManagementDao.getGroupRefListByNameAndOwner(ownerNumber, name);
			return data;
		} catch (Exception e) {
			return null;
		}
	}

	@Transactional
	public ResultResponse editGroup(GroupRef groupref) {

		List<ContactGroupTrans> groupContacts = null;
		List<String> groupRefsSet = new ArrayList<String>();
		List<Integer> groupContactIds = new ArrayList<Integer>();
		ResultResponse resultResponse = new ResultResponse();
		List<GroupRef> groupRefsList = null;
		groupRefsList = groupManagementDao.getGroupRefListByOwnerAndGroupId(groupref);

		GroupRef groupRef2 = null;
		groupRef2 = groupManagementDao.getGroupRefListByOwnerAndGroupIdandIsActive(groupref);

		List<String> groupNames = new ArrayList<String>();

		for (GroupRef ref : groupRefsList) {
			groupNames.add(ref.getGroupName());
		}
		groupNames.remove(groupRef2.getGroupName());

		if (groupRefsList.size() == 0) {
			resultResponse.setResult("No Groups for Owner to Update (or) No Mapping between Owner and GroupId");
			return resultResponse;
		} else {

			for (String grpNames : groupNames) {
				if (grpNames.equals(groupref.getGroupName())) {
					resultResponse.setResult("Group Name Already Exists with Same Owner");
					return resultResponse;
				}
			}

		}

		try {
			groupContacts = groupManagementDao.getContactGroupsTransListByIDandOwnerNumber(groupref.getOwnerNumber(),
					groupref.getGroupId());
			for (ContactGroupTrans contactGroupTrans : groupContacts) {
				groupContactIds.add(contactGroupTrans.getContactId());
				contactGroupTrans.setGroupName(groupref.getGroupName());
				groupManagementDao.methodForUpdate(contactGroupTrans);
			}
			for (Integer ids : groupContactIds) {

				AddContact contact = (AddContact) groupManagementDao.getFromDb(AddContact.class, ids);
				contact.setGroupName(groupref.getGroupName());
				groupManagementDao.methodForUpdate(contact);
			}
			groupManagementDao.methodForUpdate(groupref);
			resultResponse.setResult("Group Updated Successfully");
			resultResponse.setGroup(groupref);
			return resultResponse;
		} catch (Exception e2) {
			groupContacts = groupManagementDao.getContactGroupsTransListByIDandOwnerNumber(groupref.getOwnerNumber(),
					groupref.getGroupId());
			for (ContactGroupTrans contactGroupTrans : groupContacts) {
				groupContactIds.add(contactGroupTrans.getContactId());
			}
			for (Integer ids : groupContactIds) {

				AddContact contact = (AddContact) groupManagementDao.getFromDb(AddContact.class, ids);
				contact.setGroupName(groupref.getGroupName());
				groupManagementDao.methodForUpdate(contact);
			}
			groupManagementDao.methodForUpdate(groupref);
			resultResponse.setResult("Group Updated Successfully");
			resultResponse.setGroup(groupref);
			return resultResponse;
		}
	}

	@Transactional
	public ResultResponse addContactToGroup(String ownerNumber, String groupName, int contactId) {

		ResultResponse resultResponse = new ResultResponse();
		ContactGroupTrans contactGroupTrans = null;
		GroupRef groupRef = null;
		AddContact addContact = null;

		try {
			groupRef = groupManagementDao.getGroupRefGroupNameAndOwner(ownerNumber, groupName);
			if (groupRef != null) {
				try {
					contactGroupTrans = groupManagementDao
							.getContactGroupsTransByContactIdAndOwnerAndisActiveTrue(ownerNumber, contactId);
					if (contactGroupTrans != null) {
						resultResponse.setResult("No Duplicates Allowed in Groups");
						return resultResponse;
					}
				} catch (Exception e11) {
					try {
						contactGroupTrans = groupManagementDao
								.getContactGroupsTransByContactIdAndOwnerAndGroupNameAndGroupIDAndisActiveFalse(
										ownerNumber, groupName, contactId, groupRef);
						contactGroupTrans.setActive(true);
						groupManagementDao.methodForUpdate(contactGroupTrans);
						addContact = (AddContact) groupManagementDao.getFromDb(AddContact.class, contactId);
						addContact.setGroupName(groupName);
						groupManagementDao.methodForUpdate(addContact);
						resultResponse.setResult("Contact Added to Group Successfully");
						return resultResponse;
					} catch (Exception e2) {
						try {
							contactGroupTrans = groupManagementDao
									.getContactGroupsTransByContactIdAndOwnerAndGroupNameAndGroupIDAndisActiveTrue(
											ownerNumber, groupName, contactId, groupRef);
							resultResponse.setResult("Contact Already Exists for prticular Group");
							return resultResponse;
						} catch (Exception e1) {
							ContactGroupTrans trans = new ContactGroupTrans();
							trans.setContactId(contactId);
							trans.setGroupName(groupName);
							trans.setOwnerNumber(ownerNumber);
							trans.setGroupId(groupRef.getGroupId());
							groupManagementDao.methodForSave(trans);
							addContact = (AddContact) groupManagementDao.getFromDb(AddContact.class, contactId);
							addContact.setGroupName(groupName);
							groupManagementDao.methodForUpdate(addContact);
							resultResponse.setResult("Contact Added to Group Successfully");
							return resultResponse;
						}
					}
				}
			}
		} catch (Exception e) {
			resultResponse.setResult("No GroupName exists with particular owner. Please add group first");
			return resultResponse;
		}
		return resultResponse;
	}

	@SuppressWarnings("rawtypes")
	@Transactional
	public ResultResponse addContactsToGroup(ContactXref contact) {

		ResultResponse resultResponse = new ResultResponse();
		ResultResponse resultResponse1 = null;
		List<Integer> addContacts = contact.getContactIdsList();
		List contactIdLists = null;
		int id = 0;
		int compareId = 0;
		int temp = 0;

		for (Integer addContact : addContacts) {
			id = addContact;
			contactIdLists = groupManagementDao.getAddContacts(id, contact);
			compareId = (int) contactIdLists.get(0);
			try {
				if (compareId == id) {
					resultResponse1 = addContactToGroup(contact.getOwnerNumber(), contact.getGroupName(), compareId);
					if (resultResponse1.getResult().equals("Contact Added to Group Successfully")) {
						AddContact addcontactUpdate = null;
						addcontactUpdate = (AddContact) groupManagementDao.getFromDb(AddContact.class, compareId);
						addcontactUpdate.setGroupName(contact.getGroupName());
						groupManagementDao.methodForUpdate(addcontactUpdate);
						temp = 1;
					} else {
						temp = 2;
						String result = resultResponse1.getResult();
						resultResponse1.setResult(result + "with Contact Id :" + id);
					}
				} else {
					resultResponse.setResult("Contact cant be added to group as contact id not exists");
					return resultResponse;
				}
			} catch (Exception e) {
				resultResponse.setResult("Contact cant be added to group as contact id not exists");
				return resultResponse;
			}
		}
		if (temp == 1) {
			resultResponse.setResult("Contacts added to group Successfully");
			return resultResponse;
		} else if (temp == 2) {
			return resultResponse1;
		} else {
			resultResponse.setResult("No Operation Done");
			return resultResponse;
		}
	}

	@Transactional
	public List<AddContact> getGroupMembersList(String ownerNumber, String groupName) {

		List<ContactGroupTrans> contactGroupTransLists = null;
		List<AddContact> contactList = new ArrayList<AddContact>();
		AddContact addContact = null;

		try {
			contactGroupTransLists = groupManagementDao.getContactGroupsTransList(ownerNumber, groupName);
			for (ContactGroupTrans contactGroupTransList : contactGroupTransLists) {
				addContact = (AddContact) groupManagementDao.getFromDb(AddContact.class,
						contactGroupTransList.getContactId());
				contactList.add(addContact);
			}
			return contactList;
		} catch (Exception e) {
			return null;
		}
	}

	@Transactional
	public ResultResponse deleteMemberInGroup(int contactId, int groupId, String ownerNumber) {

		ResultResponse resultResponse = new ResultResponse();
		ContactGroupTrans contactGroupTrans = null;

		try {
			contactGroupTrans = groupManagementDao
					.getContactGroupsTransByContactIdAndOwnerAndGroupIDAndisActiveTrue(contactId, groupId, ownerNumber);
			contactGroupTrans.setActive(false);
			groupManagementDao.methodForUpdate(contactGroupTrans);
			resultResponse.setResult("Contact Deleted Successfully from group");
			return resultResponse;
		} catch (Exception e) {
			resultResponse.setResult("No contact is in the group (or) No group for Owner");
			return resultResponse;
		}
	}

	@Transactional
	public ResultResponse sharedContact(ShareContact share) {

		ResultResponse resultResponse = new ResultResponse();
		try {
			SignUpContact signUpContact = groupManagementDao.getSignUpContact(share);
			if (share.getSharedOwnerId().equalsIgnoreCase(signUpContact.getPhoneNumber())) {
				resultResponse.setResult("Contact NOT shared");
				return resultResponse;
			} else {
				share.setBorroweId(signUpContact.getPhoneNumber());
				share.setBorrowerEmail(signUpContact.getEmailId());
				groupManagementDao.methodForSave(share);
				resultResponse.setResult("Shared Successfully");
				return resultResponse;
			}
		} catch (Exception e) {
			resultResponse.setResult("Contact NOT shared");
			return resultResponse;
		}
	}

	@Transactional
	public List<SharedAddContact> getSharedContacts(String borrowerOwnerNumber) {

		List<SharedAddContact> addContactsList = new ArrayList<SharedAddContact>();
		List<ShareContact> shareContactsLists = null;
		SharedAddContact addContact = null;
		Set<Integer> contactIds = new HashSet<Integer>();

		try {
			shareContactsLists = groupManagementDao.getListShareContactByBrowerId(borrowerOwnerNumber);
			for (ShareContact shareContact : shareContactsLists) {
				contactIds.add(shareContact.getShareContactId());
			}
			for (Integer contactId : contactIds) {
				addContact = (SharedAddContact) groupManagementDao.getFromDb(SharedAddContact.class, contactId);
				addContactsList.add(addContact);
			}
			return addContactsList;
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<AddContact> contactSearch(String ownerId, String search) {

		TreeSet<AddContact> set = null;
		List<AddContact> acontact = groupManagementDao.contactSearch(ownerId, search);
		set = new TreeSet<AddContact>(new KmComparator());
		set.addAll(acontact);
		acontact.clear();
		acontact.addAll(set);
		return acontact;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<AddContact> groupSearch(int groupId, String ownerNumber, String search) {

		TreeSet<AddContact> set = null;

		try {
			List<AddContact> acontact = groupManagementDao.groupSearch(groupId, ownerNumber, search);
			set = new TreeSet<AddContact>(new KmComparator());
			set.addAll(acontact);
			acontact.clear();
			acontact.addAll(set);
			return acontact;
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<AddContact> searchSharedContact(String ownerId, String search) {

		TreeSet<AddContact> set = null;

		try {
			List<AddContact> acontact = groupManagementDao.searchSharedContact(ownerId, search);
			set = new TreeSet<AddContact>(new KmComparator());
			set.addAll(acontact);
			acontact.clear();
			acontact.addAll(set);
			return acontact;
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional
	public List<GroupCount> groupFilterByCount(String ownerNumber) {

		GroupCount gc = null;
		ArrayList<GroupCount> al = new ArrayList<GroupCount>();

		try {
			List groupIdList = groupManagementDao.getDistinctIdContactTrans(ownerNumber);
			for (int i = 0; i < groupIdList.size(); i++) {
				Iterator data = groupManagementDao.getIterator(groupIdList.get(i), ownerNumber);
				gc = new GroupCount();
				while (data.hasNext()) {
					Object[] tuple = (Object[]) data.next();
					Long count = (Long) tuple[0];
					String groupName = (String) tuple[1];
					List<AddContact> contact = getGroupMembersList(ownerNumber, groupName);
					gc.setCount(count);
					gc.setGroupName(groupName);
					gc.setContact(contact);
				}
				al.add(gc);
			}
			Collections.sort(al, new CountComparator());
			return al;
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public ContactFilter getNearByContacts(Distance d) {

		ContactFilter contactFilter = new ContactFilter();
		List<AddContact> addContactLists = groupManagementDao.getAddContactListByOwner(d.getOwnerNumber());
		// Collection<Address> addressList=null;

		/*
		 * List<AddContact> data = null; List<AddContact> finalData = new
		 * ArrayList<AddContact>(); ContactFilter cf = new ContactFilter();
		 * List<Address> addrList = getFriendsPoints(d.getOwnerNumber());
		 * List<AddContact> set = null;
		 */

		try {
			List<AddContact> result = new ArrayList<AddContact>();
			for (AddContact addContact : addContactLists) {

				Collection<Address> addressList = addContact.getAddress();

				for (Address address : addressList) {
					Address compareLat = address;
					double theta = compareLat.getLongitude() - d.getLon2();
					double dist = Math.sin(deg2rad(compareLat.getLatitude())) * Math.sin(deg2rad(d.getLat2()))
							+ Math.cos(deg2rad(compareLat.getLatitude())) * Math.cos(deg2rad(d.getLat2()))
									* Math.cos(deg2rad(theta));
					dist = Math.acos(dist);
					dist = rad2deg(dist);
					dist = dist * 60 * 1.1515;
					dist = dist * 1.609344;
					if (dist <= d.getFilter()) {
						AddContact resultContact = (AddContact) addContact.clone();
						double roundOff = Math.round(dist*100)/100;
						resultContact.setKm(roundOff);
						result.add(resultContact);
					}

				}
			}
			contactFilter.setContact(result);
			return contactFilter;
		} catch (Exception e) {
			contactFilter.setContact(new ArrayList<AddContact>());
			return contactFilter;
		}
	}

	private List<Address> getFriendsPoints(String ownerNumber) {

		List<Address> addrList = new ArrayList<Address>();
		List<AddContact> data = groupManagementDao.getAddContactListByOwner(ownerNumber);

		for (int i = 0; i < data.size(); i++) {
			AddContact ac = data.get(i);
			Collection<Address> addr = ac.getAddress();
			for (Iterator iterator = addr.iterator(); iterator.hasNext();) {
				Address ref = (Address) iterator.next();
				addrList.add(ref);
			}
		}
		return addrList;
	}

	private double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	private double rad2deg(double rad) {
		return (rad * 180.0 / Math.PI);
	}

	@Transactional
	public ContactFilter searchNearByContacts(Distance d, String search) {

		List<AddContact> data = null;
		Set<AddContact> finalData = new HashSet<AddContact>();
		ContactFilter cf = new ContactFilter();

		try {
			List<Address> addrList = searchFriendNearPoints(d.getOwnerNumber(), search);
			Set<AddContact> set = null;
			for (int i = 0; i < addrList.size(); i++) {
				Address compareLat = addrList.get(i);
				double theta = compareLat.getLongitude() - d.getLon2();
				double dist = Math.sin(deg2rad(compareLat.getLatitude())) * Math.sin(deg2rad(d.getLat2()))
						+ Math.cos(deg2rad(compareLat.getLatitude())) * Math.cos(deg2rad(d.getLat2()))
								* Math.cos(deg2rad(theta));
				dist = Math.acos(dist);
				dist = rad2deg(dist);
				dist = dist * 60 * 1.1515;
				dist = dist * 1.609344;
				if (dist <= d.getFilter()) {
					List<Address> nearbyList = new ArrayList<Address>();
					nearbyList.add(compareLat);
					for (int j = 0; j < nearbyList.size(); j++) {
						Address finalAddress = nearbyList.get(j);
						data = groupManagementDao.getAddContactList(d, finalAddress);
						for (int k = 0; k < data.size(); k++) {
							data.get(k).setKm(dist);
							finalData.add(data.get(k));
							set = new TreeSet<AddContact>(new KmComparator());
							set.addAll(finalData);
						}
						cf.setContact1(set);
					}
				}
			}
			return cf;
		} catch (Exception e) {
			return null;
		}
	}

	private List<Address> searchFriendNearPoints(String ownerNumber, String search) {

		List<Address> addrList = new ArrayList<Address>();

		try {
			List<AddContact> data = groupManagementDao.searchFriendNearPoints(ownerNumber, search);
			for (int i = 0; i < data.size(); i++) {
				AddContact ac = data.get(i);
				Collection<Address> addr = ac.getAddress();
				for (Iterator iterator = addr.iterator(); iterator.hasNext();) {
					Address ref = (Address) iterator.next();
					addrList.add(ref);
				}
			}
			return addrList;
		} catch (Exception e) {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<AddContact> groupContactList(String ownerNumber) {

		List<AddContact> addContactsLists = null;
		List<ContactGroupTrans> contactGroupTransLists = null;
		Set<Integer> contactIds = new HashSet<Integer>();
		Set<Integer> addContactIds = new HashSet<Integer>();
		List<AddContact> addContactGroupList = new ArrayList<AddContact>();
		int temp = 0;

		try {
			contactGroupTransLists = groupManagementDao.getContactGroupTransByOwner(ownerNumber);
			for (ContactGroupTrans contactGroupTransList : contactGroupTransLists) {
				contactIds.add(contactGroupTransList.getContactId());
			}
			addContactsLists = groupManagementDao.getAddContactListByOwner(ownerNumber);
			for (AddContact addContactList : addContactsLists) {
				addContactIds.add(addContactList.getId());
			}

			addContactIds.removeAll(contactIds);
			for (Integer addContactId : addContactIds) {
				addContactGroupList.add((AddContact) groupManagementDao.getFromDb(AddContact.class, addContactId));
				temp = 1;
			}
			if (temp == 1) {
				return addContactGroupList;
			} else {
				return addContactGroupList;
			}
		} catch (Exception e) {
			return addContactsLists;
		}
	}

	@Transactional
	public ResultResponse moveContactToGroup(MoveContactToGroup moveContactToGroup) {

		ResultResponse resultResponse = new ResultResponse();
		AddContact addContact = null;
		ContactGroupTrans contactGroupTrans = null;

		try {
			contactGroupTrans = groupManagementDao
					.getContactGroupByGroupNameAndContactIdAndOwnerisActiveTrue(moveContactToGroup);
			contactGroupTrans.setActive(false);
			groupManagementDao.methodForUpdate(contactGroupTrans);
			resultResponse = addContactToGroup(moveContactToGroup.getOwnerNumber(), moveContactToGroup.getToGroup(),
					moveContactToGroup.getContactId());
			if (resultResponse.getResult().equals("Contacts added to group Successfully")) {
				addContact = (AddContact) groupManagementDao.getFromDb(AddContact.class,
						moveContactToGroup.getContactId());
				addContact.setGroupName(moveContactToGroup.getToGroup());
				groupManagementDao.methodForUpdate(addContact);
				resultResponse.setResult("Contact Moved to Group Successfully");
				return resultResponse;
			}
		} catch (Exception e) {
			resultResponse.setResult("No Contact is Exists to Move from Group");
			return resultResponse;
		}
		addContact = (AddContact) groupManagementDao.getFromDb(AddContact.class, moveContactToGroup.getContactId());
		addContact.setGroupName(moveContactToGroup.getToGroup());
		groupManagementDao.methodForUpdate(addContact);
		resultResponse.setResult("Contact Moved to Group Successfully");
		return resultResponse;
	}

	@Transactional
	public ResultResponse shareDelete(String ownerNumber, int contactId) {

		ResultResponse resultResponse = new ResultResponse();
		ShareContact shareContact = null;

		try {
			shareContact = groupManagementDao.getShareContactByOwnerAdBrowerAndisActiveTrue(ownerNumber, contactId);
			shareContact.setActive(false);
			groupManagementDao.methodForUpdate(shareContact);
			resultResponse.setResult("Contact Deleted from shared list");
			return resultResponse;
		} catch (Exception e) {
			resultResponse.setResult("no Contact Exists in Shared list to Delete");
			return resultResponse;
		}
	}
	
	
	@Transactional
	public SharedAddContact getShareContactById(String ownerNumber, int contactId) {

		
		SharedAddContact sharedAddContact=new SharedAddContact();

		try {
			sharedAddContact = groupManagementDao.getShareContactById(ownerNumber, contactId);
			
			return sharedAddContact;
		} catch (Exception e) {
		
			return sharedAddContact;
		}
	}
	
	
	
	
	@Transactional
	
	public synchronized ResultResponse newSharedContact(String ownerNumber,String sharedOwnerNumber,String sharedOwnerEmail,SharedAddContact sharedAddContact) {
		
		ShareContact shareContact=new ShareContact();
		shareContact.setBorroweId(sharedOwnerNumber);
		shareContact.setBorrowerEmail(sharedOwnerEmail);
		shareContact.setActive(true);
		shareContact.setSharedOwnerId(ownerNumber);
		
		List<ShareContact> shareContactExisting=null;

		ResultResponse resultResponse = new ResultResponse();
		try {
			
			SignUpContact signUpContact = groupManagementDao.getSignUpContactByEmailIdAndPhoneNumber(sharedOwnerEmail,sharedOwnerNumber);
			//SignUpContact signUpContact = groupManagementDao.getSignUpContact(shareContact);
			if(signUpContact.getPhoneNumber() == null || signUpContact.getEmailId()== null) {
				sendEmail.sendEmailForgotPassword(sharedOwnerEmail, ""," Please install near Geo contacts app to manage all your contacts in one place with geo addresses and variety type of contacts sorting, groups creations and print contacts with number of options, backup contacts, restore contacts, Server backup etc \r\n" + 
						"Down link\r\n" + 
						"https://goo.gl/qltHJ7\r\n" + 
						" ");
				sendSms.sendSMS(sharedOwnerNumber, ""," Please install near Geo contacts app to manage all your contacts in one place with geo addresses and variety type of contacts sorting, groups creations and print contacts with number of options, backup contacts, restore contacts, Server backup etc \r\n" + 
						"Down link\r\n" + 
						"https://goo.gl/qltHJ7\r\n" + 
						"");
			}
			
			shareContactExisting=(List<ShareContact>) groupManagementDao.getListShareContactBySharedCnct(sharedAddContact.getId(),sharedOwnerNumber,sharedOwnerEmail);
			
			/*if (ownerNumber.equalsIgnoreCase(signUpContact.getPhoneNumber())) {
				resultResponse.setResult("Contact NOT shared");
				return resultResponse;
			} else */
			if(shareContactExisting.size()>0){
					resultResponse.setResult("Contact Already Shared");
					return resultResponse;
				
			}else {

				shareContact.setSharedCntct(sharedAddContact.getId());
				groupManagementDao.methodForSave(shareContact);
				
				sharedAddContact.setOwnerNumber(sharedOwnerNumber);
				groupManagementDao.methodForSave(sharedAddContact);
				
				resultResponse.setResult("Shared Successfully");
				
				return resultResponse;
			}
		} catch (Exception e) {
			resultResponse.setResult("Contact NOT shared");
			return resultResponse;
		}
	}
	

}
