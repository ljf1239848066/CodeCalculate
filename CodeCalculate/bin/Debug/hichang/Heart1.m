clear all
clc
[x,y,z]=meshgrid(linspace(-1.3,1.3));
f=(x.^2+(9/4)*y.^2+z.^2-1).^3-x.^2.*z.^3-(9/80)*y.^2.*z.^3;
p=patch(isosurface(x,y,z,f,0));
set(p,'FaceColor','red','EdgeColor','none');
daspect([1 1 1])
view(3)
camlight; 
lighting phong
axis off