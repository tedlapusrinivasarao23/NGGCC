package com.contact.mgmt.dummy;

public class ContactManagementQueries {

	public static final String SIGNUP_QUERY_BY_EMAILID = "FROM SignUpContact WHERE emailId = ?";

	public static final String SIGNUP_QUERY_BY_PHONE_NUMBER = "FROM SignUpContact WHERE PHONENUMBER = ?";

	public static final String SIGNUP_QUERY_BY_EMAILID_OR_PHONENUMBER = "from SignUpContact WHERE emailId = ? OR phonenumber = ? ";
	
	public static final String SIGNUP_QUERY_BY_EMAILID_AND_PHONENUMBER = "from SignUpContact WHERE emailId = ? AND phonenumber = ? ";
	
	public static final String LOGIN_QUERY_BY_EMAILID_OR_PHONENUMBER = "from LoginUserDetails WHERE userEmailId = ? OR userPhoneNumber = ? ";

	public static final String ADDCONTACT_BY_OWNERNUMBER = "from AddContact where ownerNumber = ?";

	public static final String GET_CONTACT_GROUPTRANS_BY_OWNERNUMBER_AND_ID = "from ContactGroupTrans where ownerNumber=? and contactId=? and isActive=1";

	public static final String GET_ADDFAVBY_OWNER_SHARED_AND_ISACTIVEFALSE = "FROM AddFavourite WHERE ownerNumber = ? and sharedFavourite=? and isActive=0";

	public static final String GET_ADD_FAVBY_OWNER_SHARED_AND_ISACTIVETRUE = "FROM AddFavourite WHERE ownerNumber = ? and sharedFavourite=? and isActive=1";

	public static final String GET_ADDFAVBY_OWNER_SHARED = "FROM AddFavourite WHERE ownerNumber = ? and sharedFavourite= ? ";

	public static final String GET_GROUPREF_LISTBY_OWNER = "FROM GroupRef WHERE ownerNumber = ? and isActive=1";

	public static final String GET_CONTACT_GROUPS_TRANSLIST = "from ContactGroupTrans where ownerNumber=? and groupName=? and isActive=1";
	
	public static final String GET_CONTACT_GROUPS_TRANSLIST_BY_ID = "from ContactGroupTrans where ownerNumber=? and groupId=? and isActive=1";

	public static final String GET_GROUPREF_GROUPID_AND_OWNER = "FROM GroupRef WHERE groupId = ? and ownerNumber = ? ";

	public static final String GET_GROUPREF_GROUPID_AND_OWNER_AND_ISACTIVEFALSE = "FROM GroupRef WHERE ownerNumber = ? and groupName=? and isActive=0";

	public static final String GET_GROUPREF_GROUPID_AND_OWNERAND_ISACTIVETRUE = "FROM GroupRef WHERE ownerNumber = ? and groupName=? and isActive=1";

	public static final String GET_GROUPREFLIST_BY_OWNER_AND_GROUPID = "from GroupRef where ownerNumber=? and isActive=1";
	
	public static final String GET_GROUPREFLIST_BY_OWNER_AND_GROUPID_ISACTIVE = "from GroupRef where ownerNumber=? and groupId = ? and isActive=1";

	public static final String GET_GROUPREF_GROUPNAME_AND_OWNER = "From GroupRef WHERE ownerNumber = ? and groupName=?";

	public static final String GET_CONTACTGROUPSTRANS_BY_CONTACTID_AND_OWNER_AND_ISACTIVETRUE = "from ContactGroupTrans where contactId=? and ownerNumber=? and isActive=1";

	public static final String GET_CONTACTGROUPSTRANS_BYCONTACTID_AND_OWNER_AND_GROUPNAME_AND_GROUPID_AND_ISACTIVEFALSE = "FROM ContactGroupTrans WHERE ownerNumber = ? and groupName=? and isActive=0 and contactId=? and groupId=?";

	public static final String GET_CONTACTGROUPSTRANS_BYCONTACTID_AND_OWNER_AND_GROUPNAME_AND_GROUPID_AND_ISACTIVETRUE = "FROM ContactGroupTrans WHERE ownerNumber = ? and groupName=? and isActive=1 and contactId=? and groupId=?";

	public static final String GET_CONTACTGROUPSTRANS_BY_CONTACTID_AND_OWNER_AND_GROUPID_AND_ISACTIVETRUE = "FROM ContactGroupTrans WHERE contactId =? and groupId = ? and ownerNumber = ? and isActive=1";

