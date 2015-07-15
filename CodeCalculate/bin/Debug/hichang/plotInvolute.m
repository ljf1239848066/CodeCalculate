t=0:0.1:7;
a=5./(cos(t));b=tan(t)-t;
x1=5*cos(t);y1=5*sin(t);
x=b.*cos(a);y=b.*sin(a);
plot(x1,y1)
hold on;
plot(x,y)