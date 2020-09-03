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
		logger.info("SMSUtil::sendSMS::{}::{}", contact,message);
		try {
			// Construct data
			String apiKey = "apikey=" + "GpjNUVXOBwM-P8sxHKLh8ieoFQh4Fmhwaa35A0q2oo";
			String smsMsg = "&message=" + message + "for the User:" +contact;
			String sender = "&sender=" + "TXTLCL";
			String numbers = "&numbers=9738521186";

			// Send data
			HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
			String data = apiKey + numbers + smsMsg + sender;
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

			return stringBuffer.toString();
		} catch (Exception e) {
			logger.info("SMSUtil::sendSMS::Error::{}", e.getLocalizedMessage());
			return "Error " + e;
		}
	}

}
