package com.mg43.model;

public class Email {
	private String[] to;
	private String[] cc;
	private String[] reply;
	private String from = "";
	private String fromLabel = "";
	private String subject = "";
	private String body = "";
	private String fileNamesDir = "";
	private String[] fileNames;

	public String[] getTo() {
		return to;
	}

	public String[] getCc() {
		return cc;
	}

	public String[] getReply() {
		return reply;
	}

	public String getFrom() {
		return from;
	}

	public String getFromLabel() {
		return fromLabel;
	}

	public String getSubject() {
		return subject;
	}

	public String getBody() {
		return body;
	}

	public String getFileNamesDir() {
		return fileNamesDir;
	}

	public String[] getFileNames() {
		return fileNames;
	}

	public void setTo(String[] to) {
		this.to = to;
	}

	public void setCc(String[] cc) {
		this.cc = cc;
	}

	public void setReply(String[] reply) {
		this.reply = reply;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setFromLabel(String fromLabel) {
		this.fromLabel = fromLabel;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setFileNamesDir(String fileNamesDir) {
		this.fileNamesDir = fileNamesDir;
	}

	public void setFileNames(String[] fileNames) {
		this.fileNames = fileNames;
	}
}