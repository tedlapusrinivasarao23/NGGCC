package com.contact.mgmt.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.contact.mgmt.bos.AddContact;
import com.contact.mgmt.bos.AddFavourite;
import com.contact.mgmt.bos.Address;
import com.contact.mgmt.bos.ContactGroupTrans;
import com.contact.mgmt.bos.ContactXref;
import com.contact.mgmt.bos.GroupRef;
import com.contact.mgmt.bos.ShareContact;
import com.contact.mgmt.bos.SignUpContact;
import com.contact.mgmt.dummy.ContactManagementQueries;
import com.contact.mgmt.dummy.Distance;
import com.contact.mgmt.dummy.MoveContactToGroup;
import com.contact.mgmt.sharedbos.SharedAddContact;

@Repository("groupManagementDao")
public class GroupManagementDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;
	@Autowired
	private SessionFactory sessionFactory;

	Session session = null;
	Transaction tx = null;

	public AddFavourite getAddFavbyOwnerSharedandisActiveFalse(AddFavourite favourite) {

		AddFavourite addFavourite = (AddFavourite) hibernateTemplate
				.find(ContactManagementQueries.GET_ADDFAVBY_OWNER_SHARED_AND_ISACTIVEFALSE,
						new Object[] { favourite.getOwnerNumber(), favourite.getSharedFavourite() })
				.get(0);
		hibernateTemplate.flush();
		hibernateTemplate.clear();

		return addFavourite;
	}

	public AddFavourite getAddFavbyOwnerSharedandisActiveTrue(AddFavourite favourite) {

		AddFavourite addFavourite = (AddFavourite) hibernateTemplate
				.find(ContactManagementQueries.GET_ADD_FAVBY_OWNER_SHARED_AND_ISACTIVETRUE,
						new Object[] { favourite.getOwnerNumber(), favourite.getSharedFavourite() })
				.get(0);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return addFavourite;
	}
	
	/*
	@SuppressWarnings("unchecked")
	public void deleteAllContacts(String ownerNumber,List<Integer> listOfIds) {
		
		hibernateTemplate.

		session = sessionFactory.openSession();
		List<AddContact> data = null;
		String sql = "DELETE FROM AddContact WHERE ownerNumber = :ownerNumber and  id IN (:ids)";
		SQLQuery query = session.createSQLQuery(sql);
		 query.setString("ownerNumber", ownerNumber);
		 query.setParameterList("ids", listOfIds);
		 query.executeUpdate();
		 session.close();
	}*/
	
	
	@SuppressWarnings("unchecked")
	public List<AddContact> loadAll(List<Integer> id) {
		session = sessionFactory.openSession();
		List<AddContact> data = new ArrayList<AddContact>();
		List<AddContact> fetchedSongs = session.createCriteria(AddContact.class).add(Restrictions.in("id",id)).list();
		data.addAll(fetchedSongs);
		session.close();
		 return data;
	}
	
	public void methodForDeleteAll(List<ShareContact> list) {
		session = sessionFactory.openSession();
		Query q = session.createQuery("update ShareContact set isActive=false where sharedId in (:sharedId) and sharedCntct = :sharedCntct");
		q.setParameterList("sharedId", list);
		q.executeUpdate();
		session.close();
		}
	
	@SuppressWarnings("unchecked")
	public List<ShareContact> loadAllSharedContacts(String ownerNumber,List<Integer> id) {
		session = sessionFactory.openSession();
		List<ShareContact> data = new ArrayList<ShareContact>();
		List<ShareContact> fetchedSongs = session.createCriteria(ShareContact.class).add(Restrictions.eq("sharedCntct", ownerNumber)).add(Restrictions.eq("isActive",1 )).add(Restrictions.in("sharedId",id)).list();
		data.addAll(fetchedSongs);
		session.close();
		 return data;
	}

	@SuppressWarnings("unchecked")
	public List<AddContact> getFavouriteList(String ownerNumber) {

		session = sessionFactory.openSession();
		List<AddContact> data = null;
		String sql = "SELECT * FROM AddContact ref inner join favourite fav on ref.id=fav.sharedfavourite where ref.ownerNumber ="
				+ ownerNumber + "and isActive=1";
		SQLQuery query = session.createSQLQuery(sql);
		query.addEntity(AddContact.class);
		data = query.list();
		return data;
	}

	public AddFavourite getAddFavbyOwnerShared(String ownerNumber, int contactId) {

		AddFavourite addFavourite = (AddFavourite) hibernateTemplate
				.find(ContactManagementQueries.GET_ADDFAVBY_OWNER_SHARED, new Object[] { ownerNumber, contactId })
				.get(0);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return addFavourite;
	}

	@SuppressWarnings("unchecked")
	public List<GroupRef> getGroupRefListByOwner(String ownerNumber) {

		List<GroupRef> groupRefsList = (List<GroupRef>) hibernateTemplate
				.find(ContactManagementQueries.GET_GROUPREF_LISTBY_OWNER, new Object[] { ownerNumber });
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return groupRefsList;

	}

	@SuppressWarnings("unchecked")
	public List<ContactGroupTrans> getContactGroupsTransList(String ownerNumber, String groupNamesSet) {

		List<ContactGroupTrans> contactGroupTransLists = (List<ContactGroupTrans>) hibernateTemplate.find(
				ContactManagementQueries.GET_CONTACT_GROUPS_TRANSLIST, new Object[] { ownerNumber, groupNamesSet });
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return contactGroupTransLists;

	}

	public GroupRef getGroupRefGroupIdAndOwner(int groupId, String ownNumber) {

		GroupRef groupRef = (GroupRef) hibernateTemplate
				.find(ContactManagementQueries.GET_GROUPREF_GROUPID_AND_OWNER, new Object[] { groupId, ownNumber })
				.get(0);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return groupRef;
	}

	public GroupRef getGroupRefGroupIdAndOwnerAndisActiveFalse(GroupRef contact) {

		GroupRef groupRef = (GroupRef) hibernateTemplate
				.find(ContactManagementQueries.GET_GROUPREF_GROUPID_AND_OWNER_AND_ISACTIVEFALSE,
						new Object[] { contact.getOwnerNumber(), contact.getGroupName() })
				.get(0);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return groupRef;
	}

	public GroupRef getGroupRefGroupIdAndOwnerAndisActiveTrue(GroupRef contact) {

		GroupRef groupRef = (GroupRef) hibernateTemplate
				.find(ContactManagementQueries.GET_GROUPREF_GROUPID_AND_OWNERAND_ISACTIVETRUE,
						new Object[] { contact.getOwnerNumber(), contact.getGroupName() })
				.get(0);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return groupRef;
	}

	@SuppressWarnings("unchecked")
	public List<GroupRef> getGroupRefListByNameAndOwner(String ownerNumber, String name) {

		session = sessionFactory.openSession();
		String sql = "SELECT * FROM group_ref where groupname Like " + "'" + name + "%' and ownerNumber=" + ownerNumber
				+ " and isActive=1";
		SQLQuery query = session.createSQLQuery(sql);
		query.addEntity(GroupRef.class);

		List<GroupRef> data = query.list();
		return data;
	}

	@SuppressWarnings("unchecked")
	public List<GroupRef> getGroupRefListByOwnerAndGroupId(GroupRef groupref) {

		List<GroupRef> groupRefsList = null;
		groupRefsList = (List<GroupRef>) hibernateTemplate.find(
				ContactManagementQueries.GET_GROUPREFLIST_BY_OWNER_AND_GROUPID,
				new Object[] { groupref.getOwnerNumber()});
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return groupRefsList;

	}
	
	@SuppressWarnings("unchecked")
	public GroupRef getGroupRefListByOwnerAndGroupIdandIsActive(GroupRef groupref) {

		GroupRef groupRefsList = null;
		groupRefsList =  (GroupRef) hibernateTemplate.find(
				ContactManagementQueries.GET_GROUPREFLIST_BY_OWNER_AND_GROUPID_ISACTIVE,
				new Object[] { groupref.getOwnerNumber(),groupref.getGroupId()}).get(0);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return groupRefsList;

	}

	public GroupRef getGroupRefGroupNameAndOwner(String ownerNumber, String groupName) {

		GroupRef groupRef = (GroupRef) hibernateTemplate.find(ContactManagementQueries.GET_GROUPREF_GROUPNAME_AND_OWNER,
				new Object[] { ownerNumber, groupName }).get(0);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return groupRef;
	}

	public ContactGroupTrans getContactGroupsTransByContactIdAndOwnerAndisActiveTrue(String ownerNumber,
			int contactId) {

		ContactGroupTrans contactGroupTrans = (ContactGroupTrans) hibernateTemplate
				.find(ContactManagementQueries.GET_CONTACTGROUPSTRANS_BY_CONTACTID_AND_OWNER_AND_ISACTIVETRUE,
						new Object[] { contactId, ownerNumber })
				.get(0);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return contactGroupTrans;

	}

	public ContactGroupTrans getContactGroupsTransByContactIdAndOwnerAndGroupNameAndGroupIDAndisActiveFalse(
			String ownerNumber, String groupName, int contactId, GroupRef groupRef) {

		ContactGroupTrans contactGroupTrans = (ContactGroupTrans) hibernateTemplate
				.find(ContactManagementQueries.GET_CONTACTGROUPSTRANS_BYCONTACTID_AND_OWNER_AND_GROUPNAME_AND_GROUPID_AND_ISACTIVEFALSE,
						new Object[] { ownerNumber, groupName, contactId, groupRef.getGroupId() })
				.get(0);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return contactGroupTrans;

	}

	public ContactGroupTrans getContactGroupsTransByContactIdAndOwnerAndGroupNameAndGroupIDAndisActiveTrue(
			String ownerNumber, String groupName, int contactId, GroupRef groupRef) {

		ContactGroupTrans contactGroupTrans = (ContactGroupTrans) hibernateTemplate
				.find(ContactManagementQueries.GET_CONTACTGROUPSTRANS_BYCONTACTID_AND_OWNER_AND_GROUPNAME_AND_GROUPID_AND_ISACTIVETRUE,
						new Object[] { ownerNumber, groupName, contactId, groupRef.getGroupId() })
				.get(0);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return contactGroupTrans;

	}

	@SuppressWarnings("rawtypes")
	public List getAddContacts(int id, ContactXref contact) {

		List contactIdLists = hibernateTemplate.find(
				"select id from AddContact ref " + "where ref.id=? and ownerNumber=?",
				new Object[] { id, contact.getOwnerNumber() });
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return contactIdLists;

	}

	public ContactGroupTrans getContactGroupsTransByContactIdAndOwnerAndGroupIDAndisActiveTrue(int contactId,
			int groupId, String ownerNumber) {

		ContactGroupTrans contactGroupTrans = (ContactGroupTrans) hibernateTemplate
				.find(ContactManagementQueries.GET_CONTACTGROUPSTRANS_BY_CONTACTID_AND_OWNER_AND_GROUPID_AND_ISACTIVETRUE,
						new Object[] { contactId, groupId, ownerNumber })
				.get(0);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return contactGroupTrans;

	}

	@SuppressWarnings("unchecked")
	public List<ShareContact> getListShareContactByBrowerId(String borrowerOwnerNumber) {

		List<ShareContact> shareContactsLists = (List<ShareContact>) hibernateTemplate
				.find(ContactManagementQueries.GET_LISTSHARECONTACT_BY_BROWERID, new Object[] { borrowerOwnerNumber });
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return shareContactsLists;
	}

	@SuppressWarnings("unchecked")
	public List<ShareContact> getListShareContactBySharedCnct(int sharedCnct,String sharedOwnerNumber,String sharedOwnerEmail) {

		List<ShareContact> shareContactsLists =  (List<ShareContact>) hibernateTemplate
				.find(ContactManagementQueries.GET_LISTSHARECONTACT_BY_SHAREDCNCT, new Object[] { sharedCnct ,sharedOwnerNumber,sharedOwnerEmail});
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return shareContactsLists;
	}
	
	@SuppressWarnings("unchecked")
	public List<AddContact> contactSearch(String ownerId, String search) {

		session = sessionFactory.openSession();
		String sql = ContactManagementQueries.contactSearch(ownerId, search);
		SQLQuery query = session.createSQLQuery(sql);
		query.addEntity(AddContact.class);
		List<AddContact> acontact = query.list();
		return acontact;

	}

	@SuppressWarnings("unchecked")
	public List<AddContact> groupSearch(int groupId, String ownerNumber, String search) {

		session = sessionFactory.openSession();
		String sql = ContactManagementQueries.groupSearch(groupId, ownerNumber, search);
		SQLQuery query = session.createSQLQuery(sql);
		query.addEntity(AddContact.class);
		List<AddContact> acontact = query.list();
		return acontact;
	}

	@SuppressWarnings("unchecked")
	public List<AddContact> searchSharedContact(String ownerId, String search) {

		session = sessionFactory.openSession();
		String sql = ContactManagementQueries.searchSharedContact(ownerId, search);
		SQLQuery query = session.createSQLQuery(sql);
		query.addEntity(AddContact.class);
		List<AddContact> acontact = query.list();
		return acontact;
	}

	@SuppressWarnings({ "rawtypes" })
	public List getDistinctIdContactTrans(String ownerNumber) {

		List groupIdList = hibernateTemplate.find(
				"select distinct groupId from ContactGroupTrans trans " + "where trans.ownerNumber=?", ownerNumber);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return groupIdList;
	}

	@SuppressWarnings("rawtypes")
	public Iterator getIterator(Object object, String ownerNumber) {

		session = sessionFactory.openSession();
		String sql = "select count(*),groupName from ContactGroupTrans where ownerNumber= " + "'" + ownerNumber
				+ "' and groupid=" + object + "group by groupName ";
		Query query = session.createQuery(sql);
		Iterator data = query.list().iterator();
		return data;
	}

	@SuppressWarnings("unchecked")
	public List<AddContact> getAddContactList(Distance d, Address finalAddress) {

		List<AddContact> data = null;
		session = sessionFactory.openSession();
		String sql = "SELECT * FROM AddContact ref inner join CONTACT_ADDRESS addr on ref.id=addr.contact_id  where ref.ownerNumber ='"
				+ d.getOwnerNumber() + "' and addr.latitude=" + finalAddress.getLatitude() + " and addr.longitude="
				+ finalAddress.getLongitude();
		SQLQuery query = session.createSQLQuery(sql);
		query.addEntity(AddContact.class);
		data = query.list();
		return data;
	}
	
