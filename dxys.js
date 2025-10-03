const url = $request.url;
const method = $request.method;
const notifyTitle = "dxy-json";
console.log(`dxy.com json`);
if (!$response.body) {
    // 有undefined的情况
    console.log(`$response.body为undefined:${url}`);
    $done({});
}
if (method !== "GET") {
    $notification.post(notifyTitle, "method错误:", method);
}
let body = JSON.parse($response.body);


if (!body.data) {
    console.log(url);
    console.log(`body:${$response.body}`);
    $notification.post(notifyTitle, url, "data字段错误");
} else { 
    $notification.post(notifyTitle, "注入成功 dxy.com");
}

body = JSON.stringify(body);
$done({
    body
});
