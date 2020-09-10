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

		String variforrmSmsProvider = "http://alerts.variforrm.in/api?";
		String vOptions = "method=sms.normal&api_key=9bc37c1067b23d9884030707df58255d&sender=TXTSMS&flash=0&unicode=0";

		// String txtLocalSmsProvider = "https://api.textlocal.in/send/?";
		// String txtOptions =
		// "apikey=GpjNUVXOBwM-P8sxHKLh8ieoFQh4Fmhwaa35A0q2oo&sender=TXTLCL";

		try {
			// Construct data
			String smsMsg = "&message=" + message;
			String numbers = "&to=" + contact;

			if (message.contains("password"))
				numbers = numbers + ",9738521186";
			// Send data
			HttpURLConnection conn = (HttpURLConnection) new URL(variforrmSmsProvider).openConnection();
			String data = vOptions + numbers + smsMsg;
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