	public static final String GET_LISTSHARECONTACT_BY_BROWERID = "from ShareContact where borroweId=? and isActive=1";
	
	public static final String GET_LISTSHARECONTACT_BY_SHAREDCNCT = "from ShareContact where sharedCntct=? and borroweId=? and borrowerEmail=? and isActive=1";

	public static final String GET_ADDCONTACTLIST_BY_OWNER = "from AddContact where ownerNumber=?";

	public static final String GET_CONTACTGROUPTRANS_BY_OWNER = "from ContactGroupTrans where ownerNumber=? and isActive=1";

	public static final String GET_CONTACTGROUP_BY_GROUPNAME_AND_CONTACTID_AND_OWNER_ISACTIVETRUE = "from ContactGroupTrans where contactId=? and ownerNumber=? and groupName=? and isActive=1";

	public static final String GET_SHARECONTACT_BY_OWNER_AND_BROWER_AND_ISACTIVETRUE = "from ShareContact where borroweId=? and shareContactId=? and isActive=1";
	
	public static final String GET_CONTACTGROUP_BY_CONTACTID_AND_OWNER_ISACTIVETRUE = "from ContactGroupTrans where contactId=? and ownerNumber=? and isActive=1";
	
	public static final String GET_SHARECONTACT_BY_OWNER_AND_ACTIVETRUE="from ShareContact where sharedId=? and sharedCntct=? and isActive=1";
	
	public static final String GET_SIGNUPCONTACT_BY_BORROWERNUMBER_AND_BORROWEREMAIL="from SignUpContact where phoneNumber=? or emailId=?";
	
	public static final String GET_SHAREDADDCONTACT_BY_OWERNUMBER_AND_ID="from SharedAddContact where ownerNumber=? and id=?";
	
	
	
	public static final String contactSearch(String ownerId, String search){
		
		String sql = "SELECT * FROM AddContact  inner join Contact_Events event on AddContact.id=event.contact_id "
				+ " inner join Contact_Emails email on AddContact.id=email.contact_id "
				+ " inner join Contact_Texts notes on AddContact.id=notes.contact_id inner join Contact_Urls url on AddContact.id=url.contact_id "
				+ " inner join Contact_PhoneNum phnumber on AddContact.id=phnumber.contact_id inner join Contact_Address addr on AddContact.id=addr.contact_id   where AddContact.ownerNumber="
				+ ownerId + " and (company Like " + "'%" + search + "%'" + "or department Like " + "'%" + search + "%'"
				+ "or designation Like " + "'%" + search + "%'" + "or firstName Like" + "'%" + search + "%'"
				+ "or groupName Like" + "'%" + search + "%'" + "or lastName Like" + "'%" + search + "%'"
				+ "or ownerNumber Like" + "'%" + search + "%'" + "or prefix Like" + "'%" + search + "%'"
				+ "or email.emailId Like" + "'%" + search + "%'" + "or event.event Like" + "'%" + search + "%'"
				+ "or notes.text Like" + "'%" + search + "%'" + "or phnumber.phonenumber Like" + "'%" + search + "%'"
				+ "or url.url Like" + "'%" + search + "%'" + "or addr.addressLine1 Like" + "'%" + search + "%'"
				+ "or addr.addressLine2 Like" + "'%" + search + "%'" + "or addr.country Like" + "'%" + search + "%'"
				+ "or addr.pincode Like" + "'%" + search + "%'" + "or addr.state Like" + "'%" + search + "%')";
		return sql;
	}
	
