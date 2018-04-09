scala 的异常处理方式可以分两种<br>

1 类似java的 try...catch...形式
```
    try {
      println(buyCigarettes(youngCustomer))
    } catch {
      case UnderAgeException(msg) => 
      case e:
    }
```

2 scala 的Try ... match...处理形式.
```
  Try{
    ...
  } match {
    case Success(_) => ...
    case Failure(_) => ...
  }
  ```