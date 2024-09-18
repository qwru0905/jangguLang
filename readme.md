# 장구 프로그래밍 언어

~~qwru0905가 해볼까 하고 YJ 가 아이디어 내고 다시 같이 만든 시작부터 이상한 언어입니다.~~

## ❓ JangguLang에 대해
YJ의 [헤으응](https://github.com/jiwootv/heeueung_lang) 언어를 보고 qwru0905 가 본인도 이런 ~~괴상한~~ 언어를 만들고 싶다고 만들기 시작한 언어입니다.

## 사용법
- `!` 는 +1을 의미합니다.
- `~` 는 -1을 의미합니다.

1. **아나리**
 - 입력받은 인덱스를 리셋합니다.
 - `아나리 (첫 번째 인덱스) ~ (마지막 인덱스)` 형태로 사용하여 범위를 지정할 수  있고, 범위의 값을 리셋합니다.
 - 예: `아나리 !!~!!!!`는 2부터 4까지의 인덱스를 리셋합니다.

2. **쿵**
 - 스택의 0번째 값을 뒤에 오는 수만큼 설정합니다.
 - 예: `쿵!!!!` 은 0번째 스택의 값을 4로 설정합니다.

3. **덩**
 - 0번째 스택의 값을 지정된 인덱스에 추가합니다.
 - 예: `덩!!` 은 0번째 스택의 값을 2번 인덱스에 추가합니다.

4. **덕**
 - 지정된 인덱스의 값을 출력합니다.
 - 예: `덕!!` 은 2번 인덱스의 값을 출력합니다.

5. **기덕**
 - 지정된 인덱스의 값을 0번째 스택으로 가져옵니다.
 - 예: `기덕!!` 은 2번 인덱스의 값을 0번째 스택에 저장합니다.

6. **더러러러**
 - 숫자 또는 문자를 입력받아 지정된 인덱스에 저장합니다.
 - 범위를 지정해 여러 인덱스에 걸쳐 입력받을 수 있습니다.
 - 예: `더러러러 !!~!!!!!!` -> 2번부터 6번 인덱스에 입력받은 값을 저장합니다.
 - asdfasdf를 적었다 하면 \[0, 0, a, s, d, f, a, s, 0, 0, ...\] 형식으로 저장됩니다.

7. **얼씨구**
 - 문자를 나타내는 명령어로, 쿵 명령어와 함께 사용해 0번 스택의 값을 해당 문자로 대체
 - 예: `쿵 얼씨구 좋!!!!!!! !!다` 는 0번 스택의 값이 'H'로 대체됨니다 (아스키 코드 72).

8. **좋다**
 - 숫자를 보다 편하게 입력 할 수 있는 수단입니다.
 - '좋'과 '다' 사이에 !과 공백을 넣으면 됩니다.
 - 예: `좋!!! ~ !! ~ ~다` 는 30200을 반환합니다. (~은 0을 의미합니다.)

9. **얼쑤**
 - 반복문의 시작을 의미합니다. 지정된 인덱스의 값이 0이 아니면 반복문을 시작합니다.
 - 예: `얼쑤!!` 는 2번 인덱스의 값이 0이 아니면 반복문 시작합니다.

10. **잘한다**
 - 반복문의 끝을 의미합니다. 이 지점에서 반복문의 시작으로 돌아가서 반복 조건을 다시 확인합니다.
 - 예: `잘한다` 는 반복문 종료 및 반복문 시작으로 돌아갑니다.

11. **에헤라디야**
 - 반복문 탈출 명령어로, 반복문 내부에서 이 명령어를 만나면 반복문을 즉시 빠져나갑니다. (잘한다 밑으로 갑니다.)
 - 예: `에헤라디야` 는 반복문 탈출을 의미합니다.

12. **//**
 - 주석입니다.

## 제작자
qwru0905 - 장구 언어 만들자 한 ~~원흉~~ 사람

YJ ( Jiwootv ) - 개발 방향에 대해 알려줌
