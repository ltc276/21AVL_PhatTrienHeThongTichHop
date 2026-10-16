LAB 04 - LAP TRINH SOCKET TCP VA UDP TRONG JAVA

Cau truc gom 3 phan:
01_ViDu_Muc_1-4: viet lai 4 vi du trong muc thuc hanh va file giai thich don gian.
02_BaiTap_TCP: 10 bai tap, moi bai co ban TCP (bai 1 la host/URI inspector, khong phu thuoc giao thuc).
03_BaiTap_UDP: 10 bai tap, moi bai co ban UDP (bai 1 la host/URI inspector, khong phu thuoc giao thuc).

Tat ca comment va thong bao trong code duoc viet tieng Viet khong dau. Cac lenh giao thuc nhu PING, DATE, MSG giu nguyen theo de.

CACH CHAY CHUNG
1. Cai JDK 17 tro len. Kiem tra: java --version va javac --version.
2. Mo terminal trong folder cua bai can chay.
3. Bien dich: javac *.java
4. Mo terminal thu nhat chay server: java Server
5. Mo terminal thu hai chay client: java Client localhost
6. Bai 1 khong can server/client; dung: java Main example.com "https://example.com:443/a?x=1#top"

LUU Y
- Dung server truoc, client sau. Moi bai dung cong rieng, nhung TCP va UDP o cac folder rieng co the dung cung so cong.
- Bai 6 can chay server va client tren cung may; client tu dong gui 1000 thong diep, lap 5 lan va in thoi gian/so phan hoi.
- Bai 8 TCP: chay `java Client <duong_dan_file> [host]`. UDP cung dung lenh nay. Server luu file vao thu muc uploads cua thu muc dang chay.
- Bai 8 UDP dung chunk co so thu tu va ACK; UDP khong bao dam giao hang, do do co retry va kiem tra SHA-256. Nen thu voi file nho truoc.
- Bai 9 TCP la thong bao broadcast toi cac client TCP dang ket noi; bai 9 UDP dung multicast 239.255.0.1. Multicast co the bi chan boi Wi-Fi/VPN/firewall.
- Bai 10 TCP: discovery bang TCP, sau do ket noi TCP service. Bai 10 UDP: discovery bang UDP, sau do ket noi TCP service.
- Bai 1 khong can TCP/UDP theo de bai; cung mot chuong trinh inspector duoc dat trong ca hai folder de duy tri quy uoc 10 bai moi folder.
- Cac file .class khong duoc dua vao file nop; hay bien dich tai may cua ban.
