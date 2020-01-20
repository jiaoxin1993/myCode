package javaSE.paidui;

public class QuanKid {
	public static void main(String[] args) {
		int con =0;
		QuanKid q = new QuanKid(500);
		//获得第一个小孩
		Kid k = q.first;
		while(q.count>1){
			con++;
			if(con==3){
				con=0;
				q.delete(k);
			}
			k = k.right;//获得下一个小孩
		}
		System.out.println(k.id);
	}
	int count = 0;//圈由几人围成
	Kid first,last;
	public QuanKid(int count){
		for (int i = 0; i < count; i++) {
			add();
		}
	}
	void add(){
		Kid k = new Kid();
		k.id = count;
		if(count <= 0){
			//只有一个人的圈
			first = k;
			last = k;
			k.left = k;
			k.right = k;
		}else{
			/*
			 * 当存在两个及两个以上的小孩时
			 * 新增的小孩成为最后一个，并于第一个小孩相连
			 */
			last.right = k; //之前last右边 连接新增
			k.left = last;	//新增左边 连接 之前的last
			k.right = first; //新增右边连接第一个小孩
			first.left = k;	//第一个小孩左边连接新增小孩	
			last = k; // 新增小孩变成最后一个小孩
		}
		count++;
	}

	void delete(Kid k){
		if(count<=0){
			System.out.println("没有人了");
			return;
		}else{
			//删除该小孩 把之前之后的小孩关联  若当前小孩是第一个或者最后一个则赋值给其他小孩 
			k.left.right = k.right;
			k.right.left = k.left;
			if(k==first){
				first = k.right;
			}else if(k==last){
				last = k.left;
			}
		}
		count--;
	}


}
