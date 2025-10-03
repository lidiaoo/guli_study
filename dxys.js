/*
丁香医生vip
*************************** 
[rewrite_local]
^https:\//dxy.com\/* url script-response-body https://raw.githubusercontent.com/lidiaoo/guli_study/refs/heads/master/dxys.js

[mitm]
hostname = dxy.com
**************************/ 

 
// 直接返回空字符串作为响应体
$done({ body: "" });
