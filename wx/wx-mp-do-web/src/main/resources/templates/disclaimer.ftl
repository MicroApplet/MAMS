<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>免责声明</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        body {
            font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, "Noto Sans", sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", "Noto Color Emoji";
            background-color: #f8f9fa;
            margin: 0;
            padding: 20px;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }

        .container {
            max-width: 600px;
            width: 100%;
            background: #ffffff;
            padding: 40px;
            border-radius: 12px;
            box-shadow: 0 6px 20px rgba(0, 0, 0, 0.08);
            text-align: center;
        }

        h1 {
            color: #212529;
            margin-bottom: 25px;
            font-size: 2em;
            font-weight: 600;
        }

        p {
            color: #495057;
            font-size: 1.1em;
            line-height: 1.7;
            margin-bottom: 35px;
        }

        .button {
            background-color: #007bff;
            color: #ffffff;
            border: none;
            padding: 14px 35px;
            font-size: 1.1em;
            border-radius: 8px;
            cursor: pointer;
            transition: background-color 0.3s ease, transform 0.2s ease;
            font-weight: 500;
            letter-spacing: 0.5px;
        }

        .button:hover {
            background-color: #0056b3;
            transform: translateY(-2px);
        }

        .button:active {
            transform: translateY(0);
        }
    </style>
</head>
<body>
<div class="container">
    <h1>免责声明</h1>
    <p>该服务由第三方提供</p>
    <p>相关服务和责任由该第三方承担</p>
    <p>如果有疑问请咨询该第三方公司客服</p>
    <button class="button" onclick="window.location.href='${targetUrl!"#"}'">知道了</button>
</div>
</body>
</html>