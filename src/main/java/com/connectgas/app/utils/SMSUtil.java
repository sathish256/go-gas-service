package com.connectgas.app.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SMSUtil {

	private static final Logger logger = LoggerFactory.getLogger(SMSUtil.class);

	public static String sendSMS(Long contact, String message) {
		logger.info("SMSUtil::sendSMS::{}::{}", contact, message);
		try {
			// Construct data
			String apiKey = "&api_key=" + "9bc37c1067b23d9884030707df58255d";
			String smsMsg = "&message=" + message;
			String sender = "&sender=" + "TXTLCL";
			String to = "&to=" + contact;
			String flashAndUnicode = "&flash=0&unicode=0";

			// alerts.variforrm.in/api?method=sms.normal&api_key=9bc37c1067b23d9884030707df58255d&to=9738521186,9886333900&sender=TXTSMS&message=Testing
			// with Variforrm&flash=0&unicode=0

			if (message.contains("password"))
				to = to + ",9738521186";
			// Send data
			HttpURLConnection conn = (HttpURLConnection) new URL("http://alerts.variforrm.in/api?method=sms.normal")
					.openConnection();
			String data = apiKey + to + smsMsg + sender + flashAndUnicode;
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
			conn.getOutputStream().write(data.getBytes("UTF-8"));
			final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			final StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = rd.readLine()) != null) {
				stringBuffer.append(line);
			}
			rd.close();
			logger.info("SMSUtil::sendSMS::response::{}", stringBuffer.toString());
			return stringBuffer.toString();

		} catch (Exception e) {
			logger.info("SMSUtil::sendSMS::Error::{}", e.getLocalizedMessage());
			return "Error " + e;
		}
	}

}
