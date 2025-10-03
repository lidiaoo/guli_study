/*
丁香医生vip
*************************** 
[rewrite_local]
^https:\//dxy.com\/app\/i\/user\/ask\/account url script-response-body https://raw.githubusercontent.com/lidiaoo/guli_study/refs/heads/master/dxys.js

[mitm]
hostname = dxy.com
**************************/  
var body = $response.body.replace(/\"status\":9/, "\"status\":0")
    .replace(/\"expire_timestamp\":0/, "\"expire_timestamp\":4102415999000");
$done({ body });
