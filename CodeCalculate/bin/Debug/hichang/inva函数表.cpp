// Note:Your choice is C++ IDE
#include<iostream>
#include<fstream>
#include<iomanip>
#include<cmath>
using namespace std;
int main()
{
	ofstream file;
	file.open("invaº¯Êý±í.txt");
    for(double i=0.001;i<90;i+=0.001)
    {
    	double x=(i/180)*3.141592654;
        file<<setw(4)<<i<<setw(16)<<":inv(i)=tanx-x="<<setw(8)<<tan(x)-x<<"	";
        if(int(i*1000)%3==0)file<<endl;    
    }
    return 0;
}