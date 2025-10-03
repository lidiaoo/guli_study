const url = $request.url;
const method = $request.method;
const notifyTitle = "dxy-json";
console.log(`dxy.com json`);
console.log(`测试1`);
if (!$response.body) {
    // 有undefined的情况
    console.log(`$response.body为undefined:${url}`);
    $done({});
}
console.log(`测试2`);
if (method !== "GET") {
    $notification.post(notifyTitle, "method错误:", method);
    console.log(notifyTitle, "method错误:", method);
}
console.log(`测试3`);
let body = JSON.parse($response.body);
console.log(notifyTitle, "注入成功 dxy.com");
if (!body.data) {
    console.log(`测试4`);
    console.log(url);
    console.log(`body:${$response.body}`);
    console.log(notifyTitle, url, "data字段错误");
    $notification.post(notifyTitle, url, "data字段错误");
} else { 
    console.log(`测试5`);
    console.log(notifyTitle, "注入成功 修改body dxy.com");
    $notification.post(notifyTitle, "注入成功 修改body dxy.com");
    body = $response.body.replace(/\"status\":9/, "\"status\":0").replace(/\"expire_timestamp\":0/, "\"expire_timestamp\":4102415999000");
    $done({ body });
}

body = JSON.stringify(body);
$done({
    body
});
