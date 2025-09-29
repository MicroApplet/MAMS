<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no"/>
    <title>提示</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: -apple-system-font, "Helvetica Neue", "PingFang SC", "Microsoft YaHei", sans-serif;
            background-color: #f5f5f5;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            padding: 20px;
        }

        .error-box {
            background: #fff;
            border-radius: 12px;
            padding: 40px 30px;
            text-align: center;
            width: 100%;
            max-width: 300px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }

        .error-icon {
            width: 60px;
            height: 60px;
            margin: 0 auto 20px;
            background: #fa5151;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            color: #fff;
            font-size: 28px;
            font-weight: bold;
        }

        .error-code {
            font-size: 18px;
            color: #333;
            margin-bottom: 10px;
            font-weight: 600;
        }

        .error-desc {
            font-size: 15px;
            color: #666;
            margin-bottom: 30px;
            line-height: 1.5;
        }

        .close-btn {
            width: 100%;
            padding: 12px;
            font-size: 16px;
            color: #fff;
            background-color: #07c160;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            -webkit-tap-highlight-color: transparent;
        }

        .close-btn:active {
            background-color: #06ae56;
        }
    </style>
</head>
<body>
<div class="error-box">
    <div class="error-icon">!</div>
    <div class="error-code">错误代码：${code!'UNKNOWN'}</div>
    <div class="error-desc">错误描述：${desc!'发生未知错误'}</div>
    <button class="close-btn" onclick="closePage()">关闭页面</button>
</div>

<script>
    function closePage(){
        if(typeof WeixinJSBridge!=='undefined'){
            WeixinJSBridge.invoke('closeWindow');
        }else{
            window.close();
            window.history.back();
            window.location.href='about:blank';
        }
    }
</script>
</body>
</html>