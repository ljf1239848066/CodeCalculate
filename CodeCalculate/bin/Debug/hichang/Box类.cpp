// Note:Your choice is C++ IDE
#include <iostream>
using namespace std;
class Box{
	int length,wideth,height;
public:
	void RBox(int,int,int);
	void GetV();
	void GetS();
};
void Box::RBox(int l,int w,int h){//不能用Box，否则为构造函数，下面不能用‘.’来操作
	length=l;wideth=w;height=h;
}
void Box::GetV(){
	int V;
	V=length*wideth*height;
	cout<<"体积  V="<<V<<endl;
}
void Box::GetS(){
	int S;
	S=2*(length*(wideth+height)+wideth*height);
	cout<<"表面积S="<<S<<endl;
}
void main(){
	int l,w,h;
	Box box;
	cout<<"请输入立方体的长宽高:"<<endl;
	cin>>l>>w>>h;
	box.RBox(l,w,h);
	box.GetV();
	box.GetS();
}