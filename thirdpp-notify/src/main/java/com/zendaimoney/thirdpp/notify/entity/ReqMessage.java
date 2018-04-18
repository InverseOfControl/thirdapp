package com.zendaimoney.thirdpp.notify.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.zendaimoney.thirdpp.notify.util.JaxbBinder;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Document")
@XmlRootElement(name = "trans")
public class ReqMessage {

	public ReqMessage() {
	}

	public ReqMessage(List<NotifyMessage> list) {
		this.list = list;
	}

	@XmlElement(name = "tran")
	private List<NotifyMessage> list;

	public List<NotifyMessage> getList() {
		return list;
	}

	public void setList(List<NotifyMessage> list) {
		this.list = list;
	}

	public String toXml() {
		JaxbBinder binder = new JaxbBinder(this.getClass());
		String xml = binder.toXml(this, "UTF-8");
		return xml.substring(xml.indexOf("\n") + 1);
	}
}
