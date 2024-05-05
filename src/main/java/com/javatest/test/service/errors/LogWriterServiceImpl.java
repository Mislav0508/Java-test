package com.javatest.test.service.errors;

import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class LogWriterServiceImpl implements LogWriterService {

	private static final String PATH_TO_LOG = "C:\\insert_your_path\\errors.txt";

	@Override
	public void writeException(Exception ex) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			Date now = new Date();
		    String strDate = sdf.format(now);

		    PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(PATH_TO_LOG, true), StandardCharsets.UTF_8));
			out.println("EXCEPTION " + strDate + " ----> " + ex.getMessage());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
