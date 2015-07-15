// 打印一个英文字母方片
#include <iostream>
#include<iomanip>
#include<cmath> 
using namespace std;
int i,j,k;
int n;
int main()
{
	cout<<"Please input the length of a side of the rhombus!(less than 20)";
	cin>>n;
    for(j=0;j<2*n+1;j++)
    {
       for(i=0;i<2*n+2;i++)
       {
       	if((i>=fabs(n-j)&&i<=2*n-fabs(n-j))||(i<=fabs(n-j)&&i>=2*n-fabs(n-j)))
       	cout<<"* ";
       	else cout<<"  ";
       }
       cout<<endl;     
    }
    return 0;
}