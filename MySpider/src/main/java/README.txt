（1）博客爬虫说明：
1.基于webmagic开源爬虫库开发（景点教科书式的爬虫开源项目，国人维护，在github和oschina上课查）；
2.自行针对博客园（cnblogs）的个人主页index page（1或n个分页）及每篇文章页两层的links和xpath等规则做了抓取；
3.将所有主页和文章页都保持，文章页用文章名做文件名，类似“文章名.html”；
4.程序带log，全部是utf-8，配置，参数及运行详见bat内说明；
5.自动运行：一个方案，在云盘的某个工作目录运行bat，并制定备份目录，且用bat中的相对路径格式，这样对于一个人有多台PC的多个云盘同步，都能自动运行备份并互相同步保持最新；
            在本地WIN中的“计划任务”执行bat，每日同步博客园内容。
6.后续开发和需求等，详见项目release_notes.txt

（2）博客备份步骤：
1. 用“爬虫”获取html的page，详见“RunConblogsSpider.bat”，可以将它设定为计划任务“step1-XXX”；
	（特别说明：jar包是带版本的，最新是0.0.2，以后升级了需要跟着修改比如0.0.X！！！）

2. 用batch脚本“RunGenIndex.bat”将爬取到的html生成index页，可以设定为计划任务“step2-XXX”；
	（特别说明：此bat文件要放置在html所在的目录！！！）

3. 用batch脚本“RunBackup2Rar.bat”将html连同index页都打包为rar文件，并用时间戳做区分；
	（特别说明：此bat文件和所需打包的folder放在同一层次！！！
	另外，请确保WINRAR有效，或者比如将命令行版本的rar“Rar.exe”
	（安装WINRAR后，在其主目录可找到命令行版本的exe目录）和“RunBackup2Rar.bat”放在同一个目录）；