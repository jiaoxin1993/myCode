package arithmetic;

/*
 * 冒泡排序加优版
 */
public class Sort {
	public static String[] sort(String sz){
		sz = "6,8,9,2,3,7,5,4,1,15,46,39,12";
		String[] s = sz.split(",");
		int k = 0;
		String temp;
		int a1;
		int a2;
		for (int i = 0; i < s.length; i++) {
			for (int j = k+1; j < s.length; j++) {
				a1 = Integer.parseInt(s[k]);
				a2 = Integer.parseInt(s[j]);
				if(a1>a2){
					temp = s[k];
					s[k] = s[j];
					s[j] = temp;
				}
			}
			k++;
		}
		return s;
	}
	/*
	 * 折半查找
	 */
	public static int binarySearch(String[] ss,String s){
		//如果ss数组为空 返回-1
		if(ss.length==0){
			return -1;
		}
		//设置起始位置和结束位置  （相当于卡一个范围）
		int startPos = 0 ;
		int endPos = ss.length-1;//当前为默认值 ，随着每次折半查找会动态改变
		int m = (startPos+endPos)/2;//默认第一次查找的下标
		int a1,a2;
		while(startPos<=endPos){
			a1 = Integer.parseInt(s);
			a2 = Integer.parseInt(ss[m]);
			if(a1==a2){
				return m; //找到相等返回下标
			}
			if(a1>a2){
				startPos = m+1;
			}
			if(a1<a2){
				endPos = m-1;
			}
			m = (startPos+endPos)/2;
		}
		return -1;
	}
	public static void main(String[] args) {
		String sz = "6,8,9,2,3,7,5,4,1,15,46,39,12";
		String[] s =  sort(sz);
		for (int i = 0; i < s.length; i++) {
			System.out.print(s[i]);
		}
		int index = binarySearch(s,"7");
		System.out.println(index);

	}
}
