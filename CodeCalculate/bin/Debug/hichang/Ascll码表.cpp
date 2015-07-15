#include<iostream>
#include<fstream>
#include<iomanip>
using namespace std;
int main()
{
	ofstream file;
	file.open("AscllÂë±í.txt");
    for(int i=32;i<127;i++)
    {
    	//cout<<i<<setw(2)<<(char)i<<'\t';
    	file<<i<<setw(2)<<(char)i<<'\t';
    	if((i-1)%6==0){
    		//cout<<endl;
    		file<<endl;
    	}
    }
    return 0;
}
