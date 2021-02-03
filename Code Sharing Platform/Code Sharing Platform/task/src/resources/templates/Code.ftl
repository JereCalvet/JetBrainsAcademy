<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Code</title>
    <link rel="stylesheet" href="/css/newStyle.css" type="text/css">
    <link rel="stylesheet"
              href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.5.0/build/styles/default.min.css">
        <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.5.0/build/highlight.min.js"></script>
        <script>hljs.initHighlightingOnLoad();</script>
</head>
<body>
    <p><span id="load_date">${codeSnippet.date}</span></p>
    <#if codeSnippet.viewRestricted == true>
        <p><span id="views_restriction"><u>${codeSnippet.views}</u> more views allowed</span></p>
    </#if>
    <#if codeSnippet.timeRestricted == true>
        <p><span id="time_restriction">The code will be available for <u>${codeSnippet.time}</u> seconds</span></p>
    </#if>
    <pre id="code_snippet"><code class="java">${codeSnippet.code}</code></pre>
</body>
</html>