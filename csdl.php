<?php
$localhost = "localhost";
$usermdt = "username";
$passtmdt = "password";
$tmdt_db = "demo2";
class csdlmdt{
    Private function connect(){
        $con = new mysqli("localhost","usermdt","passtmdt","tmdt_db");
        if($con->connect_error){
            echo 'Khong ket noi duoc csdl';
            exit();

        }
        else {
            mysqli_set_charset($con,"utf8");
            $con->query("set names 'utf8'");
            return $con;
        }
    }
    Public function xuatdulieu(){
        $link=$this->connect();
        $result=$link->query($sql);
        $num=$result->num_rows;
        $link->close();
        if($num>0){
            echo '<table>';
            echo '<tr>';
            echo '<td>STT</td>';
            echo '<td>Ten Cong Ty</td>';
            echo '<td>Dia Chi</td>';
            echo '<td>Dien thoai</td>';
            echo '</tr>';
            $i=1;
            while($row=mysqli_fetch_array($result)){
            echo '<tr>';
            echo '<td align="center">.$i.</td>';
            echo '<td align="left">.$row['tencty'].</td>';
            echo '<td align="left">.$row['diachi'].</td>';
            echo '<td align="left">.$row['dienthoai'].</td>';
            echo '</tr>';
            $i++;

            }
            echo '</table>';

        }
        else{
            echo 'dang cap nhat du lieu';
        }
    }
}
?>