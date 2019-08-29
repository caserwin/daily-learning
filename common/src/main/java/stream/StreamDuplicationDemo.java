package stream;

import json.bean.User;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

public class StreamDuplicationDemo {
    public static void main(String[] args){
        ArrayList<User> list = new ArrayList<>();
        list.add(new User(1,"a"));
        list.add(new User(1,"b"));
        list.add(new User( 2,"b"));

        list.stream().filter(distinctByKey(User::getId)).forEach(x -> System.out.println(x.toString()));
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor)
    {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
