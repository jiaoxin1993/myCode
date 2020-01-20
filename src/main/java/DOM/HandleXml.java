package DOM;

import org.dom4j.*;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

public class HandleXml {
	public static void main(String[] args) throws DocumentException, IOException {

		//read("src/spring-mvc.xml");
		write("spring-mvc.xml");
		//read("src/spring-mvc.xml");
	}

	private static void read(String fileName) throws DocumentException{
		// 创建SAXReader对象 ，用于读取XML文件
		SAXReader reader = new SAXReader();
		//读取xml文件返回 Document对象  "src/spring-mvc.xml"
		Document doc =  reader.read(new File(fileName));
		// 获取根元素
		Element root = doc.getRootElement();
		//获取根元素下所有子元素
		Iterator<?> its = root.elementIterator(); // 获取迭代器
		while(its.hasNext()){
			//取出元素
			Element element = (Element)its.next();
			//获取属性
			Attribute attribute = element.attribute("属性名");
			//获取子元素
			Element ele = element.element("子元素属性名");
			//获取元素内容
			String text = ele.getText();
		}

		System.out.println(doc.getRootElement().getName());
	}

	private static void write(String fileName) throws IOException{
		//通过DocumentHelper生成一个Document对象
		Document document = DocumentHelper.createDocument();
		//添加并得到根元素
		Element root =  document.addElement("root");
		//为元素添加属性
		root.addAttribute("id", "1110");
		//为元素添加文本
		root.addText("的撒发射点");
		//doc输入到xml文件中
		Writer writer =  new FileWriter(new File(fileName));
		document.write(writer);

		writer.close();
	}
}
