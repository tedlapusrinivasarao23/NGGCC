package com.contact.mgmt.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.contact.mgmt.bos.SignUpContact;
import com.contact.mgmt.dao.ContactManagementDao;
import com.contact.mgmt.dao.GroupManagementDao;
import com.contact.mgmt.mappers.SendEmail;
import com.contact.mgmt.mappers.SendSms;

@Component
public class JobSchedulerForSubscription {

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

	private static final DateFormat df = new SimpleDateFormat("dd-MMM-yyyy hh.mm.ss aa");

	// 2 second.
	@Scheduled(fixedDelay = 1000*60*60*24)
	@Transactional
	public void writeCurrentTime() {
		
		try {
			List<SignUpContact> signUpContactList = groupManagementDao.getAllSignUpContacts();

			for (SignUpContact signUpContact : signUpContactList) {
				if (!signUpContact.getSubscriptionEndDate().isEmpty()) {
					long l = getScheduleDays(getCurrentSystemDateAndTime(), signUpContact.getSubscriptionEndDate());
					if (l >= 1 && l <= 5) {
						sendEmail.sendEmailForgotPassword(signUpContact.getEmailId(), "",
								" Your Subscription for NGC is Ending in " + l
										+ " day's, Please Subscribe to get Server Access  ");
						sendSms.sendSMS(signUpContact.getPhoneNumber(), "", " Your Subscription for NGC is Ending in "
								+ l + "day's, Please Subscribe to get Server Access ");
					}
					if (l >= -10 && l <= 0) {
						sendEmail.sendEmailForgotPassword(signUpContact.getEmailId(), "",
								" Your Subscription for NGC is Ended. Please Subscribe to get Server Access ");
						sendSms.sendSMS(signUpContact.getPhoneNumber(), "", " Your Subscription for NGC is Ended. Please Subscribe to get Server Access  ");
					}
					if (l <= 0) {
						signUpContact.setPaid(false);
					}
					signUpContact.setSubscriptionDaysLeft((int) l);
					groupManagementDao.methodForUpdate(signUpContact);
				}
			}
		} catch (Exception e) {

			System.out.println("Query Not Executed" + e);
		}

	}

	private String getCurrentSystemDateAndTime() {
		DateFormat df = new SimpleDateFormat("dd-MMM-yyyy hh.mm.ss aa");

		Date today = Calendar.getInstance().getTime();
		String reportDate = df.format(today);
		return reportDate;

	}

	private static long getScheduleDays(String strDate, String strDate2) {
		long l = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		Date date1 = null;
		Date date2 = null;
		if ((!strDate.equals("") || !strDate.isEmpty()) && (!strDate2.equals("") || !strDate2.isEmpty())) {
			try {
				date1 = sdf.parse(strDate);

				Calendar cal = Calendar.getInstance();
				cal.setTime(date1);
				Date dt = cal.getTime();

				date2 = sdf.parse(strDate2);
				Calendar cal1 = Calendar.getInstance();
				cal1.setTime(date2);
				Date dt2 = cal1.getTime();

				System.out.println(dt.getTime() + ":::::::::::");
				System.out.println(dt2.getTime() + ":::::::::::");
				l = (dt2.getTime() - dt.getTime()) / 86400000;
				System.out.println("Days  Left" + l);
				return l;
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}
		System.out.println("<<>>");
		return l;
	}

}
