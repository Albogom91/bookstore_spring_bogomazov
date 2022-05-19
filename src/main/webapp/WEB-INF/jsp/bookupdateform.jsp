<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
    <title>Book Form</title>
    <link rel="stylesheet" href="../../css/style.css">
</head>
<body>
<table>
     <form action="update" method="POST">
        <tr>
            <td>ISBN</td>
            <td><input name="isbn"/></td>
        </tr>
        <tr>
            <td>Title</td>
            <td><input name="title"/></td>
        </tr>
        <tr>
            <td>Author</td>
            <td><input name="author"/></td>
        </tr>
        <tr>
            <td>Cover</td>
            <td><input type="radio" name="coverDto" value="SOFT" checked/>Soft
                <input type="radio" name="coverDto" value="HARD"/>Hard
                <input type="radio" name="coverDto" value="SPECIAL"/>Special</td>
        </tr>
        <tr>
            <td>Price</td>
            <td><input name="price"/></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="Submit" /></td>
        </tr>
        </form>
    </table>
</body>
</html>