시나리오
========

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

해설
====

터미널 실행
-----------

TODO
* 입출력 디바이스(키보드와 모니터)와 어떻게 연결되는가?
[scenario2.md]의 터미널 / 사용자 입력 처리 부분 확인해보기

gcc 실행
---------

TODO
[scenario2.md]의 curl 실행부분 확인해보기

컴파일 및 링크
--------------

TODO
