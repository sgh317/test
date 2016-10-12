package com.rayuniverse.framework.comm;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.axis.utils.XMLUtils;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;


public class XmlMapUtil {
	private static Logger logger = LoggerFactory.getLogger(XmlMapUtil.class);

	

	public static String formatXML(String xml) {
		StringWriter out = null;
		try {
			ByteArrayInputStream in = new ByteArrayInputStream(
					xml.getBytes("UTF-8"));
			Document XMLDoc = XMLUtils.newDocument(in);
			in.close();

			OutputFormat formate = OutputFormat.createPrettyPrint();
			out = new StringWriter();
			XMLWriter writer = new XMLWriter(out, formate);
			writer.write(XMLDoc);
			return out.toString();
		} catch (Throwable e) {
			return xml;
		} finally {
			try {
				out.close();
			} catch (Throwable e) {
			}
		}

	}

	public static Object fromBytes(byte[] data) {

		ByteArrayInputStream bin = new ByteArrayInputStream(data);
		ObjectInputStream oin;
		try {
			oin = new ObjectInputStream(bin);
			return oin.readObject();
		} catch (Exception e) {
			throw new RuntimeException("Serialize exception", e);
		}
	}

	public static byte[] toBytes(Object object) {
		try {
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(bout);
			out.writeObject(object);
			return bout.toByteArray();
		} catch (Exception e) {
			throw new RuntimeException("Serialize exception", e);
		}
	}

	public static Map createMapField(Map map, String fieldName) {
		HashMap field = new HashMap();
		map.put(fieldName, field);
		return field;
	}

	public static List createListField(Map map, String fieldName) {
		ArrayList field = new ArrayList();
		map.put(fieldName, field);
		return field;
	}

	public static String getYYYYmmDD(Date date) {
		SimpleDateFormat dateformat1 = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return dateformat1.format(date);
	}

	public static Map clone(Map map) {
		if (map == null) {
			return null;
		}
		return (Map) fromBytes(toBytes(map));
	}

	public static List clone(List list) {
		if (list == null) {
			return null;
		}
		return (List) fromBytes(toBytes(list));
	}

	public static Object clone(Object o) {
		if (o == null) {
			return null;
		}
		return (Map) fromBytes(toBytes(o));
	}

	public static Map XMLStrToMap(String XMLStr) {
		HashMap hashmap = new HashMap();
		try {

			ByteArrayInputStream in = new ByteArrayInputStream(
					XMLStr.getBytes("UTF-8"));
			Document XMLDoc = XMLUtils.newDocument(in);
			in.close();

			Node rootNode = XMLDoc.getDocumentElement();

			Object v = converXML(rootNode);

			hashmap.put(rootNode.getNodeName(), v);

		} catch (Throwable e) {
			logger.error("MapToXMLStr", e);
			throw new RuntimeException(e);
		}

		return hashmap;
	}

	public static String getStringFromXmlMap(Map map, String path) {
		return (String) getFromXmlMap(map, path);
	}

	public static Map getMapFromXmlMap(Map map, String path) {
		return (Map) getFromXmlMap(map, path);
	}

	public static List getAsListFromXmlMap(Map map, String path) {
		Object v = getFromXmlMap(map, path);
		if (v == null) {
			return null;
		}
		if (v instanceof List) {
			return (List) v;
		} else {
			ArrayList list = new ArrayList();
			list.add(v);
			return list;
		}

	}

	
	public static Object getFromXmlMap(Map map, String path) {
		if (map == null) {
			return null;
		}

		if (path.startsWith("/")) {
			path = path.substring(1);
		}

		if (path.indexOf("/") == -1) {
			String fieldStr = path;
			String fieldName = null;
			String fieldIndex = null;
			if (fieldStr.indexOf("[") != -1) {
				fieldName = fieldStr.substring(0, fieldStr.indexOf("["));
				fieldIndex = fieldStr.substring(fieldStr.indexOf("[") + 1,
						fieldStr.indexOf("]"));
			} else {
				fieldName = fieldStr;
			}

			if (fieldIndex == null) {
				return map.get(fieldName);
			} else {
				Object v = map.get(fieldName);
				if (v instanceof List) {
					List lv = (List) v;
					int index = Integer.parseInt(fieldIndex);
					if (lv.size() > index) {
						return lv.get(index);
					} else {
						return null;
					}
				} else {
					return v;
				}
			}

		} else {
			String fieldStr = path.substring(0, path.indexOf("/"));
			String fieldName = null;
			String fieldIndex = null;
			if (fieldStr.indexOf("[") != -1) {
				fieldName = fieldStr.substring(0, fieldStr.indexOf("["));
				fieldIndex = fieldStr.substring(fieldStr.indexOf("[") + 1,
						fieldStr.indexOf("]"));
			} else {
				fieldName = fieldStr;
			}

			if (fieldIndex == null) {
				Object v = map.get(fieldName);
				if (v instanceof Map) {
					return getFromXmlMap((Map) v,
							path.substring(path.indexOf("/") + 1));
				} else {
					return null;
				}
			} else {
				Object v = map.get(fieldName);
				if (v instanceof List) {
					List lv = (List) v;
					int index = Integer.parseInt(fieldIndex);
					if (lv.size() > index) {
						Object ov = lv.get(index);
						if (ov instanceof Map) {
							return getFromXmlMap((Map) ov,
									path.substring(path.indexOf("/") + 1));
						} else {
							return null;
						}
					} else {
						return null;
					}
				} else {
					if (v instanceof Map) {
						return getFromXmlMap((Map) v,
								path.substring(path.indexOf("/") + 1));
					} else {
						return null;
					}

				}
			}

		}

	}

