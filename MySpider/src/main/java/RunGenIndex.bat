@echo off
setlocal
set LISTFILE=IndexALL.html
echo MAKING LISTFILE �� (PLEASE WAIT)
rem �����������ҳ��ͷ��������^���ű�ʾ��һ���ַ�û�����⺬�壬����ͨ�ַ�������
echo ^<head^>^<style type=text/css^>A:link{FONT-SIZE:9pt;COLOR:#000000;TEXT-DECORATION: none} > %LISTFILE%
echo A:visited{FONT-SIZE:9pt;COLOR:#000000;TEXT-DECORATION:none}A:hover{COLOR:#ff0000; >> %LISTFILE%
echo TEXT-DECORATION: underline}BODY{FONT-SIZE:9pt}TABLE{FONT-SIZE:9pt}^</style^>^</head^> >> %LISTFILE%
echo [INDEX OF ALL HTML]^<br^> >> %LISTFILE%
echo [��ǰ·��=%~dp0]^<br^> >> %LISTFILE%
echo ^<br^> >> %LISTFILE%
rem ����������Ҫ�Ĳ��֣���ʵ����ʹ��dir��������򵥵���ʽ�г���ǰĿ¼�£�������Ŀ¼�������е��ļ�
rem Ȼ��ʹ��for������ת��Ϊ�����ӵ���ʽ������Ȥ������һ�����������������ʲô���Ľ����"dir *.* /a:-d/s/b"
for /f "tokens=* delims=" %%i in ('dir *.html /a:-s/b/on/tc') do (
	echo ^<a href="%%i" target=_blank^>%%i^</a^>^<br^> >> %LISTFILE%
)
echo ^<br^> >> %LISTFILE%
echo [This index page is generated by %~nx0 at %~t0 ]^<br^> >> %LISTFILE%
start .\%LISTFILE%