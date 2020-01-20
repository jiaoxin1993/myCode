package javaSE.regExp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/*
 * 正则表达式   https://www.csdn.net/  
 */
public class RegExp {
	public static void main(String[] args) {
		String str = "";
		try {
			str = urlTodata("https://news.163.com/");
		} catch (Exception e) {
			// TODO Auto-generated catch block <a\b[^>]+\bhref="([^"]*)"[^>]*>([\s\S]*?)</a>
			e.printStackTrace();
		}
		Pattern pattern = Pattern.compile("<a\\b[^>]+\\bhref=\"([^\"]*)\"[^>]*>([\\s\\S]*?)</a>");
		//Pattern pattern = Pattern.compile("<a((\\s|\\S)*?)</a>");
		Matcher m = pattern.matcher(str);
//		m.find();
//		m.group();
		while(m.find()){
			System.out.println(m.group(1));
			System.out.println(m.group(2));
		}

	}
	private static String urlTodata(String urlStr) throws Exception{
		URL url = new URL(urlStr);
		BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(),"gbk"));
		StringBuilder sb = new StringBuilder();

		String str = br.readLine();
		while((str = br.readLine())!=null && str.length()>0){
			sb.append(str);
		}
		return sb.toString();
	}
}