public List<SignUpContact> getAllSignUpContacts(){
	
	/*List<SignUpContact> data = null;
	session = sessionFactory.openSession();
	String sql = "SELECT * FROM SignUpContact";
	SQLQuery query = session.createSQLQuery(sql);
	query.addEntity(SignUpContact.class);
	data = query.list();
	return data;*/
	
		List<SignUpContact> signUpContactList = (List<SignUpContact>) hibernateTemplate.loadAll(SignUpContact.class);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return signUpContactList;
		
	}

	@SuppressWarnings("unchecked")
	public List<AddContact> getAddContactListByOwner(String ownerNumber) {

		List<AddContact> data = (List<AddContact>) hibernateTemplate
				.find(ContactManagementQueries.GET_ADDCONTACTLIST_BY_OWNER, new Object[] { ownerNumber });
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return data;

	}

	@SuppressWarnings("unchecked")
	public List<AddContact> searchFriendNearPoints(String ownerNumber, String search) {

		String sql = ContactManagementQueries.searchFriendNearPoints(ownerNumber, search);
		session = sessionFactory.openSession();
		SQLQuery query = session.createSQLQuery(sql);
		query.addEntity(AddContact.class);
		List<AddContact> data = query.list();
		return data;
	}

	@SuppressWarnings("unchecked")
	public List<ContactGroupTrans> getContactGroupTransByOwner(String ownerNumber) {

		List<ContactGroupTrans> contactGroupTransLists = (List<ContactGroupTrans>) hibernateTemplate
				.find(ContactManagementQueries.GET_CONTACTGROUPTRANS_BY_OWNER, new Object[] { ownerNumber });
		return contactGroupTransLists;

	}

	public ContactGroupTrans getContactGroupByGroupNameAndContactIdAndOwnerisActiveTrue(
			MoveContactToGroup moveContactToGroup) {

		ContactGroupTrans contactGroupTrans = (ContactGroupTrans) hibernateTemplate
				.find(ContactManagementQueries.GET_CONTACTGROUP_BY_GROUPNAME_AND_CONTACTID_AND_OWNER_ISACTIVETRUE,
						new Object[] { moveContactToGroup.getContactId(), moveContactToGroup.getOwnerNumber(),
								moveContactToGroup.getFromGroup() })
				.get(0);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return contactGroupTrans;

	}

	public ContactGroupTrans getContactGroupByContactIdAndOwnerisActiveTrue(AddContact addContact) {

		ContactGroupTrans contactGroupTrans = (ContactGroupTrans) hibernateTemplate
				.find(ContactManagementQueries.GET_CONTACTGROUP_BY_CONTACTID_AND_OWNER_ISACTIVETRUE,
						new Object[] { addContact.getId(), addContact.getOwnerNumber() })
				.get(0);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return contactGroupTrans;
	}

	public ShareContact getShareContactByOwnerAdBrowerAndisActiveTrue(String ownerNumber, int contactId) {

		ShareContact shareContact = (ShareContact) hibernateTemplate
				.find(ContactManagementQueries.GET_SHARECONTACT_BY_OWNER_AND_BROWER_AND_ISACTIVETRUE,
						new Object[] { ownerNumber, contactId })
				.get(0);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return shareContact;
	}

	public SignUpContact getSignUpContact(ShareContact shareContact) {

		SignUpContact signUpContact=	(SignUpContact) hibernateTemplate.find(ContactManagementQueries.GET_SIGNUPCONTACT_BY_BORROWERNUMBER_AND_BORROWEREMAIL,
				new Object[] { shareContact.getBorroweId(), shareContact.getBorrowerEmail() }).get(0);
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return signUpContact;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<ContactGroupTrans> getContactGroupsTransListByIDandOwnerNumber(String ownerNumber, int groupId){
		List<ContactGroupTrans> contactGroupTransList=(List<ContactGroupTrans>) hibernateTemplate.find(ContactManagementQueries.GET_CONTACT_GROUPS_TRANSLIST_BY_ID, new Object[]{ownerNumber,groupId});
		return contactGroupTransList;
	}
	
	public SharedAddContact getShareContactById(String ownerNumber, int contactId) {
		SharedAddContact sharedAddContact=	(SharedAddContact) hibernateTemplate.find(ContactManagementQueries.GET_SHAREDADDCONTACT_BY_OWERNUMBER_AND_ID,
				new Object[] { ownerNumber, contactId }).get(0);
		return sharedAddContact;	
		
	}
	
	public SignUpContact getSignUpContactByEmailIdAndPhoneNumber(String sharedEmail, String sharedOwnerNumber) {

		List<SignUpContact> signUpContactList = (List<SignUpContact>) hibernateTemplate
				.find(ContactManagementQueries.SIGNUP_QUERY_BY_EMAILID_OR_PHONENUMBER,
						new Object[] { sharedEmail, sharedOwnerNumber });
		if(signUpContactList.size()==0) {
			return new SignUpContact();
		}
		hibernateTemplate.flush();
		hibernateTemplate.clear();
		return signUpContactList.get(0);
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

}
