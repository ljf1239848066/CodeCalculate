function love
close all;clear all;
name='love';
love_fig=figure(...
	'name',name,...
	'color',[1 1 1],...
	'Visible','off',...
	'units','normalized',...
	'menubar','none',...
	'Numbertitle','off');
f=@(x,y,z)(x.^2+(9/4)*y.^2+z.^2-1).^3-x.^2.*z.^3-(9/80)*y.^2.*z.^3;
love_value=implicitsurf(f,[-1.5 1.5],[-.8 .8],[-1.5 1.5],52.1);
set(love_value,'AmbientStrength',.5);
set(love_fig,'Visible','on');
rotate3d on;
axis off;
view(20,20);
for i=1:1
	pause(0.5);
	shading faceted;	%��ÿ������Ƭ����߶ȶ�Ӧ����ɫ������ɫ�����������Ա����ţ�����ɫ�Ǻ�ɫ������ϵͳ��ȱʡ��ɫ��ʽ��
	title('I');set(love_fig,'name','I');
	pause(0.5);
	shading flat;		%��ÿ������Ƭ��ͬһ����ɫ������ɫ����������Ҳ����Ӧ����ɫ���Ӷ�ʹ��ͼ�α����Եø��ӹ⻬��
	title('I Love');set(love_fig,'name','I Love');
	pause(0.5);
	shading interp;		%������Ƭ�ڲ�����ɫ��ֵ�����ó��ı���ͼ�Ե���⻬��
	title('I Love you!');set(love_fig,'name','I Love You!');
end

%%
function h=implicitsurf(f,xlimit,ylimit,zlimit,gd)
if nargin==2
    ylimit=xlimit;zlimit=xlimit;gd=25;
elseif nargin==3
    gd=ylimit;ylimit=xlimit;zlimit=xlimit;
elseif nargin==4
    gd=25;
elseif nargin==5
else
    error('Error in input arguments')
end
x=linspace(xlimit(1),xlimit(2),gd);
y=linspace(ylimit(1),ylimit(2),gd);
z=linspace(zlimit(1),zlimit(2),gd); 
[x,y,z]=meshgrid(x,y,z);val=f(x,y,z);
[f,v]=isosurface(x,y,z,val,0);
if isempty(f)
    warning('There is no graph in the range.');
    p=[];
else
    newplot;
    p=patch('Faces',f,'Vertices',v,'CData',v(:,3),'facecolor','flat','EdgeColor','k');
    isonormals(x,y,z,val,p);view(3);grid on
end
if nargout==0
else
    h=p;
end
