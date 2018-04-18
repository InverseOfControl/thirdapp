package com.zendaimoney.thirdpp.notify.util;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang.StringUtils;

/**
 * xml工具类，使用JAXB
 */
public class JaxbBinder {

	/**
	 * 被替换standalone="yes"
	 */
	public static String REPLACE_STANDALONE = "standalone=\"yes\"";

	private JAXBContext jaxbContext;

	/**
	 * @param types
	 *            所有需要序列化的对象的类型
	 */
	public JaxbBinder(Class<?>... types) {
		try {
			jaxbContext = JAXBContext.newInstance(types);
		} catch (JAXBException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	/**
	 * @param encoding
	 *            字符集
	 * @return 创建Marshaller
	 */
	public Marshaller createMarshaller(String encoding) {
		try {
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
					Boolean.TRUE);
			if (StringUtils.isNotBlank(encoding)) {
				marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
			}
			return marshaller;
		} catch (JAXBException e) {
			throw new RuntimeException();
		}

	}

	/**
	 * @return 创建Unmarshaller
	 */
	public Unmarshaller createUnMarshaller() {
		try {
			return jaxbContext.createUnmarshaller();
		} catch (JAXBException e) {
			throw new RuntimeException();
		}
	}

	/**
	 * 将java对象转换为Xml
	 */
	public String toXml(Object obj, String encoding) {
		try {
			StringWriter writer = new StringWriter();
			createMarshaller(encoding).marshal(obj, writer);
			return writer.toString();
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将xml转换为java对象
	 */
	@SuppressWarnings("unchecked")
	public <T> T fromXml(String xml) {
		try {
			// StringReader reader = new StringReader(xml);
			// Unmarshaller unmarshaller = createUnMarshaller();
			// return unmarshaller.unmarshal(reader);
			return (T) createUnMarshaller().unmarshal(new StringReader(xml));
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 根据指定的xsd文件对xml报文做格式检查
	 * 
	 * @param xmlText
	 *            xml报文
	 * @param schemaName
	 *            xsd文件名称
	 */
	public void doValidate(String xmlText, String schemaName) {
		/*
		 * try { SchemaFactory factory =
		 * SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI); File
		 * schemaFile = getschemaFile(schemaName); Schema schema =
		 * factory.newSchema(schemaFile); Document document =
		 * DocumentHelper.parseText(xmlText); Source source = new
		 * DocumentSource(document); schema.newValidator().validate(source); }
		 * catch (Exception e) { throw new
		 * PlatformException(PlatformErrorCode.XML_VALIDATE_ERROR, e); }
		 */
	}

	/*
	 * private File getschemaFile(String schemaName) throws IOException { String
	 * schemaTemplate = "schema/" + schemaName + ".xsd"; Resource res = new
	 * ClassPathResource(schemaTemplate); return res.getFile(); }
	 */

}