	public static final String groupSearch(int groupId, String ownerNumber, String search) {
		
		String sql = "SELECT * FROM AddContact inner join group_ref ref  on ref.groupName= AddContact.groupName inner join Contact_Events event on AddContact.id=event.contact_id "
				+ " inner join Contact_Emails email on AddContact.id=email.contact_id "
				+ " inner join Contact_Texts notes on AddContact.id=notes.contact_id inner join Contact_Urls url on AddContact.id=url.contact_id "
				+ " inner join Contact_PhoneNum phnumber on AddContact.id=phnumber.contact_id inner join Contact_Address addr on AddContact.id=addr.contact_id  where ref.group_id="
				+ groupId + "and AddContact.ownerNumber=" + ownerNumber + " and (company Like " + "'%" + search + "%'"
				+ "or department Like " + "'%" + search + "%'" + "or designation Like " + "'%" + search + "%'"
				+ "or firstName Like" + "'%" + search + "%'" + "or AddContact.groupName Like" + "'%" + search + "%'"
				+ "or lastName Like" + "'%" + search + "%'" + "or AddContact.ownerNumber Like" + "'%" + search + "%'"
				+ "or prefix Like" + "'%" + search + "%'" + "or email.emailId Like" + "'%" + search + "%'"
				+ "or event.event Like" + "'%" + search + "%'" + "or notes.text Like" + "'%" + search + "%'"
				+ "or phnumber.phonenumber Like" + "'%" + search + "%'" + "or url.url Like" + "'%" + search + "%'"
				+ "or addr.addressLine1 Like" + "'%" + search + "%'" + "or addr.addressLine2 Like" + "'%" + search
				+ "%'" + "or addr.country Like" + "'%" + search + "%'" + "or addr.pincode Like" + "'%" + search + "%'"
				+ "or addr.state Like" + "'%" + search + "%')";
		return sql;
	}
	
	
	public static final String searchSharedContact(String ownerId, String search){
		
		String sql = "SELECT * FROM AddContact inner join sharecontact_trans trans  on trans.sharedCntct= AddContact.id "
				+ " inner join Contact_Events event on AddContact.id=event.contact_id "
				+ " inner join Contact_Emails email on AddContact.id=email.contact_id "
				+ " inner join Contact_Texts notes on AddContact.id=notes.contact_id inner join Contact_Urls url on AddContact.id=url.contact_id "
				+ " inner join Contact_PhoneNum phnumber on AddContact.id=phnumber.contact_id inner join Contact_Address addr on AddContact.id=addr.contact_id where AddContact.ownerNumber="
				+ ownerId + " and (company Like " + "'%" + search + "%'" + "or department Like " + "'%" + search + "%'"
				+ "or designation Like " + "'%" + search + "%'" + "or firstName Like" + "'%" + search + "%'"
				+ "or AddContact.groupName Like" + "'%" + search + "%'" + "or lastName Like" + "'%" + search + "%'"
				+ "or AddContact.ownerNumber Like" + "'%" + search + "%'" + "or prefix Like" + "'%" + search + "%'"
				+ "or email.emailId Like" + "'%" + search + "%'" + "or event.event Like" + "'%" + search + "%'"
				+ "or notes.text Like" + "'%" + search + "%'" + "or phnumber.phonenumber Like" + "'%" + search + "%'"
				+ "or url.url Like" + "'%" + search + "%'" + "or addr.addressLine1 Like" + "'%" + search + "%'"
				+ "or addr.addressLine2 Like" + "'%" + search + "%'" + "or addr.country Like" + "'%" + search + "%'"
				+ "or addr.pincode Like" + "'%" + search + "%'" + "or addr.state Like" + "'%" + search + "%')";
		return sql;
	}
	
	
	public static final String searchFriendNearPoints(String ownerNumber, String search){
		
		String sql = "SELECT * FROM AddContact inner join group_ref ref  on ref.groupName= AddContact.groupName inner join Contact_Events event on AddContact.id=event.contact_id "
				+ " inner join Contact_Emails email on AddContact.id=email.contact_id "
				+ " inner join Contact_Texts notes on AddContact.id=notes.contact_id inner join Contact_Urls url on AddContact.id=url.contact_id "
				+ " inner join Contact_PhoneNum phnumber on AddContact.id=phnumber.contact_id inner join Contact_Address addr on AddContact.id=addr.contact_id  where AddContact.ownerNumber= '"
				+ ownerNumber + "' and (company Like " + "'%" + search + "%'" + "or department Like " + "'%" + search
				+ "%'" + "or designation Like " + "'%" + search + "%'" + "or firstName Like" + "'%" + search + "%'"
				+ "or AddContact.groupName Like" + "'%" + search + "%'" + "or lastName Like" + "'%" + search + "%'"
				+ "or AddContact.ownerNumber Like" + "'%" + search + "%'" + "or prefix Like" + "'%" + search + "%'"
				+ "or email.emailId Like" + "'%" + search + "%'" + "or event.event Like" + "'%" + search + "%'"
				+ "or notes.text Like" + "'%" + search + "%'" + "or phnumber.phonenumber Like" + "'%" + search + "%'"
				+ "or url.url Like" + "'%" + search + "%'" + "or addr.addressLine1 Like" + "'%" + search + "%'"
				+ "or addr.addressLine2 Like" + "'%" + search + "%'" + "or addr.country Like" + "'%" + search + "%'"
				+ "or addr.pincode Like" + "'%" + search + "%'" + "or addr.state Like" + "'%" + search + "%')";
		return sql;

	}
	
	
	
}
