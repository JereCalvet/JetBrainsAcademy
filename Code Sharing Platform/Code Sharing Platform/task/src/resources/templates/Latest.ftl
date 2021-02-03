<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Latest</title>
    <link rel="stylesheet" href="../css/style.css" type="text/css">
    <link rel="stylesheet"
          href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.5.0/build/styles/default.min.css">
    <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.5.0/build/highlight.min.js"></script>
    <script>hljs.initHighlightingOnLoad();</script>
</head>
<body>
    <#list latestCodeSnippets as codeSnippet>
        <p><span id="load_date">${codeSnippet.date}</span></p>
        <pre id="code_snippet"><code class="java">${codeSnippet.code}</code></pre>
    </#list>
</body>
</html>