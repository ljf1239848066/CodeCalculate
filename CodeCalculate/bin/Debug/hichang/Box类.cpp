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
void Box::RBox(int l,int w,int h){//������Box������Ϊ���캯�������治���á�.��������
	length=l;wideth=w;height=h;
}
void Box::GetV(){
	int V;
	V=length*wideth*height;
	cout<<"���  V="<<V<<endl;
}
void Box::GetS(){
	int S;
	S=2*(length*(wideth+height)+wideth*height);
	cout<<"�����S="<<S<<endl;
}
void main(){
	int l,w,h;
	Box box;
	cout<<"������������ĳ����:"<<endl;
	cin>>l>>w>>h;
	box.RBox(l,w,h);
	box.GetV();
	box.GetS();
}