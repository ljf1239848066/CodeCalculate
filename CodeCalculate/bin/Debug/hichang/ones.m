sys=tf(1,[1 1 3]);
x=0:0.01:20;y=step(sys,x);plot(x,y);
yt=y(t)*ones(1,length(x))