REM "USAGE: java -jar xxxSpider.jar URL=http://www.cnblogs.com/taichu/ LocalBackupFolder=H:/博客备份/博客园-www.cnblogs.com/taichu/ \n"
REM		"arg[URL] is first url feed to spider,"
REM		"arg[LocalBackupFolder] is used as local backup folder name."

REM 确保在运行目录中“java -version”可以运行，则JDK安装及路径正确；
REM 确保URL格式正确，LocalBackupFolder路径正确(JAVA程序要求路径在WIN系统下为\\或/)
REM 程序只能接受2个参数，多了少了都错，参数等号两边不能有空格，参数之间一般最好1个空格；
REM 确保log4j.properties配置在同目录，则会自动生成MySpider.log；
REM 因为win默认是GBK模式，也就是说在CMD命令行运行的控制台输出是GBK的有乱码；不影响程序运行时全部用utf-8；
REM 重要！！！目前只能爬取cnblogs（博客园）的网站！不支持其他网站！！！

REM 如下用绝对路径来启动爬虫
REM java -cp .;MySpider_0.0.1-SNAPSHOT_JDK1.8.jar MySpider.spider.CnblogsSpider URL=http://www.cnblogs.com/taichu/ LocalBackupFolder=J:/360YUNPAN/360SYNC/我的程序/私人博客/博客园备份/博客园-www.cnblogs.com/taichu/

REM 如下用相对路径来启动爬虫（有利于将程序和数据都放在云盘上，因为云盘对在本地绝对路径不同，但相对路径不变！）
java -cp .;MySpider_0.0.1-SNAPSHOT_JDK1.8.jar MySpider.spider.CnblogsSpider URL=http://www.cnblogs.com/taichu/ LocalBackupFolder=../../../../私人博客/博客园备份/博客园-www.cnblogs.com/taichu/

REM 如下启动不了（TODO：待调查）
REM java -jar MySpider_0.0.1-SNAPSHOT_JDK1.8.jar URL=http://www.cnblogs.com/taichu/ LocalBackupFolder=H:/博客备份/博客园-www.cnblogs.com/taichu/