	private static Object converXML(Node node) {

		Object obj = null;

		HashMap hashmap = new HashMap();

		Node childnode = node.getFirstChild();

		while (childnode != null) {
			if (childnode.getNodeType() == Node.TEXT_NODE) {

				String nodeVal = childnode.getNodeValue();

				if (nodeVal != null) {
					nodeVal = nodeVal.trim();
				}

				if (nodeVal != null && !nodeVal.equals(""))
					obj = childnode.getNodeValue();

			} else if (childnode.getNodeType() == Node.ELEMENT_NODE) {

				String childnodname = childnode.getNodeName();
				Object childObj = converXML(childnode);
				Object preChildObj = hashmap.get(childnodname);
				if (preChildObj != null) {
					if (preChildObj instanceof List) {
						List preChildList = (List) preChildObj;
						preChildList.add(childObj);
					} else {
						List preChildList = new ArrayList();
						hashmap.put(childnodname, preChildList);
						preChildList.add(preChildObj);
						preChildList.add(childObj);
					}
				} else {
					hashmap.put(childnodname, childObj);
				}

				obj = hashmap;
			}
			childnode = childnode.getNextSibling();
		}
		return obj;
	}

	public static String MapToXMLStr(Map input) {

		String XMLStr = null;
		try {
			Document XMLDoc = XMLUtils.newDocument();
			XMLDoc = XMLUtils.newDocument();

			Iterator it = input.keySet().iterator();
			while (it.hasNext()) {
				String NodeName = (String) it.next();
				Element el = XMLDoc.createElement(NodeName.trim());

				if (input.get(NodeName) instanceof Map) {
					Map child = (Map) input.get(NodeName);
					MapToNode(XMLDoc, el, child);
				}
				if (input.get(NodeName) instanceof List) {
					List child = (List) input.get(NodeName);
					ListToNode(XMLDoc, el, child);
				}
				if (input.get(NodeName) instanceof String) {

					Text text = XMLDoc.createTextNode((String) input
							.get(NodeName));
					el.appendChild(text);
				}
				XMLDoc.appendChild(el);
			}
			XMLStr = XMLUtils.ElementToString(XMLDoc.getDocumentElement());
		} catch (Throwable e) {
			logger.error("MapToXMLStr", e);
			throw new RuntimeException(e);
		}
		return XMLStr;

	}

	private static void MapToNode(Document XMLDoc, Node node, Map input) {
		try {
			Iterator it = input.keySet().iterator();
			while (it.hasNext()) {
				String NodeName = (String) it.next();
				Element el = XMLDoc.createElement(NodeName.trim());
				if (input.get(NodeName) instanceof Map) {
					Map child = (Map) input.get(NodeName);
					MapToNode(XMLDoc, el, child);
				} else if (input.get(NodeName) instanceof ArrayList) {
					ArrayList child = (ArrayList) input.get(NodeName);
					ListToNode(XMLDoc, el, child);
				} else if (input.get(NodeName) instanceof String) {
					Text text = XMLDoc.createTextNode((String) input
							.get(NodeName));
					el.appendChild(text);
				}
				node.appendChild(el);
			}
		} catch (Exception e) {
			logger.error("MapToNode", e);
		}
	}

	private static void ListToNode(Document XMLDoc, Node node, List input) {
		try {
			Iterator it = input.iterator();
			while (it.hasNext()) {
				Object obj = it.next();

				if (obj instanceof Map) {
					Map child = (Map) obj;
					MapToNode(XMLDoc, node, child);
				}
			}
		} catch (Exception e) {
			logger.error("ListToNode", e);
		}
	}

}
