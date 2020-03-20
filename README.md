* Read RSS<br>
https://medium.com/@limgyumin
https://developer.android.com/training/basics/network-ops/xml#choose
https://recipes4dev.tistory.com/134
RSS를 읽는 것은 XML을 읽는 것이다.<br>
XML을 읽는 것은 DOM, SAX 2가지 방식이 있다. 안드로이드에서 지원하는 XmlPullParserFactory는 SAX방식이다.
SAX방식의 장점은 파싱 중 유효한 요소가 식별되면 이벤트로 전달하기 때문에 많은 메모리를 필요하지 않는다.