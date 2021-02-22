//get IP of win localhost
grep -m 1 nameserver /etc/resolv.conf | awk '{print $2}'

//wrk command
wrk -t1 -c10 -d30s --latency -s scripts/otus.lua http://172.28.112.1



https://www.poppastring.com/blog/wrk-for-benchmarking-and-testing
https://superuser.com/questions/1535269/how-to-connect-wsl-to-a-windows-localhost
https://opensource.com/article/19/3/getting-started-vim