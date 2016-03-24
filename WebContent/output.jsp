<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Hello World</title>
        <style>.error { color: red; } .success { color: green; }</style>
    </head>
    <body>
        <form action="HelloServlet" method="post">
            <h1>Hello</h1>
            <p>
                <label for="sku">Enter the sku you wish to check</label>
                <input id="sku" name="sku" value="${param.sku}">
                <span class="error">${messages.sku}</span>
            </p>
            <p>
                <input type="submit">
                <span class="success">${messages.success}</span>
            </p>
        </form>
    </body>
</html>