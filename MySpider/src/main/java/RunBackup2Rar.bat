REM 将当前目录所有内容（含子目录，递归） 压缩为一个带时间戳的rar包来备份 
set datetime=%date:~0,4%%date:~5,2%%date:~8,2%-%time:~0,2%%time:~3,2%%time:~6,2%
Rar.exe a -r 博客园blog备份-%datetime%.rar * 