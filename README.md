목적
----

컴퓨터가 어떻게 동작하는지에 대해 전 영역에 걸쳐 깊이 이해한다.

목표
----

다음의 시나리오 1. 전 과정에 대해 2. 저수준에서 일어나는 일들까지 정확히 설명할 수 있어야 한다.

1. 전 과정: 명령을 입력하기 위해 키보드를 두드리기 시작한 순간부터, 명령의 수행이 완료되어 그 결과가 화면에 출력 완료된 시점까지
2. 저수준: 네트워크라면 Ethernet까지, 컴퓨터라면 CPU가 instruction을 수행하는 수준까지

### 시나리오1 - 컴파일

1. 맥북에어 노트북에서 터미널을 실행
2. hello 프로그램을 작성하고 컴파일

```sh
$ echo '#include <stdio.h>
int main(int argc, char** argv) {
    printf("hello, world");
    return 0;
}' > main.c
$ gcc main.c -o hello
```

3. hello 프로그램을 실행하면 `hello, world` 가 출력됨

```sh
$ ./hello
hello, world
```
See [scenario1.md](scenario1.md)

### 시나리오2 - HTTP 통신

1. 맥북에어 노트북에서 무선으로 인터넷을 연결
2. 터미널을 열고 아래의 명령을 키보드로 입력하여 실행

    $ curl http://npcode.com/test/README

3. 아래와 같이 출력됨

    hello, world

See [scenario2.md](scenario2.md)

### 시나리오3 - Shell 실행

1. 버추얼 박스에서 우분투 12.04를 콘솔 부팅 모드로 실행
2. 로그인 후 [MY_SHELL] 프롬프트가 나타나는지 확인
3. ls 시에 ls 명령 수행하여 해당 디렉토리의 파일이 나타나는지 확인

See [scenario3.md](scenario3.md)
