시나리오
========

1. 맥북에어 노트북에서 무선으로 인터넷을 연결
2. 터미널을 열고 아래의 명령을 키보드로 입력하여 실행

    $ curl http://npcode.com/test/README

3. 아래와 같이 출력되는 것을 눈으로 확인

    hello, world

해설
====

무선 인터넷 연결
----------------

TODO

터미널 실행
-----------

TODO
* 입출력 디바이스(키보드와 모니터)와 어떻게 연결되는가?

사용자 입력 처리
----------------

터미널은 다음과 같은 사용자의 키보드 임력을 처리한다.

    curl http://npcode.com/test/README

device(키보드?)는 사용자가 키보드의 키를 한번 누를 때 마다 tty-in 의 put 프로시져를 실행하여 입력한 글자를 메시지로 전송한다. tty-in의 put 프로시져는 들어온 메시지(글자 한개)를 tty-in queue에 보관하고 동시에 화면에 출력(echo)한다. 만약 메시지가 new-line 혹은 캐리지 리턴이라면 queue을 enable 한다.

queue가 enable되면 tty-in의 service 프로시져가 실행된다. service 프로시져는 queue에서 엔터키까지 모든 블럭을 읽어들인 뒤 queue에서 삭제한다. 그 후 읽어들인 블럭을 다음 queue(쉘의 입력)의 put 프로시져를 실행해 메시지로 전송한다. 그런 뒤 DELIM 메시지도 다음 queue에 전송한다.

TODO
* 키보드의 입력은 어떻게 stream 을 위한 메시지로 변환되는가?

curl 실행
---------

    $ curl http://npcode.com/test/README

쉘은 위의 명령을 해석한다. 이 명령이 프로그램을 실행하는 것이므로, 우선 fork(2)로 프로세스를 포크한 뒤 exec 계열 함수를 통해 curl을 실행한다.

    execl('curl', 'http://npcode.com/test/README');

TODO
* 쉘의 입력 queue는 언제 명령을 해석하기 시작하는가? (DELIM 메시지가 들어왔을 때 해석을 시작할 것으로 추측한다)
* 쉘은 주어진 명령이 프로그램의 실행인지 어떻게 판단하는가? (그러한 판단은 반드시 필요한가?)
* execl은 어떻게 curl 프로그램을 실행하는가?
    * execl은 어떻게 실행할 프로그램의 절대경로를 찾는가? See Adavnced Progrmaming in Unix Environment
    * 디스크에서 어떻게 curl 프로그램을 읽어들이는가? 
    * 읽어들인 curl 프로그램을 어떻게 메모리에 적재하는가?
        * 이 때 필요한 메모리를 어떻게 할당하는가? (malloc을 쓰는가? best-fit, first-fit, quick-fit 중 어떤 전략인가?)
    * 메모리에 적제된 curl 프로그램을 어떻게 실행하는가?

HTTP 요청 발송
--------------

curl은 argument 를 분석하여 url을 알아낸다. 알아낸 url을 해석하여, scheme이 http, hostname이 npcode.com, path가 /test/README 임을 확인한다. 이 정보를 토대로 아래와 같은 내용의 HTTP 요청 메시지를 작성한 뒤 TCP 패킷으로 전송한다.

    GET /test/README HTTP/1.1
    User-Agent: curl/7.21.4 (universal-apple-darwin11.0) libcurl/7.21.4 OpenSSL/0.9.8r zlib/1.2.5
    Host: npcode.com
    Accept: */*

TODO
* TCP 헤더는 어떻게 작성되는가?
* IP 헤더는 어떻게 작성되는가?
    * source와 destination 의 ip
    * 주어진 domain name 에서 어떻게 ip를 알아내는가?
* Ethernet 헤더(link header)는 어떻게 작성되는가?
    * source와 destination 의 mac address

See
* HTTP: The Definitive Guide
* TCP/IP Illustrated Vol. 1
* RFC 2616
* RFC 793

패킷의 이동
-----------

패킷이 어떤 router에 도달했을 때, 해당 패킷을 어디로 보내야 할 것인지는 아래의 방법으로 결정한다.

routing table의 entry들 중 패킷의 destination과
1. 완전히 일치하는 entry가 있다면 그것을 선택
2. 없다면 네트웍 어드레스가 일치하는 ip를 선택
3. 아무것도 없다면 default entry를 선택

이렇게 해서 entry를 선택하고 해당 entry의 flag에,
* G가 있다면 link header의 destination address를 해당 entry의 gateway로 설정하고, ip header의 destination address는 패킷을 보내려는 address로 설정
* G가 없다면 link header의 destination address와 ip header의 destination address를 모두 패킷을 보내려는 address로 설정
    * AnswerMe G의 의미는?

See
* TCP/IP Illustrated Vol. 1
* RFC 793

HTTP 요청 수신 및 처리
---------------------

TODO

See
* TCP/IP Illustrated Vol. 1
* RFC 793

HTTP 응답 발송
--------------

Apache httpd 는 아래와 같은 내용의 HTTP 응답 메시지를 작성하여 TCP 패킷으로 전송한다.

    HTTP/1.1 200 OK
    Date: Wed, 11 Apr 2012 13:46:23 GMT
    Server: Apache/2.2.14 (Ubuntu)
    Last-Modified: Tue, 10 Apr 2012 18:53:28 GMT
    ETag: "10a0d8-d-4bd57a365ba00"
    Accept-Ranges: bytes
    Content-Length: 13
    Content-Type: text/plain

    hello, world

See
* HTTP: The Definitive Guide
* TCP/IP Illustrated Vol. 1
* RFC 2616
* RFC 793
  
HTTP 응답 수신 및 처리
----------------------

TODO
* curl 은 응답을 어떻게 tty-out으로 내보내는가?
