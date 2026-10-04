<?php
include("myclass/csdl.php");
$p=new csdlmdt();
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <?php
    $p->xuatdulieu("select * from congty order by tencty asc");
    ?>
    
</body>
</html>