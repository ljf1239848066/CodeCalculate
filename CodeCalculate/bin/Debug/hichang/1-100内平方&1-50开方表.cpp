#include <iostream>
#include<cmath>
#include<iomanip>
using namespace std;
int i,count1,count2;
float n;
int main()
{for(i=1;i<=100;i++){
	cout<<setw(3)<<i<<setw(6)<<i*i<<'\t';
	count1++;
if(count1%5==0)cout<<endl;
}
for(i=1;i<=50;i++){
	cout<<setw(3)<<i<<setw(10)<<sqrt(i)<<"\t";
	count2++;
if(count2%4==0)cout<<endl;}
    return 0;
}