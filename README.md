목적
----

컴퓨터가 어떻게 동작하는지에 대해 전 영역에 걸쳐 깊이 이해한다.

목표
----

다음의 시나리오 1. 전 과정에 대해 2. 저수준에서 일어나는 일들까지 정확히 설명할 수 있어야 한다.

1. 전 과정: 명령을 입력하기 위해 키보드를 두드리기 시작한 순간부터, 명령의 수행이 완료되어 그 결과가 화면에 출력 완료된 시점까지
2. 저수준: 네트워크라면 Ethernet 까지, 컴퓨터라면 CPU가 instruction 을 수행하는 수준까지

### 시나리오1 - 컴파일

1. 맥북에어 노트북에서 터미널을 실행
2. hello 프로그램을 작성하고 컴파일

    $ echo '#include <stdio.h>
    int main(int argc, char** argv) {
        printf("hello, world");
        return 0;
    }' > main.c
    $ gcc main.c -o hello

3. hello 프로그램을 실행하면 `hello, world` 가 출력됨

    $ ./hello
    hello, world

### 시나리오2 - HTTP 통신

1. 맥북에어 노트북에서 무선으로 인터넷을 연결
2. 터미널을 열고 아래의 명령을 키보드로 입력하여 실행

    $ curl http://npcode.com/test/README

3. 아래와 같이 출력됨

    hello, world

See [scenario2.md](scenario2.md)
