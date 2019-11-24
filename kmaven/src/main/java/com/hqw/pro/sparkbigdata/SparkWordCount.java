package com.hqw.pro.sparkbigdata;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.util.LongAccumulator;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class SparkWordCount {


    public static void main(String[] args) {
        //创建SparkConf
        SparkConf conf = new SparkConf().setAppName("SparkWordCount").setMaster("local");
        //创建spark程序执行的入口
        JavaSparkContext jsc = new JavaSparkContext(conf);
        //指定以后从哪里读取数据
        JavaRDD<String> lines = jsc.textFile(args[0], 1);
//        lines = jsc.parallelize(new ArrayList<String>());
        final LongAccumulator accCount = jsc.sc().longAccumulator();

        JavaPairRDD<String, String> pairRdd = jsc.parallelizePairs(new ArrayList<Tuple2<String, String>>());
        JavaRDD<String> wds = lines.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterator<String> call(String line) throws Exception {
                final String[] words = line.split(" ");
                final Iterator<String> iter = Arrays.asList(words).iterator();
                return iter;
            }
        });
        //将单词和1组装到一起,如果我们想让处理之后的值为元组，就要使用mapToPair
        //因为java中没有元组这种类型所以就需要我们手动搞一个Pair的类型来装
        //PairFunction中的第一个参数为传入的参数类型，第二，第三为返回类型
        JavaPairRDD<String, Integer> wordAndOne = wds.mapToPair(new PairFunction<String, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(String word) throws Exception {
                return new Tuple2<>(word, 1);
            }
        });
        //聚合
        JavaPairRDD<String, Integer> reduced = wordAndOne.reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1 + v2;
            }
        });
        //java的RDD只支持sortByKey,但是key是单词不是次数
        //调换单词和次数的顺序
        JavaPairRDD<Integer, String> swaped = reduced.mapToPair(new PairFunction<Tuple2<String, Integer>, Integer, String>() {

            @Override
            public Tuple2<Integer, String> call(Tuple2<String, Integer> tuple) throws Exception {
                //return new Tuple2<>(tuple._2,tuple._1);
                return tuple.swap();
            }
        });
        JavaPairRDD<Integer, String> sorted = swaped.sortByKey(false);
        //再调换顺序
        JavaPairRDD<String, Integer> result = sorted.mapToPair(new PairFunction<Tuple2<Integer, String>, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(Tuple2<Integer, String> tp) throws Exception {
                return tp.swap();
            }
        });
        //保存
        result.saveAsTextFile(args[1]);
        //释放资源
        jsc.stop();
    }

}
