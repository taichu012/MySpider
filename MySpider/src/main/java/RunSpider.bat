REM "USAGE: java -jar xxxSpider.jar URL=http://www.cnblogs.com/taichu/ LocalBackupFolder=H:/���ͱ���/����԰-www.cnblogs.com/taichu/ \n"
REM		"arg[URL] is first url feed to spider,"
REM		"arg[LocalBackupFolder] is used as local backup folder name."

REM ȷ��������Ŀ¼�С�java -version���������У���JDK��װ��·����ȷ��
REM ȷ��URL��ʽ��ȷ��LocalBackupFolder·����ȷ(JAVA����Ҫ��·����WINϵͳ��Ϊ\\��/)
REM ����ֻ�ܽ���2���������������˶��������Ⱥ����߲����пո񣬲���֮��һ�����1���ո�
REM ȷ��log4j.properties������ͬĿ¼������Զ�����MySpider.log��
REM ��ΪwinĬ����GBKģʽ��Ҳ����˵��CMD���������еĿ���̨�����GBK�������룻��Ӱ���������ʱȫ����utf-8��

REM java -jar MySpider_0.0.1-SNAPSHOT_JDK1.8.jar URL=http://www.cnblogs.com/taichu/ LocalBackupFolder=H:/���ͱ���/����԰-www.cnblogs.com/taichu/
java -cp .;MySpider_0.0.1-SNAPSHOT_JDK1.8.jar MySpider.spider.CnblogsSpider URL=http://www.cnblogs.com/taichu/ LocalBackupFolder=H:/���ͱ���/����԰-www.cnblogs.com/taichu/